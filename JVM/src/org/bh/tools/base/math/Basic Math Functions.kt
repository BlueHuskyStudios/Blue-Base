@file:JvmName("MathBasics")
@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*

/* Basic Math Functions, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * To help you do math!
 *
 * Created by Kyli Rouge on 2016-10-24.
 */

/**
 * Indicates whether this integer is even
 */
inline val Integer.isEven get() = (this % 2L) == 0L

/**
 * Indicates whether this integer is even
 */
inline val Int32.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is even
 */
inline val Int16.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is even
 */
inline val Int8.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is odd
 *
 * @see isEven
 */
inline val Integer.isOdd get() = !this.isEven

/**
 * Indicates whether this integer is odd
 *
 * @see isEven
 */
inline val Int32.isOdd get() = !this.isEven

/**
 * Indicates whether this integer is odd
 *
 * @see isEven
 */
inline val Int16.isOdd get() = !this.isEven

/**
 * Indicates whether this integer is odd
 *
 * @see isEven
 */
inline val Int8.isOdd get() = !this.isEven


/**
 * Finds the exponentiation of this fraction to the power of [exponent]. This attempts to conform to modern mathematics.
 *
 * See also: [_Exponentiation_ on Wikipedia](https://en.wikipedia.org/wiki/Exponentiation)
 */
infix fun Fraction.toThePowerOf(exponent: Fraction): Fraction =
        if (this == 0.0 && exponent <= 0.0) Fraction.nan
        else Math.pow(this, exponent)


/**
 * Finds the exponentiation of this integer to the power of [exponent]. If the result would be higher than
 * [max][Long.MAX_VALUE], then that max is returned. If [exponent] is negative, `0` is returned (unless `this` is `1`).
 */
infix fun Integer.toThePowerOf(exponent: Integer): Integer =
        this.fractionValue.toThePowerOf(exponent.fractionValue).clampedIntegerValue


/**
 * Finds the exponentiation of this integer to the power of [exponent]. If the result would be higher than
 * [max][Int.MAX_VALUE], then that max is returned. If [exponent] is negative, `0` is returned.
 */
infix fun Int32.toThePowerOf(exponent: Int32): Int32 = this.integerValue.toThePowerOf(exponent.integerValue).clampedInt32Value
