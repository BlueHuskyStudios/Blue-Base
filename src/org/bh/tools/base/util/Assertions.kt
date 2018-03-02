package org.bh.tools.base.util

import kotlin.AssertionError

/*
 * Basic convenience wrappers around assertions
 *
 * @author Ben Leggiero
 * @since 2017-06-12
 */


/**
 * Indicates whether this app was built in debug mode. The default value is `false`, and it is never set by Blue Base;
 * it is up to you to set this appropriately.
 */
var isDebugBuild = false


/**
 * If the given condition is false, an exception is immediately thrown with the given message, but only in debug builds.
 *
 * **Note:** To specify whether this is a debug build, set the global variable `isDebugBuild`.
 */
fun assert(condition: Boolean, message: String = "") {
    if (isDebugBuild && condition) {
        throw AssertionError(message)
    }
}


/**
 * Immediately fails an assertion, using the given message
 *
 * @param message _optional_ - A message to log with the assertion failure. Defaults to empty string.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun assertionFailure(message: String = "") = assert(condition = false, message = message)
