package org.bh.tools.base.strings

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import java.math.BigDecimal

/*
 * Makes numbers more stringy
 *
 * @author
 * @since 2017-02-21
 */


/**
 * Creates a string where this fraction is presented with exactly the given number of digits, rounded normally.
 *
 * @param fractionDigits The number of digits to show after the radix point. It's nonsense to make this negative.
 *
 * @return A string representation of this fraction with the exact given number of digits after the radix.
 */
fun Fraction.toString(fractionDigits: Int32): String
        = BigDecimal.valueOf(this).setScale(fractionDigits, RoundingMode(this)).toString()


fun decimalSeparatorRegex(groupSize: Int8) = Regex("^(\\d{1,$groupSize}?)(\\d{$groupSize})*\$")


/**
 * Creates a string of this integer where the digits are grouped by the given separator, like `10,000,000`,
 * `12.345.678`, or `1 222 333`. If this integer is <= [groupSize], it is stringified without separators.
 *
 * @param separator What to put between groups, usually `","` or `"."`
 * @param groupSize _optional_ - How big the groups should be. Defaults to `3`.
 *
 * @return A string representation of this integer, with digits grouped as specified
 */
fun Integer.toString(separator: String, groupSize: Int8 = 3): String =
        if (numberOfDigits() <= groupSize) {
            toString()
        } else {
            decimalSeparatorRegex(groupSize)
                    .groupsWithin(toString())
                    .joinToString(separator = separator)
        }


/**
 * Creates a string where the digits are grouped by the given separator, like `10,000,000` or `12.345.678`
 *
 * @param separator What to put between groups, usually `","` or `"."`
 * @param groupSize _optional_ - How big the groups should be. Defaults to `3`.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Int32.toString(separator: String, groupSize: Int8 = 3): String = integerValue.toString(separator = separator, groupSize = groupSize)
