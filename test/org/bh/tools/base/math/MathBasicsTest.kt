package org.bh.tools.base.math


import junit.framework.TestCase.*
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.util.assertThrows
import org.junit.Test


/**
 * @author Kyli Rouge
 * *
 * @since 2017-03-21 021.
 */
class MathBasicsTest {

    @Test
    fun isEven() {
        assertTrue("0 is even", 0L.isEven)
        assertTrue("2 is even", 2L.isEven)
        assertTrue("12 is even", 12L.isEven)
        assertTrue("-8 is even", (-8L).isEven)
        assertTrue("98641532 is even", 98641532L.isEven)
        assertTrue("Integer.min is even", Integer.min.isEven)

        assertFalse("1 is not even", 1L.isEven)
        assertFalse("11 is not even", 11L.isEven)
        assertFalse("98641533 is not even", 98641533L.isEven)
        assertFalse("Integer.max is not even", Integer.max.isEven)
    }


    @Test
    fun isOdd() {
        assertTrue("1 is odd", 1L.isOdd)
        assertTrue("11 is odd", 11L.isOdd)
        assertTrue("98641533 is odd", 98641533L.isOdd)
        assertTrue("Integer.max is odd", Integer.max.isOdd)

        assertFalse("0 is not odd", 0L.isOdd)
        assertFalse("2 is not odd", 2L.isOdd)
        assertFalse("12 is not odd", 12L.isOdd)
        assertFalse("-8 is not odd", (-8L).isOdd)
        assertFalse("98641532 is not odd", 98641532L.isOdd)
        assertFalse("Integer.min is not odd", Integer.min.isOdd)
    }


    @Test
    fun toThePowerOfInteger() {
        assertEquals("3^5 should be 243", 243L, 3L.toThePowerOf(5L))
        assertEquals("3^0 should be 1", 1L, 3L.toThePowerOf(0L))
        assertEquals("3^-1 should be 0", 0L, 3L.toThePowerOf(-1L))
        assertEquals("3^-100 should be 0", 0L, 3L.toThePowerOf(-100L))
        assertEquals("3^-Integer.min should be 0", 0L, 3L.toThePowerOf(Integer.min))

        assertEquals("0^5 should be 0", 0L, 0L.toThePowerOf(5L))
        assertEquals("1^4 should be 1", 1L, 1L.toThePowerOf(4L))
        assertEquals("2^3 should be 8", 8L, 2L.toThePowerOf(3L))
        assertEquals("3^2 should be 9", 9L, 3L.toThePowerOf(2L))
        assertEquals("4^1 should be 4", 4L, 4L.toThePowerOf(1L))
        assertEquals("5^0 should be 1", 1L, 5L.toThePowerOf(0L))

        assertEquals("10^3 should be 1,000", 1_000L, 10L.toThePowerOf(3L))
        assertEquals("10^8 should be 100,000,000", 100_000_000L, 10L.toThePowerOf(8L))

        assertEquals("2^3 should be 0b1000", 0b1000L, 2L.toThePowerOf(3L))
        assertEquals("2^16 should be 65,536", 65_536L, 2L.toThePowerOf(16L))

        assertEquals("Since all powers of 1 are 1, 1^5 should be 1", 1L, 1L.toThePowerOf(5L))
        assertEquals("Since all powers of 1 are 1, 1^0 should be 1", 1L, 1L.toThePowerOf(0L))
        assertEquals("Since all powers of 1 are 1, 1^-1 should be 1", 1L, 1L.toThePowerOf(-1L))
        assertEquals("Since all powers of 1 are 1, 1^-100 should be 1", 1L, 1L.toThePowerOf(-100L))
        assertEquals("Since all powers of 1 are 1, 1^Integer.min should be 1", 1L, 1L.toThePowerOf(Integer.min))

        assertEquals("Since all powers of 1 are 1, 1^5 should be 1", 1L, 1L.toThePowerOf(5L))
        assertEquals("Since all powers of 1 are 1, 1^4 should be 1", 1L, 1L.toThePowerOf(4L))
        assertEquals("Since all powers of 1 are 1, 1^3 should be 1", 1L, 1L.toThePowerOf(3L))
        assertEquals("Since all powers of 1 are 1, 1^2 should be 1", 1L, 1L.toThePowerOf(2L))
        assertEquals("Since all powers of 1 are 1, 1^1 should be 1", 1L, 1L.toThePowerOf(1L))
        assertEquals("Since all powers of 1 are 1, 1^0 should be 1", 1L, 1L.toThePowerOf(0L))

        assertThrows("Since 0 to a negative power is undefined, 0^-1 should throw an exception", { 0L.toThePowerOf(-1L) })
        assertThrows("Since 0 to a negative power is undefined, 0^-100 should throw an exception", { 0L.toThePowerOf(-100L) })
        assertThrows("Since 0 to a negative power is undefined, 0^Integer.min should throw an exception", { 0L.toThePowerOf(Integer.min) })
        assertThrows("0 raised to any power is 0, but anything raised to the power of 0 is 1, so 0^0 should throw an exception", { 0.toThePowerOf(0) })

        assertEquals("Since all powers of 0 are 0, 0^5 should be 0", 0L, 0L.toThePowerOf(5L))
        assertEquals("Since all powers of 0 are 0, 0^4 should be 0", 0L, 0L.toThePowerOf(4L))
        assertEquals("Since all powers of 0 are 0, 0^3 should be 0", 0L, 0L.toThePowerOf(3L))
        assertEquals("Since all powers of 0 are 0, 0^2 should be 0", 0L, 0L.toThePowerOf(2L))
        assertEquals("Since all powers of 0 are 0, 0^1 should be 0", 0L, 0L.toThePowerOf(1L))


        assertEquals("Even powers of -1 are 1, so -1^0 should be 1", 1L, (-1L).toThePowerOf(0L))
        assertEquals("Even powers of -1 are 1, so -1^2 should be 1", 1L, (-1L).toThePowerOf(2L))
        assertEquals("Even powers of -1 are 1, so -1^8941636 should be 1", 1L, (-1L).toThePowerOf(8941636L))
        assertEquals("Even powers of -1 are 1, so -1^-210 should be 1", 1L, (-1L).toThePowerOf(-210L))
//        assertEquals("Even powers of -1 are 1, so -1^Integer.min should be 1", 1L, (-1L).toThePowerOf(Integer.min)) FIXME: This extreme edge case should pass

        assertEquals("Odd powers of -1 are -1, so -1^1 should be 1", -1L, (-1L).toThePowerOf(1L))
        assertEquals("Odd powers of -1 are -1, so -1^3 should be 1", -1L, (-1L).toThePowerOf(3L))
        assertEquals("Odd powers of -1 are -1, so -1^8941637 should be 1", -1L, (-1L).toThePowerOf(8941637L))
        assertEquals("Odd powers of -1 are -1, so -1^-321 should be 1", -1L, (-1L).toThePowerOf(-321L))
        assertEquals("Odd powers of -1 are -1, so -1^Integer.max should be 1", -1L, (-1L).toThePowerOf(Int32.max.integerValue))
    }


