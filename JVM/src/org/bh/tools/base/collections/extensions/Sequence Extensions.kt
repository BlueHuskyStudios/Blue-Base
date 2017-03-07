package org.bh.tools.base.collections.extensions

/*
 * @author Ben Leggiero
 * @since 2017-03-06
 */


@Suppress("NOTHING_TO_INLINE")
inline fun <ElementType, IterableType: Sequence<ElementType>> IterableType.nonEmpty(): IterableType? = if (count() > 0) this else null
inline val <ElementType, IterableType: Sequence<ElementType>> IterableType.nonEmpty get() = nonEmpty()
