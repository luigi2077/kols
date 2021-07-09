package org.javacs

import org.javacs.lsp.impl.KotlinLanguageClient
import org.javacs.lsp.impl.KotlinLanguageServer
import java.io.InputStream
import java.io.OutputStream

class Launcher {
    fun run(server: KotlinLanguageServer, receive: InputStream, read: OutputStream) {
        server.apply { this.client = KotlinLanguageClient(read) }
        while (true) {
            print(",")
        }
    }
}
