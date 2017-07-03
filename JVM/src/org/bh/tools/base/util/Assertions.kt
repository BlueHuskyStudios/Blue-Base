package org.bh.tools.base.util

/*
 * Basic convenience wrappers around assertions
 *
 * @author Ben Leggiero
 * @since 2017-06-12
 */


private class __AssertionsKt_Dummy


///**
// * Indicates whether this app was built in debug mode. The default value is `false`, and it is never set by Blue Base;
// * it is up to you to set this appropriately.
// */
/**
 * Determines whether this is the build where assertions are enabled. This is never set by Blue Base;
 * it is up to you to set this appropriately.
 */
var isDebugBuild: Boolean
    get() {
        return __AssertionsKt_Dummy::class.java.desiredAssertionStatus()
    }
    set(newValue) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(newValue)
        ClassLoader.getSystemClassLoader().setClassAssertionStatus(__AssertionsKt_Dummy::class.java.name, newValue)
    }


///**
// * If the given condition is false, an exception is immediately thrown with the given message, but only in debug builds.
// *
// * **Note:** To specify whether this is a debug build, set the global variable `isDebugBuild`.
// */
//fun assert(condition: Boolean, message: String = "") {
//    if (condition) {
//        println("Assertion Failure! $message")
//
//        if (isDebugBuild) {
//            throw AssertionError(message)
//        }
//    }
//}


@Suppress("NOTHING_TO_INLINE")
inline fun assert(value: Boolean, message: String) = kotlin.assert(value = value) { message }


/**
 * Immediately fails an assertion, using the given message
 *
 * @param message _optional_ - A message to log with the assertion failure. Defaults to empty string.
 */
inline fun assertionFailure(message: () -> Any) = kotlin.assert(value = false, lazyMessage = message)


/**
 * Immediately fails an assertion, using the given message
 *
 * @param message _optional_ - A message to log with the assertion failure. Defaults to empty string.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun assertionFailure(message: String = "") = assert(value = false, message = message)
