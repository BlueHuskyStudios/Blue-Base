package BlueBase

import BlueBase.OpenRange.Companion.openEnd
import BlueBase.TimeTrialMeasurementMode.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


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
        assertFalse(rangeEmpty.isOpen, "An empty range must not be open")
        assertFalse(rangeOnly5.isOpen, "A range of only 5 must not be open")
        assertFalse(rangeOnly10.isOpen, "A range of only 10 must not be open")
        assertFalse(range5To17.isOpen, "A range of 5 to 17 must not be open")
        assertFalse(range10To23.isOpen, "A range of 10 to 23 must not be open")
        assertTrue(range17ToOpen.isOpen, "A range of 17 to Open must be open")
        assertTrue(rangeOpenTo23.isOpen, "A range of Open to 23 must be open")
        assertTrue(rangeOpenTo5.isOpen, "A range of Open to 5 must be open")
        assertTrue(range10ToOpen.isOpen, "A range of 10 to Open must be open")
        assertTrue(rangeOpenToOpen.isOpen, "A range of Open to Open must be open")
    }

    @Test
    fun contains() {

        // MARK: Single value:

        assertFalse(rangeOnly5.contains(Fraction.NaN), "A range of only 5 must not contain Not-a-Number")
        assertFalse(rangeOnly5.contains(Fraction.NEGATIVE_INFINITY), "A range of only 5 must not contain -∞")
        assertFalse(rangeOnly5.contains(Fraction.POSITIVE_INFINITY), "A range of only 5 must not contain ∞")

        assertFalse(rangeOnly5.contains(4.0), "A range of only 5 must not contain 4")
        assertFalse(rangeOnly5.contains(4.99), "A range of only 5 must not contain 4.99")
        assertTrue(rangeOnly5.contains(5.0), "A range of only 5 must contain 5")
        assertFalse(rangeOnly5.contains(5.01), "A range of only 5 must not contain 5.01")
        assertFalse(rangeOnly5.contains(6.0), "A range of only 5 must not contain 6")


        // MARK: Closed range:

        assertFalse(range5To17.contains(Fraction.NaN), "A range of 5 to 17 must not contain Not-a-Number")
        assertFalse(range5To17.contains(Fraction.NEGATIVE_INFINITY), "A range of 5 to 17 must not contain -∞")
        assertFalse(range5To17.contains(Fraction.POSITIVE_INFINITY), "A range of 5 to 17 must not contain ∞")

        assertFalse(range5To17.contains(4.0), "A range of 5 to 17 must not contain 4")
        assertFalse(range5To17.contains(4.99), "A range of 5 to 17 must not contain 4.99")
        assertTrue(range5To17.contains(5.0), "A range of 5 to 17 must contain 5")
        assertTrue(range5To17.contains(5.01), "A range of 5 to 17 must contain 5.01")
        assertTrue(range5To17.contains(6.0), "A range of 5 to 17 must contain 6")

        assertTrue(range5To17.contains(16.0), "A range of 5 to 17 must contain 16")
        assertTrue(range5To17.contains(16.99), "A range of 5 to 17 must contain 16.99")
        assertTrue(range5To17.contains(17.0), "A range of 5 to 17 must contain 17")
        assertFalse(range5To17.contains(17.01), "A range of 5 to 17 must not contain 17.01")
        assertFalse(range5To17.contains(18.0), "A range of 5 to 17 must not contain 18")


        // MARK: Left-open Range:

        assertFalse(range17ToOpen.contains(Fraction.NaN), "A range of 17 to Open must not contain Not-a-Number")
        assertFalse(range17ToOpen.contains(Fraction.NEGATIVE_INFINITY), "A range of 17 to Open must not contain -∞")
        assertTrue(range17ToOpen.contains(Fraction.POSITIVE_INFINITY), "A range of 17 to Open must contain ∞")

        assertFalse(range17ToOpen.contains(5.0), "A range of 17 to Open must not contain 5")
        assertFalse(range17ToOpen.contains(5.01), "A range of 17 to Open must not contain 5.01")
        assertFalse(range17ToOpen.contains(6.0), "A range of 17 to Open must not contain 6")

        assertFalse(range17ToOpen.contains(16.0), "A range of 17 to Open must not contain 16")
        assertFalse(range17ToOpen.contains(16.99), "A range of 17 to Open must not contain 16.99")
        assertTrue(range17ToOpen.contains(17.0), "A range of 17 to Open must contain 17")
        assertTrue(range17ToOpen.contains(17.01), "A range of 17 to Open must contain 17.01")
        assertTrue(range17ToOpen.contains(18.0), "A range of 17 to Open must contain 18")


        // MARK: Right-open Range:

        assertFalse(rangeOpenTo5.contains(Fraction.NaN), "A range of Open to 5 must not contain Not-a-Number")
        assertTrue(rangeOpenTo5.contains(Fraction.NEGATIVE_INFINITY), "A range of Open to 5 must contain -∞")
        assertFalse(rangeOpenTo5.contains(Fraction.POSITIVE_INFINITY), "A range of Open to 5 must not contain ∞")

        assertTrue(rangeOpenTo5.contains(4.0), "A range of Open to 5 must contain 4")
        assertTrue(rangeOpenTo5.contains(4.99), "A range of Open to 5 must contain 4.99")
        assertTrue(rangeOpenTo5.contains(5.0), "A range of Open to 5 must contain 5")
        assertFalse(rangeOpenTo5.contains(5.01), "A range of Open to 5 must not contain 5.01")
        assertFalse(rangeOpenTo5.contains(6.0), "A range of Open to 5 must not contain 6")

        assertFalse(rangeOpenTo5.contains(16.0), "A range of Open to 5 must not contain 16")
        assertFalse(rangeOpenTo5.contains(16.99), "A range of Open to 5 must not contain 16.99")
        assertFalse(rangeOpenTo5.contains(17.0), "A range of Open to 5 must not contain 17")
        assertFalse(rangeOpenTo5.contains(17.01), "A range of Open to 5 must not contain 17.01")
        assertFalse(rangeOpenTo5.contains(18.0), "A range of Open to 5 must not contain 18")


        // MARK: Fully-open Range:

        assertFalse(rangeOpenToOpen.contains(Fraction.NaN), "A range of Open to Open must not contain Not-a-Number")
        assertTrue(rangeOpenToOpen.contains(Fraction.NEGATIVE_INFINITY), "A range of Open to Open must contain -∞")
        assertTrue(rangeOpenToOpen.contains(Fraction.POSITIVE_INFINITY), "A range of Open to Open must contain ∞")

        assertTrue(rangeOpenToOpen.contains(4.0), "A range of Open to Open must contain 4")
        assertTrue(rangeOpenToOpen.contains(4.99), "A range of Open to Open must contain 4.99")
        assertTrue(rangeOpenToOpen.contains(5.0), "A range of Open to Open must contain 5")
        assertTrue(rangeOpenToOpen.contains(5.01), "A range of Open to Open must contain 5.01")
        assertTrue(rangeOpenToOpen.contains(6.0), "A range of Open to Open must contain 6")

        assertTrue(rangeOpenToOpen.contains(16.0), "A range of Open to Open must contain 16")
        assertTrue(rangeOpenToOpen.contains(16.99), "A range of Open to Open must contain 16.99")
        assertTrue(rangeOpenToOpen.contains(17.0), "A range of Open to Open must contain 17")
        assertTrue(rangeOpenToOpen.contains(17.01), "A range of Open to Open must contain 17.01")
        assertTrue(rangeOpenToOpen.contains(18.0), "A range of Open to Open must contain 18")
    }

    @Test
    fun intersects() {

        // MARK: Single value A:

        assertTrue(rangeOnly5.intersects(rangeOnly5), "A range of only 5 must intersect one of only 5")
        assertFalse(rangeOnly5.intersects(rangeOnly10), "A range of only 5 must not intersect one of only 10")
        assertTrue(rangeOnly5.intersects(range5To17), "A range of only 5 must intersect one of 5 to 17")
        assertFalse(rangeOnly5.intersects(range10To23), "A range of only 5 must not intersect one of 10 to 23")
        assertFalse(rangeOnly5.intersects(range17ToOpen), "A range of only 5 must not intersect one of 17 to Open")
        assertTrue(rangeOnly5.intersects(rangeOpenTo23), "A range of only 5 must intersect one of Open to 23")
        assertTrue(rangeOnly5.intersects(rangeOpenTo5), "A range of only 5 must intersect one of Open to 5")
        assertFalse(rangeOnly5.intersects(range10ToOpen), "A range of only 5 must not intersect one of 10 to Open")
        assertTrue(rangeOnly5.intersects(rangeOpenToOpen), "A range of only 5 must intersect one of Open to Open")


        // MARK: Single value B:

        assertFalse(rangeOnly10.intersects(rangeOnly5), "A range of only 10 must not intersect one of only 5")
        assertTrue(rangeOnly10.intersects(rangeOnly10), "A range of only 10 must intersect one of only 10")
        assertTrue(rangeOnly10.intersects(range5To17), "A range of only 10 must intersect one of 5 to 17")
        assertTrue(rangeOnly10.intersects(range10To23), "A range of only 10 must intersect one of 10 to 23")
        assertFalse(rangeOnly10.intersects(range17ToOpen), "A range of only 10 must not intersect one of 17 to Open")
        assertTrue(rangeOnly10.intersects(rangeOpenTo23), "A range of only 10 must intersect one of Open to 23")
        assertFalse(rangeOnly10.intersects(rangeOpenTo5), "A range of only 10 must not intersect one of Open to 5")
        assertTrue(rangeOnly10.intersects(range10ToOpen), "A range of only 10 must intersect one of 10 to Open")
        assertTrue(rangeOnly10.intersects(rangeOpenToOpen), "A range of only 10 must intersect one of Open to Open")


        // MARK: Closed range A:

        assertTrue(range5To17.intersects(rangeOnly5), "A range of 5 to 17 must intersect one of only 5")
        assertTrue(range5To17.intersects(rangeOnly10), "A range of 5 to 17 must intersect one of only 10")
        assertTrue(range5To17.intersects(range5To17), "A range of 5 to 17 must intersect one of 5 to 17")
        assertTrue(range5To17.intersects(range10To23), "A range of 5 to 17 must intersect one of 10 to 23")
        assertTrue(range5To17.intersects(range17ToOpen), "A range of 5 to 17 must intersect one of 17 to Open")
        assertTrue(range5To17.intersects(rangeOpenTo23), "A range of 5 to 17 must intersect one of Open to 23")
        assertTrue(range5To17.intersects(rangeOpenTo5), "A range of 5 to 17 must intersect one of Open to 5")
        assertTrue(range5To17.intersects(range10ToOpen), "A range of 5 to 17 must intersect one of 10 to Open")
        assertTrue(range5To17.intersects(rangeOpenToOpen), "A range of 5 to 17 must intersect one of Open to Open")


        // MARK: Closed range B:

        assertFalse(range10To23.intersects(rangeOnly5), "A range of 10 to 23 must not intersect one of only 5")
        assertTrue(range10To23.intersects(rangeOnly10), "A range of 10 to 23 must intersect one of only 10")
        assertTrue(range10To23.intersects(range5To17), "A range of 10 to 23 must intersect one of 5 to 17")
        assertTrue(range10To23.intersects(range10To23), "A range of 10 to 23 must intersect one of 10 to 23")
        assertTrue(range10To23.intersects(range17ToOpen), "A range of 10 to 23 must intersect one of 17 to Open")
        assertTrue(range10To23.intersects(rangeOpenTo23), "A range of 10 to 23 must intersect one of Open to 23")
        assertFalse(range10To23.intersects(rangeOpenTo5), "A range of 10 to 23 must not intersect one of Open to 5")
        assertTrue(range10To23.intersects(range10ToOpen), "A range of 10 to 23 must intersect one of 10 to Open")
        assertTrue(range10To23.intersects(rangeOpenToOpen), "A range of 10 to 23 must intersect one of Open to Open")


        // MARK: Right-open range A:

        assertFalse(range17ToOpen.intersects(rangeOnly5), "A range of 17 to Open must not intersect one of only 5")
        assertFalse(range17ToOpen.intersects(rangeOnly10), "A range of 17 to Open must not intersect one of only 10")
        assertTrue(range17ToOpen.intersects(range5To17), "A range of 17 to Open must intersect one of 5 to 17")
        assertTrue(range17ToOpen.intersects(range10To23), "A range of 17 to Open must intersect one of 10 to 23")
        assertTrue(range17ToOpen.intersects(range17ToOpen), "A range of 17 to Open must intersect one of 17 to Open")
        assertTrue(range17ToOpen.intersects(rangeOpenTo23), "A range of 17 to Open must intersect one of Open to 23")
        assertFalse(range17ToOpen.intersects(rangeOpenTo5), "A range of 17 to Open must not intersect one of Open to 5")
        assertTrue(range17ToOpen.intersects(range10ToOpen), "A range of 17 to Open must intersect one of 10 to Open")
        assertTrue(range17ToOpen.intersects(rangeOpenToOpen), "A range of 17 to Open must intersect one of Open to Open")


        // MARK: Left-open range B:

        assertTrue(rangeOpenTo23.intersects(rangeOnly5), "A range of Open to 23 must intersect one of only 5")
        assertTrue(rangeOpenTo23.intersects(rangeOnly10), "A range of Open to 23 must intersect one of only 10")
        assertTrue(rangeOpenTo23.intersects(range5To17), "A range of Open to 23 must intersect one of 5 to 17")
        assertTrue(rangeOpenTo23.intersects(range10To23), "A range of Open to 23 must intersect one of 10 to 23")
        assertTrue(rangeOpenTo23.intersects(range17ToOpen), "A range of Open to 23 must intersect one of 17 to Open")
        assertTrue(rangeOpenTo23.intersects(rangeOpenTo23), "A range of Open to 23 must intersect one of Open to 23")
        assertTrue(rangeOpenTo23.intersects(rangeOpenTo5), "A range of Open to 23 must intersect one of Open to 5")
        assertTrue(rangeOpenTo23.intersects(range10ToOpen), "A range of Open to 23 must intersect one of 10 to Open")
        assertTrue(rangeOpenTo23.intersects(rangeOpenToOpen), "A range of Open to 23 must intersect one of Open to Open")


        // MARK: Left-open range A:

        assertTrue(rangeOpenTo5.intersects(rangeOnly5), "A range of Open to 5 must intersect one of only 5")
        assertFalse(rangeOpenTo5.intersects(rangeOnly10), "A range of Open to 5 must not intersect one of only 10")
        assertTrue(rangeOpenTo5.intersects(range5To17), "A range of Open to 5 must intersect one of 5 to 17")
        assertFalse(rangeOpenTo5.intersects(range10To23), "A range of Open to 5 must not intersect one of 10 to 23")
        assertFalse(rangeOpenTo5.intersects(range17ToOpen), "A range of Open to 5 must not intersect one of 17 to Open")
        assertTrue(rangeOpenTo5.intersects(rangeOpenTo23), "A range of Open to 5 must intersect one of Open to 23")
        assertTrue(rangeOpenTo5.intersects(rangeOpenTo5), "A range of Open to 5 must intersect one of Open to 5")
        assertFalse(rangeOpenTo5.intersects(range10ToOpen), "A range of Open to 5 must not intersect one of 10 to Open")
        assertTrue(rangeOpenTo5.intersects(rangeOpenToOpen), "A range of Open to 5 must intersect one of Open to Open")


        // MARK: Right-open range B:

        assertFalse(range10ToOpen.intersects(rangeOnly5), "A range of 10 to Open must not intersect one of only 5")
        assertTrue(range10ToOpen.intersects(rangeOnly10), "A range of 10 to Open must intersect one of only 10")
        assertTrue(range10ToOpen.intersects(range5To17), "A range of 10 to Open must intersect one of 5 to 17")
        assertTrue(range10ToOpen.intersects(range10To23), "A range of 10 to Open must intersect one of 10 to 23")
        assertTrue(range10ToOpen.intersects(range17ToOpen), "A range of 10 to Open must intersect one of 17 to Open")
        assertTrue(range10ToOpen.intersects(rangeOpenTo23), "A range of 10 to Open must intersect one of Open to 23")
        assertFalse(range10ToOpen.intersects(rangeOpenTo5), "A range of 10 to Open must not intersect one of Open to 5")
        assertTrue(range10ToOpen.intersects(range10ToOpen), "A range of 10 to Open must intersect one of 10 to Open")
        assertTrue(range10ToOpen.intersects(rangeOpenToOpen), "A range of 10 to Open must intersect one of Open to Open")


        // MARK: Infinite range:

        assertTrue(rangeOpenToOpen.intersects(rangeOnly5), "A range of Open to Open must intersect one of only 5")
        assertTrue(rangeOpenToOpen.intersects(rangeOnly10), "A range of Open to Open must intersect one of only 10")
        assertTrue(rangeOpenToOpen.intersects(range5To17), "A range of Open to Open must intersect one of 5 to 17")
        assertTrue(rangeOpenToOpen.intersects(range10To23), "A range of Open to Open must intersect one of 10 to 23")
        assertTrue(rangeOpenToOpen.intersects(range17ToOpen), "A range of Open to Open must intersect one of 17 to Open")
        assertTrue(rangeOpenToOpen.intersects(rangeOpenTo23), "A range of Open to Open must intersect one of Open to 23")
        assertTrue(rangeOpenToOpen.intersects(rangeOpenTo5), "A range of Open to Open must intersect one of Open to 5")
        assertTrue(rangeOpenToOpen.intersects(range10ToOpen), "A range of Open to Open must intersect one of 10 to Open")
        assertTrue(rangeOpenToOpen.intersects(rangeOpenToOpen), "A range of Open to Open must intersect one of Open to Open")
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

        assertTrue(rangeEmpty.intersection(rangeEmpty).isEmpty, "A range of nothing must not intersect one of nothing")
        assertTrue(rangeEmpty.intersection(rangeOnly5).isEmpty, "A range of nothing must not intersect one of only 5 at 5 only")
        assertTrue(rangeEmpty.intersection(rangeOnly10).isEmpty, "A range of nothing must not not intersect one of only 10")
        assertTrue(rangeEmpty.intersection(range5To17).isEmpty, "A range of nothing must not intersect one of 5 to 17 at 5 only")
        assertTrue(rangeEmpty.intersection(range10To23).isEmpty, "A range of nothing must not not intersect one of 10 to 23")
        assertTrue(rangeEmpty.intersection(range17ToOpen).isEmpty, "A range of nothing must not not intersect one of 17 to Open")
        assertTrue(rangeEmpty.intersection(rangeOpenTo23).isEmpty, "A range of nothing must not intersect one of Open to 23 at 5 only")
        assertTrue(rangeEmpty.intersection(rangeOpenTo5).isEmpty, "A range of nothing must not intersect one of Open to 5 at 5 only")
        assertTrue(rangeEmpty.intersection(range10ToOpen).isEmpty, "A range of nothing must not not intersect one of 10 to Open")
        assertTrue(rangeEmpty.intersection(rangeOpenToOpen).isEmpty, "A range of nothing must not intersect one of Open to Open at 5 only")


        // MARK: Single value A:

        assertTrue(rangeOnly5.intersection(rangeEmpty).isEmpty, "A range of only 5 must not intersect one of only empty")
        assertTrue(rangeOnly5.intersection(rangeOnly5).equals(rangeOnly5), "A range of only 5 must intersect one of only 5 at 5 only")
        assertTrue(rangeOnly5.intersection(rangeOnly10).isEmpty, "A range of only 5 must not intersect one of only 10")
        assertTrue(rangeOnly5.intersection(range5To17).equals(rangeOnly5), "A range of only 5 must intersect one of 5 to 17 at 5 only")
        assertTrue(rangeOnly5.intersection(range10To23).isEmpty, "A range of only 5 must not intersect one of 10 to 23")
        assertTrue(rangeOnly5.intersection(range17ToOpen).isEmpty, "A range of only 5 must not intersect one of 17 to Open")
        assertTrue(rangeOnly5.intersection(rangeOpenTo23).equals(rangeOnly5), "A range of only 5 must intersect one of Open to 23 at 5 only")
        assertTrue(rangeOnly5.intersection(rangeOpenTo5).equals(rangeOnly5), "A range of only 5 must intersect one of Open to 5 at 5 only")
        assertTrue(rangeOnly5.intersection(range10ToOpen).isEmpty, "A range of only 5 must not intersect one of 10 to Open")
        assertTrue(rangeOnly5.intersection(rangeOpenToOpen).equals(rangeOnly5), "A range of only 5 must intersect one of Open to Open at 5 only")


        // MARK: Single value B:

        assertTrue(rangeOnly10.intersection(rangeEmpty).isEmpty, "A range of only 10 must not intersect one of only empty")
        assertTrue(rangeOnly10.intersection(rangeOnly5).isEmpty, "A range of only 10 must not intersect one of only 5")
        assertTrue(rangeOnly10.intersection(rangeOnly10).equals(rangeOnly10), "A range of only 10 must intersect one of only 10")
        assertTrue(rangeOnly10.intersection(range5To17).equals(rangeOnly10), "A range of only 10 must intersect one of 5 to 17")
        assertTrue(rangeOnly10.intersection(range10To23).equals(rangeOnly10), "A range of only 10 must intersect one of 10 to 23")
        assertTrue(rangeOnly10.intersection(range17ToOpen).isEmpty, "A range of only 10 must not intersect one of 17 to Open")
        assertTrue(rangeOnly10.intersection(rangeOpenTo23).equals(rangeOnly10), "A range of only 10 must intersect one of Open to 23")
        assertTrue(rangeOnly10.intersection(rangeOpenTo5).isEmpty, "A range of only 10 must not intersect one of Open to 5")
        assertTrue(rangeOnly10.intersection(range10ToOpen).equals(rangeOnly10), "A range of only 10 must intersect one of 10 to Open")
        assertTrue(rangeOnly10.intersection(rangeOpenToOpen).equals(rangeOnly10), "A range of only 10 must intersect one of Open to Open")


        // MARK: Closed range A:

        assertTrue(range5To17.intersection(rangeEmpty).isEmpty, "A range of 5 to 17 must not intersect one of only empty")
        assertTrue(range5To17.intersection(rangeOnly5).equals(rangeOnly5), "A range of 5 to 17 must intersect one of only 5 at 5 only")
        assertTrue(range5To17.intersection(rangeOnly10).equals(rangeOnly10), "A range of 5 to 17 must intersect one of only 10 at 10 only")
        assertTrue(range5To17.intersection(range5To17).equals(range5To17), "A range of 5 to 17 must intersect one of 5 to 17 completely")
        assertTrue(range5To17.intersection(range10To23).equals(range10To17), "A range of 5 to 17 must intersect one of 10 to 23 at 10 to 17")
        assertTrue(range5To17.intersection(range17ToOpen).equals(rangeOnly17), "A range of 5 to 17 must intersect one of 17 to Open at 17 only")
        assertTrue(range5To17.intersection(rangeOpenTo23).equals(range5To17), "A range of 5 to 17 must intersect one of Open to 23 at 5 to 17")
        assertTrue(range5To17.intersection(rangeOpenTo5).equals(rangeOnly5), "A range of 5 to 17 must intersect one of Open to 5 at only 7")
        assertTrue(range5To17.intersection(range10ToOpen).equals(range10To17), "A range of 5 to 17 must intersect one of 10 to Open")
        assertTrue(range5To17.intersection(rangeOpenToOpen).equals(range5To17), "A range of 5 to 17 must intersect one of Open to Open")


        // MARK: Closed range B:

        assertTrue(range10To23.intersection(rangeEmpty).isEmpty, "A range of 10 to 23 must not intersect one of only empty")
        assertTrue(range10To23.intersection(rangeOnly5).isEmpty, "A range of 10 to 23 must not intersect one of only 5")
        assertTrue(range10To23.intersection(rangeOnly10).equals(rangeOnly10), "A range of 10 to 23 must intersect one of only 10 at 10 only")
        assertTrue(range10To23.intersection(range5To17).equals(range10To17), "A range of 10 to 23 must intersect one of 5 to 17 at 10 to 17")
        assertTrue(range10To23.intersection(range10To23).equals(range10To23), "A range of 10 to 23 must intersect one of 10 to 23 completely")
        assertTrue(range10To23.intersection(range17ToOpen).equals(range17To23), "A range of 10 to 23 must intersect one of 17 to Open at 17 to 23")
        assertTrue(range10To23.intersection(rangeOpenTo23).equals(range10To23), "A range of 10 to 23 must intersect one of Open to 23 at 10 to 23")
        assertTrue(range10To23.intersection(rangeOpenTo5).isEmpty, "A range of 10 to 23 must not intersect one of Open to 5")
        assertTrue(range10To23.intersection(range10ToOpen).equals(range10To23), "A range of 10 to 23 must intersect one of 10 to Open at 10 to 23")
        assertTrue(range10To23.intersection(rangeOpenToOpen).equals(range10To23), "A range of 10 to 23 must intersect one of Open to Open at 10 to 23")


        // MARK: Right-open range A:

        assertTrue(range17ToOpen.intersection(rangeEmpty).isEmpty, "A range of 17 to Open must not intersect one of only empty")
        assertTrue(range17ToOpen.intersection(rangeOnly5).isEmpty, "A range of 17 to Open must not intersect one of only 5")
        assertTrue(range17ToOpen.intersection(rangeOnly10).isEmpty, "A range of 17 to Open must not intersect one of only 10")
        assertTrue(range17ToOpen.intersection(range5To17).equals(rangeOnly17), "A range of 17 to Open must intersect one of 5 to 17 at 5 to 17")
        assertTrue(range17ToOpen.intersection(range10To23).equals(range17To23), "A range of 17 to Open must intersect one of 10 to 23 at 17 to 23")
        assertTrue(range17ToOpen.intersection(range17ToOpen).equals(range17ToOpen), "A range of 17 to Open must intersect one of 17 to Open completely")
        assertTrue(range17ToOpen.intersection(rangeOpenTo23).equals(range17To23), "A range of 17 to Open must intersect one of Open to 23 at 17 to 23")
        assertTrue(range17ToOpen.intersection(rangeOpenTo5).isEmpty, "A range of 17 to Open must not intersect one of Open to 5")
        assertTrue(range17ToOpen.intersection(range10ToOpen).equals(range17ToOpen), "A range of 17 to Open must intersect one of 10 to Open at 17 to Open")
        assertTrue(range17ToOpen.intersection(rangeOpenToOpen).equals(range17ToOpen), "A range of 17 to Open must intersect one of Open to Open at 17 to Open")


        // MARK: Left-open range B:

        assertTrue(rangeOpenTo23.intersection(rangeEmpty).isEmpty, "A range of Open to 23 must not intersect one of only empty")
        assertTrue(rangeOpenTo23.intersection(rangeOnly5).equals(rangeOnly5), "A range of Open to 23 must intersect one of only 5 at 5 only")
        assertTrue(rangeOpenTo23.intersection(rangeOnly10).equals(rangeOnly10), "A range of Open to 23 must intersect one of only 10 at 10 only")
        assertTrue(rangeOpenTo23.intersection(range5To17).equals(range5To17), "A range of Open to 23 must intersect one of 5 to 17 at 5 to 17")
        assertTrue(rangeOpenTo23.intersection(range10To23).equals(range10To23), "A range of Open to 23 must intersect one of 10 to 23 at 10 to 23")
        assertTrue(rangeOpenTo23.intersection(range17ToOpen).equals(range17To23), "A range of Open to 23 must intersect one of 17 to Open at 17 to 23")
        assertTrue(rangeOpenTo23.intersection(rangeOpenTo23).equals(rangeOpenTo23), "A range of Open to 23 must intersect one of Open to 23 completely")
        assertTrue(rangeOpenTo23.intersection(rangeOpenTo5).equals(rangeOpenTo5), "A range of Open to 23 must intersect one of Open to 5 at Open to 5")
        assertTrue(rangeOpenTo23.intersection(range10ToOpen).equals(range10To23), "A range of Open to 23 must intersect one of 10 to Open at 10 to 23")
        assertTrue(rangeOpenTo23.intersection(rangeOpenToOpen).equals(rangeOpenTo23), "A range of Open to 23 must intersect one of Open to Open at Open to 23")


        // MARK: Left-open range A:

        assertTrue(rangeOpenTo5.intersection(rangeEmpty).isEmpty, "A range of Open to 5 must not intersect one of only empty")
        assertTrue(rangeOpenTo5.intersection(rangeOnly5).equals(rangeOnly5), "A range of Open to 5 must intersect one of only 5 at 5 only")
        assertTrue(rangeOpenTo5.intersection(rangeOnly10).isEmpty, "A range of Open to 5 must not intersect one of only 10")
        assertTrue(rangeOpenTo5.intersection(range5To17).equals(rangeOnly5), "A range of Open to 5 must intersect one of 5 to 17 at 5 only")
        assertTrue(rangeOpenTo5.intersection(range10To23).isEmpty, "A range of Open to 5 must not intersect one of 10 to 23")
        assertTrue(rangeOpenTo5.intersection(range17ToOpen).isEmpty, "A range of Open to 5 must not intersect one of 17 to Open")
        assertTrue(rangeOpenTo5.intersection(rangeOpenTo23).equals(rangeOpenTo5), "A range of Open to 5 must intersect one of Open to 23 at Open to 5")
        assertTrue(rangeOpenTo5.intersection(rangeOpenTo5).equals(rangeOpenTo5), "A range of Open to 5 must intersect one of Open to 5 completely")
        assertTrue(rangeOpenTo5.intersection(range10ToOpen).isEmpty, "A range of Open to 5 must not intersect one of 10 to Open")
        assertTrue(rangeOpenTo5.intersection(rangeOpenToOpen).equals(rangeOpenTo5), "A range of Open to 5 must intersect one of Open to Open at Open to 5")


        // MARK: Right-open range B:

        assertTrue(range10ToOpen.intersection(rangeEmpty).isEmpty, "A range of 10 to Open must not intersect one of only empty")
        assertTrue(range10ToOpen.intersection(rangeOnly5).isEmpty, "A range of 10 to Open must not intersect one of only 5")
        assertTrue(range10ToOpen.intersection(rangeOnly10).equals(rangeOnly10), "A range of 10 to Open must intersect one of only 10 at 10 only")
        assertTrue(range10ToOpen.intersection(range5To17).equals(range10To17), "A range of 10 to Open must intersect one of 5 to 17 at 10 to 17")
        assertTrue(range10ToOpen.intersection(range10To23).equals(range10To23), "A range of 10 to Open must intersect one of 10 to 23 at 10 to 23")
        assertTrue(range10ToOpen.intersection(range17ToOpen).equals(range17ToOpen), "A range of 10 to Open must intersect one of 17 to Open at 17 to Open")
        assertTrue(range10ToOpen.intersection(rangeOpenTo23).equals(range10To23), "A range of 10 to Open must intersect one of Open to 23 at 10 to 23")
        assertTrue(range10ToOpen.intersection(rangeOpenTo5).isEmpty, "A range of 10 to Open must not intersect one of Open to 5")
        assertTrue(range10ToOpen.intersection(range10ToOpen).equals(range10ToOpen), "A range of 10 to Open must intersect one of 10 to Open completely")
        assertTrue(range10ToOpen.intersection(rangeOpenToOpen).equals(range10ToOpen), "A range of 10 to Open must intersect one of Open to Open at 10 to Open")


        // MARK: Infinite range:

        assertTrue(rangeOpenToOpen.intersection(rangeEmpty).isEmpty, "A range of Open to Open must not intersect one of only empty")
        assertTrue(rangeOpenToOpen.intersection(rangeOnly5).equals(rangeOnly5), "A range of Open to Open must intersect one of only 5 at only 5")
        assertTrue(rangeOpenToOpen.intersection(rangeOnly10).equals(rangeOnly10), "A range of Open to Open must intersect one of only 10 at only 10")
        assertTrue(rangeOpenToOpen.intersection(range5To17).equals(range5To17), "A range of Open to Open must intersect one of 5 to 17 at 5 to 17")
        assertTrue(rangeOpenToOpen.intersection(range10To23).equals(range10To23), "A range of Open to Open must intersect one of 10 to 23 at 10 to 23")
        assertTrue(rangeOpenToOpen.intersection(range17ToOpen).equals(range17ToOpen), "A range of Open to Open must intersect one of 17 to Open at 17 to Open")
        assertTrue(rangeOpenToOpen.intersection(rangeOpenTo23).equals(rangeOpenTo23), "A range of Open to Open must intersect one of Open to 23 at Open to 23")
        assertTrue(rangeOpenToOpen.intersection(rangeOpenTo5).equals(rangeOpenTo5), "A range of Open to Open must intersect one of Open to 5 at Open to 5")
        assertTrue(rangeOpenToOpen.intersection(range10ToOpen).equals(range10ToOpen), "A range of Open to Open must intersect one of 10 to Open at 10 to Open")
        assertTrue(rangeOpenToOpen.intersection(rangeOpenToOpen).equals(rangeOpenToOpen), "A range of Open to Open must intersect one of Open to Open completely")


        val closedIntersectClosed = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.intersection(range10To23)
        }
        println("It took $closedIntersectClosed seconds to calculate $range5To17 ∩ $range10To23 $intersectionTrialCount times")

        val closedIntersectOpen = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.intersection(range10ToOpen)
        }
        println("It took $closedIntersectOpen seconds to calculate $range5To17 ∩ $range10ToOpen $intersectionTrialCount times")

        val closedIntersectPoint = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.intersection(rangeOnly10)
        }
        println("It took $closedIntersectPoint seconds to calculate $range5To17 ∩ $rangeOnly10 $intersectionTrialCount times")
    }

    @Test
    fun containsCompletely() {


        // MARK: Single value A:

        assertTrue(rangeOnly5.containsCompletely(rangeOnly5), "A range of only 5 must completely contain one of only 5")
        assertFalse(rangeOnly5.containsCompletely(rangeOnly10), "A range of only 5 must not completely contain one of only 10")
        assertFalse(rangeOnly5.containsCompletely(range5To17), "A range of only 5 must not completely contain one of 5 to 17")
        assertFalse(rangeOnly5.containsCompletely(range10To23), "A range of only 5 must not completely contain one of 10 to 23")
        assertFalse(rangeOnly5.containsCompletely(range17ToOpen), "A range of only 5 must not completely contain one of 17 to Open")
        assertFalse(rangeOnly5.containsCompletely(rangeOpenTo23), "A range of only 5 must not completely contain one of Open to 23")
        assertFalse(rangeOnly5.containsCompletely(rangeOpenTo5), "A range of only 5 must not completely contain one of Open to 5")
        assertFalse(rangeOnly5.containsCompletely(range10ToOpen), "A range of only 5 must not completely contain one of 10 to Open")
        assertFalse(rangeOnly5.containsCompletely(rangeOpenToOpen), "A range of only 5 must not completely contain one of Open to Open")


        // MARK: Single value B:

        assertFalse(rangeOnly10.containsCompletely(rangeOnly5), "A range of only 10 must not completely contain one of only 5")
        assertTrue(rangeOnly10.containsCompletely(rangeOnly10), "A range of only 10 must completely contain one of only 10")
        assertFalse(rangeOnly10.containsCompletely(range5To17), "A range of only 10 must not completely contain one of 5 to 17")
        assertFalse(rangeOnly10.containsCompletely(range10To23), "A range of only 10 must not completely contain one of 10 to 23")
        assertFalse(rangeOnly10.containsCompletely(range17ToOpen), "A range of only 10 must not completely contain one of 17 to Open")
        assertFalse(rangeOnly10.containsCompletely(rangeOpenTo23), "A range of only 10 must not completely contain one of Open to 23")
        assertFalse(rangeOnly10.containsCompletely(rangeOpenTo5), "A range of only 10 must not completely contain one of Open to 5")
        assertFalse(rangeOnly10.containsCompletely(range10ToOpen), "A range of only 10 must not completely contain one of 10 to Open")
        assertFalse(rangeOnly10.containsCompletely(rangeOpenToOpen), "A range of only 10 must not completely contain one of Open to Open")


        // MARK: Closed range A:

        assertTrue(range5To17.containsCompletely(rangeOnly5), "A range of 5 to 17 must completely contain one of only 5")
        assertTrue(range5To17.containsCompletely(rangeOnly10), "A range of 5 to 17 must completely contain one of only 10")
        assertTrue(range5To17.containsCompletely(range5To17), "A range of 5 to 17 must completely contain one of 5 to 17")
        assertFalse(range5To17.containsCompletely(range10To23), "A range of 5 to 17 must not completely contain one of 10 to 23")
        assertFalse(range5To17.containsCompletely(range17ToOpen), "A range of 5 to 17 must not completely contain one of 17 to Open")
        assertFalse(range5To17.containsCompletely(rangeOpenTo23), "A range of 5 to 17 must not completely contain one of Open to 23")
        assertFalse(range5To17.containsCompletely(rangeOpenTo5), "A range of 5 to 17 must not completely contain one of Open to 5")
        assertFalse(range5To17.containsCompletely(range10ToOpen), "A range of 5 to 17 must not completely contain one of 10 to Open")
        assertFalse(range5To17.containsCompletely(rangeOpenToOpen), "A range of 5 to 17 must not completely contain one of Open to Open")


        // MARK: Closed range B:

        assertFalse(range10To23.containsCompletely(rangeOnly5), "A range of 10 to 23 must not completely contain one of only 5")
        assertTrue(range10To23.containsCompletely(rangeOnly10), "A range of 10 to 23 must completely contain one of only 10")
        assertFalse(range10To23.containsCompletely(range5To17), "A range of 10 to 23 must not completely contain one of 5 to 17")
        assertTrue(range10To23.containsCompletely(range10To23), "A range of 10 to 23 must completely contain one of 10 to 23")
        assertFalse(range10To23.containsCompletely(range17ToOpen), "A range of 10 to 23 must not completely contain one of 17 to Open")
        assertFalse(range10To23.containsCompletely(rangeOpenTo23), "A range of 10 to 23 must not completely contain one of Open to 23")
        assertFalse(range10To23.containsCompletely(rangeOpenTo5), "A range of 10 to 23 must not completely contain one of Open to 5")
        assertFalse(range10To23.containsCompletely(range10ToOpen), "A range of 10 to 23 must not completely contain one of 10 to Open")
        assertFalse(range10To23.containsCompletely(rangeOpenToOpen), "A range of 10 to 23 must not completely contain one of Open to Open")


        // MARK: Right-open range A:

        assertFalse(range17ToOpen.containsCompletely(rangeOnly5), "A range of 17 to Open must not completely contain one of only 5")
        assertFalse(range17ToOpen.containsCompletely(rangeOnly10), "A range of 17 to Open must not completely contain one of only 10")
        assertFalse(range17ToOpen.containsCompletely(range5To17), "A range of 17 to Open must not completely contain one of 5 to 17")
        assertFalse(range17ToOpen.containsCompletely(range10To23), "A range of 17 to Open must not completely contain one of 10 to 23")
        assertTrue(range17ToOpen.containsCompletely(range17ToOpen), "A range of 17 to Open must completely contain one of 17 to Open")
        assertFalse(range17ToOpen.containsCompletely(rangeOpenTo23), "A range of 17 to Open must not completely contain one of Open to 23")
        assertFalse(range17ToOpen.containsCompletely(rangeOpenTo5), "A range of 17 to Open must not completely contain one of Open to 5")
        assertFalse(range17ToOpen.containsCompletely(range10ToOpen), "A range of 17 to Open must not completely contain one of 10 to Open")
        assertFalse(range17ToOpen.containsCompletely(rangeOpenToOpen), "A range of 17 to Open must not completely contain one of Open to Open")


        // MARK: Left-open range B:

        assertTrue(rangeOpenTo23.containsCompletely(rangeOnly5), "A range of Open to 23 must completely contain one of only 5")
        assertTrue(rangeOpenTo23.containsCompletely(rangeOnly10), "A range of Open to 23 must completely contain one of only 10")
        assertTrue(rangeOpenTo23.containsCompletely(range5To17), "A range of Open to 23 must completely contain one of 5 to 17")
        assertTrue(rangeOpenTo23.containsCompletely(range10To23), "A range of Open to 23 must completely contain one of 10 to 23")
        assertFalse(rangeOpenTo23.containsCompletely(range17ToOpen), "A range of Open to 23 must not completely contain one of 17 to Open")
        assertTrue(rangeOpenTo23.containsCompletely(rangeOpenTo23), "A range of Open to 23 must completely contain one of Open to 23")
        assertTrue(rangeOpenTo23.containsCompletely(rangeOpenTo5), "A range of Open to 23 must completely contain one of Open to 5")
        assertFalse(rangeOpenTo23.containsCompletely(range10ToOpen), "A range of Open to 23 must not completely contain one of 10 to Open")
        assertFalse(rangeOpenTo23.containsCompletely(rangeOpenToOpen), "A range of Open to 23 must completely contain one of Open to Open")


        // MARK: Left-open range A:

        assertTrue(rangeOpenTo5.containsCompletely(rangeOnly5), "A range of Open to 5 must completely contain one of only 5")
        assertFalse(rangeOpenTo5.containsCompletely(rangeOnly10), "A range of Open to 5 must not completely contain one of only 10")
        assertFalse(rangeOpenTo5.containsCompletely(range5To17), "A range of Open to 5 must not completely contain one of 5 to 17")
        assertFalse(rangeOpenTo5.containsCompletely(range10To23), "A range of Open to 5 must not completely contain one of 10 to 23")
        assertFalse(rangeOpenTo5.containsCompletely(range17ToOpen), "A range of Open to 5 must not completely contain one of 17 to Open")
        assertFalse(rangeOpenTo5.containsCompletely(rangeOpenTo23), "A range of Open to 5 must not completely contain one of Open to 23")
        assertTrue(rangeOpenTo5.containsCompletely(rangeOpenTo5), "A range of Open to 5 must completely contain one of Open to 5")
        assertFalse(rangeOpenTo5.containsCompletely(range10ToOpen), "A range of Open to 5 must not completely contain one of 10 to Open")
        assertFalse(rangeOpenTo5.containsCompletely(rangeOpenToOpen), "A range of Open to 5 must not completely contain one of Open to Open")


        // MARK: Right-open range B:

        assertFalse(range10ToOpen.containsCompletely(rangeOnly5), "A range of 10 to Open must not completely contain one of only 5")
        assertTrue(range10ToOpen.containsCompletely(rangeOnly10), "A range of 10 to Open must completely contain one of only 10")
        assertFalse(range10ToOpen.containsCompletely(range5To17), "A range of 10 to Open must not completely contain one of 5 to 17")
        assertTrue(range10ToOpen.containsCompletely(range10To23), "A range of 10 to Open must completely contain one of 10 to 23")
        assertTrue(range10ToOpen.containsCompletely(range17ToOpen), "A range of 10 to Open must completely contain one of 17 to Open")
        assertFalse(range10ToOpen.containsCompletely(rangeOpenTo23), "A range of 10 to Open must not completely contain one of Open to 23")
        assertFalse(range10ToOpen.containsCompletely(rangeOpenTo5), "A range of 10 to Open must not completely contain one of Open to 5")
        assertTrue(range10ToOpen.containsCompletely(range10ToOpen), "A range of 10 to Open must completely contain one of 10 to Open")
        assertFalse(range10ToOpen.containsCompletely(rangeOpenToOpen), "A range of 10 to Open must not completely contain one of Open to Open")


        // MARK: Infinite range:

        assertTrue(rangeOpenToOpen.containsCompletely(rangeOnly5), "A range of Open to Open must completely contain one of only 5")
        assertTrue(rangeOpenToOpen.containsCompletely(rangeOnly10), "A range of Open to Open must completely contain one of only 10")
        assertTrue(rangeOpenToOpen.containsCompletely(range5To17), "A range of Open to Open must completely contain one of 5 to 17")
        assertTrue(rangeOpenToOpen.containsCompletely(range10To23), "A range of Open to Open must completely contain one of 10 to 23")
        assertTrue(rangeOpenToOpen.containsCompletely(range17ToOpen), "A range of Open to Open must completely contain one of 17 to Open")
        assertTrue(rangeOpenToOpen.containsCompletely(rangeOpenTo23), "A range of Open to Open must completely contain one of Open to 23")
        assertTrue(rangeOpenToOpen.containsCompletely(rangeOpenTo5), "A range of Open to Open must completely contain one of Open to 5")
        assertTrue(rangeOpenToOpen.containsCompletely(range10ToOpen), "A range of Open to Open must completely contain one of 10 to Open")
        assertTrue(rangeOpenToOpen.containsCompletely(rangeOpenToOpen), "A range of Open to Open must completely contain one of Open to Open completely")


        val closedIntersectClosed = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.containsCompletely(range10To23)
        }
        println("It took $closedIntersectClosed seconds to calculate $range5To17 ⊆ $range10To23 $intersectionTrialCount times")

        val closedIntersectOpen = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.containsCompletely(range10ToOpen)
        }
        println("It took $closedIntersectOpen seconds to calculate $range5To17 ⊆ $range10ToOpen $intersectionTrialCount times")

        val closedIntersectPoint = measureTimeInterval(trials = intersectionTrialCount, mode = total) {
            range5To17.containsCompletely(rangeOnly10)
        }
        println("It took $closedIntersectPoint seconds to calculate $range5To17 ⊆ $rangeOnly10 $intersectionTrialCount times")
    }

}