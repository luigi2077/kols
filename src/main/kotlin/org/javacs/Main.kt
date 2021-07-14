package org.javacs

import org.javacs.debug.KLogging
import org.javacs.lsp.impl.KotlinCompiler
import org.javacs.lsp.impl.KotlinLanguageServer
import kotlin.system.exitProcess


fun main() {
    try {
        val launcher = Launcher()
        val host = "localhost"
        val port = 5005
        KLogging.info("main start...")
//        val (read, write) = Socket(host, port).let { Pair(it.getInputStream(), it.getOutputStream()) }
        launcher.run(KotlinLanguageServer(KotlinCompiler()), System.`in`, System.out)
    } catch (e: Exception) {
        exitProcess(-1)
    }
}
