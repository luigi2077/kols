package org.javacs.lsp

import org.javacs.lsp.params.InitializeParam

interface LanguageServer {
    fun initialize(param: InitializeParam)
    fun initialized(param: InitializeParam)
    fun shutdown()
    fun exit()
}
