@file:Suppress("unused")

package BlueBase


/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * For when you wanna use the best stuff for this particular processor
 *
 * @author Kyli Rouge
 * @since 2016-10-31
 */

// MARK: - Native Numbers

/**
 * An integer represented as an 8-bit signed two's-compliment number
 */
typealias Int8 = Byte

/**
 * An integer represented as a 16-bit signed two's-compliment number
 */
typealias Int16 = Short

/**
 * An integer represented as a 32-bit signed two's-compliment number
 */
typealias Int32 = Int

/**
 * An integer represented as a 64-bit signed two's-compliment number
 */
typealias Int64 = Long

/**
 * The ideal type of integer. This may vary by platform.
 * **BH Standards dictate you use this instead of an `Int` or `Long` when possible.**
 */
typealias Integer = Int64



/**
 * A fractional number represented as a 32-bit IEEE floating-point decimal
 */
typealias Float32 = Float

/**
 * A fractional number represented as a 64-bit IEEE floating-point decimal
 */
typealias Float64 = Double

/**
 * The ideal type of fractional number. This may vary by platform.
 * **BH Standards dictate you use this instead of a `Double` or `Float` when possible.**
 */
typealias Fraction = Float64



// MARK: - Collections

typealias Int8Array = ByteArray
typealias Int16Array = ShortArray
typealias Int32Array = IntArray
typealias Int64Array = LongArray
typealias IntegerArray = Int64Array


typealias Float32Array = FloatArray
typealias Float64Array = DoubleArray
typealias FractionArray = Float64Array
