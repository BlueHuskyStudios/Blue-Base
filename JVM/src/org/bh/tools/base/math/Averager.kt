@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer

/**
 * Averager, made for BHToolbox, is made by and copyrighted to Blue Husky Programming, ©2017 BH-0-PD.
 * https://github.com/BlueHuskyStudios/Licenses/blob/master/Licenses/BH-0-PD.txt
 * <hr/>
 * Averages very many numbers while using only 128 bits of memory (one floating-point and one integer), to store the average
 * information. This also allows for a more accurate result than adding all and dividing by the number of inputs. The downside
 * is that you sacrifice speed, but rigorous testing of this speed loss has not yet been performed.
 *
 * @license BH-0-PD to Blue Husky Programming, ©2017
 * @author Kyli Rouge of Blue Husky Studios
 * @since 2017-01-08
 * @version 1.1.0
 */
class Averager : Number {
    /** Holds the current average. If startingNumber is specified, it is used. Else, this is 0  */
    private var currentAverage: Fraction = 0.0

    /**
     * Remembers the number of times we've averaged this, to ensure proportional division. If startingNumber is specified, this
     * is 1. Else, it is 0.
     */
    private var timesAveraged: Integer = 0

    /**
     * Creates a new Averager. Of course, the current average and number of times averaged are both set to 0
     */
    constructor() {
        currentAverage = 0.0
        timesAveraged = 0
    }

    /**
     * Creates a new Averager. The current average is set to the given number and number of times averaged is set to 1
     *
     * @param startingNumber the number to start with.
     */
    constructor(startingNumber: Fraction) {
        currentAverage = startingNumber
        timesAveraged = 1
    }

    /**
     * Adds the given numbers to the average. Any number of arguments can be given.
     *
     * @param newValues one or more numbers to average.
     *
     * @return a copy of this, so calls can be chained. For example: `averager.average(arrayOfNumbers).average(123, 654);`
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Strictfp fun average(vararg newValues: Fraction): Averager {
        newValues.forEach { average(it) }
        return this
    }

    /**
     * Adds the given number to the average.
     *
     * @param newValue one number to average.
     *
     * @return a copy of this, so calls can be chained. For example: `averager.average(myNumber).average(123);`
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Strictfp fun average(newValue: Fraction): Averager {
        currentAverage = (currentAverage * timesAveraged + newValue) / ++timesAveraged
        return this
    }

    /**
     * Returns the current average
     *
     * @return the current average, as a floating-point number
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    val current get() = currentAverage

    /**
     * Returns the number of times this has been averaged
     *
     * @return the number of times this has been averaged, as an integer
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    val count get() = timesAveraged

    /**
     * Returns the number of times this has been averaged
     *
     * @return the number of times this has been averaged, as an integer
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    fun clear(): Averager {
        currentAverage = 0.0
        timesAveraged = 0
        return this
    }



    // MARK: - Number conformance

    /**
     * @return the value of the current average, as an 8-bit integer.
     *
     * @see toDouble
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toByte(): Byte = toDouble().toByte()

    /**
     * @return the value of the current average, as a 16-bit integer.
     *
     * @see toDouble
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toShort(): Short = toDouble().toShort()

    /**
     * @return the value of the current average, as a 16-bit character.
     *
     * @see toDouble
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toChar(): Char = toDouble().toChar()

    /**
     * @return the value of the current average, as a 32-bit integer.
     *
     * @see toDouble
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
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
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    @Deprecated("This might be inaccurate. Use {@link #doubleValue()} instead.", ReplaceWith("toDouble()"))
    override fun toFloat(): Float = toDouble().toFloat()

    /**
     * @return the value of the current average, as a 64-bit floating-point number.
     *
     * @author Kyli Rouge
     * @since 2017-01-08
     * @version 1.0.0
     */
    override fun toDouble(): Double = current
}