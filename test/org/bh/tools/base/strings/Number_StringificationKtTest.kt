package org.bh.tools.base.strings


import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.util.Assertion
import org.bh.tools.base.util.expect
import org.junit.Test

import org.junit.Assert.*


/**
 * @author Ben Leggiero
 * @since 2017-08-07
 */
class Number_StringificationKtTest {

    @Test
    fun Fraction_toString() {
        val toString_2FractionDigits: (Fraction) -> String? = {
            it.toString(fractionDigits = 2)
        }

        val result = expect("Fraction.toString", toString_2FractionDigits, listOf(
                Assertion.valid(0.0, "0.00")
        ))
        assertTrue(result.message, result.success)
    }


    @Test
    fun Integer_toString() {
    }


    @Test
    fun Int32_toString() {
    }
}
