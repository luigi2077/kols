package org.javacs.lsp

import java.io.OutputStream

interface LanguageClient {

    fun read(): OutputStream
}
