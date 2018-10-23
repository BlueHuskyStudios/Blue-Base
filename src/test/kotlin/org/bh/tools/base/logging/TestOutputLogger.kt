package org.bh.tools.base.logging

/**
 * @author Ben
 * @since 2018-05-02
 */
object TestOutputLogger: Logger {
    override var lowestAllowedLevel: LoggerLevel = LoggerLevel.trace

    private val backend = org.junit.platform.commons.logging.LoggerFactory.getLogger(TestOutputLogger::class.java)

    init {
        Loggers.register(this)
    }

    override fun log(level: LoggerLevel, message: String) {
        log(level) { message }
    }

    override fun log(level: LoggerLevel, messageGenerator: () -> String) {
        if (level >= lowestAllowedLevel) {
            when (level) {
                LoggerLevel.trace -> backend.trace(messageGenerator)
                LoggerLevel.debug -> backend.debug(messageGenerator)
                LoggerLevel.informational -> backend.info(messageGenerator)
                LoggerLevel.warning -> backend.warn(messageGenerator)
                LoggerLevel.error -> backend.error(messageGenerator)
                LoggerLevel.fatal -> backend.error(messageGenerator)
            }
        }
    }

}