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
fun pow(base: Fraction, exponent: Fraction): Fraction =
        if (base == 0.0 && exponent <= 0.0)
            /* return */ Fraction.nan
        else if (exponent == 0.0)
            /* return */ 1.0
        else if ((exponent % 2) == 1.0)
            /* return */ base * pow(base, exponent - 1)
        else {
            val p = pow(base, exponent / 2)
            /* return */ p * p
        }



/**
 * Finds the exponentiation of this fraction to the power of [exponent]. This attempts to conform to modern mathematics.
 *
 * See also: [_Exponentiation_ on Wikipedia](https://en.wikipedia.org/wiki/Exponentiation)
 */
infix fun Fraction.toThePowerOf(exponent: Fraction): Fraction = pow(this, exponent)


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


/**
 * Finds the absolute value of the given fraction
 */
fun abs(n: Fraction) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given fraction
 */
fun abs(n: Float32) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given integer
 */
fun abs(n: Integer) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given integer
 */
fun abs(n: Int32) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given integer
 */
fun abs(n: Int16): Int16 = if (n < 0) (-n).int16Value else n


/**
 * Finds the absolute value of the given integer
 */
fun abs(n: Int8): Int8 = if (n < 0) (-n).int8Value else n


/**
 * Finds the absolute value of this fraction
 */
inline val Fraction.absoluteValue get() = abs(this)


/**
 * Finds the absolute value of this fraction
 */
inline val Float32.absoluteValue get() = abs(this)


/**
 * Finds the absolute value of this integer
 */
inline val Integer.absoluteValue get() = abs(this)


/**
 * Finds the absolute value of this integer
 */
inline val Int32.absoluteValue get() = abs(this)


/**
 * Finds the absolute value of this integer
 */
inline val Int16.absoluteValue get() = abs(this)


/**
 * Finds the absolute value of this integer
 */
inline val Int8.absoluteValue get() = abs(this)
