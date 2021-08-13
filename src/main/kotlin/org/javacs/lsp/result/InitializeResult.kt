package org.javacs.lsp.result

import com.google.gson.JsonObject

data class InitializeResult(
    val capabilities: JsonObject,
)
