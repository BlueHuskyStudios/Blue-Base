@file:Suppress("DEPRECATION")

package org.bh.tools.base.math


import junit.framework.TestCase.assertEquals
import org.bh.tools.base.abstraction.Fraction
import org.junit.Test


/**
 * @author Kyli Rouge
 * *
 * @since 2017-03-20 020.
 */
class AveragerTest {


    @Test
    fun averageOneAtATime() {
        val averager = Averager()
        assertEquals("Expect average of no numbers to be not a number", Fraction.nan, averager.currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1] to be 1", 1.0, averager.average(1.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2] to be 1.5", 1.5, averager.average(2.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2, 10] to be 4.333...", 4.333333, averager.average(10.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2, 10, 2] to be 3.75", 3.75, averager.average(2.0).currentAverage, defaultFractionCalculationTolerance)
    }


    @Test
    fun averageOneAtATimeWithClear() {
        val averager = Averager()
        assertEquals("Expect average of no numbers to be not a number", Fraction.nan, averager.currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1] to be 1", 1.0, averager.average(1.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2] to be 1.5", 1.5, averager.average(2.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2, 10] to be 4.333...", 4.333333, averager.average(10.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [1, 2, 10, 2] to be 3.75", 3.75, averager.average(2.0).currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of no numbers to be not a number", Fraction.nan, averager.clear().currentAverage, defaultFractionCalculationTolerance)
        assertEquals("Expect average of [50] to be 50", 50.0, averager.average(50.0).currentAverage, defaultFractionCalculationTolerance)
    }


    @Test
    fun averageManyAtATime() {
        val averager = Averager()
        assertEquals("Expect average of [1, 2, 10, 2] to be 3.75", 3.75, averager.average(1.0, 2.0, 10.0, 2.0).currentAverage, defaultFractionCalculationTolerance)
    }


    @Test
    fun getTimesAveraged() {
        val averager = Averager()
        assertEquals("Expect average of no numbers to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of [1] to be 1 average", 1, averager.average(1.0).timesAveraged)
        assertEquals("Expect average of [1, 2, 10, 2] to be 4 averages", 4, averager.average(2.0, 10.0, 2.0).timesAveraged)
    }


    @Test
    fun getTimesAveragedWithClear() {
        val averager = Averager()
        assertEquals("Expect average of no numbers to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of [1] to be 1 average", 1, averager.average(1.0).timesAveraged)
        assertEquals("Expect average of [1, 2, 10, 2] to be 4 averages", 4, averager.average(2.0, 10.0, 2.0).timesAveraged)
        assertEquals("Expect average of no numbers to be 0 averages", 0, averager.clear().timesAveraged)
        assertEquals("Expect average of [50] to be 1 average", 1, averager.average(50.0).timesAveraged)
    }


    @Test
    fun clear() {
        val averager = Averager()
        assertEquals("Expect average of no numbers (with no action taken) to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of no numbers (with no action taken) to not a number", Fraction.nan, averager.currentAverage)

        averager.clear()
        assertEquals("Expect average of no numbers (after clearing no numbers) to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of no numbers (after clearing no numbers) to 0 averages", Fraction.nan, averager.currentAverage)

        averager.average(1.0)
        assertEquals("Expect average of [1] (after clearing no numbers) to be 1 average", 1, averager.timesAveraged)
        assertEquals("Expect average of [1] (after clearing no numbers) to be 1", 1.0, averager.currentAverage, defaultFractionCalculationTolerance)

        averager.clear()
        assertEquals("Expect average of no numbers (after clearing 1 number) to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of no numbers (after clearing 1 number) to nan", Fraction.nan, averager.currentAverage, defaultFractionCalculationTolerance)

        averager.average(1.0)
        assertEquals("Expect average of [1] (after clearing 1 number) to be 1 average", 1, averager.timesAveraged)
        assertEquals("Expect average of [1] (after clearing 1 number) to be 1", 1.0, averager.currentAverage, defaultFractionCalculationTolerance)

        averager.average(2.0, 10.0, 2.0)
        assertEquals("Expect average of [1, 2, 10, 2] (after clearing 1 number) to be 4 averages", 4, averager.timesAveraged)
        assertEquals("Expect average of [1, 2, 10, 2] (after clearing 1 number) to be 3.75", 3.75, averager.currentAverage, defaultFractionCalculationTolerance)

        averager.clear()
        assertEquals("Expect average of no numbers (after clearing 4 numbers) to 0 averages", 0, averager.timesAveraged)
        assertEquals("Expect average of no numbers (after clearing 4 numbers) to nan", Fraction.nan, averager.currentAverage, defaultFractionCalculationTolerance)
    }


    @Test
    fun byteValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the byte value of the average of [1, 2, 10, 2] to be 3", 3.toByte(), averager.toByte())
    }


    @Test
    fun shortValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the short value of the average of [1, 2, 10, 2] to be 3", 3.toShort(), averager.toShort())
    }


    @Test
    fun intValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the int value of the average of [1, 2, 10, 2] to be 3", 3, averager.toInt())
    }


    @Test
    fun longValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the long value of the average of [1, 2, 10, 2] to be 3", 3L, averager.toLong())
    }


    @Test
    fun floatValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the float value of the average of [1, 2, 10, 2] to be 3.75", 3.75f, averager.toFloat())
    }


    @Test
    fun doubleValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals("Expect the double value of the average of [1, 2, 10, 2] to be 3.75", 3.75, averager.toDouble())
    }
}