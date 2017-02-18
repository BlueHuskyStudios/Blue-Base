package org.bh.tools.base.struct

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.struct.OpenRange.Companion.openEnd
import org.bh.tools.base.util.TimeTrialMeasurementMode.total
import org.bh.tools.base.util.measureTimeInterval
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


private val intersectionTrialCount: Integer = 1_000_000


/**
 * @author Kyli Rouge
 * @since 2017-02-08
 */
class OpenRangeTest {

    val rangeEmpty = FractionOpenRange.empty()
    val rangeOnly5 = FractionOpenRange(5.0)
    val rangeOnly10 = FractionOpenRange(10.0)
    val range5To17 = FractionOpenRange(5.0, 17.0)
    val range10To23 = FractionOpenRange(10.0, 23.0)
    val range17ToOpen = FractionOpenRange(17.0, openEnd)
    val rangeOpenTo23 = FractionOpenRange(openEnd, 23.0)
    val rangeOpenTo5 = FractionOpenRange(openEnd, 5.0)
    val range10ToOpen = FractionOpenRange(10.0, openEnd)
    val rangeOpenToOpen = FractionOpenRange(openEnd, openEnd)

    @Test
    fun isOpen() {
        assertFalse("An empty range must not be open", rangeEmpty.isOpen)
        assertFalse("A range of only 5 must not be open", rangeOnly5.isOpen)
        assertFalse("A range of only 10 must not be open", rangeOnly10.isOpen)
        assertFalse("A range of 5 to 17 must not be open", range5To17.isOpen)
        assertFalse("A range of 10 to 23 must not be open", range10To23.isOpen)
        assertTrue("A range of 17 to Open must be open", range17ToOpen.isOpen)
        assertTrue("A range of Open to 23 must be open", rangeOpenTo23.isOpen)
        assertTrue("A range of Open to 5 must be open", rangeOpenTo5.isOpen)
        assertTrue("A range of 10 to Open must be open", range10ToOpen.isOpen)
        assertTrue("A range of Open to Open must be open", rangeOpenToOpen.isOpen)
    }

    @Test
    fun contains() {

        // MARK: Single value:

        assertFalse("A range of only 5 must not contain Not-a-Number", rangeOnly5.contains(Fraction.NaN))
        assertFalse("A range of only 5 must not contain -∞", rangeOnly5.contains(Fraction.NEGATIVE_INFINITY))
        assertFalse("A range of only 5 must not contain ∞", rangeOnly5.contains(Fraction.POSITIVE_INFINITY))

        assertFalse("A range of only 5 must not contain 4", rangeOnly5.contains(4.0))
        assertFalse("A range of only 5 must not contain 4.99", rangeOnly5.contains(4.99))
        assertTrue("A range of only 5 must contain 5", rangeOnly5.contains(5.0))
        assertFalse("A range of only 5 must not contain 5.01", rangeOnly5.contains(5.01))
        assertFalse("A range of only 5 must not contain 6", rangeOnly5.contains(6.0))


        // MARK: Closed range:

        assertFalse("A range of 5 to 17 must not contain Not-a-Number", range5To17.contains(Fraction.NaN))
        assertFalse("A range of 5 to 17 must not contain -∞", range5To17.contains(Fraction.NEGATIVE_INFINITY))
        assertFalse("A range of 5 to 17 must not contain ∞", range5To17.contains(Fraction.POSITIVE_INFINITY))

        assertFalse("A range of 5 to 17 must not contain 4", range5To17.contains(4.0))
        assertFalse("A range of 5 to 17 must not contain 4.99", range5To17.contains(4.99))
        assertTrue("A range of 5 to 17 must contain 5", range5To17.contains(5.0))
        assertTrue("A range of 5 to 17 must contain 5.01", range5To17.contains(5.01))
        assertTrue("A range of 5 to 17 must contain 6", range5To17.contains(6.0))

        assertTrue("A range of 5 to 17 must contain 16", range5To17.contains(16.0))
        assertTrue("A range of 5 to 17 must contain 16.99", range5To17.contains(16.99))
        assertTrue("A range of 5 to 17 must contain 17", range5To17.contains(17.0))
        assertFalse("A range of 5 to 17 must not contain 17.01", range5To17.contains(17.01))
        assertFalse("A range of 5 to 17 must not contain 18", range5To17.contains(18.0))


        // MARK: Left-open Range:

        assertFalse("A range of 17 to Open must not contain Not-a-Number", range17ToOpen.contains(Fraction.NaN))
        assertFalse("A range of 17 to Open must not contain -∞", range17ToOpen.contains(Fraction.NEGATIVE_INFINITY))
        assertTrue("A range of 17 to Open must contain ∞", range17ToOpen.contains(Fraction.POSITIVE_INFINITY))

        assertFalse("A range of 17 to Open must not contain 5", range17ToOpen.contains(5.0))
        assertFalse("A range of 17 to Open must not contain 5.01", range17ToOpen.contains(5.01))
        assertFalse("A range of 17 to Open must not contain 6", range17ToOpen.contains(6.0))

        assertFalse("A range of 17 to Open must not contain 16", range17ToOpen.contains(16.0))
        assertFalse("A range of 17 to Open must not contain 16.99", range17ToOpen.contains(16.99))
        assertTrue("A range of 17 to Open must contain 17", range17ToOpen.contains(17.0))
        assertTrue("A range of 17 to Open must contain 17.01", range17ToOpen.contains(17.01))
        assertTrue("A range of 17 to Open must contain 18", range17ToOpen.contains(18.0))


        // MARK: Right-open Range:

        assertFalse("A range of Open to 5 must not contain Not-a-Number", rangeOpenTo5.contains(Fraction.NaN))
        assertTrue("A range of Open to 5 must contain -∞", rangeOpenTo5.contains(Fraction.NEGATIVE_INFINITY))
        assertFalse("A range of Open to 5 must not contain ∞", rangeOpenTo5.contains(Fraction.POSITIVE_INFINITY))

        assertTrue("A range of Open to 5 must contain 4", rangeOpenTo5.contains(4.0))
        assertTrue("A range of Open to 5 must contain 4.99", rangeOpenTo5.contains(4.99))
        assertTrue("A range of Open to 5 must contain 5", rangeOpenTo5.contains(5.0))
        assertFalse("A range of Open to 5 must not contain 5.01", rangeOpenTo5.contains(5.01))
        assertFalse("A range of Open to 5 must not contain 6", rangeOpenTo5.contains(6.0))

        assertFalse("A range of Open to 5 must not contain 16", rangeOpenTo5.contains(16.0))
        assertFalse("A range of Open to 5 must not contain 16.99", rangeOpenTo5.contains(16.99))
        assertFalse("A range of Open to 5 must not contain 17", rangeOpenTo5.contains(17.0))
        assertFalse("A range of Open to 5 must not contain 17.01", rangeOpenTo5.contains(17.01))
        assertFalse("A range of Open to 5 must not contain 18", rangeOpenTo5.contains(18.0))


        // MARK: Fully-open Range:

        assertFalse("A range of Open to Open must not contain Not-a-Number", rangeOpenToOpen.contains(Fraction.NaN))
        assertTrue("A range of Open to Open must contain -∞", rangeOpenToOpen.contains(Fraction.NEGATIVE_INFINITY))
        assertTrue("A range of Open to Open must contain ∞", rangeOpenToOpen.contains(Fraction.POSITIVE_INFINITY))

        assertTrue("A range of Open to Open must contain 4", rangeOpenToOpen.contains(4.0))
        assertTrue("A range of Open to Open must contain 4.99", rangeOpenToOpen.contains(4.99))
        assertTrue("A range of Open to Open must contain 5", rangeOpenToOpen.contains(5.0))
        assertTrue("A range of Open to Open must contain 5.01", rangeOpenToOpen.contains(5.01))
        assertTrue("A range of Open to Open must contain 6", rangeOpenToOpen.contains(6.0))

        assertTrue("A range of Open to Open must contain 16", rangeOpenToOpen.contains(16.0))
        assertTrue("A range of Open to Open must contain 16.99", rangeOpenToOpen.contains(16.99))
        assertTrue("A range of Open to Open must contain 17", rangeOpenToOpen.contains(17.0))
        assertTrue("A range of Open to Open must contain 17.01", rangeOpenToOpen.contains(17.01))
        assertTrue("A range of Open to Open must contain 18", rangeOpenToOpen.contains(18.0))
    }

