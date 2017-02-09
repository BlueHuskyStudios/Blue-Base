package org.bh.tools.base.struct

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.struct.OpenRange.Companion.open
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Kyli Rouge
 * @since 2017-02-08
 */
class OpenRangeTest {

    val rangeOnly5 = OpenRange(5.0)
    val rangeOnly10 = OpenRange(10.0)
    val range5To17 = OpenRange(5.0, 17.0)
    val range10To23 = OpenRange(10.0, 23.0)
    val range17ToOpen = OpenRange(17.0, open)
    val rangeOpenTo23 = OpenRange(open, 23.0)
    val rangeOpenTo5 = OpenRange(open, 5.0)
    val range10ToOpen = OpenRange(10.0, open)
    val rangeOpenToOpen = OpenRange<Fraction>(open, open)

    @Test
    fun isOpen() {
        assertFalse("A range of only 5 must not be open", rangeOnly5.isOpen)
        assertFalse("A range of 5 to 17 must not be open", range5To17.isOpen)
        assertTrue("A range of 17 to Open must be open", range17ToOpen.isOpen)
        assertTrue("A range of Open to 5 must be open", rangeOpenTo5.isOpen)
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
    fun containsCompletely() {

    }

}