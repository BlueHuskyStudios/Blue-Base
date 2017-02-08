package org.bh.tools.base.struct

import org.bh.tools.base.abstraction.Fraction
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Kyli Rouge
 * @since 2017-02-08
 */
class OpenRangeTest {

    val rangeOnly5 = OpenRange(5.0)
    val range5To17 = OpenRange(5.0, 17.0)
    val rangeOpenTo17 = OpenRange(null, 17.0)
    val range5ToOpen = OpenRange(5.0, null)
    val rangeOpenToOpen = OpenRange(null, null)

    @Test
    fun isOpen() {
        assertFalse("A range of only 5 must not be open", rangeOnly5.isOpen)
        assertFalse("A range of 5 to 17 must not be open", range5To17.isOpen)
        assertTrue("A range of open to 17 must be open", rangeOpenTo17.isOpen)
        assertTrue("A range of 5 to open must be open", range5ToOpen.isOpen)
        assertTrue("A range of open to open must be open", rangeOpenToOpen.isOpen)
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

        assertFalse("A range of Open to 17 must not contain Not-a-Number", rangeOpenTo17.contains(Fraction.NaN))
        assertTrue("A range of Open to 17 must contain -∞", rangeOpenTo17.contains(Fraction.NEGATIVE_INFINITY))
        assertFalse("A range of Open to 17 must not contain ∞", rangeOpenTo17.contains(Fraction.POSITIVE_INFINITY))

        assertTrue("A range of Open to 17 must contain 5", rangeOpenTo17.contains(5.0))
        assertTrue("A range of Open to 17 must contain 5.01", rangeOpenTo17.contains(5.01))
        assertTrue("A range of Open to 17 must contain 6", rangeOpenTo17.contains(6.0))

        assertTrue("A range of Open to 17 must contain 16", rangeOpenTo17.contains(16.0))
        assertTrue("A range of Open to 17 must contain 16.99", rangeOpenTo17.contains(16.99))
        assertTrue("A range of Open to 17 must contain 17", rangeOpenTo17.contains(17.0))
        assertFalse("A range of Open to 17 must not contain 17.01", rangeOpenTo17.contains(17.01))
        assertFalse("A range of Open to 17 must not contain 18", rangeOpenTo17.contains(18.0))


        // MARK: Right-open Range:

        assertFalse("A range of 5 to Open must not contain Not-a-Number", range5ToOpen.contains(Fraction.NaN))
        assertFalse("A range of 5 to Open must not contain -∞", range5ToOpen.contains(Fraction.NEGATIVE_INFINITY))
        assertTrue("A range of 5 to Open must contain ∞", range5ToOpen.contains(Fraction.POSITIVE_INFINITY))

        assertFalse("A range of 5 to Open must not contain 4", range5ToOpen.contains(4.0))
        assertFalse("A range of 5 to Open must not contain 4.99", range5ToOpen.contains(4.99))
        assertTrue("A range of 5 to Open must contain 5", range5ToOpen.contains(5.0))
        assertTrue("A range of 5 to Open must contain 5.01", range5ToOpen.contains(5.01))
        assertTrue("A range of 5 to Open must contain 6", range5ToOpen.contains(6.0))

        assertTrue("A range of 5 to Open must contain 16", range5ToOpen.contains(16.0))
        assertTrue("A range of 5 to Open must contain 16.99", range5ToOpen.contains(16.99))
        assertTrue("A range of 5 to Open must contain 17", range5ToOpen.contains(17.0))
        assertTrue("A range of 5 to Open must contain 17.01", range5ToOpen.contains(17.01))
        assertTrue("A range of 5 to Open must contain 18", range5ToOpen.contains(18.0))


        // MARK: Fully-open Range:

        assertFalse("A range of Open to Open must not contain Not-a-Number", range5ToOpen.contains(Fraction.NaN))
        assertTrue("A range of Open to Open must contain -∞", range5ToOpen.contains(Fraction.NEGATIVE_INFINITY))
        assertTrue("A range of Open to Open must contain ∞", range5ToOpen.contains(Fraction.POSITIVE_INFINITY))

        assertTrue("A range of Open to Open must contain 4", range5ToOpen.contains(4.0))
        assertTrue("A range of Open to Open must contain 4.99", range5ToOpen.contains(4.99))
        assertTrue("A range of Open to Open must contain 5", range5ToOpen.contains(5.0))
        assertTrue("A range of Open to Open must contain 5.01", range5ToOpen.contains(5.01))
        assertTrue("A range of Open to Open must contain 6", range5ToOpen.contains(6.0))

        assertTrue("A range of Open to Open must contain 16", range5ToOpen.contains(16.0))
        assertTrue("A range of Open to Open must contain 16.99", range5ToOpen.contains(16.99))
        assertTrue("A range of Open to Open must contain 17", range5ToOpen.contains(17.0))
        assertTrue("A range of Open to Open must contain 17.01", range5ToOpen.contains(17.01))
        assertTrue("A range of Open to Open must contain 18", range5ToOpen.contains(18.0))
    }

    @Test
    fun intersects() {

    }

    @Test
    fun union() {

    }

    @Test
    fun containsCompletely() {

    }

}