    @Test
    fun intersects() {

        // MARK: Single value A:

        assertTrue("A range of only 5 must intersect one of only 5", rangeOnly5.intersects(rangeOnly5))
        assertFalse("A range of only 5 must not intersect one of only 10", rangeOnly5.intersects(rangeOnly10))
        assertTrue("A range of only 5 must intersect one of 5 to 17", rangeOnly5.intersects(range5To17))
        assertFalse("A range of only 5 must not intersect one of 10 to 23", rangeOnly5.intersects(range10To23))
        assertFalse("A range of only 5 must not intersect one of 17 to Open", rangeOnly5.intersects(range17ToOpen))
        assertTrue("A range of only 5 must intersect one of Open to 23", rangeOnly5.intersects(rangeOpenTo23))
        assertTrue("A range of only 5 must intersect one of Open to 5", rangeOnly5.intersects(rangeOpenTo5))
        assertFalse("A range of only 5 must not intersect one of 10 to Open", rangeOnly5.intersects(range10ToOpen))
        assertTrue("A range of only 5 must intersect one of Open to Open", rangeOnly5.intersects(rangeOpenToOpen))


        // MARK: Single value B:

        assertFalse("A range of only 10 must not intersect one of only 5", rangeOnly10.intersects(rangeOnly5))
        assertTrue("A range of only 10 must intersect one of only 10", rangeOnly10.intersects(rangeOnly10))
        assertTrue("A range of only 10 must intersect one of 5 to 17", rangeOnly10.intersects(range5To17))
        assertTrue("A range of only 10 must intersect one of 10 to 23", rangeOnly10.intersects(range10To23))
        assertFalse("A range of only 10 must not intersect one of 17 to Open", rangeOnly10.intersects(range17ToOpen))
        assertTrue("A range of only 10 must intersect one of Open to 23", rangeOnly10.intersects(rangeOpenTo23))
        assertFalse("A range of only 10 must not intersect one of Open to 5", rangeOnly10.intersects(rangeOpenTo5))
        assertTrue("A range of only 10 must intersect one of 10 to Open", rangeOnly10.intersects(range10ToOpen))
        assertTrue("A range of only 10 must intersect one of Open to Open", rangeOnly10.intersects(rangeOpenToOpen))


        // MARK: Closed range A:

        assertTrue("A range of 5 to 17 must intersect one of only 5", range5To17.intersects(rangeOnly5))
        assertTrue("A range of 5 to 17 must intersect one of only 10", range5To17.intersects(rangeOnly10))
        assertTrue("A range of 5 to 17 must intersect one of 5 to 17", range5To17.intersects(range5To17))
        assertTrue("A range of 5 to 17 must intersect one of 10 to 23", range5To17.intersects(range10To23))
        assertTrue("A range of 5 to 17 must intersect one of 17 to Open", range5To17.intersects(range17ToOpen))
        assertTrue("A range of 5 to 17 must intersect one of Open to 23", range5To17.intersects(rangeOpenTo23))
        assertTrue("A range of 5 to 17 must intersect one of Open to 5", range5To17.intersects(rangeOpenTo5))
        assertTrue("A range of 5 to 17 must intersect one of 10 to Open", range5To17.intersects(range10ToOpen))
        assertTrue("A range of 5 to 17 must intersect one of Open to Open", range5To17.intersects(rangeOpenToOpen))


        // MARK: Closed range B:

        assertFalse("A range of 10 to 23 must not intersect one of only 5", range10To23.intersects(rangeOnly5))
        assertTrue("A range of 10 to 23 must intersect one of only 10", range10To23.intersects(rangeOnly10))
        assertTrue("A range of 10 to 23 must intersect one of 5 to 17", range10To23.intersects(range5To17))
        assertTrue("A range of 10 to 23 must intersect one of 10 to 23", range10To23.intersects(range10To23))
        assertTrue("A range of 10 to 23 must intersect one of 17 to Open", range10To23.intersects(range17ToOpen))
        assertTrue("A range of 10 to 23 must intersect one of Open to 23", range10To23.intersects(rangeOpenTo23))
        assertFalse("A range of 10 to 23 must not intersect one of Open to 5", range10To23.intersects(rangeOpenTo5))
        assertTrue("A range of 10 to 23 must intersect one of 10 to Open", range10To23.intersects(range10ToOpen))
        assertTrue("A range of 10 to 23 must intersect one of Open to Open", range10To23.intersects(rangeOpenToOpen))


        // MARK: Right-open range A:

        assertFalse("A range of 17 to Open must not intersect one of only 5", range17ToOpen.intersects(rangeOnly5))
        assertFalse("A range of 17 to Open must not intersect one of only 10", range17ToOpen.intersects(rangeOnly10))
        assertTrue("A range of 17 to Open must intersect one of 5 to 17", range17ToOpen.intersects(range5To17))
        assertTrue("A range of 17 to Open must intersect one of 10 to 23", range17ToOpen.intersects(range10To23))
        assertTrue("A range of 17 to Open must intersect one of 17 to Open", range17ToOpen.intersects(range17ToOpen))
        assertTrue("A range of 17 to Open must intersect one of Open to 23", range17ToOpen.intersects(rangeOpenTo23))
        assertFalse("A range of 17 to Open must not intersect one of Open to 5", range17ToOpen.intersects(rangeOpenTo5))
        assertTrue("A range of 17 to Open must intersect one of 10 to Open", range17ToOpen.intersects(range10ToOpen))
        assertTrue("A range of 17 to Open must intersect one of Open to Open", range17ToOpen.intersects(rangeOpenToOpen))


        // MARK: Left-open range B:

        assertTrue("A range of Open to 23 must intersect one of only 5", rangeOpenTo23.intersects(rangeOnly5))
        assertTrue("A range of Open to 23 must intersect one of only 10", rangeOpenTo23.intersects(rangeOnly10))
        assertTrue("A range of Open to 23 must intersect one of 5 to 17", rangeOpenTo23.intersects(range5To17))
        assertTrue("A range of Open to 23 must intersect one of 10 to 23", rangeOpenTo23.intersects(range10To23))
        assertTrue("A range of Open to 23 must intersect one of 17 to Open", rangeOpenTo23.intersects(range17ToOpen))
        assertTrue("A range of Open to 23 must intersect one of Open to 23", rangeOpenTo23.intersects(rangeOpenTo23))
        assertTrue("A range of Open to 23 must intersect one of Open to 5", rangeOpenTo23.intersects(rangeOpenTo5))
        assertTrue("A range of Open to 23 must intersect one of 10 to Open", rangeOpenTo23.intersects(range10ToOpen))
        assertTrue("A range of Open to 23 must intersect one of Open to Open", rangeOpenTo23.intersects(rangeOpenToOpen))


        // MARK: Left-open range A:

        assertTrue("A range of Open to 5 must intersect one of only 5", rangeOpenTo5.intersects(rangeOnly5))
        assertFalse("A range of Open to 5 must not intersect one of only 10", rangeOpenTo5.intersects(rangeOnly10))
        assertTrue("A range of Open to 5 must intersect one of 5 to 17", rangeOpenTo5.intersects(range5To17))
        assertFalse("A range of Open to 5 must not intersect one of 10 to 23", rangeOpenTo5.intersects(range10To23))
        assertFalse("A range of Open to 5 must not intersect one of 17 to Open", rangeOpenTo5.intersects(range17ToOpen))
        assertTrue("A range of Open to 5 must intersect one of Open to 23", rangeOpenTo5.intersects(rangeOpenTo23))
        assertTrue("A range of Open to 5 must intersect one of Open to 5", rangeOpenTo5.intersects(rangeOpenTo5))
        assertFalse("A range of Open to 5 must not intersect one of 10 to Open", rangeOpenTo5.intersects(range10ToOpen))
        assertTrue("A range of Open to 5 must intersect one of Open to Open", rangeOpenTo5.intersects(rangeOpenToOpen))


        // MARK: Right-open range B:

        assertFalse("A range of 10 to Open must not intersect one of only 5", range10ToOpen.intersects(rangeOnly5))
        assertTrue("A range of 10 to Open must intersect one of only 10", range10ToOpen.intersects(rangeOnly10))
        assertTrue("A range of 10 to Open must intersect one of 5 to 17", range10ToOpen.intersects(range5To17))
        assertTrue("A range of 10 to Open must intersect one of 10 to 23", range10ToOpen.intersects(range10To23))
        assertTrue("A range of 10 to Open must intersect one of 17 to Open", range10ToOpen.intersects(range17ToOpen))
        assertTrue("A range of 10 to Open must intersect one of Open to 23", range10ToOpen.intersects(rangeOpenTo23))
        assertFalse("A range of 10 to Open must not intersect one of Open to 5", range10ToOpen.intersects(rangeOpenTo5))
        assertTrue("A range of 10 to Open must intersect one of 10 to Open", range10ToOpen.intersects(range10ToOpen))
        assertTrue("A range of 10 to Open must intersect one of Open to Open", range10ToOpen.intersects(rangeOpenToOpen))


        // MARK: Infinite range:

        assertTrue("A range of Open to Open must intersect one of only 5", rangeOpenToOpen.intersects(rangeOnly5))
        assertTrue("A range of Open to Open must intersect one of only 10", rangeOpenToOpen.intersects(rangeOnly10))
        assertTrue("A range of Open to Open must intersect one of 5 to 17", rangeOpenToOpen.intersects(range5To17))
        assertTrue("A range of Open to Open must intersect one of 10 to 23", rangeOpenToOpen.intersects(range10To23))
        assertTrue("A range of Open to Open must intersect one of 17 to Open", rangeOpenToOpen.intersects(range17ToOpen))
        assertTrue("A range of Open to Open must intersect one of Open to 23", rangeOpenToOpen.intersects(rangeOpenTo23))
        assertTrue("A range of Open to Open must intersect one of Open to 5", rangeOpenToOpen.intersects(rangeOpenTo5))
        assertTrue("A range of Open to Open must intersect one of 10 to Open", rangeOpenToOpen.intersects(range10ToOpen))
        assertTrue("A range of Open to Open must intersect one of Open to Open", rangeOpenToOpen.intersects(rangeOpenToOpen))
    }

