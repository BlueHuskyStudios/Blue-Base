@file:Suppress("unused", "NOTHING_TO_INLINE")

package BlueBase

import kotlin.DeprecationLevel.*

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
 * Converts this number to a [Int8], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
@Throws(ArithmeticException::class)
fun <N: Number> N.toInt8Checked(): Int8 {
    checkBeforeConversionToNativeInteger()
    return this.toInt8()
}


/**
 * Converts this number to a [Int16], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
@Throws(ArithmeticException::class)
fun <N: Number> N.toInt16Checked(): Int16 {
    checkBeforeConversionToNativeInteger()
    return this.toInt16()
}


/**
 * Converts this number to a [Int32], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
@Throws(ArithmeticException::class)
fun <N: Number> N.toInt32Checked(): Int32 {
    checkBeforeConversionToNativeInteger()
    return this.toInt32()
}


/**
 * Converts this number to a [Int64], but first checks if that's a sane move; throws exceptions when this is infinite or NaN
 */
@Throws(UnexpectedNaNException::class)
fun <N: Number> N.toInt64Checked(): Int64 {
    checkBeforeConversionToNativeInteger()
    return this.toInt64()
}


// MARK: toInt8()

inline fun <N: Number> N.toInt8(): Int8 = this.toByte()

@Deprecated("This does nothing", ReplaceWith("this"), level = WARNING)
inline fun Int8.toInt8(): Int8 = this
inline fun Int16.toInt8(): Int8 = this.toByte()
inline fun Int32.toInt8(): Int8 = this.toByte()
inline fun Int64.toInt8(): Int8 = this.toByte()


// MARK: toInt16()

inline fun <N: Number> N.toInt16(): Int16 = this.toShort()

@Deprecated("This does nothing", ReplaceWith("this"), level = WARNING)
inline fun Int16.toInt16(): Int16 = this
inline fun Int8.toInt16(): Int16 = this.toShort()
inline fun Int32.toInt16(): Int16 = this.toShort()
inline fun Int64.toInt16(): Int16 = this.toShort()


// MARK: toInt32()

inline fun <N: Number> N.toInt32(): Int32 = this.toInt()

@Deprecated("This does nothing", ReplaceWith("this"), level = WARNING)
inline fun Int32.toInt32(): Int32 = this
inline fun Int8.toInt32(): Int32 = this.toInt()
inline fun Int16.toInt32(): Int32 = this.toInt()
inline fun Int64.toInt32(): Int32 = this.toInt()


// MARK: toInt64()

inline fun <N: Number> N.toInt64(): Int64 = this.toLong()

@Deprecated("This does nothing", ReplaceWith("this"), level = WARNING)
inline fun Int64.toInt64(): Int64 = this
inline fun Int8.toInt64(): Int64 = this.toLong()
inline fun Int16.toInt64(): Int64 = this.toLong()
inline fun Int32.toInt64(): Int64 = this.toLong()


inline fun <N: Number> N.toInteger(): Integer = this.toInt64()

inline fun <N: Number> N.toFloat32(): Float32 = this.toFloat()
inline fun <N: Number> N.toFloat64(): Float64 = this.toDouble()
inline fun <N: Number> N.toFraction(): Fraction = this.toFloat64()

inline fun <N: Number> N.toIntegerChecked(): Integer = this.toInt64Checked()


@Throws(NumberFormatException::class)
inline fun String.toInt8(radix: Int8 = 10): Int8 = this.toByte(radix = radix.int32Value)
@Throws(NumberFormatException::class)
inline fun String.toInt16(radix: Int8 = 10): Int16 = this.toShort(radix = radix.int32Value)
@Throws(NumberFormatException::class)
inline fun String.toInt32(radix: Int8 = 10): Int32 = this.toInt(radix = radix.int32Value)
@Throws(NumberFormatException::class)
inline fun String.toInt64(radix: Int8 = 10): Int64 = this.toLong(radix = radix.int32Value)
@Throws(NumberFormatException::class)
inline fun String.toInteger(radix: Int8 = 10): Integer = this.toInt64(radix = radix)

@Throws(NumberFormatException::class)
inline fun String.toFloat32(): Float32 = this.toFloat()
@Throws(NumberFormatException::class)
inline fun String.toFloat64(): Float64 = this.toDouble()
@Throws(NumberFormatException::class)
inline fun String.toFraction(): Fraction = this.toFloat64()


@Throws(UnexpectedNaNException::class)
private inline fun <N: Number> N.checkBeforeConversionToNativeInteger() {
    checkNaN()
}


@Throws(UnexpectedNaNException::class)
private fun <N: Number> N.checkNaN() {
    if (this.isNaN) {
        throw UnexpectedNaNException("NaN cannot be converted to an integer")
    }
}


@Throws(UnexpectedInfinityException::class)
private fun <N: Number> N.checkInfinite() {
    if (this.isInfinite) {
        throw UnexpectedInfinityException("Infinity cannot be converted to an integer")
    }
}



typealias UnexpectedNaNException = ArithmeticException
typealias UnexpectedInfinityException = ArithmeticException



// MARK: - Efficiencies
// TODO: Add more



// MARK: - .int8Value

/**
 * The 8-bit integer value of this [Number]. If this is not a number, an exception is thrown.
 */
inline val <N: Number> N.int8Value: Int8 get() = this.toInt8Checked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int8.int8Value: Int8 get() = this

inline val Int16.int8Value: Int8 get() = this.toInt8()
inline val Int32.int8Value: Int8 get() = this.toInt8()
inline val Int64.int8Value: Int8 get() = this.toInt8()



