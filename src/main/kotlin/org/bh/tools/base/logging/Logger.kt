@file:Suppress("NOTHING_TO_INLINE", "unused")

package BlueBase

/**
 * @author Ben
 * @since 2018-05-02
 */
interface Logger {
    var lowestAllowedLevel: LoggerLevel

    fun log(level: LoggerLevel, message: String)
    fun log(level: LoggerLevel, messageGenerator: () -> String)
}



inline fun Logger.trace(noinline messageGenerator: () -> String) = log(level = LoggerLevel.trace, messageGenerator = messageGenerator)
inline fun Logger.verbose(noinline messageGenerator: () -> String) = log(level = LoggerLevel.verbose, messageGenerator = messageGenerator)
inline fun Logger.debug(noinline messageGenerator: () -> String) = log(level = LoggerLevel.debug, messageGenerator = messageGenerator)
inline fun Logger.informational(noinline messageGenerator: () -> String) = log(level = LoggerLevel.informational, messageGenerator = messageGenerator)
inline fun Logger.info(noinline messageGenerator: () -> String) = log(level = LoggerLevel.info, messageGenerator = messageGenerator)
inline fun Logger.warning(noinline messageGenerator: () -> String) = log(level = LoggerLevel.warning, messageGenerator = messageGenerator)
inline fun Logger.warn(noinline messageGenerator: () -> String) = log(level = LoggerLevel.warn, messageGenerator = messageGenerator)
inline fun Logger.error(noinline messageGenerator: () -> String) = log(level = LoggerLevel.error, messageGenerator = messageGenerator)
inline fun Logger.fatal(noinline messageGenerator: () -> String) = log(level = LoggerLevel.fatal, messageGenerator = messageGenerator)



enum class LoggerLevel {
    trace,
    debug,
    informational,
    warning,
    error,
    fatal,
    ;

    companion object {
        val verbose get() = trace
        val info get() = informational
        val warn get() = warning
    }
}



object Loggers {
    private val allLoggers = mutableSetOf<Logger>()

    fun register(newLogger: Logger) {
        allLoggers.insert(newLogger)
    }
}
