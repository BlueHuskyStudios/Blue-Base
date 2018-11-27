package org.bh.tools.io.logging

/*
 * Convenient ways to log
 *
 * @author Ben Leggiero
 * @since 2018-03-09
 */



/**
 * Logs a message and then returns the this value
 */
inline fun <ValueType> ValueType.alsoLog(message: String, logger: (String) -> Unit = ::consoleLogString) = also { log(message + "\t$this", logger) }


/**
 * Simply logs the given message using the given logger (defaults to console.log)
 */
inline fun log(message: String, logger: (String) -> Unit = ::consoleLogString) = logger(message)


/**
 * Simply logs the given object using the given logger (defaults to console.log)
 */
inline fun <T> log(`object`: T?, logger: (T?) -> Unit = ::consoleLog) = logger(`object`)


/**
 * An alias for `console.log` that can only log `String`s
 */
@Suppress("NOTHING_TO_INLINE") // Inlined on-purpose because this is an alias for console.log
inline fun consoleLogString(message: String) {
    console.log(message)
}


/**
 * An alias for `console.log` that can log anything
 */
@Suppress("NOTHING_TO_INLINE") // Inlined on-purpose because this is an alias for console.log
inline fun <T> consoleLog(`object`: T?) {
    console.log(`object`)
}