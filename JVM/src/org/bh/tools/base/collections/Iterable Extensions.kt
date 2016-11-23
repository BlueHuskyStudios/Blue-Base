package org.bh.tools.base.collections

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */



/**
 * @return the first element matching the given [predicate], or `null` if no such element is found.
 */
inline fun <T> Iterable<T>.safeFirst(predicate: (T) -> Boolean): T? {
    try {
        return first(predicate)
    } catch (_: Throwable) {
        return null
    }
}


/**
 * @return the first element, or `null` if there are no elements.
 */
inline val <T> Iterable<T>.safeFirst: T? get() {
    try {
        return first()
    } catch (_: Throwable) {
        return null
    }
}
