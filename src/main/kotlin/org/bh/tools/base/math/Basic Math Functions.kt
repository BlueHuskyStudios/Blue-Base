@file:Suppress("unused", "NOTHING_TO_INLINE")

package BlueBase

import kotlin.math.*

/* Basic Math Functions, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * To help you do math!
 *
 * Created by Kyli Rouge on 2016-10-24.
 */

/**
 * Indicates whether this integer is even
 */
val Int64.isEven get() = (this % 2L) == 0L

/**
 * Indicates whether this integer is even
 */
val Int32.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is even
 */
val Int16.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is even
 */
val Int8.isEven get() = (this % 2) == 0

/**
 * Indicates whether this integer is odd
 *
 * @see isEven
 */
inline val Int64.isOdd get() = !this.isEven

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
fun pow(base: Fraction, exponent: Fraction): Fraction = base.toThePowerOf(exponent) //pow_wikipedia_exponentiationBySquaring_basicMethod(base, exponent)

// Vitrified 2017-09-16 in case we need a custom pow implementation in the future:
//private fun pow_iForgotWhereIGotThis(base: Fraction, exponent: Fraction) =
//        when {
//            base == 0.0 && exponent <= 0.0 -> /* return */ Fraction.nan
//            exponent == 0.0 -> /* return */ 1.0
//            (exponent % 2.0) == 1.0 -> /* return */ base * pow(base, exponent - 1.0)
//            else -> {
//                val p = pow(base, exponent / 2.0)
//                /* return */ p * p
//            }
//        }
//
//fun pow_wikipedia_exponentiationBySquaring_basicMethod(base: Fraction, exponent: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Fraction =
//        when {
//            exponent.isInfinite -> if (exponent < 0.0) 0.0 else Fraction.infinity
//
//            exponent.isLessThan(0.0, tolerance = tolerance) ->
//                if (base.equals(0.0, tolerance = tolerance)) Fraction.nan
//                else pow_wikipedia_exponentiationBySquaring_basicMethod(base = 1.0 / base, exponent = -exponent, tolerance = tolerance)
//            exponent.equals(0.0, tolerance = tolerance) ->
//                if (base.equals(0.0, tolerance = tolerance)) Fraction.nan
//                else 1.0
//            exponent.equals(1.0, tolerance = tolerance) -> base
//            exponent.integerValue.isEven -> pow_wikipedia_exponentiationBySquaring_basicMethod(base = base * base, exponent = exponent / 2.0, tolerance = tolerance)
//            /* exponent.isOdd, */ else -> base * pow_wikipedia_exponentiationBySquaring_basicMethod(base = base * base, exponent = (exponent - 1.0) / 2.0, tolerance = tolerance)
//        }
//
//fun pow_wikipedia_exponentiationBySquaring_basicMethodIterative(base: Fraction, exponent: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Fraction {
//    var x = base
//    var n = exponent
//    if (n.isLessThan(0.0, tolerance = tolerance)) {
//        x = 1.0 / x
//        n = -n
//    }
//    if (n.equals(0.0, tolerance = tolerance)) {
//        return 1.0
//    }
//    var y = 1.0
//    while (n.isGreaterThan(1.0, tolerance = tolerance)) {
//        if (n.integerValue.isEven) {
//            x = x * x
//            n = n / 2.0
//        }
//        else {
//            y = x * y
//            x = x * x
//            n = (n - 1) / 2.0
//        }
//    }
//    return x * y
//}



/**
 * Finds the exponentiation of this fraction to the power of [exponent]. This attempts to conform to modern mathematics.
 *
 * See also: [_Exponentiation_ on Wikipedia](https://en.wikipedia.org/wiki/Exponentiation)
 */
infix fun Fraction.toThePowerOf(exponent: Fraction): Fraction = when (this) {
    0.0 -> if (exponent <= 0.0) Fraction.nan else 0.0
    else -> pow(exponent)
}


/**
 * Finds the exponentiation of this integer to the power of [exponent]. If the result would be higher than
 * [max][Long.MAX_VALUE], then that max is returned. If [exponent] is negative, `0` is returned (unless `this` is `1`).
 */
infix fun Int64.toThePowerOf(exponent: Int64): Int64 =
        this.fractionValue.toThePowerOf(exponent.fractionValue).clampedInt64Value


/**
 * Finds the exponentiation of this integer to the power of [exponent]. If the result would be higher than
 * [max][Int.MAX_VALUE], then that max is returned. If [exponent] is negative, `0` is returned.
 */
infix fun Int32.toThePowerOf(exponent: Int32): Int32 =
        this.fractionValue.toThePowerOf(exponent.fractionValue).clampedInt32Value


/**
 * Finds the absolute value of the given fraction
 */
fun abs(n: Float64) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given fraction
 */
fun abs(n: Float32) = if (n < 0) -n else n


