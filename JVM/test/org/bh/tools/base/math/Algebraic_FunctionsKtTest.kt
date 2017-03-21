package org.bh.tools.base.math


import junit.framework.TestCase.*
import org.junit.Test


/**
 * @author Ben Leggiero
 * @since 2017-03-20
 */
class Algebraic_FunctionsKtTest {

    @Test
    fun greatestCommonDivisor() {
        assertEquals("The greatest common divisor of 54 and 24 is 6", 6, greatestCommonDivisor(54, 24))
        assertEquals("The greatest common divisor of 42 and 56 is 14", 14, greatestCommonDivisor(42, 56))

        assertEquals("The greatest common divisor 4 and 9 is 1", 1, greatestCommonDivisor(4, 9))
        assertEquals("The greatest common divisor 6 and 25 is 1", 1, greatestCommonDivisor(6, 25))
        assertEquals("The greatest common divisor 14 and 15 is 1", 1, greatestCommonDivisor(14, 15))
        assertEquals("The greatest common divisor 27 and 16 is 1", 1, greatestCommonDivisor(27, 16))

        assertEquals("The greatest common divisor of 14 and 21 is 7", 7, greatestCommonDivisor(14, 21))
        assertEquals("The greatest common divisor of 12 and 21 is 3", 3, greatestCommonDivisor(12, 21))
    }


    @Test
    fun isCoprime() {
        assertTrue("4 and 9 are coprime", isCoprime(4, 9))
        assertTrue("6 and 25 are coprime", isCoprime(6, 25))
        assertTrue("14 and 15 are coprime", isCoprime(14, 15))
        assertTrue("27 and 16 are coprime", isCoprime(27, 16))

        assertFalse("14 and 21 are not coprime", isCoprime(14, 21))
        assertFalse("12 and 21 are not coprime", isCoprime(12, 21))
    }
}