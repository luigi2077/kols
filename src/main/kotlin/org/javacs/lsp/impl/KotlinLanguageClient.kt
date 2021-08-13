package org.javacs.lsp.impl

import org.javacs.lsp.LanguageClient
import java.io.OutputStream

class KotlinLanguageClient(
    private val send: OutputStream,
) : LanguageClient {
    override fun read(): OutputStream = this.send
}
