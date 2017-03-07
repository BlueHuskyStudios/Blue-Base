@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*

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
    val numberValue: NumberType
}



/**
 * The 32-bit floating-point value of this [Number]
 */
inline val Number.float32Value: Float32 get() = this.toFloat()

/**
 * The 64-bit floating-point value of this [Number]
 */
inline val Number.float64Value: Float64 get() = this.toDouble()

/**
 * The ideal native floating-point value of this [Number]
 */
inline val Number.fractionValue: Fraction get() = this.float64Value



/**
 * The 8-bit integer value of this [Number]
 */
inline val Number.int8Value: Int8 get() = this.toByte()

/**
 * The 16-bit integer value of this [Number]
 */
inline val Number.int16Value: Int16 get() = this.toShort()

/**
 * The 32-bit integer value of this [Number]
 */
inline val Number.int32Value: Int32 get() = this.toInt()

/**
 * The 64-bit integer value of this [Number]
 */
inline val Number.int64Value: Int64 get() = this.toLong()

/**
 * The ideal integer value of this [Number]
 */
inline val Number.integerValue: Integer get() = this.int64Value




val Number.isNativeInteger: Boolean get()
    = when (this) {
        is Int8, is Int16, is Int32, is Int64 -> true
        else -> false
    }


val Number.isNativeFraction: Boolean get()
    = when (this) {
        is Float32, is Float64 -> true
        else -> false
    }


/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if this
 * number is larger than the largest native int value, the largest native int value is returned. Likewise for the
 * smallest native int value. If it is within the range of valid native ints, its value is returned rounded to an int
 * using the default rounding method.
 */
val Fraction.clampedIntegerValue: Integer get()
    = clamp(low = Integer.min.fractionValue, value = this, high = Integer.max.fractionValue).integerValue

/**
 * This native floating-point number as a native integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 32-bit int value, the largest native 32-bit int value is returned.
 * Likewise for the smallest native 32-bit int value. If it is within the range of valid native 32-bit ints, its value
 * is returned rounded to a 32-bit int using the default rounding method.
 */
val Fraction.clampedInt32Value: Int32 get()
    = clamp(low = Int32.min.fractionValue, value = this, high = Int32.max.fractionValue).int32Value

/**
 * This native ideal-size integer as a native 32-bit integer, clamped to guard against overflow. That is to say, if
 * this number is larger than the largest native 32-bit int value, the largest native 32-bit int value is returned.
 * Likewise for the smallest native 32-bit int value. If it is within the range of valid native 32-bit ints, its value
 * is returned unchanged.
 */
val Integer.clampedInt32Value: Int32 get()
    = clamp(low = Int32.min.integerValue, value = this, high = Int32.max.integerValue).int32Value


val Int8.clampToPositive: Int8 get() = if (this < 0) 0 else this
val Int16.clampToPositive: Int16 get() = if (this < 0) 0 else this
val Int32.clampToPositive: Int32 get() = if (this < 0) 0 else this
val Int64.clampToPositive: Int64 get() = if (this < 0) 0 else this
val Float32.clampToPositive: Float32 get() = if (this < 0.0f) 0.0f else this
val Float64.clampToPositive: Float64 get() = if (this < 0.0) 0.0 else this



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
