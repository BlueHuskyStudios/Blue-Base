@file:Suppress("unused")

package org.bh.tools.base.collections.extensions

/*
 * @author Ben Leggiero
 * @since 2017-03-06
 */


/** Returns this iterable if it is not empty, else returns `null` */
@Suppress("NOTHING_TO_INLINE")
inline fun <ElementType, IterableType: Sequence<ElementType>> IterableType.nonEmpty(): IterableType? = if (count() > 0) this else null

/** Returns this iterable if it is not empty, else returns `null` */
inline val <ElementType, IterableType: Sequence<ElementType>> IterableType.nonEmpty get() = nonEmpty()
