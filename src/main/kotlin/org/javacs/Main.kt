package org.javacs

import kotlinx.coroutines.runBlocking
import org.javacs.debug.KLogging
import org.javacs.lsp.impl.KotlinCompiler
import org.javacs.lsp.impl.KotlinLanguageServer
import java.net.ServerSocket
import kotlin.system.exitProcess

fun main() {
    try {
        val launcher = LSPLauncher()
        val host = "localhost"
        val port = 5005
        val (read, write) = ServerSocket(port)
            .also { KLogging.info("main start...") }
            .accept()
            .let { Pair(it.getInputStream(), it.getOutputStream()) }
        runBlocking {
            KLogging.info("run--")
            launcher.run(KotlinLanguageServer(KotlinCompiler()), read, write)
        }
    } catch (e: Exception) {
        KLogging.info("catch exception: ${e.stackTraceToString()}")
        exitProcess(-1)
    }
}
