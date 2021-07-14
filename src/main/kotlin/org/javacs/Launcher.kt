package org.javacs

import org.javacs.debug.KLogging
import org.javacs.lsp.Message
import org.javacs.lsp.impl.KotlinLanguageClient
import org.javacs.lsp.impl.KotlinLanguageServer
import java.io.InputStream
import java.io.OutputStream

class Launcher {
    fun run(server: KotlinLanguageServer, receive: InputStream, read: OutputStream) {
        KLogging.info("run the server")
        server.apply { this.client = KotlinLanguageClient(read) }
        while (true) {
            val token = nextToken(receive)
            val message = parseToken(token)


        }
    }

    private fun parseToken(token: String): Message {
        TODO()
    }

    private fun nextToken(client: InputStream): String {
        val line = readHeader(client)
        KLogging.info(line)
        return ""
    }

    private fun readHeader(client: InputStream): String {
        val line = StringBuffer()
        var next = read(client)
        while (next != '\r') {
            line.append(next)
            next = read(client)
        }
        return line.toString()
    }

    private fun read(client: InputStream): Char {
        val c = client.read()
        if (c == -1) {
            println("client closed")
        }
        return c.toChar()
    }
}
