@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer

/**
 * Averager, made for BHToolbox, is made by and copyrighted to Blue Husky Programming, ©2017 [BH-0-PD](https://github.com/BlueHuskyStudios/Licenses/blob/master/Licenses/BH-0-PD.txt).
 *
 * <hr/>
 *
 * Averages very many numbers while using only 128 bits of memory (one floating-point and one integer), to store the
 * average information. This also allows for a more accurate result than adding all and dividing by the number of
 * inputs. The downside is that you sacrifice speed, but rigorous testing of this speed loss has not yet been performed.
 *
 * @license BH-0-PD to Blue Husky Programming, ©2017
 * @author Kyli Rouge and Ben Leggiero of Blue Husky Studios
 * @since 2017-01-08
 * @version 2.0.0
 */
class Averager
    /**
     * Creates a new Averager. The current average is set to the given number and number of times averaged is set to 1
     *
     * @param startingNumber the number to start with.
     */
    (startingNumber: Fraction? = null)
    : Number() {

    private var _currentAverage: Fraction = startingNumber ?: 0.0

    /**
     * Returns the number of times this has been averaged
     *
     * @return the number of times this has been averaged, as an integer
     */
    var timesAveraged: Integer = if (startingNumber != null) 1 else 0
        private set

    /**
     * Returns the current average. If no number have yet been averaged, this is [nan]
     *
     * @return the current average, as a floating-point number
     */
    val currentAverageOrNaN get() = if (timesAveraged > 0) _currentAverage else Fraction.nan

    /**
     * Returns the current average. If no number have yet been averaged, this is `null`
     *
     * @return the current average, as a floating-point number, or `null`
     */
    val currentAverageOrNull get() = if (timesAveraged > 0) _currentAverage else null

    /**
     * Alias for [currentAverageOrNaN]
     */
    inline val currentAverage get() = currentAverageOrNaN


    /**
     * Adds the given numbers to the average. Any number of arguments can be given.
     *
     * @param newValues one or more numbers to average.
     *
     * @return `this`, so calls can be chained. For example: `averager.average(arrayOfNumbers).average(123, 654);`
     */
    fun average(vararg newValues: Fraction): Averager {
        newValues.forEach { average(it) }
        return this
    }


    /**
     * Adds the given numbers to the average. Any number of arguments can be given.
     *
     * @param newValues one or more numbers to average.
     *
     * @return `this`, so calls can be chained. For example: `averager.average(arrayOfNumbers).average(123, 654);`
     */
    fun average(newValues: List<Fraction>): Averager {
        newValues.forEach { average(it) }
        return this
    }


    /**
     * Adds the given number to the average.
     *
     * @param newValue one number to average.
     *
     * @return `this`, so calls can be chained. For example: `averager.average(myNumber).average(123);`
     */
//    @Strictfp
    fun average(newValue: Fraction): Averager {
        _currentAverage = (_currentAverage * timesAveraged + newValue) / ++timesAveraged
        return this
    }


    /**
     * Clears the average, so that [timesAveraged] is `0` and [currentAverage] is [`nan`][Double.NaN]
     *
     * @return `this`, so calls can be chained. For example: `averager.clear().average(123, 654);`
     */
    fun clear(): Averager {
        _currentAverage = 0.0
        timesAveraged = 0
        return this
    }



    // MARK: - Number conformance

    /**
     * @return the value of the current average, as an 8-bit integer.
     *
     * @see toDouble
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toByte(): Byte = toDouble().toByte()


    /**
     * @return the value of the current average, as a 16-bit integer.
     *
     * @see toDouble
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toShort(): Short = toDouble().toShort()


    /**
     * @return the value of the current average, as a 16-bit character.
     *
     * @see toDouble
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toChar(): Char = toDouble().toChar()


    /**
     * @return the value of the current average, as a 32-bit integer.
     *
     * @see toDouble
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toInt(): Int = toDouble().toInt()


    /**
     * @return the value of the current average, as a 64-bit integer.
     *
     * @see toDouble
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toLong(): Long = toDouble().toLong()


    /**
     * @return the value of the current average, as a 32-bit floating-point number.
     *
     * @see toDouble
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toFloat(): Float = toDouble().toFloat()


    /**
     * @return the value of the current average, as a 64-bit floating-point number.
     */
    override fun toDouble(): Double = currentAverage
}


fun List<Fraction>.efficientAverage(): Fraction {
    return Averager().average(this).currentAverage
}
