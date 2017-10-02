@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.UnexpectedNaNException

/*
 * NumberConversion, made for BHToolbox, is copyright Blue Husky Software ©2016 BH-1-PS
 *
 * @author Kyli and Ben of Blue Husky Software
 * @since 2016-10-21
 */


/**
 * Implementing classes can be converted to a number
 */
interface NumberConvertible<out NumberType: Number> {
    /** This object as a number */
    val numberValue: NumberType
}



// MARK: - .float32Value

/**
 * The 32-bit floating-point value of this [Number]
 */
inline val <N: Number> N.float32Value: Float32 get() = this.toFloat()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Float32.float32Value: Float32 get() = this



// MARK: - .float64Value

/**
 * The 64-bit floating-point value of this [Number]
 */
inline val <N: Number> N.float64Value: Float64 get() = this.toDouble()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Float64.float64Value: Float64 get() = this



// MARK: - .fractionValue

/**
 * The ideal native floating-point value of this [Number]
 */
inline val <N: Number> N.fractionValue: Fraction get() = this.float64Value

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Fraction.fractionValue: Fraction get() = this



// MARK: - .to___Checked


/**
 * Converts this number to a [Byte], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
//@Throws(ArithmeticException::class)
fun <N: Number> N.toByteChecked(): Byte {
    _checkBeforeConversionToNativeInteger()
    return this.toByte()
}


/**
 * Converts this number to a [Short], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
//@Throws(ArithmeticException::class)
fun <N: Number> N.toShortChecked(): Short {
    _checkBeforeConversionToNativeInteger()
    return this.toShort()
}


/**
 * Converts this number to a [Int], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
//@Throws(ArithmeticException::class)
fun <N: Number> N.toIntChecked(): Int {
    _checkBeforeConversionToNativeInteger()
    return this.toInt()
}


/**
 * Converts this number to a [Long], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
//@Throws(ArithmeticException::class)
fun <N: Number> N.toLongChecked(): Long {
    _checkBeforeConversionToNativeInteger()
    return this.toLong()
}


//@Throws(UnexpectedNaNException::class)
private fun <N: Number> N._checkBeforeConversionToNativeInteger() {
    _checkNaN()
}


//@Throws(UnexpectedNaNException::class)
private fun <N: Number> N._checkNaN() {
    if (this.isNaN) {
        throw org.bh.tools.base.math.UnexpectedNaNException("NaN cannot be converted to an integer")
    }
}


private fun <N: Number> N._checkInfinite() {
    if (this.isInfinite) {
        throw UnexpectedInfinityException("Infinity cannot be converted to an integer")
    }
}



class ArithmeticException(message: String?, cause: Throwable? = null): Throwable(message = message, cause = cause)
typealias UnexpectedNaNException = ArithmeticException
typealias UnexpectedInfinityException = ArithmeticException



// MARK: - .int8Value

/**
 * The 8-bit integer value of this [Number]. If this is not a number, an exception is thrown.
 */
inline val <N: Number> N.int8Value: Int8 get() = this.toByteChecked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int8.int8Value: Int8 get() = this



// MARK: - .int16Value

/**
 * The 16-bit integer value of this [Number]
 */
inline val <N: Number> N.int16Value: Int16 get() = this.toShortChecked()

/**
 * The 16-bit integer value of this [Number]
 */
inline val Float32.int16Value: Int16 get() = this.toShortChecked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int16.int16Value: Int16 get() = this



// MARK: - .int32Value

/**
 * The 32-bit integer value of this [Number]
 */
inline val <N: Number> N.int32Value: Int32 get() = this.toIntChecked()

/**
 * The 32-bit integer value of this [Float32]
 */
inline val Float32.int32Value: Int32 get() = this.toInt()

/**
 * The 32-bit integer value of this [Float64]
 */
inline val Float64.int32Value: Int32 get() = this.toInt()

/**
 * The 32-bit integer value of this [Int8]
 */
inline val Int8.int32Value: Int32 get() = this.toInt()

/**
 * The 32-bit integer value of this [Int16]
 */
inline val Int16.int32Value: Int32 get() = this.toInt()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int32.int32Value: Int32 get() = this

/**
 * The 32-bit integer value of this [Int64]
 */
inline val Int64.int32Value: Int32 get() = this.toInt()



// MARK: - .int64Value

/**
 * The 64-bit integer value of this [Number]
 */
inline val <N: Number> N.int64Value: Int64 get() = this.toLongChecked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int64.int64Value: Int64 get() = this

/**
 * The ideal integer value of this [Number]
 */
inline val <N: Number> N.integerValue: Integer get() = this.int64Value

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Integer.integerValue: Integer get() = this

fun <N: Number> N.integerValue(rounded: RoundingDirection): Integer = when {
    this.isNativeInteger -> this.integerValue
    //this.isNativeFraction ->
    else -> this.fractionValue.rounded(rounded).integerValue
}



