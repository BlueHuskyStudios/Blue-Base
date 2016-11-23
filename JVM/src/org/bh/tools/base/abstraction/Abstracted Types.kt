@file:Suppress("unused")

package org.bh.tools.base.abstraction


/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * For when you wanna use the best stuff for this particular processor
 *
 * @author Kyli Rouge
 * @since 2016-10-31
 */

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
 * The ideal type of integer
 */
typealias BHInt = Int64

/**
 * The floating-point value of this [Int8]
 */
val Int8.floatValue: BHFloat get() = this.toDouble()

/**
 * The floating-point value of this [Int16]
 */
val Int16.floatValue: BHFloat get() = this.toDouble()

/**
 * The floating-point value of this [Int32]
 */
val Int32.floatValue: BHFloat get() = this.toDouble()

/**
 * The floating-point value of this [Int64]
 */
val Int64.floatValue: BHFloat get() = this.toDouble()


/**
 * The ideal integer value of this [Int8]
 */
val Int8.integerValue: BHInt get() = this.toLong()

/**
 * The ideal integer value of this [Int16]
 */
val Int16.integerValue: BHInt get() = this.toLong()

/**
 * The ideal integert value of this [Int32]
 */
val Int32.integerValue: BHInt get() = this.toLong()

/**
 * The ideal integer value of this [Int64]
 */
val Int64.integerValue: BHInt get() = this.toLong()



/**
 * A fractional number represented as a 32-bit IEEE floating-point decimal
 */
typealias Float32 = Float

/**
 * A fractional number represented as a 64-bit IEEE floating-point decimal
 */
typealias Float64 = Double

/**
 * The ideal type of fractional number
 */
typealias BHFloat = Float64

/**
 * The integer value of this [Float32]
 */
val Float32.integerValue: BHInt get() = this.toLong()

/**
 * The integer value of this [Float64]
 */
val Float64.integerValue: BHInt get() = this.toLong()

/**
 * The integer value of this [Float32]
 */
val Float32.floatValue: BHFloat get() = this.toDouble()

/**
 * The integer value of this [Float64]
 */
val Float64.floatValue: BHFloat get() = this.toDouble()
