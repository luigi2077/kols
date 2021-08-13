package org.javacs.debug

import java.util.logging.Logger

object KLogging {
    private val logger: Logger = Logger.getLogger("KLogging")

    fun info(info: String) {
        this.logger.info(info)
    }
}