/** Indicates whether this is an integer native to the platform */
inline val <N: Number> N.isNativeInteger: Boolean get()
    = when (this) {
        is Int8, is Int16, is Int32, is Int64 -> true
        else -> false
    }

/** Indicates whether this is a fraction native to the platform */
inline val <N: Number> N.isNativeFraction: Boolean get()
    = when (this) {
        is Float32, is Float64 -> true
        else -> false
    }


/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if this
 * number is larger than the largest native int value, the largest native int value is returned. Likewise for the
 * smallest native int value. If it is within the range of valid native ints, its value is returned rounded to an int
 * using the default rounding method.
 *
 * @throws ArithmeticException if this [is not a number][isNaN]
 */
val Fraction.clampedIntegerValue: Integer get() = when {
    isPositiveInfinity -> Integer.max
    isNegativeInfinity -> Integer.min
    else -> integerValue
}

/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 64-bit int value, the largest native 64-bit int value is returned.
 * Likewise for the smallest native 64-bit int value. If it is within the range of valid native 64-bit ints, its value
 * is returned rounded to a 64-bit int using the default rounding method.
 *
 * @throws ArithmeticException if this [is not a number][isNaN]
 */
val Fraction.clampedInt64Value: Int64 get() = when {
    isPositiveInfinity -> Int64.max
    isNegativeInfinity -> Int64.min
    else -> int64Value
}

/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 32-bit int value, the largest native 32-bit int value is returned.
 * Likewise for the smallest native 32-bit int value. If it is within the range of valid native 32-bit ints, its value
 * is returned rounded to a 32-bit int using the default rounding method.
 *
 * @throws ArithmeticException if this [is not a number][isNaN]
 */
val Fraction.clampedInt32Value: Int32 get() = when {
    isPositiveInfinity -> Int32.max
    isNegativeInfinity -> Int32.min
    else -> int32Value
}

/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 16-bit int value, the largest native 16-bit int value is returned.
 * Likewise for the smallest native 16-bit int value. If it is within the range of valid native 16-bit ints, its value
 * is returned rounded to a 16-bit int using the default rounding method.
 *
 * @throws ArithmeticException if this [is not a number][isNaN]
 */
val Fraction.clampedInt162Value: Int16 get() = when {
    isPositiveInfinity -> Int16.max
    isNegativeInfinity -> Int16.min
    else -> int16Value
}

/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 8-bit int value, the largest native 8-bit int value is returned.
 * Likewise for the smallest native 8-bit int value. If it is within the range of valid native 8-bit ints, its value
 * is returned rounded to a 8-bit int using the default rounding method.
 *
 * @throws ArithmeticException if this [is not a number][isNaN]
 */
val Fraction.clampedInt8Value: Int8 get() = when {
    isPositiveInfinity -> Int8.max
    isNegativeInfinity -> Int8.min
    else -> int8Value
}

/**
 * This native ideal-size integer as a native 32-bit integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 32-bit int value, the largest native 32-bit int value is returned.
 * Likewise for the smallest native 32-bit int value. If it is within the range of valid native 32-bit ints, its value
 * is returned unchanged.
 */
val Integer.clampedInt32Value: Int32
    get() = clamp(low = Int32.min.integerValue, value = this, high = Int32.max.integerValue).int32Value

/**
 * This native ideal-size integer as a native 16-bit integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 16-bit int value, the largest native 16-bit int value is returned.
 * Likewise for the smallest native 16-bit int value. If it is within the range of valid native 16-bit ints, its value
 * is returned unchanged.
 */
val Integer.clampedInt16Value: Int16
    get() = clamp(low = Int16.min.integerValue, value = this, high = Int16.max.integerValue).int16Value

/**
 * This native ideal-size integer as a native 8-bit integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 8-bit int value, the largest native 8-bit int value is returned.
 * Likewise for the smallest native 8-bit int value. If it is within the range of valid native 8-bit ints, its value
 * is returned unchanged.
 */
val Integer.clampedInt8Value: Int8
    get() = clamp(low = Int8.min.integerValue, value = this, high = Int8.max.integerValue).int8Value


/** Returns this integer if it is positive, else `0`. */
inline val Int8.clampToPositive   : Int8    get() = if (this < 0)    0    else this
/** Returns this integer if it is positive, else `0`. */
inline val Int16.clampToPositive  : Int16   get() = if (this < 0)    0    else this
/** Returns this integer if it is positive, else `0`. */
inline val Int32.clampToPositive  : Int32   get() = if (this < 0)    0    else this
/** Returns this integer if it is positive, else `0`. */
inline val Int64.clampToPositive  : Int64   get() = if (this < 0)    0    else this
/** Returns this fraction if it is positive, else `0`. */
inline val Float32.clampToPositive: Float32 get() = if (this < 0.0f) 0.0f else this
/** Returns this fraction if it is positive, else `0`. */
inline val Float64.clampToPositive: Float64 get() = if (this < 0.0)  0.0  else this



