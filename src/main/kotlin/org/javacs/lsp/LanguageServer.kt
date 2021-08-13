package org.javacs.lsp

import org.javacs.lsp.params.InitializeParam
import org.javacs.lsp.result.InitializeResult

interface LanguageServer {
    fun initialize(param: InitializeParam): InitializeResult
    fun initialized(param: InitializeParam)
    fun shutdown()
    fun exit()
}
