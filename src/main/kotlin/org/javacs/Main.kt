package org.javacs

import org.javacs.lsp.impl.KotlinCompiler
import org.javacs.lsp.impl.KotlinLanguageServer
import kotlin.system.exitProcess


fun main() {
    try {
        val launcher = Launcher()
        launcher.run(KotlinLanguageServer(KotlinCompiler()), System.`in`, System.out)
    } catch (e: Exception) {
        exitProcess(-1)
    }
}
