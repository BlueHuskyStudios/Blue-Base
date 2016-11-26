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
inline fun <T> Iterable<T>.safeFirst(predicate: (T) -> Boolean): T? = firstOrNull(predicate)


/**
 * @return the first element, or `null` if there are no elements.
 */
inline val <T> Iterable<T>.safeFirst: T? get() = firstOrNull()


/**
 * @return the first element, or `null` if there are no elements.
 */
inline val <T> Iterable<T>.firstOrNull: T? get() = firstOrNull()
