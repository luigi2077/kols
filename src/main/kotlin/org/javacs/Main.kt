package org.javacs

import org.javacs.factory.LanguageServerFactory

fun main() {
    val server = LanguageServerFactory.createKotlin()
}