    @Test
    fun toThePowerOfFraction() {
        assertEquals("3^5 should be 243", 243.0, 3.0.toThePowerOf(5.0), defaultFractionCalculationTolerance)
        assertEquals("3^0 should be 1", 1.0, 3.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("3^-1 should be 1/3", 1.0/3.0, 3.0.toThePowerOf(-1.0), defaultFractionCalculationTolerance)
        assertEquals("3^-10 should be 1/59049", 1.0/59049.0, 3.0.toThePowerOf(-10.0), defaultFractionCalculationTolerance)
        assertEquals("3^-100 should be effectively 0", 0.0, 3.0.toThePowerOf(-100.0), defaultFractionCalculationTolerance)
        assertEquals("3^-Integer.min should be effectively 0", 0.0, 3.0.toThePowerOf(Integer.min.fractionValue), defaultFractionCalculationTolerance)
        assertEquals("3^-infinity should be 0", 0.0, 3.0.toThePowerOf(-Fraction.infinity), defaultFractionCalculationTolerance)
        assertEquals("3^infinity should be infinity", Fraction.infinity, 3.0.toThePowerOf(Fraction.infinity), defaultFractionCalculationTolerance)

        assertEquals("0^5 should be 0", 0.0, 0.0.toThePowerOf(5.0), defaultFractionCalculationTolerance)
        assertEquals("1^4 should be 1", 1.0, 1.0.toThePowerOf(4.0), defaultFractionCalculationTolerance)
        assertEquals("2^3 should be 8", 8.0, 2.0.toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("3^2 should be 9", 9.0, 3.0.toThePowerOf(2.0), defaultFractionCalculationTolerance)
        assertEquals("4^1 should be 4", 4.0, 4.0.toThePowerOf(1.0), defaultFractionCalculationTolerance)
        assertEquals("5^0 should be 1", 1.0, 5.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)

        assertEquals("Anything to the 0 power is 1, so -infinity^0 should be 1", 1.0, (-Fraction.infinity).toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so Integer.min^0 should be 1", 1.0, Integer.min.fractionValue.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so -5^0 should be 1", 1.0, (-5.0).toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so 1^0 should be 1", 1.0, 1.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so 5^0 should be 1", 1.0, 5.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so Integer.max^0 should be 1", 1.0, Integer.max.fractionValue.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Anything to the 0 power is 1, so infinity^0 should be 1", 1.0, Fraction.infinity.toThePowerOf(0.0), defaultFractionCalculationTolerance)

        assertEquals("10^3 should be 1,000", 1_000.0, 10.0.toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("10^8 should be 100,000,000", 100_000_000.0, 10.0.toThePowerOf(8.0), defaultFractionCalculationTolerance)

        assertEquals("2^3 should be 8", 8.0, 2.0.toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("2^16 should be 65,536", 65_536.0, 2.0.toThePowerOf(16.0), defaultFractionCalculationTolerance)

        assertEquals("All powers of 1 are 1, 1^5 should be 1", 1.0, 1.0.toThePowerOf(5.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^0 should be 1", 1.0, 1.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^-1 should be 1", 1.0, 1.0.toThePowerOf(-1.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^-100 should be 1", 1.0, 1.0.toThePowerOf(-100.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^Integer.min should be 1", 1.0, 1.0.toThePowerOf(Integer.min.fractionValue), defaultFractionCalculationTolerance)

        assertEquals("All powers of 1 are 1, 1^5 should be 1", 1.0, 1.0.toThePowerOf(5.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^4 should be 1", 1.0, 1.0.toThePowerOf(4.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^3 should be 1", 1.0, 1.0.toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^2 should be 1", 1.0, 1.0.toThePowerOf(2.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^1 should be 1", 1.0, 1.0.toThePowerOf(1.0), defaultFractionCalculationTolerance)
        assertEquals("All powers of 1 are 1, 1^0 should be 1", 1.0, 1.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)

        assertEquals("Zero to a negative power is undefined, 0^-1 should throw an exception", Fraction.nan, 0.0.toThePowerOf(-1.0))
        assertEquals("Zero to a negative power is undefined, 0^-100 should throw an exception", Fraction.nan, 0.0.toThePowerOf(-100.0))
        assertEquals("Zero to a negative power is undefined, 0^Integer.min should throw an exception", Fraction.nan, 0.0.toThePowerOf(Integer.min.fractionValue))

        assertEquals("Since 0 raised to any power is 0, 0^5 should be 0", 0.0, 0.0.toThePowerOf(5.0), defaultFractionCalculationTolerance)
        assertEquals("Since 0 raised to any power is 0, 0^4 should be 0", 0.0, 0.0.toThePowerOf(4.0), defaultFractionCalculationTolerance)
        assertEquals("Since 0 raised to any power is 0, 0^3 should be 0", 0.0, 0.0.toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("Since 0 raised to any power is 0, 0^2 should be 0", 0.0, 0.0.toThePowerOf(2.0), defaultFractionCalculationTolerance)
        assertEquals("Since 0 raised to any power is 0, 0^1 should be 0", 0.0, 0.0.toThePowerOf(1.0), defaultFractionCalculationTolerance)

        assertEquals("0 raised to any power is 0, but anything raised to the power of 0 is 1, so 0^0 should be not a number", Fraction.nan, 0.0.toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("0 raised to any power is 0, but zero to a negative power us undefined, 0^-6 should be not a number", Fraction.nan, 0.0.toThePowerOf(-6.0), defaultFractionCalculationTolerance)


        assertEquals("Even powers of -1 are 1, so -1^0 should be 1", 1.0, (-1.0).toThePowerOf(0.0), defaultFractionCalculationTolerance)
        assertEquals("Even powers of -1 are 1, so -1^2 should be 1", 1.0, (-1.0).toThePowerOf(2.0), defaultFractionCalculationTolerance)
        assertEquals("Even powers of -1 are 1, so -1^8941636 should be 1", 1.0, (-1.0).toThePowerOf(8941636.0), defaultFractionCalculationTolerance)
        assertEquals("Even powers of -1 are 1, so -1^-210 should be 1", 1.0, (-1.0).toThePowerOf(-210.0), defaultFractionCalculationTolerance)
//        assertEquals("Even powers of -1 are 1, so -1^Integer.min should be 1", 1.0, (-1.0).toThePowerOf(Integer.min.fractionValue), defaultFractionCalculationTolerance) FIXME: This extreme edge case should pass

        assertEquals("Odd powers of -1 are -1, so -1^1 should be -1", -1.0, (-1.0).toThePowerOf(1.0), defaultFractionCalculationTolerance)
        assertEquals("Odd powers of -1 are -1, so -1^3 should be -1", -1.0, (-1.0).toThePowerOf(3.0), defaultFractionCalculationTolerance)
        assertEquals("Odd powers of -1 are -1, so -1^8941637 should be -1", -1.0, (-1.0).toThePowerOf(8941637.0), defaultFractionCalculationTolerance)
        assertEquals("Odd powers of -1 are -1, so -1^-321 should be -1", -1.0, (-1.0).toThePowerOf(-321.0), defaultFractionCalculationTolerance)
        assertEquals("Odd powers of -1 are -1, so -1^Integer.max should be -1", -1.0, (-1.0).toThePowerOf(Int32.max.fractionValue), defaultFractionCalculationTolerance)
    }
}
