package org.javacs

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.javacs.debug.KLogging
import org.javacs.lsp.Message
import org.javacs.lsp.impl.KotlinLanguageClient
import org.javacs.lsp.impl.KotlinLanguageServer
import org.javacs.lsp.params.InitializeParam
import java.io.InputStream
import java.io.OutputStream
import kotlin.system.exitProcess

class LSPLauncher {
    private var currentLength = 0
    private val gson = Gson()
    private val polls = Channel<Message>(10)
    suspend fun run(server: KotlinLanguageServer, receive: InputStream, read: OutputStream) {
        server.apply { this.client = KotlinLanguageClient(read) }
        withContext(Dispatchers.IO) {
            receive.bufferedReader()
                .lineSequence()
                .asFlow()
                .flowOn(Dispatchers.IO)
                .filter { it.trim().isNotBlank() }
                .collect { handleBuffersLineByLine(it) }
        }
        withContext(Dispatchers.Default) {
            KLogging.info("start another coroutine scope")
            consumeMessage(server)
        }
    }

    private suspend fun handleBuffersLineByLine(lineString: String) {
        when (val size = considerIfContentLength(lineString)) {
            -1 -> buildAsMessage(lineString)
            else -> currentLength = size
        }
    }

    private suspend fun buildAsMessage(lineString: String) {
        val message = gson.fromJson(lineString.substring(0, currentLength), Message::class.java)
        val tails = lineString.substring(currentLength, lineString.length)
        currentLength = considerIfContentLength(tails)
        polls.send(message)
    }

    private suspend fun consumeMessage(server: KotlinLanguageServer) {
        for (message in polls) {
            when (message.method) {
                "initialize" -> {
                    KLogging.info("-----------------------------------")
                    val param = gson.fromJson(message.params, InitializeParam::class.java)
                    KLogging.info(message.toString())
                    val result = gson.toJson(server.initialize(param))
                    val messageText = "{\"jsonrpc\":\"2.0\",\"id\":${message.id},\"result\":$result}\""
                    KLogging.info(messageText)
                    sendToClient(server.client.read(), messageText)
                    KLogging.info("initialize end------------------")
                }
                "shutdown" -> {
                    KLogging.info("im exit....")
                    exitProcess(0)
                }
                else -> {
                    KLogging.info(message.method)
                }
            }
        }
    }

    private suspend fun sendToClient(client: OutputStream, messageText: String) {
        val messageBytes = messageText.toByteArray()
        val headerBytes = "Content Length: ${messageBytes.size}\r\n\r\n".toByteArray()
        withContext(Dispatchers.IO) {
            client.use {
                it.write(headerBytes)
                it.write(messageBytes)
            }
        }
    }

    private fun considerIfContentLength(line: String): Int = "Content-Length: ".let {
        if (line.startsWith(it)) line.substring(it.length).toInt() else -1
    }
}