// MARK: - .int16Value

/**
 * The 16-bit integer value of this [Number]
 */
inline val <N: Number> N.int16Value: Int16 get() = this.toInt16Checked()

/**
 * The 16-bit integer value of this [Number]
 */
inline val Float32.int16Value: Int16 get() = this.toInt16Checked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int16.int16Value: Int16 get() = this
inline val Int8.int16Value: Int16 get() = this.toInt16()
inline val Int32.int16Value: Int16 get() = this.toInt16()
inline val Int64.int16Value: Int16 get() = this.toInt16()



// MARK: - .int32Value

/**
 * The 32-bit integer value of this [Number]
 */
inline val <N: Number> N.int32Value: Int32 get() = this.toInt32Checked()

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
inline val <N: Number> N.int64Value: Int64 get() = this.toInt64Checked()

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Int64.int64Value: Int64 get() = this

/**
 * The ideal integer value of this [Number]
 */
inline val <N: Number> N.integerValue: Integer get() = this.int64Value

@Deprecated("This does nothing", ReplaceWith(""), DeprecationLevel.WARNING)
inline val Integer.integerValue: Integer get() = this

fun <N: Number> N.integerValue(rounded: RoundingDirection = RoundingDirection.default): Integer = when {
    this.isNativeInteger -> this.integerValue
    //this.isNativeFraction ->
    else -> this.fractionValue.rounded(rounded).integerValue
}



@Deprecated("It's nonsense to round an Int8 to an Integer", ReplaceWith("integerValue"), DeprecationLevel.HIDDEN)
fun Int8.integerValue(rounded: RoundingDirection): Integer = error("Not implemented on-purpose")
@Deprecated("It's nonsense to round an Int16 to an Integer", ReplaceWith("integerValue"), DeprecationLevel.HIDDEN)
fun Int16.integerValue(rounded: RoundingDirection): Integer = error("Not implemented on-purpose")
@Deprecated("It's nonsense to round an Int32 to an Integer", ReplaceWith("integerValue"), DeprecationLevel.HIDDEN)
fun Int32.integerValue(rounded: RoundingDirection): Integer = error("Not implemented on-purpose")
@Deprecated("It's nonsense to round an Int64 to an Integer", ReplaceWith("integerValue"), DeprecationLevel.HIDDEN)
fun Int64.integerValue(rounded: RoundingDirection): Integer = error("Not implemented on-purpose")



/** Indicates whether this is an integer native to the platform */
inline val <N: Number> N.isNativeInteger: Boolean get() // TODO: Enhance with contracts in Kotlin 1.3+
    = when (this) {
        is Int8, is Int16, is Int32, is Int64 -> true
        else -> false
    }

/** Indicates whether this is a fraction native to the platform */
inline val <N: Number> N.isNativeFraction: Boolean get() // TODO: Enhance with contracts in Kotlin 1.3+
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


/** Returns this integer if it is positive, else `null`. */
inline val Int8.positiveOrNull   : Int8?    get() = if (this < 0)    null else this
/** Returns this integer if it is positive, else `null`. */
inline val Int16.positiveOrNull  : Int16?   get() = if (this < 0)    null else this
/** Returns this integer if it is positive, else `null`. */
inline val Int32.positiveOrNull  : Int32?   get() = if (this < 0)    null else this
/** Returns this integer if it is positive, else `null`. */
inline val Int64.positiveOrNull  : Int64?   get() = if (this < 0)    null else this
/** Returns this fraction if it is positive, else `null`. */
inline val Float32.positiveOrNull: Float32? get() = if (this < 0.0f) null else this
/** Returns this fraction if it is positive, else `null`. */
inline val Float64.positiveOrNull: Float64? get() = if (this < 0.0)  null else this



@Suppress("NOTHING_TO_INLINE", "FunctionName")
@Throws(NumberFormatException::class)
inline fun Integer(from: String, radix: Int8 = 10): Integer = from.toInteger(radix = radix)



// MARK: Collections

inline fun int8ArrayOf(vararg int8: Int8): Int8Array = byteArrayOf(*int8)

inline fun int16ArrayOf(vararg int16: Int16): Int16Array = shortArrayOf(*int16)

inline fun int32ArrayOf(vararg int32: Int32): Int32Array = intArrayOf(*int32)

inline fun int64ArrayOf(vararg int64: Int64): Int64Array = longArrayOf(*int64)

inline fun integerArrayOf(vararg integers: Integer): IntegerArray = int64ArrayOf(*integers)


inline fun float32ArrayOf(vararg float32s: Float32): Float32Array = floatArrayOf(*float32s)

inline fun float64ArrayOf(vararg float64s: Float64): Float64Array = doubleArrayOf(*float64s)

inline fun fractionArrayOf(vararg fractions: Fraction): FractionArray = float64ArrayOf(*fractions)


inline fun Collection<Int8>.toInt8Array(): Int8Array = toByteArray()

inline fun Collection<Int16>.toInt16Array(): Int16Array = toShortArray()

inline fun Collection<Int32>.toInt32Array(): Int32Array = toIntArray()

inline fun Collection<Int64>.toInt64Array(): Int64Array = toLongArray()

inline fun Collection<Integer>.toIntegerArray(): IntegerArray = toInt64Array()


inline fun Collection<Float32>.toFloat32Array(): Float32Array = toFloatArray()

inline fun Collection<Float64>.toFloat64Array(): Float64Array = toDoubleArray()

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