    @Test
    fun union() {

    }

    @Test
    fun intersection() {

        val range10To17 by lazy { FractionOpenRange(startInclusive = 10.0, endInclusive = 17.0) }
        val rangeOnly17 by lazy { FractionOpenRange(onlyValue = 17.0) }
        val range17To23 by lazy { FractionOpenRange(startInclusive = 17.0, endInclusive = 23.0) }


        // MARK: Empty:

        assertTrue("A range of nothing must not intersect one of nothing", rangeEmpty.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of nothing must not intersect one of only 5 at 5 only", rangeEmpty.intersection(rangeOnly5).isEmpty)
        assertTrue("A range of nothing must not not intersect one of only 10", rangeEmpty.intersection(rangeOnly10).isEmpty)
        assertTrue("A range of nothing must not intersect one of 5 to 17 at 5 only", rangeEmpty.intersection(range5To17).isEmpty)
        assertTrue("A range of nothing must not not intersect one of 10 to 23", rangeEmpty.intersection(range10To23).isEmpty)
        assertTrue("A range of nothing must not not intersect one of 17 to Open", rangeEmpty.intersection(range17ToOpen).isEmpty)
        assertTrue("A range of nothing must not intersect one of Open to 23 at 5 only", rangeEmpty.intersection(rangeOpenTo23).isEmpty)
        assertTrue("A range of nothing must not intersect one of Open to 5 at 5 only", rangeEmpty.intersection(rangeOpenTo5).isEmpty)
        assertTrue("A range of nothing must not not intersect one of 10 to Open", rangeEmpty.intersection(range10ToOpen).isEmpty)
        assertTrue("A range of nothing must not intersect one of Open to Open at 5 only", rangeEmpty.intersection(rangeOpenToOpen).isEmpty)


        // MARK: Single value A:

        assertTrue("A range of only 5 must not intersect one of only empty", rangeOnly5.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of only 5 must intersect one of only 5 at 5 only", rangeOnly5.intersection(rangeOnly5).equals(rangeOnly5))
        assertTrue("A range of only 5 must not intersect one of only 10", rangeOnly5.intersection(rangeOnly10).isEmpty)
        assertTrue("A range of only 5 must intersect one of 5 to 17 at 5 only", rangeOnly5.intersection(range5To17).equals(rangeOnly5))
        assertTrue("A range of only 5 must not intersect one of 10 to 23", rangeOnly5.intersection(range10To23).isEmpty)
        assertTrue("A range of only 5 must not intersect one of 17 to Open", rangeOnly5.intersection(range17ToOpen).isEmpty)
        assertTrue("A range of only 5 must intersect one of Open to 23 at 5 only", rangeOnly5.intersection(rangeOpenTo23).equals(rangeOnly5))
        assertTrue("A range of only 5 must intersect one of Open to 5 at 5 only", rangeOnly5.intersection(rangeOpenTo5).equals(rangeOnly5))
        assertTrue("A range of only 5 must not intersect one of 10 to Open", rangeOnly5.intersection(range10ToOpen).isEmpty)
        assertTrue("A range of only 5 must intersect one of Open to Open at 5 only", rangeOnly5.intersection(rangeOpenToOpen).equals(rangeOnly5))


        // MARK: Single value B:

        assertTrue("A range of only 10 must not intersect one of only empty", rangeOnly10.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of only 10 must not intersect one of only 5", rangeOnly10.intersection(rangeOnly5).isEmpty)
        assertTrue("A range of only 10 must intersect one of only 10", rangeOnly10.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of only 10 must intersect one of 5 to 17", rangeOnly10.intersection(range5To17).equals(rangeOnly10))
        assertTrue("A range of only 10 must intersect one of 10 to 23", rangeOnly10.intersection(range10To23).equals(rangeOnly10))
        assertTrue("A range of only 10 must not intersect one of 17 to Open", rangeOnly10.intersection(range17ToOpen).isEmpty)
        assertTrue("A range of only 10 must intersect one of Open to 23", rangeOnly10.intersection(rangeOpenTo23).equals(rangeOnly10))
        assertTrue("A range of only 10 must not intersect one of Open to 5", rangeOnly10.intersection(rangeOpenTo5).isEmpty)
        assertTrue("A range of only 10 must intersect one of 10 to Open", rangeOnly10.intersection(range10ToOpen).equals(rangeOnly10))
        assertTrue("A range of only 10 must intersect one of Open to Open", rangeOnly10.intersection(rangeOpenToOpen).equals(rangeOnly10))


        // MARK: Closed range A:

        assertTrue("A range of 5 to 17 must not intersect one of only empty", range5To17.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of 5 to 17 must intersect one of only 5 at 5 only", range5To17.intersection(rangeOnly5).equals(rangeOnly5))
        assertTrue("A range of 5 to 17 must intersect one of only 10 at 10 only", range5To17.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of 5 to 17 must intersect one of 5 to 17 completely", range5To17.intersection(range5To17).equals(range5To17))
        assertTrue("A range of 5 to 17 must intersect one of 10 to 23 at 10 to 17", range5To17.intersection(range10To23).equals(range10To17))
        assertTrue("A range of 5 to 17 must intersect one of 17 to Open at 17 only", range5To17.intersection(range17ToOpen).equals(rangeOnly17))
        assertTrue("A range of 5 to 17 must intersect one of Open to 23 at 5 to 17", range5To17.intersection(rangeOpenTo23).equals(range5To17))
        assertTrue("A range of 5 to 17 must intersect one of Open to 5 at only 7", range5To17.intersection(rangeOpenTo5).equals(rangeOnly5))
        assertTrue("A range of 5 to 17 must intersect one of 10 to Open", range5To17.intersection(range10ToOpen).equals(range10To17))
        assertTrue("A range of 5 to 17 must intersect one of Open to Open", range5To17.intersection(rangeOpenToOpen).equals(range5To17))


        // MARK: Closed range B:

        assertTrue("A range of 10 to 23 must not intersect one of only empty", range10To23.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of 10 to 23 must not intersect one of only 5", range10To23.intersection(rangeOnly5).isEmpty)
        assertTrue("A range of 10 to 23 must intersect one of only 10 at 10 only", range10To23.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of 10 to 23 must intersect one of 5 to 17 at 10 to 17", range10To23.intersection(range5To17).equals(range10To17))
        assertTrue("A range of 10 to 23 must intersect one of 10 to 23 completely", range10To23.intersection(range10To23).equals(range10To23))
        assertTrue("A range of 10 to 23 must intersect one of 17 to Open at 17 to 23", range10To23.intersection(range17ToOpen).equals(range17To23))
        assertTrue("A range of 10 to 23 must intersect one of Open to 23 at 10 to 23", range10To23.intersection(rangeOpenTo23).equals(range10To23))
        assertTrue("A range of 10 to 23 must not intersect one of Open to 5", range10To23.intersection(rangeOpenTo5).isEmpty)
        assertTrue("A range of 10 to 23 must intersect one of 10 to Open at 10 to 23", range10To23.intersection(range10ToOpen).equals(range10To23))
        assertTrue("A range of 10 to 23 must intersect one of Open to Open at 10 to 23", range10To23.intersection(rangeOpenToOpen).equals(range10To23))


        // MARK: Right-open range A:

        assertTrue("A range of 17 to Open must not intersect one of only empty", range17ToOpen.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of 17 to Open must not intersect one of only 5", range17ToOpen.intersection(rangeOnly5).isEmpty)
        assertTrue("A range of 17 to Open must not intersect one of only 10", range17ToOpen.intersection(rangeOnly10).isEmpty)
        assertTrue("A range of 17 to Open must intersect one of 5 to 17 at 5 to 17", range17ToOpen.intersection(range5To17).equals(rangeOnly17))
        assertTrue("A range of 17 to Open must intersect one of 10 to 23 at 17 to 23", range17ToOpen.intersection(range10To23).equals(range17To23))
        assertTrue("A range of 17 to Open must intersect one of 17 to Open completely", range17ToOpen.intersection(range17ToOpen).equals(range17ToOpen))
        assertTrue("A range of 17 to Open must intersect one of Open to 23 at 17 to 23", range17ToOpen.intersection(rangeOpenTo23).equals(range17To23))
        assertTrue("A range of 17 to Open must not intersect one of Open to 5", range17ToOpen.intersection(rangeOpenTo5).isEmpty)
        assertTrue("A range of 17 to Open must intersect one of 10 to Open at 17 to Open", range17ToOpen.intersection(range10ToOpen).equals(range17ToOpen))
        assertTrue("A range of 17 to Open must intersect one of Open to Open at 17 to Open", range17ToOpen.intersection(rangeOpenToOpen).equals(range17ToOpen))


        // MARK: Left-open range B:

        assertTrue("A range of Open to 23 must not intersect one of only empty", rangeOpenTo23.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of Open to 23 must intersect one of only 5 at 5 only", rangeOpenTo23.intersection(rangeOnly5).equals(rangeOnly5))
        assertTrue("A range of Open to 23 must intersect one of only 10 at 10 only", rangeOpenTo23.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of Open to 23 must intersect one of 5 to 17 at 5 to 17", rangeOpenTo23.intersection(range5To17).equals(range5To17))
        assertTrue("A range of Open to 23 must intersect one of 10 to 23 at 10 to 23", rangeOpenTo23.intersection(range10To23).equals(range10To23))
        assertTrue("A range of Open to 23 must intersect one of 17 to Open at 17 to 23", rangeOpenTo23.intersection(range17ToOpen).equals(range17To23))
        assertTrue("A range of Open to 23 must intersect one of Open to 23 completely", rangeOpenTo23.intersection(rangeOpenTo23).equals(rangeOpenTo23))
        assertTrue("A range of Open to 23 must intersect one of Open to 5 at Open to 5", rangeOpenTo23.intersection(rangeOpenTo5).equals(rangeOpenTo5))
        assertTrue("A range of Open to 23 must intersect one of 10 to Open at 10 to 23", rangeOpenTo23.intersection(range10ToOpen).equals(range10To23))
        assertTrue("A range of Open to 23 must intersect one of Open to Open at Open to 23", rangeOpenTo23.intersection(rangeOpenToOpen).equals(rangeOpenTo23))


        // MARK: Left-open range A:

        assertTrue("A range of Open to 5 must not intersect one of only empty", rangeOpenTo5.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of Open to 5 must intersect one of only 5 at 5 only", rangeOpenTo5.intersection(rangeOnly5).equals(rangeOnly5))
        assertTrue("A range of Open to 5 must not intersect one of only 10", rangeOpenTo5.intersection(rangeOnly10).isEmpty)
        assertTrue("A range of Open to 5 must intersect one of 5 to 17 at 5 only", rangeOpenTo5.intersection(range5To17).equals(rangeOnly5))
        assertTrue("A range of Open to 5 must not intersect one of 10 to 23", rangeOpenTo5.intersection(range10To23).isEmpty)
        assertTrue("A range of Open to 5 must not intersect one of 17 to Open", rangeOpenTo5.intersection(range17ToOpen).isEmpty)
        assertTrue("A range of Open to 5 must intersect one of Open to 23 at Open to 5", rangeOpenTo5.intersection(rangeOpenTo23).equals(rangeOpenTo5))
        assertTrue("A range of Open to 5 must intersect one of Open to 5 completely", rangeOpenTo5.intersection(rangeOpenTo5).equals(rangeOpenTo5))
        assertTrue("A range of Open to 5 must not intersect one of 10 to Open", rangeOpenTo5.intersection(range10ToOpen).isEmpty)
        assertTrue("A range of Open to 5 must intersect one of Open to Open at Open to 5", rangeOpenTo5.intersection(rangeOpenToOpen).equals(rangeOpenTo5))


        // MARK: Right-open range B:

        assertTrue("A range of 10 to Open must not intersect one of only empty", range10ToOpen.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of 10 to Open must not intersect one of only 5", range10ToOpen.intersection(rangeOnly5).isEmpty)
        assertTrue("A range of 10 to Open must intersect one of only 10 at 10 only", range10ToOpen.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of 10 to Open must intersect one of 5 to 17 at 10 to 17", range10ToOpen.intersection(range5To17).equals(range10To17))
        assertTrue("A range of 10 to Open must intersect one of 10 to 23 at 10 to 23", range10ToOpen.intersection(range10To23).equals(range10To23))
        assertTrue("A range of 10 to Open must intersect one of 17 to Open at 17 to Open", range10ToOpen.intersection(range17ToOpen).equals(range17ToOpen))
        assertTrue("A range of 10 to Open must intersect one of Open to 23 at 10 to 23", range10ToOpen.intersection(rangeOpenTo23).equals(range10To23))
        assertTrue("A range of 10 to Open must not intersect one of Open to 5", range10ToOpen.intersection(rangeOpenTo5).isEmpty)
        assertTrue("A range of 10 to Open must intersect one of 10 to Open completely", range10ToOpen.intersection(range10ToOpen).equals(range10ToOpen))
        assertTrue("A range of 10 to Open must intersect one of Open to Open at 10 to Open", range10ToOpen.intersection(rangeOpenToOpen).equals(range10ToOpen))


        // MARK: Infinite range:

        assertTrue("A range of Open to Open must not intersect one of only empty", rangeOpenToOpen.intersection(rangeEmpty).isEmpty)
        assertTrue("A range of Open to Open must intersect one of only 5 at only 5", rangeOpenToOpen.intersection(rangeOnly5).equals(rangeOnly5))
        assertTrue("A range of Open to Open must intersect one of only 10 at only 10", rangeOpenToOpen.intersection(rangeOnly10).equals(rangeOnly10))
        assertTrue("A range of Open to Open must intersect one of 5 to 17 at 5 to 17", rangeOpenToOpen.intersection(range5To17).equals(range5To17))
        assertTrue("A range of Open to Open must intersect one of 10 to 23 at 10 to 23", rangeOpenToOpen.intersection(range10To23).equals(range10To23))
        assertTrue("A range of Open to Open must intersect one of 17 to Open at 17 to Open", rangeOpenToOpen.intersection(range17ToOpen).equals(range17ToOpen))
        assertTrue("A range of Open to Open must intersect one of Open to 23 at Open to 23", rangeOpenToOpen.intersection(rangeOpenTo23).equals(rangeOpenTo23))
        assertTrue("A range of Open to Open must intersect one of Open to 5 at Open to 5", rangeOpenToOpen.intersection(rangeOpenTo5).equals(rangeOpenTo5))
        assertTrue("A range of Open to Open must intersect one of 10 to Open at 10 to Open", rangeOpenToOpen.intersection(range10ToOpen).equals(range10ToOpen))
        assertTrue("A range of Open to Open must intersect one of Open to Open completely", rangeOpenToOpen.intersection(rangeOpenToOpen).equals(rangeOpenToOpen))


        var temporaryHoldingVariable: Any? = null
        val closedIntersectClosed = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.intersection(range10To23)
        }
        println("It took $closedIntersectClosed seconds to calculate $intersectionTrialCount times that $range5To17 ∩ $range10To23 = $temporaryHoldingVariable")

        val closedIntersectOpen = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.intersection(range10ToOpen)
        }
        println("It took $closedIntersectOpen seconds to calculate $intersectionTrialCount times that $range5To17 ∩ $range10ToOpen = $temporaryHoldingVariable")

        val closedIntersectPoint = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.intersection(rangeOnly10)
        }
        println("It took $closedIntersectPoint seconds to calculate $intersectionTrialCount times that $range5To17 ∩ $rangeOnly10 = $temporaryHoldingVariable")
    }

    @Test
    fun containsCompletely() {


        // MARK: Single value A:

        assertTrue("A range of only 5 must completely contain one of only 5", rangeOnly5.containsCompletely(rangeOnly5))
        assertFalse("A range of only 5 must not completely contain one of only 10", rangeOnly5.containsCompletely(rangeOnly10))
        assertFalse("A range of only 5 must not completely contain one of 5 to 17", rangeOnly5.containsCompletely(range5To17))
        assertFalse("A range of only 5 must not completely contain one of 10 to 23", rangeOnly5.containsCompletely(range10To23))
        assertFalse("A range of only 5 must not completely contain one of 17 to Open", rangeOnly5.containsCompletely(range17ToOpen))
        assertFalse("A range of only 5 must not completely contain one of Open to 23", rangeOnly5.containsCompletely(rangeOpenTo23))
        assertFalse("A range of only 5 must not completely contain one of Open to 5", rangeOnly5.containsCompletely(rangeOpenTo5))
        assertFalse("A range of only 5 must not completely contain one of 10 to Open", rangeOnly5.containsCompletely(range10ToOpen))
        assertFalse("A range of only 5 must not completely contain one of Open to Open", rangeOnly5.containsCompletely(rangeOpenToOpen))


        // MARK: Single value B:

        assertFalse("A range of only 10 must not completely contain one of only 5", rangeOnly10.containsCompletely(rangeOnly5))
        assertTrue("A range of only 10 must completely contain one of only 10", rangeOnly10.containsCompletely(rangeOnly10))
        assertFalse("A range of only 10 must not completely contain one of 5 to 17", rangeOnly10.containsCompletely(range5To17))
        assertFalse("A range of only 10 must not completely contain one of 10 to 23", rangeOnly10.containsCompletely(range10To23))
        assertFalse("A range of only 10 must not completely contain one of 17 to Open", rangeOnly10.containsCompletely(range17ToOpen))
        assertFalse("A range of only 10 must not completely contain one of Open to 23", rangeOnly10.containsCompletely(rangeOpenTo23))
        assertFalse("A range of only 10 must not completely contain one of Open to 5", rangeOnly10.containsCompletely(rangeOpenTo5))
        assertFalse("A range of only 10 must not completely contain one of 10 to Open", rangeOnly10.containsCompletely(range10ToOpen))
        assertFalse("A range of only 10 must not completely contain one of Open to Open", rangeOnly10.containsCompletely(rangeOpenToOpen))


        // MARK: Closed range A:

        assertTrue("A range of 5 to 17 must completely contain one of only 5", range5To17.containsCompletely(rangeOnly5))
        assertTrue("A range of 5 to 17 must completely contain one of only 10", range5To17.containsCompletely(rangeOnly10))
        assertTrue("A range of 5 to 17 must completely contain one of 5 to 17", range5To17.containsCompletely(range5To17))
        assertFalse("A range of 5 to 17 must not completely contain one of 10 to 23", range5To17.containsCompletely(range10To23))
        assertFalse("A range of 5 to 17 must not completely contain one of 17 to Open", range5To17.containsCompletely(range17ToOpen))
        assertFalse("A range of 5 to 17 must not completely contain one of Open to 23", range5To17.containsCompletely(rangeOpenTo23))
        assertFalse("A range of 5 to 17 must not completely contain one of Open to 5", range5To17.containsCompletely(rangeOpenTo5))
        assertFalse("A range of 5 to 17 must not completely contain one of 10 to Open", range5To17.containsCompletely(range10ToOpen))
        assertFalse("A range of 5 to 17 must not completely contain one of Open to Open", range5To17.containsCompletely(rangeOpenToOpen))


        // MARK: Closed range B:

        assertFalse("A range of 10 to 23 must not completely contain one of only 5", range10To23.containsCompletely(rangeOnly5))
        assertTrue("A range of 10 to 23 must completely contain one of only 10", range10To23.containsCompletely(rangeOnly10))
        assertFalse("A range of 10 to 23 must not completely contain one of 5 to 17", range10To23.containsCompletely(range5To17))
        assertTrue("A range of 10 to 23 must completely contain one of 10 to 23", range10To23.containsCompletely(range10To23))
        assertFalse("A range of 10 to 23 must not completely contain one of 17 to Open", range10To23.containsCompletely(range17ToOpen))
        assertFalse("A range of 10 to 23 must not completely contain one of Open to 23", range10To23.containsCompletely(rangeOpenTo23))
        assertFalse("A range of 10 to 23 must not completely contain one of Open to 5", range10To23.containsCompletely(rangeOpenTo5))
        assertFalse("A range of 10 to 23 must not completely contain one of 10 to Open", range10To23.containsCompletely(range10ToOpen))
        assertFalse("A range of 10 to 23 must not completely contain one of Open to Open", range10To23.containsCompletely(rangeOpenToOpen))


        // MARK: Right-open range A:

        assertFalse("A range of 17 to Open must not completely contain one of only 5", range17ToOpen.containsCompletely(rangeOnly5))
        assertFalse("A range of 17 to Open must not completely contain one of only 10", range17ToOpen.containsCompletely(rangeOnly10))
        assertFalse("A range of 17 to Open must not completely contain one of 5 to 17", range17ToOpen.containsCompletely(range5To17))
        assertFalse("A range of 17 to Open must not completely contain one of 10 to 23", range17ToOpen.containsCompletely(range10To23))
        assertTrue("A range of 17 to Open must completely contain one of 17 to Open", range17ToOpen.containsCompletely(range17ToOpen))
        assertFalse("A range of 17 to Open must not completely contain one of Open to 23", range17ToOpen.containsCompletely(rangeOpenTo23))
        assertFalse("A range of 17 to Open must not completely contain one of Open to 5", range17ToOpen.containsCompletely(rangeOpenTo5))
        assertFalse("A range of 17 to Open must not completely contain one of 10 to Open", range17ToOpen.containsCompletely(range10ToOpen))
        assertFalse("A range of 17 to Open must not completely contain one of Open to Open", range17ToOpen.containsCompletely(rangeOpenToOpen))


        // MARK: Left-open range B:

        assertTrue("A range of Open to 23 must completely contain one of only 5", rangeOpenTo23.containsCompletely(rangeOnly5))
        assertTrue("A range of Open to 23 must completely contain one of only 10", rangeOpenTo23.containsCompletely(rangeOnly10))
        assertTrue("A range of Open to 23 must completely contain one of 5 to 17", rangeOpenTo23.containsCompletely(range5To17))
        assertTrue("A range of Open to 23 must completely contain one of 10 to 23", rangeOpenTo23.containsCompletely(range10To23))
        assertFalse("A range of Open to 23 must not completely contain one of 17 to Open", rangeOpenTo23.containsCompletely(range17ToOpen))
        assertTrue("A range of Open to 23 must completely contain one of Open to 23", rangeOpenTo23.containsCompletely(rangeOpenTo23))
        assertTrue("A range of Open to 23 must completely contain one of Open to 5", rangeOpenTo23.containsCompletely(rangeOpenTo5))
        assertFalse("A range of Open to 23 must not completely contain one of 10 to Open", rangeOpenTo23.containsCompletely(range10ToOpen))
        assertFalse("A range of Open to 23 must completely contain one of Open to Open", rangeOpenTo23.containsCompletely(rangeOpenToOpen))


        // MARK: Left-open range A:

        assertTrue("A range of Open to 5 must completely contain one of only 5", rangeOpenTo5.containsCompletely(rangeOnly5))
        assertFalse("A range of Open to 5 must not completely contain one of only 10", rangeOpenTo5.containsCompletely(rangeOnly10))
        assertFalse("A range of Open to 5 must not completely contain one of 5 to 17", rangeOpenTo5.containsCompletely(range5To17))
        assertFalse("A range of Open to 5 must not completely contain one of 10 to 23", rangeOpenTo5.containsCompletely(range10To23))
        assertFalse("A range of Open to 5 must not completely contain one of 17 to Open", rangeOpenTo5.containsCompletely(range17ToOpen))
        assertFalse("A range of Open to 5 must not completely contain one of Open to 23", rangeOpenTo5.containsCompletely(rangeOpenTo23))
        assertTrue("A range of Open to 5 must completely contain one of Open to 5", rangeOpenTo5.containsCompletely(rangeOpenTo5))
        assertFalse("A range of Open to 5 must not completely contain one of 10 to Open", rangeOpenTo5.containsCompletely(range10ToOpen))
        assertFalse("A range of Open to 5 must not completely contain one of Open to Open", rangeOpenTo5.containsCompletely(rangeOpenToOpen))


        // MARK: Right-open range B:

        assertFalse("A range of 10 to Open must not completely contain one of only 5", range10ToOpen.containsCompletely(rangeOnly5))
        assertTrue("A range of 10 to Open must completely contain one of only 10", range10ToOpen.containsCompletely(rangeOnly10))
        assertFalse("A range of 10 to Open must not completely contain one of 5 to 17", range10ToOpen.containsCompletely(range5To17))
        assertTrue("A range of 10 to Open must completely contain one of 10 to 23", range10ToOpen.containsCompletely(range10To23))
        assertTrue("A range of 10 to Open must completely contain one of 17 to Open", range10ToOpen.containsCompletely(range17ToOpen))
        assertFalse("A range of 10 to Open must not completely contain one of Open to 23", range10ToOpen.containsCompletely(rangeOpenTo23))
        assertFalse("A range of 10 to Open must not completely contain one of Open to 5", range10ToOpen.containsCompletely(rangeOpenTo5))
        assertTrue("A range of 10 to Open must completely contain one of 10 to Open", range10ToOpen.containsCompletely(range10ToOpen))
        assertFalse("A range of 10 to Open must not completely contain one of Open to Open", range10ToOpen.containsCompletely(rangeOpenToOpen))


        // MARK: Infinite range:

        assertTrue("A range of Open to Open must completely contain one of only 5", rangeOpenToOpen.containsCompletely(rangeOnly5))
        assertTrue("A range of Open to Open must completely contain one of only 10", rangeOpenToOpen.containsCompletely(rangeOnly10))
        assertTrue("A range of Open to Open must completely contain one of 5 to 17", rangeOpenToOpen.containsCompletely(range5To17))
        assertTrue("A range of Open to Open must completely contain one of 10 to 23", rangeOpenToOpen.containsCompletely(range10To23))
        assertTrue("A range of Open to Open must completely contain one of 17 to Open", rangeOpenToOpen.containsCompletely(range17ToOpen))
        assertTrue("A range of Open to Open must completely contain one of Open to 23", rangeOpenToOpen.containsCompletely(rangeOpenTo23))
        assertTrue("A range of Open to Open must completely contain one of Open to 5", rangeOpenToOpen.containsCompletely(rangeOpenTo5))
        assertTrue("A range of Open to Open must completely contain one of 10 to Open", rangeOpenToOpen.containsCompletely(range10ToOpen))
        assertTrue("A range of Open to Open must completely contain one of Open to Open completely", rangeOpenToOpen.containsCompletely(rangeOpenToOpen))


        var temporaryHoldingVariable: Any? = null
        val closedIntersectClosed = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.containsCompletely(range10To23)
        }
        println("It took $closedIntersectClosed seconds to calculate $intersectionTrialCount times that $range5To17 ⊆ $range10To23 = $temporaryHoldingVariable")

        val closedIntersectOpen = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.containsCompletely(range10ToOpen)
        }
        println("It took $closedIntersectOpen seconds to calculate $intersectionTrialCount times that $range5To17 ⊆ $range10ToOpen = $temporaryHoldingVariable")

        val closedIntersectPoint = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            temporaryHoldingVariable = range5To17.containsCompletely(rangeOnly10)
        }
        println("It took $closedIntersectPoint seconds to calculate $intersectionTrialCount times that $range5To17 ⊆ $rangeOnly10 = $temporaryHoldingVariable")
    }

}