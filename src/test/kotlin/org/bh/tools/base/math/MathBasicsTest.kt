package BlueBase


import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


/**
 * @author Kyli Rouge
 * *
 * @since 2017-03-21
 */
class MathBasicsTest {

    @Test
    fun isEven() {
        assertTrue(0L.isEven, "0 is even")
        assertTrue(2L.isEven, "2 is even")
        assertTrue(12L.isEven, "12 is even")
        assertTrue((-8L).isEven, "-8 is even")
        assertTrue(98641532L.isEven, "98641532 is even")
        assertTrue(Integer.min.isEven, "Integer.min is even")

        assertFalse(1L.isEven, "1 is not even")
        assertFalse(11L.isEven, "11 is not even")
        assertFalse(98641533L.isEven, "98641533 is not even")
        assertFalse(Integer.max.isEven, "Integer.max is not even")
    }


    @Test
    fun isOdd() {
        assertTrue(1L.isOdd, "1 is odd")
        assertTrue(11L.isOdd, "11 is odd")
        assertTrue(98641533L.isOdd, "98641533 is odd")
        assertTrue(Integer.max.isOdd, "Integer.max is odd")

        assertFalse(0L.isOdd, "0 is not odd")
        assertFalse(2L.isOdd, "2 is not odd")
        assertFalse(12L.isOdd, "12 is not odd")
        assertFalse((-8L).isOdd, "-8 is not odd")
        assertFalse(98641532L.isOdd, "98641532 is not odd")
        assertFalse(Integer.min.isOdd, "Integer.min is not odd")
    }


    @Test
    fun toThePowerOfInteger() {
        assertEquals(243L, 3L.toThePowerOf(5L), "3^5 should be 243")
        assertEquals(1L, 3L.toThePowerOf(0L), "3^0 should be 1")
        assertEquals(0L, 3L.toThePowerOf(-1L), "3^-1 should be 0")
        assertEquals(0L, 3L.toThePowerOf(-100L), "3^-100 should be 0")
        assertEquals(0L, 3L.toThePowerOf(Integer.min), "3^Integer.min should be 0")

        assertEquals(0L, 0L.toThePowerOf(5L), "0^5 should be 0")
        assertEquals(1L, 1L.toThePowerOf(4L), "1^4 should be 1")
        assertEquals(8L, 2L.toThePowerOf(3L), "2^3 should be 8")
        assertEquals(9L, 3L.toThePowerOf(2L), "3^2 should be 9")
        assertEquals(4L, 4L.toThePowerOf(1L), "4^1 should be 4")
        assertEquals(1L, 5L.toThePowerOf(0L), "5^0 should be 1")

        assertEquals(1_000L, 10L.toThePowerOf(3L), "10^3 should be 1,000")
        assertEquals(100_000_000L, 10L.toThePowerOf(8L), "10^8 should be 100,000,000")

        assertEquals(0b1000L, 2L.toThePowerOf(3L), "2^3 should be 0b1000")
        assertEquals(65_536L, 2L.toThePowerOf(16L), "2^16 should be 65,536")

        assertEquals(1L, 1L.toThePowerOf(5L), "Since all powers of 1 are 1, 1^5 should be 1")
        assertEquals(1L, 1L.toThePowerOf(0L), "Since all powers of 1 are 1, 1^0 should be 1")
        assertEquals(1L, 1L.toThePowerOf(-1L), "Since all powers of 1 are 1, 1^-1 should be 1")
        assertEquals(1L, 1L.toThePowerOf(-100L), "Since all powers of 1 are 1, 1^-100 should be 1")
        assertEquals(1L, 1L.toThePowerOf(Integer.min), "Since all powers of 1 are 1, 1^Integer.min should be 1")

        assertEquals(1L, 1L.toThePowerOf(5L), "Since all powers of 1 are 1, 1^5 should be 1")
        assertEquals(1L, 1L.toThePowerOf(4L), "Since all powers of 1 are 1, 1^4 should be 1")
        assertEquals(1L, 1L.toThePowerOf(3L), "Since all powers of 1 are 1, 1^3 should be 1")
        assertEquals(1L, 1L.toThePowerOf(2L), "Since all powers of 1 are 1, 1^2 should be 1")
        assertEquals(1L, 1L.toThePowerOf(1L), "Since all powers of 1 are 1, 1^1 should be 1")
        assertEquals(1L, 1L.toThePowerOf(0L), "Since all powers of 1 are 1, 1^0 should be 1")

        assertEquals(0L, 0L.toThePowerOf(5L), "Since all powers of 0 are 0, 0^5 should be 0")
        assertEquals(0L, 0L.toThePowerOf(4L), "Since all powers of 0 are 0, 0^4 should be 0")
        assertEquals(0L, 0L.toThePowerOf(3L), "Since all powers of 0 are 0, 0^3 should be 0")
        assertEquals(0L, 0L.toThePowerOf(2L), "Since all powers of 0 are 0, 0^2 should be 0")
        assertEquals(0L, 0L.toThePowerOf(1L), "Since all powers of 0 are 0, 0^1 should be 0")


        assertEquals(1L, (-1L).toThePowerOf(0L), "Even powers of -1 are 1, so -1^0 should be 1")
        assertEquals(1L, (-1L).toThePowerOf(2L), "Even powers of -1 are 1, so -1^2 should be 1")
        assertEquals(1L, (-1L).toThePowerOf(8941636L), "Even powers of -1 are 1, so -1^8941636 should be 1")
        assertEquals(1L, (-1L).toThePowerOf(-210L), "Even powers of -1 are 1, so -1^-210 should be 1")
//        assertEquals("Even powers of -1 are 1, so -1^Integer.min should be 1", 1L, (-1L).toThePowerOf(Integer.min)) FIXME: This extreme edge case should pass

        assertEquals(-1L, (-1L).toThePowerOf(1L), "Odd powers of -1 are -1, so -1^1 should be -1")
        assertEquals(-1L, (-1L).toThePowerOf(3L), "Odd powers of -1 are -1, so -1^3 should be -1")
        assertEquals(-1L, (-1L).toThePowerOf(8941637L), "Odd powers of -1 are -1, so -1^8941637 should be -1")
        assertEquals(-1L, (-1L).toThePowerOf(-321L), "Odd powers of -1 are -1, so -1^-321 should be -1")
        assertEquals(-1L, (-1L).toThePowerOf(Int32.max.integerValue), "Odd powers of -1 are -1, so -1^Integer.max should be -1")
    }