/**
 * Finds the absolute value of the given integer
 */
fun abs(n: Int64) = if (n < 0) -n else n


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


inline fun Float64.invertedSign() = -this
inline fun Float32.invertedSign() = -this
inline fun Int64.invertedSign() = -this
inline fun Int32.invertedSign() = -this
inline fun Int16.invertedSign() = -this
inline fun Int8.invertedSign() = -this


inline fun inverseSquareRoot(x: Float64): Float64 = inverseSquareRoot_allInOne_accuracy2(x)


@Suppress("NOTHING_TO_INLINE", "FunctionName")
private inline fun _inverseSquareRootUnrefined(x: Float64): Float64 {
    var i = x.toRawBits()
    i = 0x5fe6ec85e7de30daL - (i shr 1)
    return Double.fromBits(i)
}
@Suppress("NOTHING_TO_INLINE", "FunctionName")
private inline fun _inverseSquareRoot_accuracyRefiner(xHalf: Float64, unrefined: Float64): Float64 = unrefined * (1.5 - (xHalf * unrefined * unrefined))
@Suppress("FunctionName")
fun inverseSquareRoot_functional_accuracy1(x: Float64): Float64 =
        _inverseSquareRoot_accuracyRefiner(xHalf = 0.5 * x, unrefined = _inverseSquareRootUnrefined(x))
@Suppress("FunctionName")
fun inverseSquareRoot_functional_accuracy2(x: Float64): Float64 {
    val xHalf = 0.5 * x
    return _inverseSquareRoot_accuracyRefiner(
            xHalf = xHalf,
            unrefined = _inverseSquareRoot_accuracyRefiner(
                    xHalf = xHalf,
                    unrefined = _inverseSquareRootUnrefined(x)
            )
    )
}
@Suppress("FunctionName")
fun inverseSquareRoot_functional_accuracy3(x: Float64): Float64 {
    val xHalf = 0.5 * x
    return _inverseSquareRoot_accuracyRefiner(
            xHalf = xHalf,
            unrefined = _inverseSquareRoot_accuracyRefiner(
                    xHalf = xHalf,
                    unrefined = _inverseSquareRoot_accuracyRefiner(
                            xHalf = xHalf,
                            unrefined = _inverseSquareRootUnrefined(x)
                    )
            )
    )
}
@Suppress("FunctionName")
fun inverseSquareRoot_functional_accuracy4(x: Float64): Float64 {
    val xHalf = 0.5 * x
    return _inverseSquareRoot_accuracyRefiner(
            xHalf = xHalf,
            unrefined = _inverseSquareRoot_accuracyRefiner(
                    xHalf = xHalf,
                    unrefined = _inverseSquareRoot_accuracyRefiner(
                            xHalf = xHalf,
                            unrefined = _inverseSquareRoot_accuracyRefiner(
                                    xHalf = xHalf,
                                    unrefined = _inverseSquareRootUnrefined(x)
                            )
                    )
            )
    )
}

@Suppress("FunctionName")
fun inverseSquareRoot_allInOne_accuracy1(x: Float64): Float64 {
    @Suppress("NAME_SHADOWING")
    var x = x
    val xhalf = 0.5f * x
    var i = x.toRawBits()
    i = 0x5fe6ec85e7de30daL - (i shr 1)
    x = Double.fromBits(i)
    x *= 1.5f - xhalf * x * x
    return x
}

fun inverseSquareRoot_allInOne_accuracy2(x: Float64): Float64 {
    @Suppress("NAME_SHADOWING")
    var x = x
    val xhalf = 0.5f * x
    var i = x.toRawBits()
    i = 0x5fe6ec85e7de30daL - (i shr 1)
    x = Double.fromBits(i)
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    return x
}

fun inverseSquareRoot_allInOne_accuracy3(x: Float64): Float64 {
    @Suppress("NAME_SHADOWING")
    var x = x
    val xhalf = 0.5f * x
    var i = x.toRawBits()
    i = 0x5fe6ec85e7de30daL - (i shr 1)
    x = Double.fromBits(i)
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    return x
}

fun inverseSquareRoot_allInOne_accuracy4(x: Float64): Float64 {
    @Suppress("NAME_SHADOWING")
    var x = x
    val xhalf = 0.5f * x
    var i = x.toRawBits()
    i = 0x5fe6ec85e7de30daL - (i shr 1)
    x = Double.fromBits(i)
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    x *= 1.5f - xhalf * x * x
    return x
}


fun inverseSquareRoot(x: Float32): Float32 {
    @Suppress("NAME_SHADOWING")
    var x = x
    val xhalf = 0.5f * x
    var i = x.toRawBits()
    i = 0x5f3759df - (i shr 1)
    x = Float.fromBits(i)
    x *= 1.5f - xhalf * x * x
    return x
}
