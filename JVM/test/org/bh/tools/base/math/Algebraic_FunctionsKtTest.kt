package org.bh.tools.base.math


import junit.framework.TestCase.*
import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.abstraction.Int64
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

    @Test
    fun numberOfDigits() {
        assertEquals("1 has 1 digit", 1, 1.numberOfDigits())
        assertEquals("4 has 1 digit", 1, 4.numberOfDigits())
        assertEquals("9 has 1 digit", 1, 9.numberOfDigits())
        assertEquals("-1 has 1 digit", 1, (-1).numberOfDigits())
        assertEquals("-4 has 1 digit", 1, (-4).numberOfDigits())
        assertEquals("-9 has 1 digit", 1, (-9).numberOfDigits())

        assertEquals("10 has 2 digits", 2, 10.numberOfDigits())
        assertEquals("11 has 2 digits", 2, 11.numberOfDigits())
        assertEquals("12 has 2 digits", 2, 12.numberOfDigits())
        assertEquals("40 has 2 digits", 2, 40.numberOfDigits())
        assertEquals("99 has 2 digits", 2, 99.numberOfDigits())
        assertEquals("-10 has 2 digits", 2, (-10).numberOfDigits())
        assertEquals("-11 has 2 digits", 2, (-11).numberOfDigits())
        assertEquals("-12 has 2 digits", 2, (-12).numberOfDigits())
        assertEquals("-40 has 2 digits", 2, (-40).numberOfDigits())
        assertEquals("-99 has 2 digits", 2, (-99).numberOfDigits())

        assertEquals("100 has 3 digits", 3, 100.numberOfDigits())
        assertEquals("101 has 3 digits", 3, 101.numberOfDigits())
        assertEquals("123 has 3 digits", 3, 123.numberOfDigits())
        assertEquals("400 has 3 digits", 3, 400.numberOfDigits())
        assertEquals("999 has 3 digits", 3, 999.numberOfDigits())
        assertEquals("-100 has 3 digits", 3, (-100).numberOfDigits())
        assertEquals("-101 has 3 digits", 3, (-101).numberOfDigits())
        assertEquals("-123 has 3 digits", 3, (-123).numberOfDigits())
        assertEquals("-400 has 3 digits", 3, (-400).numberOfDigits())
        assertEquals("-999 has 3 digits", 3, (-999).numberOfDigits())

        assertEquals("1,000 has 4 digits", 4, 1000.numberOfDigits())
        assertEquals("1,001 has 4 digits", 4, 1001.numberOfDigits())
        assertEquals("1,234 has 4 digits", 4, 1234.numberOfDigits())
        assertEquals("4,000 has 4 digits", 4, 4000.numberOfDigits())
        assertEquals("9,999 has 4 digits", 4, 9999.numberOfDigits())
        assertEquals("-1,000 has 4 digits", 4, (-1000).numberOfDigits())
        assertEquals("-1,001 has 4 digits", 4, (-1001).numberOfDigits())
        assertEquals("-1,234 has 4 digits", 4, (-1234).numberOfDigits())
        assertEquals("-4,000 has 4 digits", 4, (-4000).numberOfDigits())
        assertEquals("-9,999 has 4 digits", 4, (-9999).numberOfDigits())

        assertEquals("10,000 has 5 digits", 5, 10000.numberOfDigits())
        assertEquals("10,001 has 5 digits", 5, 10001.numberOfDigits())
        assertEquals("12,345 has 5 digits", 5, 12345.numberOfDigits())
        assertEquals("40,000 has 5 digits", 5, 40000.numberOfDigits())
        assertEquals("99,999 has 5 digits", 5, 99999.numberOfDigits())
        assertEquals("-10,000 has 5 digits", 5, (-10000).numberOfDigits())
        assertEquals("-10,001 has 5 digits", 5, (-10001).numberOfDigits())
        assertEquals("-12,345 has 5 digits", 5, (-12345).numberOfDigits())
        assertEquals("-40,000 has 5 digits", 5, (-40000).numberOfDigits())
        assertEquals("-99,999 has 5 digits", 5, (-99999).numberOfDigits())

        assertEquals("100,000 has 6 digits", 6, 100000.numberOfDigits())
        assertEquals("100,001 has 6 digits", 6, 100001.numberOfDigits())
        assertEquals("123,456 has 6 digits", 6, 123456.numberOfDigits())
        assertEquals("400,000 has 6 digits", 6, 400000.numberOfDigits())
        assertEquals("999,999 has 6 digits", 6, 999999.numberOfDigits())
        assertEquals("-100,000 has 6 digits", 6, (-100000).numberOfDigits())
        assertEquals("-100,001 has 6 digits", 6, (-100001).numberOfDigits())
        assertEquals("-123,456 has 6 digits", 6, (-123456).numberOfDigits())
        assertEquals("-400,000 has 6 digits", 6, (-400000).numberOfDigits())
        assertEquals("-999,999 has 6 digits", 6, (-999999).numberOfDigits())

        assertEquals("1,000,000 has 7 digits", 7, 1000000.numberOfDigits())
        assertEquals("1,000,001 has 7 digits", 7, 1000001.numberOfDigits())
        assertEquals("1,234,567 has 7 digits", 7, 1234567.numberOfDigits())
        assertEquals("4,000,000 has 7 digits", 7, 4000000.numberOfDigits())
        assertEquals("9,999,999 has 7 digits", 7, 9999999.numberOfDigits())
        assertEquals("-1,000,000 has 7 digits", 7, (-1000000).numberOfDigits())
        assertEquals("-1,000,001 has 7 digits", 7, (-1000001).numberOfDigits())
        assertEquals("-1,234,567 has 7 digits", 7, (-1234567).numberOfDigits())
        assertEquals("-4,000,000 has 7 digits", 7, (-4000000).numberOfDigits())
        assertEquals("-9,999,999 has 7 digits", 7, (-9999999).numberOfDigits())

        assertEquals("10,000,000 has 8 digits", 8, 10000000.numberOfDigits())
        assertEquals("10,000,001 has 8 digits", 8, 10000001.numberOfDigits())
        assertEquals("12,345,678 has 8 digits", 8, 12345678.numberOfDigits())
        assertEquals("40,000,000 has 8 digits", 8, 40000000.numberOfDigits())
        assertEquals("99,999,999 has 8 digits", 8, 99999999.numberOfDigits())
        assertEquals("-10,000,000 has 8 digits", 8, (-10000000).numberOfDigits())
        assertEquals("-10,000,001 has 8 digits", 8, (-10000001).numberOfDigits())
        assertEquals("-12,345,678 has 8 digits", 8, (-12345678).numberOfDigits())
        assertEquals("-40,000,000 has 8 digits", 8, (-40000000).numberOfDigits())
        assertEquals("-99,999,999 has 8 digits", 8, (-99999999).numberOfDigits())

        assertEquals("100,000,000 has 9 digits", 9, 100000000.numberOfDigits())
        assertEquals("100,000,001 has 9 digits", 9, 100000001.numberOfDigits())
        assertEquals("123,456,789 has 9 digits", 9, 123456789.numberOfDigits())
        assertEquals("400,000,000 has 9 digits", 9, 400000000.numberOfDigits())
        assertEquals("999,999,999 has 9 digits", 9, 999999999.numberOfDigits())
        assertEquals("-100,000,000 has 9 digits", 9, (-100000000).numberOfDigits())
        assertEquals("-100,000,001 has 9 digits", 9, (-100000001).numberOfDigits())
        assertEquals("-123,456,789 has 9 digits", 9, (-123456789).numberOfDigits())
        assertEquals("-400,000,000 has 9 digits", 9, (-400000000).numberOfDigits())
        assertEquals("-999,999,999 has 9 digits", 9, (-999999999).numberOfDigits())

        assertEquals("1,000,000,000 has 10 digits", 10, 1000000000.numberOfDigits())
        assertEquals("1,000,000,001 has 10 digits", 10, 1000000001.numberOfDigits())
        assertEquals("1,234,567,891 has 10 digits", 10, 1234567891.numberOfDigits())
        assertEquals("4,000,000,000 has 10 digits", 10, 4000000000.numberOfDigits())
        assertEquals("9,999,999,999 has 10 digits", 10, 9999999999.numberOfDigits())
        assertEquals("-1,000,000,000 has 10 digits", 10, (-1000000000).numberOfDigits())
        assertEquals("-1,000,000,001 has 10 digits", 10, (-1000000001).numberOfDigits())
        assertEquals("-1,234,567,891 has 10 digits", 10, (-1234567891).numberOfDigits())
        assertEquals("-4,000,000,000 has 10 digits", 10, (-4000000000).numberOfDigits())
        assertEquals("-9,999,999,999 has 10 digits", 10, (-9999999999).numberOfDigits())

        assertEquals("1,000,000,000 has 10 digits", 10, 1000000000.numberOfDigits())
        assertEquals("1,000,000,001 has 10 digits", 10, 1000000001.numberOfDigits())
        assertEquals("1,234,567,891 has 10 digits", 10, 1234567891.numberOfDigits())
        assertEquals("Int32.max has 10 digits", 10, Int32.max.numberOfDigits())
        assertEquals("4,000,000,000 has 10 digits", 10, 4000000000.numberOfDigits())
        assertEquals("9,999,999,999 has 10 digits", 10, 9999999999.numberOfDigits())
        assertEquals("-1,000,000,000 has 10 digits", 10, (-1000000000).numberOfDigits())
        assertEquals("-1,000,000,001 has 10 digits", 10, (-1000000001).numberOfDigits())
        assertEquals("-1,234,567,891 has 10 digits", 10, (-1234567891).numberOfDigits())
        assertEquals("Int32.min has 10 digits", 10, Int32.min.numberOfDigits())
        assertEquals("-4,000,000,000 has 10 digits", 10, (-4000000000).numberOfDigits())
        assertEquals("-9,999,999,999 has 10 digits", 10, (-9999999999).numberOfDigits())

        assertEquals("10,000,000,000 has 11 digits", 11, 10000000000.numberOfDigits())
        assertEquals("10,000,000,001 has 11 digits", 11, 10000000001.numberOfDigits())
        assertEquals("12,345,678,911 has 11 digits", 11, 12345678911.numberOfDigits())
        assertEquals("40,000,000,000 has 11 digits", 11, 40000000000.numberOfDigits())
        assertEquals("99,999,999,999 has 11 digits", 11, 99999999999.numberOfDigits())
        assertEquals("-10,000,000,000 has 11 digits", 11, (-10000000000).numberOfDigits())
        assertEquals("-10,000,000,001 has 11 digits", 11, (-10000000001).numberOfDigits())
        assertEquals("-12,345,678,911 has 11 digits", 11, (-12345678911).numberOfDigits())
        assertEquals("-40,000,000,000 has 11 digits", 11, (-40000000000).numberOfDigits())
        assertEquals("-99,999,999,999 has 11 digits", 11, (-99999999999).numberOfDigits())

        assertEquals("100,000,000,000 has 12 digits", 12, 100000000000.numberOfDigits())
        assertEquals("100,000,000,001 has 12 digits", 12, 100000000001.numberOfDigits())
        assertEquals("123,456,789,112 has 12 digits", 12, 123456789112.numberOfDigits())
        assertEquals("400,000,000,000 has 12 digits", 12, 400000000000.numberOfDigits())
        assertEquals("999,999,999,999 has 12 digits", 12, 999999999999.numberOfDigits())
        assertEquals("-100,000,000,000 has 12 digits", 12, (-100000000000).numberOfDigits())
        assertEquals("-100,000,000,001 has 12 digits", 12, (-100000000001).numberOfDigits())
        assertEquals("-123,456,789,112 has 12 digits", 12, (-123456789112).numberOfDigits())
        assertEquals("-400,000,000,000 has 12 digits", 12, (-400000000000).numberOfDigits())
        assertEquals("-999,999,999,999 has 12 digits", 12, (-999999999999).numberOfDigits())

        // Guessing 13, 14, 15, 16, and 17 digits are also OK

        assertEquals("100,000,000,000,000,000 has 18 digits", 18, 100000000000000000.numberOfDigits())
        assertEquals("100,000,000,000,000,001 has 18 digits", 18, 100000000000000001.numberOfDigits())
        assertEquals("123,456,789,112,345,678 has 18 digits", 18, 123456789112345678.numberOfDigits())
        assertEquals("400,000,000,000,000,000 has 18 digits", 18, 400000000000000000.numberOfDigits())
        assertEquals("999,999,999,999,999,999 has 18 digits", 18, 999999999999999999.numberOfDigits())
        assertEquals("-100,000,000,000,000,000 has 18 digits", 18, (-100000000000000000).numberOfDigits())
        assertEquals("-100,000,000,001,000,000 has 18 digits", 18, (-100000000000000001).numberOfDigits())
        assertEquals("-123,456,789,112,345,678 has 18 digits", 18, (-123456789112345678).numberOfDigits())
        assertEquals("-400,000,000,000,000,000 has 18 digits", 18, (-400000000000000000).numberOfDigits())
//        assertEquals("-999,999,999,999,999,999 has 18 digits", 18, (-999999999999999999).numberOfDigits())

        assertEquals("1,000,000,000,000,000,000 has 19 digits", 19, 1000000000000000000.numberOfDigits())
        assertEquals("1,000,000,000,000,000,001 has 19 digits", 19, 1000000000000000001.numberOfDigits())
        assertEquals("1,234,567,891,123,456,789 has 19 digits", 19, 1234567891123456789.numberOfDigits())
        assertEquals("4,000,000,000,000,000,000 has 19 digits", 19, 4000000000000000000.numberOfDigits())
        assertEquals("Int64.max has 19 digits", 19, Int64.max.numberOfDigits())
        assertEquals("-1,000,000,000,000,000,000 has 19 digits", 19, (-1000000000000000000).numberOfDigits())
        assertEquals("-1,000,000,000,000,000,001 has 19 digits", 19, (-1000000000000000001).numberOfDigits())
        assertEquals("-1,234,567,891,123,456,789 has 19 digits", 19, (-1234567891123456789).numberOfDigits())
        assertEquals("-4,000,000,000,000,000,000 has 19 digits", 19, (-4000000000000000000).numberOfDigits())
        assertEquals("Int64.min has 19 digits", 19, Int64.min.numberOfDigits())

    }
}