package org.bh.tools.base.math


import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.util.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


/**
 * @author Ben Leggiero
 * @since 2017-03-20
 */
class Algebraic_FunctionsKtTest {

    @Test
    fun greatestCommonDivisor() {
        assertEquals(6, greatestCommonDivisor(54, 24), "The greatest common divisor of 54 and 24 is 6")
        assertEquals(14, greatestCommonDivisor(42, 56), "The greatest common divisor of 42 and 56 is 14")

        assertEquals(1, greatestCommonDivisor(4, 9), "The greatest common divisor 4 and 9 is 1")
        assertEquals(1, greatestCommonDivisor(6, 25), "The greatest common divisor 6 and 25 is 1")
        assertEquals(1, greatestCommonDivisor(14, 15), "The greatest common divisor 14 and 15 is 1")
        assertEquals(1, greatestCommonDivisor(27, 16), "The greatest common divisor 27 and 16 is 1")

        assertEquals(7, greatestCommonDivisor(14, 21), "The greatest common divisor of 14 and 21 is 7")
        assertEquals(3, greatestCommonDivisor(12, 21), "The greatest common divisor of 12 and 21 is 3")
    }


    @Test
    fun isCoprime() {
        assertTrue(isCoprime(4, 9), "4 and 9 are coprime")
        assertTrue(isCoprime(6, 25), "6 and 25 are coprime")
        assertTrue(isCoprime(14, 15), "14 and 15 are coprime")
        assertTrue(isCoprime(27, 16), "27 and 16 are coprime")

        assertFalse(isCoprime(14, 21), "14 and 21 are not coprime")
        assertFalse(isCoprime(12, 21), "12 and 21 are not coprime")
    }

