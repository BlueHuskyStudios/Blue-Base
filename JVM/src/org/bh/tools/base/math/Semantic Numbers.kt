@file:Suppress("unused")

package org.bh.tools.base.math

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Blue Base for JVM.
 *
 * For when the standard library's terminology isn't good enough.
 *
 * @author Ben Leggiero
 * @since 2016-10-30
 */


/**
 * The smallest possible value of an 8-bit signed integer
 */
inline val Byte.Companion.min: Byte get() = Byte.MIN_VALUE

/**
 * The largest possible value of an 8-bit signed integer
 */
inline val Byte.Companion.max: Byte get() = Byte.MAX_VALUE


/**
 * The smallest possible value of a 16-bit signed integer
 */
inline val Short.Companion.min: Short get() = Short.MIN_VALUE

/**
 * The largest possible value of a 16-bit signed integer
 */
inline val Short.Companion.max: Short get() = Short.MAX_VALUE


/**
 * The smallest possible value of a 32-bit signed integer
 */
inline val Int.Companion.min: Int get() = Int.MIN_VALUE

/**
 * The largest possible value of a 32-bit signed integer
 */
inline val Int.Companion.max: Int get() = Int.MAX_VALUE


/**
 * The smallest possible value of a 64-bit signed integer
 */
inline val Long.Companion.min: Long get() = Long.MIN_VALUE

/**
 * The largest possible value of a 64-bit signed integer
 */
inline val Long.Companion.max: Long get() = Long.MAX_VALUE


/**
 * The smallest possible positive non-zero value of a 32-bit signed float
 */
inline val Float.Companion.leastNonzeroMagnitude: Float get() = Float.MIN_VALUE

/**
 * The largest possible positive non-infinite value of a 32-bit signed float
 */
inline val Float.Companion.greatestFiniteMagnitude: Float get() = Float.MAX_VALUE


/**
 * The smallest possible positive non-zero value of a 64-bit signed float
 */
inline val Double.Companion.leastNonzeroMagnitude: Double get() = Double.MIN_VALUE

/**
 * The largest possible positive non-infinite value of a 64-bit signed float
 */
inline val Double.Companion.greatestFiniteMagnitude: Double get() = Double.MAX_VALUE
