package org.bh.tools.base.strings

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Int32
import java.math.BigDecimal
import java.math.RoundingMode

/*
 * Makes numbers more stringy
 *
 * @author
 * @since 2017-02-21
 */


fun Fraction.toString(fractionDigits: Int32): String
        = BigDecimal.valueOf(this).setScale(fractionDigits, RoundingMode.HALF_UP).toString()