    @Test
    fun numberOfDigits() {
        assertEquals(1, 1.numberOfDigits(), "1 has 1 digit")
        assertEquals(1, 4.numberOfDigits(), "4 has 1 digit")
        assertEquals(1, 9.numberOfDigits(), "9 has 1 digit")
        assertEquals(1, (-1).numberOfDigits(), "-1 has 1 digit")
        assertEquals(1, (-4).numberOfDigits(), "-4 has 1 digit")
        assertEquals(1, (-9).numberOfDigits(), "-9 has 1 digit")

        assertEquals(2, 10.numberOfDigits(), "10 has 2 digits")
        assertEquals(2, 11.numberOfDigits(), "11 has 2 digits")
        assertEquals(2, 12.numberOfDigits(), "12 has 2 digits")
        assertEquals(2, 40.numberOfDigits(), "40 has 2 digits")
        assertEquals(2, 99.numberOfDigits(), "99 has 2 digits")
        assertEquals(2, (-10).numberOfDigits(), "-10 has 2 digits")
        assertEquals(2, (-11).numberOfDigits(), "-11 has 2 digits")
        assertEquals(2, (-12).numberOfDigits(), "-12 has 2 digits")
        assertEquals(2, (-40).numberOfDigits(), "-40 has 2 digits")
        assertEquals(2, (-99).numberOfDigits(), "-99 has 2 digits")

        assertEquals(3, 100.numberOfDigits(), "100 has 3 digits")
        assertEquals(3, 101.numberOfDigits(), "101 has 3 digits")
        assertEquals(3, 123.numberOfDigits(), "123 has 3 digits")
        assertEquals(3, 400.numberOfDigits(), "400 has 3 digits")
        assertEquals(3, 999.numberOfDigits(), "999 has 3 digits")
        assertEquals(3, (-100).numberOfDigits(), "-100 has 3 digits")
        assertEquals(3, (-101).numberOfDigits(), "-101 has 3 digits")
        assertEquals(3, (-123).numberOfDigits(), "-123 has 3 digits")
        assertEquals(3, (-400).numberOfDigits(), "-400 has 3 digits")
        assertEquals(3, (-999).numberOfDigits(), "-999 has 3 digits")

        assertEquals(4, 1000.numberOfDigits(), "1,000 has 4 digits")
        assertEquals(4, 1001.numberOfDigits(), "1,001 has 4 digits")
        assertEquals(4, 1234.numberOfDigits(), "1,234 has 4 digits")
        assertEquals(4, 4000.numberOfDigits(), "4,000 has 4 digits")
        assertEquals(4, 9999.numberOfDigits(), "9,999 has 4 digits")
        assertEquals(4, (-1000).numberOfDigits(), "-1,000 has 4 digits")
        assertEquals(4, (-1001).numberOfDigits(), "-1,001 has 4 digits")
        assertEquals(4, (-1234).numberOfDigits(), "-1,234 has 4 digits")
        assertEquals(4, (-4000).numberOfDigits(), "-4,000 has 4 digits")
        assertEquals(4, (-9999).numberOfDigits(), "-9,999 has 4 digits")

        assertEquals(5, 10000.numberOfDigits(), "10,000 has 5 digits")
        assertEquals(5, 10001.numberOfDigits(), "10,001 has 5 digits")
        assertEquals(5, 12345.numberOfDigits(), "12,345 has 5 digits")
        assertEquals(5, 40000.numberOfDigits(), "40,000 has 5 digits")
        assertEquals(5, 99999.numberOfDigits(), "99,999 has 5 digits")
        assertEquals(5, (-10000).numberOfDigits(), "-10,000 has 5 digits")
        assertEquals(5, (-10001).numberOfDigits(), "-10,001 has 5 digits")
        assertEquals(5, (-12345).numberOfDigits(), "-12,345 has 5 digits")
        assertEquals(5, (-40000).numberOfDigits(), "-40,000 has 5 digits")
        assertEquals(5, (-99999).numberOfDigits(), "-99,999 has 5 digits")

        assertEquals(6, 100000.numberOfDigits(), "100,000 has 6 digits")
        assertEquals(6, 100001.numberOfDigits(), "100,001 has 6 digits")
        assertEquals(6, 123456.numberOfDigits(), "123,456 has 6 digits")
        assertEquals(6, 400000.numberOfDigits(), "400,000 has 6 digits")
        assertEquals(6, 999999.numberOfDigits(), "999,999 has 6 digits")
        assertEquals(6, (-100000).numberOfDigits(), "-100,000 has 6 digits")
        assertEquals(6, (-100001).numberOfDigits(), "-100,001 has 6 digits")
        assertEquals(6, (-123456).numberOfDigits(), "-123,456 has 6 digits")
        assertEquals(6, (-400000).numberOfDigits(), "-400,000 has 6 digits")
        assertEquals(6, (-999999).numberOfDigits(), "-999,999 has 6 digits")

        assertEquals(7, 1000000.numberOfDigits(), "1,000,000 has 7 digits")
        assertEquals(7, 1000001.numberOfDigits(), "1,000,001 has 7 digits")
        assertEquals(7, 1234567.numberOfDigits(), "1,234,567 has 7 digits")
        assertEquals(7, 4000000.numberOfDigits(), "4,000,000 has 7 digits")
        assertEquals(7, 9999999.numberOfDigits(), "9,999,999 has 7 digits")
        assertEquals(7, (-1000000).numberOfDigits(), "-1,000,000 has 7 digits")
        assertEquals(7, (-1000001).numberOfDigits(), "-1,000,001 has 7 digits")
        assertEquals(7, (-1234567).numberOfDigits(), "-1,234,567 has 7 digits")
        assertEquals(7, (-4000000).numberOfDigits(), "-4,000,000 has 7 digits")
        assertEquals(7, (-9999999).numberOfDigits(), "-9,999,999 has 7 digits")

        assertEquals(8, 10000000.numberOfDigits(), "10,000,000 has 8 digits")
        assertEquals(8, 10000001.numberOfDigits(), "10,000,001 has 8 digits")
        assertEquals(8, 12345678.numberOfDigits(), "12,345,678 has 8 digits")
        assertEquals(8, 40000000.numberOfDigits(), "40,000,000 has 8 digits")
        assertEquals(8, 99999999.numberOfDigits(), "99,999,999 has 8 digits")
        assertEquals(8, (-10000000).numberOfDigits(), "-10,000,000 has 8 digits")
        assertEquals(8, (-10000001).numberOfDigits(), "-10,000,001 has 8 digits")
        assertEquals(8, (-12345678).numberOfDigits(), "-12,345,678 has 8 digits")
        assertEquals(8, (-40000000).numberOfDigits(), "-40,000,000 has 8 digits")
        assertEquals(8, (-99999999).numberOfDigits(), "-99,999,999 has 8 digits")

        assertEquals(9, 100000000.numberOfDigits(), "100,000,000 has 9 digits")
        assertEquals(9, 100000001.numberOfDigits(), "100,000,001 has 9 digits")
        assertEquals(9, 123456789.numberOfDigits(), "123,456,789 has 9 digits")
        assertEquals(9, 400000000.numberOfDigits(), "400,000,000 has 9 digits")
        assertEquals(9, 999999999.numberOfDigits(), "999,999,999 has 9 digits")
        assertEquals(9, (-100000000).numberOfDigits(), "-100,000,000 has 9 digits")
        assertEquals(9, (-100000001).numberOfDigits(), "-100,000,001 has 9 digits")
        assertEquals(9, (-123456789).numberOfDigits(), "-123,456,789 has 9 digits")
        assertEquals(9, (-400000000).numberOfDigits(), "-400,000,000 has 9 digits")
        assertEquals(9, (-999999999).numberOfDigits(), "-999,999,999 has 9 digits")

        assertEquals(10, 1000000000.numberOfDigits(), "1,000,000,000 has 10 digits")
        assertEquals(10, 1000000001.numberOfDigits(), "1,000,000,001 has 10 digits")
        assertEquals(10, 1234567891.numberOfDigits(), "1,234,567,891 has 10 digits")
        assertEquals(10, 4000000000.numberOfDigits(), "4,000,000,000 has 10 digits")
        assertEquals(10, 9999999999.numberOfDigits(), "9,999,999,999 has 10 digits")
        assertEquals(10, (-1000000000).numberOfDigits(), "-1,000,000,000 has 10 digits")
        assertEquals(10, (-1000000001).numberOfDigits(), "-1,000,000,001 has 10 digits")
        assertEquals(10, (-1234567891).numberOfDigits(), "-1,234,567,891 has 10 digits")
        assertEquals(10, (-4000000000).numberOfDigits(), "-4,000,000,000 has 10 digits")
        assertEquals(10, (-9999999999).numberOfDigits(), "-9,999,999,999 has 10 digits")

        assertEquals(10, 1000000000.numberOfDigits(), "1,000,000,000 has 10 digits")
        assertEquals(10, 1000000001.numberOfDigits(), "1,000,000,001 has 10 digits")
        assertEquals(10, 1234567891.numberOfDigits(), "1,234,567,891 has 10 digits")
        assertEquals(10, Int32.max.numberOfDigits(), "Int32.max has 10 digits")
        assertEquals(10, 4000000000.numberOfDigits(), "4,000,000,000 has 10 digits")
        assertEquals(10, 9999999999.numberOfDigits(), "9,999,999,999 has 10 digits")
        assertEquals(10, (-1000000000).numberOfDigits(), "-1,000,000,000 has 10 digits")
        assertEquals(10, (-1000000001).numberOfDigits(), "-1,000,000,001 has 10 digits")
        assertEquals(10, (-1234567891).numberOfDigits(), "-1,234,567,891 has 10 digits")
        assertEquals(10, Int32.min.numberOfDigits(), "Int32.min has 10 digits")
        assertEquals(10, (-4000000000).numberOfDigits(), "-4,000,000,000 has 10 digits")
        assertEquals(10, (-9999999999).numberOfDigits(), "-9,999,999,999 has 10 digits")

        assertEquals(11, 10000000000.numberOfDigits(), "10,000,000,000 has 11 digits")
        assertEquals(11, 10000000001.numberOfDigits(), "10,000,000,001 has 11 digits")
        assertEquals(11, 12345678911.numberOfDigits(), "12,345,678,911 has 11 digits")
        assertEquals(11, 40000000000.numberOfDigits(), "40,000,000,000 has 11 digits")
        assertEquals(11, 99999999999.numberOfDigits(), "99,999,999,999 has 11 digits")
        assertEquals(11, (-10000000000).numberOfDigits(), "-10,000,000,000 has 11 digits")
        assertEquals(11, (-10000000001).numberOfDigits(), "-10,000,000,001 has 11 digits")
        assertEquals(11, (-12345678911).numberOfDigits(), "-12,345,678,911 has 11 digits")
        assertEquals(11, (-40000000000).numberOfDigits(), "-40,000,000,000 has 11 digits")
        assertEquals(11, (-99999999999).numberOfDigits(), "-99,999,999,999 has 11 digits")

        assertEquals(12, 100000000000.numberOfDigits(), "100,000,000,000 has 12 digits")
        assertEquals(12, 100000000001.numberOfDigits(), "100,000,000,001 has 12 digits")
        assertEquals(12, 123456789112.numberOfDigits(), "123,456,789,112 has 12 digits")
        assertEquals(12, 400000000000.numberOfDigits(), "400,000,000,000 has 12 digits")
        assertEquals(12, 999999999999.numberOfDigits(), "999,999,999,999 has 12 digits")
        assertEquals(12, (-100000000000).numberOfDigits(), "-100,000,000,000 has 12 digits")
        assertEquals(12, (-100000000001).numberOfDigits(), "-100,000,000,001 has 12 digits")
        assertEquals(12, (-123456789112).numberOfDigits(), "-123,456,789,112 has 12 digits")
        assertEquals(12, (-400000000000).numberOfDigits(), "-400,000,000,000 has 12 digits")
        assertEquals(12, (-999999999999).numberOfDigits(), "-999,999,999,999 has 12 digits")

        // Guessing 13, 14, 15, 16, and 17 digits are also OK

        assertEquals(18, 100000000000000000.numberOfDigits(), "100,000,000,000,000,000 has 18 digits")
        assertEquals(18, 100000000000000001.numberOfDigits(), "100,000,000,000,000,001 has 18 digits")
        assertEquals(18, 123456789112345678.numberOfDigits(), "123,456,789,112,345,678 has 18 digits")
        assertEquals(18, 400000000000000000.numberOfDigits(), "400,000,000,000,000,000 has 18 digits")
        assertEquals(18, 999999999999999999.numberOfDigits(), "999,999,999,999,999,999 has 18 digits")
        assertEquals(18, (-100000000000000000).numberOfDigits(), "-100,000,000,000,000,000 has 18 digits")
        assertEquals(18, (-100000000000000001).numberOfDigits(), "-100,000,000,001,000,000 has 18 digits")
        assertEquals(18, (-123456789112345678).numberOfDigits(), "-123,456,789,112,345,678 has 18 digits")
        assertEquals(18, (-400000000000000000).numberOfDigits(), "-400,000,000,000,000,000 has 18 digits")
//        assertEquals(18, (-999999999999999999).numberOfDigits(), "-999,999,999,999,999,999 has 18 digits")

        assertEquals(19, 1000000000000000000.numberOfDigits(), "1,000,000,000,000,000,000 has 19 digits")
        assertEquals(19, 1000000000000000001.numberOfDigits(), "1,000,000,000,000,000,001 has 19 digits")
        assertEquals(19, 1234567891123456789.numberOfDigits(), "1,234,567,891,123,456,789 has 19 digits")
        assertEquals(19, 4000000000000000000.numberOfDigits(), "4,000,000,000,000,000,000 has 19 digits")
        assertEquals(19, Int64.max.numberOfDigits(), "Int64.max has 19 digits")
        assertEquals(19, (-1000000000000000000).numberOfDigits(), "-1,000,000,000,000,000,000 has 19 digits")
        assertEquals(19, (-1000000000000000001).numberOfDigits(), "-1,000,000,000,000,000,001 has 19 digits")
        assertEquals(19, (-1234567891123456789).numberOfDigits(), "-1,234,567,891,123,456,789 has 19 digits")
        assertEquals(19, (-4000000000000000000).numberOfDigits(), "-4,000,000,000,000,000,000 has 19 digits")
        assertEquals(19, Int64.min.numberOfDigits(), "Int64.min has 19 digits")

    }
}