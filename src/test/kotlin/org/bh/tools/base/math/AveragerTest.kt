@file:Suppress("DEPRECATION")

package org.bh.tools.base.math


import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.util.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


/**
 * @author Kyli Rouge
 * *
 * @since 2017-03-20 020.
 */
class AveragerTest {


    @Test
    fun averageOneAtATime() {
        val averager = Averager()
        assertEquals(expected = Fraction.nan, actual = averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of no numbers to be not a number")
        assertEquals(1.0, averager.average(1.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1] to be 1")
        assertEquals(1.5, averager.average(2.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2] to be 1.5")
        assertEquals(4.333333, averager.average(10.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2, 10] to be 4.333...")
        assertEquals(3.75, averager.average(2.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2, 10, 2] to be 3.75")
    }


    @Test
    fun averageOneAtATimeWithClear() {
        val averager = Averager()
        assertEquals(Fraction.nan, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of no numbers to be not a number")
        assertEquals(1.0, averager.average(1.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1] to be 1")
        assertEquals(1.5, averager.average(2.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2] to be 1.5")
        assertEquals(4.333333, averager.average(10.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2, 10] to be 4.333...")
        assertEquals(3.75, averager.average(2.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2, 10, 2] to be 3.75")
        assertEquals(Fraction.nan, averager.clear().currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of no numbers to be not a number")
        assertEquals(50.0, averager.average(50.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [50] to be 50")
    }


    @Test
    fun averageManyAtATime() {
        val averager = Averager()
        assertEquals(3.75, averager.average(1.0, 2.0, 10.0, 2.0).currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of [1, 2, 10, 2] to be 3.75")
    }


    @Test
    fun getTimesAveraged() {
        val averager = Averager()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers to 0 averages")
        assertEquals(1, averager.average(1.0).timesAveraged, "Expect average of [1] to be 1 average")
        assertEquals(4, averager.average(2.0, 10.0, 2.0).timesAveraged, "Expect average of [1, 2, 10, 2] to be 4 averages")
    }


    @Test
    fun getTimesAveragedWithClear() {
        val averager = Averager()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers to 0 averages")
        assertEquals(1, averager.average(1.0).timesAveraged, "Expect average of [1] to be 1 average")
        assertEquals(4, averager.average(2.0, 10.0, 2.0).timesAveraged, "Expect average of [1, 2, 10, 2] to be 4 averages")
        assertEquals(0, averager.clear().timesAveraged, "Expect average of no numbers to be 0 averages")
        assertEquals(1, averager.average(50.0).timesAveraged, "Expect average of [50] to be 1 average")
    }


    @Test
    fun clear() {
        val averager = Averager()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers (with no action taken) to 0 averages")
        assertEquals(Fraction.nan, averager.currentAverage, "Expect average of no numbers (with no action taken) to not a number")

        averager.clear()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers (after clearing no numbers) to 0 averages")
        assertEquals(Fraction.nan, averager.currentAverage, "Expect average of no numbers (after clearing no numbers) to 0 averages")

        averager.average(1.0)
        assertEquals(1, averager.timesAveraged, "Expect average of [1] (after clearing no numbers) to be 1 average")
        assertEquals(1.0, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message =  "Expect average of [1] (after clearing no numbers) to be 1")

        averager.clear()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers (after clearing 0, then 1 number) to 0 averages")
        assertEquals(Fraction.nan, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message =  "Expect average of no numbers (after clearing 1 number) to nan")

        averager.average(1.0)
        assertEquals(1, averager.timesAveraged, "Expect average of [1] (after clearing 1 number) to be 1 average")
        assertEquals(1.0, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message =  "Expect average of [1] (after clearing 1 number) to be 1")

        averager.average(2.0, 10.0, 2.0)
        assertEquals(4, averager.timesAveraged, "Expect average of [1, 2, 10, 2] (after clearing 0, then 1 number) to be 4 averages")
        assertEquals(3.75, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message =  "Expect average of [1, 2, 10, 2] (after clearing 1 number) to be 3.75")

        averager.clear()
        assertEquals(0, averager.timesAveraged, "Expect average of no numbers (after clearing 0, 1, then 4 numbers) to 0 averages")
        assertEquals(Fraction.nan, averager.currentAverage, tolerance = defaultFractionCalculationTolerance, message = "Expect average of no numbers (after clearing 4 numbers) to nan")
    }


    @Test
    fun byteValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3.toByte(), averager.toByte(), "Expect the byte value of the average of [1, 2, 10, 2] to be 3")
    }


    @Test
    fun shortValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3.toShort(), averager.toShort(), "Expect the short value of the average of [1, 2, 10, 2] to be 3")
    }


    @Test
    fun intValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3, averager.toInt(), "Expect the int value of the average of [1, 2, 10, 2] to be 3")
    }


    @Test
    fun longValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3L, averager.toLong(), "Expect the long value of the average of [1, 2, 10, 2] to be 3")
    }


    @Test
    fun floatValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3.75f, averager.toFloat(), "Expect the float value of the average of [1, 2, 10, 2] to be 3.75")
    }


    @Test
    fun doubleValue() {
        val averager = Averager().average(1.0, 2.0, 10.0, 2.0)
        assertEquals(3.75, averager.toDouble(), "Expect the double value of the average of [1, 2, 10, 2] to be 3.75")
    }
}