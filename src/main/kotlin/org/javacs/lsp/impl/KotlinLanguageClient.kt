package org.javacs.lsp.impl

import org.javacs.lsp.LanguageClient
import java.io.OutputStream

class KotlinLanguageClient(
    val send: OutputStream
) : LanguageClient {
}
