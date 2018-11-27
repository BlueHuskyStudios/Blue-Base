package org.bh.tools.base.util

/*
 * @author Ben Leggiero
 * @since 2018-03-09
 */


/**
 * If this can be turned into a `Boolean`, then it is. Otherwise, `null` is returned.
 */
fun Any.toBooleanOrNull(): Boolean? = when(this) {
    is Boolean -> this
    is String -> if (isEmpty()) null else when (toLowerCase()) {
        "true", "t", "yes", "y", "1", "on" -> true
        "false", "f", "no", "n", "0", "off" -> false
        else -> null
    }
    is Int -> when (this) {
        0 -> false
        1 -> true
        else -> null
    }
    else -> null
}