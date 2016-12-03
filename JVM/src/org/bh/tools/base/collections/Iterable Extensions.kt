package org.bh.tools.base.collections

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */





/**
 * @return the first element, or `null` if there are no elements.
 */
inline val <T> Iterable<T>.firstOrNull: T? get() = firstOrNull()