@Suppress("NOTHING_TO_INLINE")
inline fun Integer(from: String, radix: Int = 10) = from.toInt(radix = radix)



// MARK: Collections

@Suppress("NOTHING_TO_INLINE")
inline fun int8ArrayOf(vararg int8: Int8): Int8Array = byteArrayOf(*int8)

@Suppress("NOTHING_TO_INLINE")
inline fun int16ArrayOf(vararg int16: Int16): Int16Array = shortArrayOf(*int16)

@Suppress("NOTHING_TO_INLINE")
inline fun int32ArrayOf(vararg int32: Int32): Int32Array = intArrayOf(*int32)

@Suppress("NOTHING_TO_INLINE")
inline fun int64ArrayOf(vararg int64: Int64): Int64Array = longArrayOf(*int64)

@Suppress("NOTHING_TO_INLINE")
inline fun integerArrayOf(vararg integers: Integer): IntegerArray = int64ArrayOf(*integers)


@Suppress("NOTHING_TO_INLINE")
inline fun float32ArrayOf(vararg float32s: Float32): Float32Array = floatArrayOf(*float32s)

@Suppress("NOTHING_TO_INLINE")
inline fun float64ArrayOf(vararg float64s: Float64): Float64Array = doubleArrayOf(*float64s)

@Suppress("NOTHING_TO_INLINE")
inline fun fractionArrayOf(vararg fractions: Fraction): FractionArray = float64ArrayOf(*fractions)


@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Int8>.toInt8Array(): Int8Array = toByteArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Int16>.toInt16Array(): Int16Array = toShortArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Int32>.toInt32Array(): Int32Array = toIntArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Int64>.toInt64Array(): Int64Array = toLongArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Integer>.toIntegerArray(): IntegerArray = toInt64Array()


@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Float32>.toFloat32Array(): Float32Array = toFloatArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Float64>.toFloat64Array(): Float64Array = toDoubleArray()

@Suppress("NOTHING_TO_INLINE")
inline fun Collection<Fraction>.toFractionArray(): FractionArray = toFloat64Array()


inline val Int8Array.int8Value: Int8Array get() = this//map { it.int8Value }.toInt8Array()
inline val Int16Array.int8Value: Int8Array get() = map { it.int8Value }.toInt8Array()
inline val Int32Array.int8Value: Int8Array get() = map { it.int8Value }.toInt8Array()
inline val Int64Array.int8Value: Int8Array get() = map { it.int8Value }.toInt8Array()


inline val Int8Array.int16Value: Int16Array get() = map { it.int16Value }.toInt16Array()
inline val Int16Array.int16Value: Int16Array get() = this//map { it.int16Value }.toInt16Array()
inline val Int32Array.int16Value: Int16Array get() = map { it.int16Value }.toInt16Array()
inline val Int64Array.int16Value: Int16Array get() = map { it.int16Value }.toInt16Array()


inline val Int8Array.int32Value: Int32Array get() = map { it.int32Value }.toInt32Array()
inline val Int16Array.int32Value: Int32Array get() = map { it.int32Value }.toInt32Array()
inline val Int32Array.int32Value: Int32Array get() = this//map { it.int32Value }.toInt32Array()
inline val Int64Array.int32Value: Int32Array get() = map { it.int32Value }.toInt32Array()


inline val Int8Array.int64Value: Int64Array get() = map { it.int64Value }.toInt64Array()
inline val Int16Array.int64Value: Int64Array get() = map { it.int64Value }.toInt64Array()
inline val Int32Array.int64Value: Int64Array get() = map { it.int64Value }.toInt64Array()
inline val Int64Array.int64Value: Int64Array get() = this//map { it.int64Value }.toInt64Array()


inline val Int8Array.integerValue: IntegerArray get() = int64Value
inline val Int16Array.integerValue: IntegerArray get() = int64Value
inline val Int32Array.integerValue: IntegerArray get() = int64Value
inline val Int64Array.integerValue: IntegerArray get() = int64Value


inline val Float32Array.float32Value: Float32Array get() = this//map { it.fractionValue }.toFractionArray()
inline val Float64Array.float32Value: Float32Array get() = map { it.float32Value }.toFloat32Array()


inline val Float32Array.float64Value: Float64Array get() = map { it.float64Value }.toFloat64Array()
inline val Float64Array.float64Value: Float64Array get() = this//map { it.float64Value }.toFloat64Array()


inline val Float32Array.fractionValue: FractionArray get() = float64Value
inline val Float64Array.fractionValue: FractionArray get() = float64Value
