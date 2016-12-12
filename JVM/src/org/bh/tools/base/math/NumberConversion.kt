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
 * The ideal native floating-point value of this [Number]
 */
val Number.floatValue: BHFloat get() = this.float64Value

/**
 * The 32-bit floating-point value of this [Number]
 */
val Number.float32Value: Float32 get() = this.toFloat()

/**
 * The 64-bit floating-point value of this [Number]
 */
val Number.float64Value: Float64 get() = this.toDouble()


/**
 * The ideal integer value of this [Number]
 */
val Number.integerValue: BHInt get() = this.int64Value

/**
 * The 8-bit integer value of this [Number]
 */
val Number.int8Value: Int8 get() = this.toByte()

/**
 * The 16-bit integer value of this [Number]
 */
val Number.int16Value: Int16 get() = this.toShort()

/**
 * The 32-bit integer value of this [Number]
 */
val Number.int32Value: Int32 get() = this.toInt()

/**
 * The 64-bit integer value of this [Number]
 */
val Number.int64Value: Int64 get() = this.toLong()

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
val BHFloat.clampedIntegerValue: BHInt get()
    = clamp(low = BHInt.min.floatValue, value = this, high = BHInt.max.floatValue).integerValue
