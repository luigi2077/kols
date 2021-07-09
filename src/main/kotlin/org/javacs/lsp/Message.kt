package org.javacs.lsp

import com.google.gson.JsonElement

data class Message(
    val id: Int,
    val jsonrpc: String,
    val method: String,
    val params: JsonElement,
)
