package org.javacs.debug

import java.util.logging.FileHandler
import java.util.logging.Logger

object KLogging {
    private val logger: Logger = Logger.getLogger("debug")

    init {
        val fh = FileHandler("/tmp/kols.log")
        logger.addHandler(fh)
    }

    fun info(info: String) {
        this.logger.info(info)
    }
}
