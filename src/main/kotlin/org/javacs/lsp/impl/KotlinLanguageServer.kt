package org.javacs.lsp.impl

import com.google.gson.JsonObject
import org.javacs.lsp.LanguageClient
import org.javacs.lsp.LanguageServer
import org.javacs.lsp.params.InitializeParam
import org.javacs.lsp.result.InitializeResult
import java.nio.file.Path

class KotlinLanguageServer(
    private val compiler: KotlinCompiler,
) : LanguageServer {
    private lateinit var workspace: Path
    lateinit var client: LanguageClient
    override fun initialize(param: InitializeParam): InitializeResult {
        return InitializeResult(JsonObject())
    }

    override fun initialized(param: InitializeParam) {
        TODO("Not yet implemented")
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun exit() {
        TODO("Not yet implemented")
    }
}