    @Test
    fun toThePowerOfFraction() {
        assertEquals(243.0, 3.0.toThePowerOf(5.0), tolerance = defaultCalculationTolerance, message = "3^5 should be 243")
        assertEquals(1.0, 3.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "3^0 should be 1")
        assertEquals(1.0/3.0, 3.0.toThePowerOf(-1.0), tolerance = defaultCalculationTolerance, message = "3^-1 should be 1/3")
        assertEquals(1.0/59049.0, 3.0.toThePowerOf(-10.0), tolerance = defaultCalculationTolerance, message = "3^-10 should be 1/59049")
        assertEquals(0.0, 3.0.toThePowerOf(-100.0), tolerance = defaultCalculationTolerance, message = "3^-100 should be effectively 0")
        assertEquals(0.0, 3.0.toThePowerOf(Integer.min.fractionValue), tolerance = defaultCalculationTolerance, message = "3^-Integer.min should be effectively 0")
        assertEquals(0.0, 3.0.toThePowerOf(-Fraction.infinity), tolerance = defaultCalculationTolerance, message = "3^-infinity should be 0")
        assertEquals(Fraction.infinity, 3.0.toThePowerOf(Fraction.infinity), tolerance = defaultCalculationTolerance, message = "3^infinity should be infinity")

        assertEquals(0.0, 0.0.toThePowerOf(5.0), tolerance = defaultCalculationTolerance, message = "0^5 should be 0")
        assertEquals(1.0, 1.0.toThePowerOf(4.0), tolerance = defaultCalculationTolerance, message = "1^4 should be 1")
        assertEquals(8.0, 2.0.toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "2^3 should be 8")
        assertEquals(9.0, 3.0.toThePowerOf(2.0), tolerance = defaultCalculationTolerance, message = "3^2 should be 9")
        assertEquals(4.0, 4.0.toThePowerOf(1.0), tolerance = defaultCalculationTolerance, message = "4^1 should be 4")
        assertEquals(1.0, 5.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "5^0 should be 1")

        assertEquals(1.0, (-Fraction.infinity).toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so -infinity^0 should be 1")
        assertEquals(1.0, Integer.min.fractionValue.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so Integer.min^0 should be 1")
        assertEquals(1.0, (-5.0).toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so -5^0 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so 1^0 should be 1")
        assertEquals(1.0, 5.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so 5^0 should be 1")
        assertEquals(1.0, Integer.max.fractionValue.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so Integer.max^0 should be 1")
        assertEquals(1.0, Fraction.infinity.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Anything to the 0 power is 1, so infinity^0 should be 1")

        assertEquals(1_000.0, 10.0.toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "10^3 should be 1,000")
        assertEquals(100_000_000.0, 10.0.toThePowerOf(8.0), tolerance = defaultCalculationTolerance, message = "10^8 should be 100,000,000")

        assertEquals(8.0, 2.0.toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "2^3 should be 8")
        assertEquals(65_536.0, 2.0.toThePowerOf(16.0), tolerance = defaultCalculationTolerance, message = "2^16 should be 65,536")

        assertEquals(1.0, 1.0.toThePowerOf(5.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^5 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^0 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(-1.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^-1 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(-100.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^-100 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(Integer.min.fractionValue), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^Integer.min should be 1")

        assertEquals(1.0, 1.0.toThePowerOf(5.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^5 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(4.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^4 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^3 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(2.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^2 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(1.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^1 should be 1")
        assertEquals(1.0, 1.0.toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "All powers of 1 are 1, 1^0 should be 1")

        assertTrue(0.0.toThePowerOf(-1.0).isNaN, "Zero to a negative power is undefined, 0^-1 should be not a number")
        assertTrue(0.0.toThePowerOf(-100.0).isNaN, "Zero to a negative power is undefined, 0^-100 should be not a number")
        assertTrue(0.0.toThePowerOf(Integer.min.fractionValue).isNaN, "Zero to a negative power is undefined, 0^Integer.min should be not a number")

        assertEquals(0.0, 0.0.toThePowerOf(5.0), tolerance = defaultCalculationTolerance, message = "Since 0 raised to any power is 0, 0^5 should be 0")
        assertEquals(0.0, 0.0.toThePowerOf(4.0), tolerance = defaultCalculationTolerance, message = "Since 0 raised to any power is 0, 0^4 should be 0")
        assertEquals(0.0, 0.0.toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "Since 0 raised to any power is 0, 0^3 should be 0")
        assertEquals(0.0, 0.0.toThePowerOf(2.0), tolerance = defaultCalculationTolerance, message = "Since 0 raised to any power is 0, 0^2 should be 0")
        assertEquals(0.0, 0.0.toThePowerOf(1.0), tolerance = defaultCalculationTolerance, message = "Since 0 raised to any power is 0, 0^1 should be 0")

        assertTrue(0.0.toThePowerOf(0.0).isNaN, "0 raised to any power is 0, but anything raised to the power of 0 is 1, so 0^0 should be not a number")
        assertTrue(0.0.toThePowerOf(-6.0).isNaN, "0 raised to any power is 0, but zero to a negative power us undefined, 0^-6 should be not a number")


        assertEquals(1.0, (-1.0).toThePowerOf(0.0), tolerance = defaultCalculationTolerance, message = "Even powers of -1 are 1, so -1^0 should be 1")
        assertEquals(1.0, (-1.0).toThePowerOf(2.0), tolerance = defaultCalculationTolerance, message = "Even powers of -1 are 1, so -1^2 should be 1")
        assertEquals(1.0, (-1.0).toThePowerOf(8941636.0), tolerance = defaultCalculationTolerance, message = "Even powers of -1 are 1, so -1^8941636 should be 1")
        assertEquals(1.0, (-1.0).toThePowerOf(-210.0), tolerance = defaultCalculationTolerance, message = "Even powers of -1 are 1, so -1^-210 should be 1")
//        assertEquals("Even powers of -1 are 1, so -1^Integer.min should be 1", 1.0, (-1.0).toThePowerOf(Integer.min.fractionValue), defaultCalculationTolerance) FIXME: This extreme edge case should pass

        assertEquals(-1.0, (-1.0).toThePowerOf(1.0), tolerance = defaultCalculationTolerance, message = "Odd powers of -1 are -1, so -1^1 should be -1")
        assertEquals(-1.0, (-1.0).toThePowerOf(3.0), tolerance = defaultCalculationTolerance, message = "Odd powers of -1 are -1, so -1^3 should be -1")
        assertEquals(-1.0, (-1.0).toThePowerOf(8941637.0), tolerance = defaultCalculationTolerance, message = "Odd powers of -1 are -1, so -1^8941637 should be -1")
        assertEquals(-1.0, (-1.0).toThePowerOf(-321.0), tolerance = defaultCalculationTolerance, message = "Odd powers of -1 are -1, so -1^-321 should be -1")
        assertEquals(-1.0, (-1.0).toThePowerOf(Int32.max.fractionValue), tolerance = defaultCalculationTolerance, message = "Odd powers of -1 are -1, so -1^Integer.max should be -1")
    }
}
