package org.bh.tools.base.strings

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.math.*

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
 * @param roundingDirection The direction to round if this is not already an integer
 * @param roundingThreshold The part at which the number rounding should occur
 *
 * @return A string representation of this fraction with the exact given number of digits after the radix.
 */
fun Float64.toString(fractionDigits: Int32,
                     roundingDirection: RoundingDirection = RoundingDirection.default,
                     roundingThreshold: RoundingThreshold = RoundingThreshold.default
): String {
//    return BigDecimal.valueOf(this).setScale(fractionDigits, RoundingMode(this)).toString()

    // /* PRETEND: */ 123.456.toString(fractionDigits = 2) | 123.4.toString(fractionDigits = 3) | 123.4.toString(fractionDigits = 0)

    //                                                                 // 123.456  | 123.4     | 123.4
    val multiplier = 10 toThePowerOf fractionDigits                    // 100      | 1000      | 1
    val shifted = this * multiplier                                    // 12345.6  | 123400.0  | 123.4
    val rounded = shifted.rounded(
            direction = roundingDirection,
            threshold = roundingThreshold)                             // 12346.0  | 123400.0  | 123.0
    val reshifted = rounded / multiplier                               // 123.46   | 123.4     | 123.0
    val rawString = reshifted.toString()                               // "123.46" | "123.4"   | "123.0"
    val digitsBeforeRadix = rawString.substringBefore(delimiter = '.') // "123"    | "123"     | "123"
    if (0 == fractionDigits) {                                         // false    | false     | true
        return digitsBeforeRadix                                       //          |           | "123"
    }
    var digitsAfterRadix = rawString.substringAfter(delimiter = '.')   // "46"     | "4"       |
    val missingDigits = fractionDigits - digitsAfterRadix.length       // 0        | 2         |
    if (missingDigits > 0) {                                           // false    | true      |
        digitsAfterRadix += "0" * missingDigits                        //          | "400"     |
    }
    return "$digitsBeforeRadix.$digitsAfterRadix"                      // "123.46" | "123.400" |
}


fun Float32.toString(fractionDigits: Int32,
                     roundingDirection: RoundingDirection = RoundingDirection.default,
                     roundingThreshold: RoundingThreshold = RoundingThreshold.default
) = float64Value.toString(
        fractionDigits = fractionDigits,
        roundingDirection = roundingDirection,
        roundingThreshold = roundingThreshold)


private fun decimalSeparatorRegex(groupSize: Int8) = Regex("^(\\d{1,$groupSize}?)(\\d{$groupSize})*\$")


/**
 * Creates a string of this integer where the digits are grouped by the given separator, like `10,000,000`,
 * `12.345.678`, or `1 222 333`. If this integer is <= [groupSize], it is stringified without separators.
 *
 * @param separator What to put between groups, usually `","` or `"."`
 * @param groupSize _optional_ - How big the groups should be. Defaults to `3`.
 *
 * @return A string representation of this integer, with digits grouped as specified
 */
fun Int64.toString(separator: String, groupSize: Int8 = 3): String =
        if (numberOfDigits() <= groupSize) {
            toString()
        }
        else {
            decimalSeparatorRegex(groupSize)
                    .find(input = toString())?.groupValues?.allButFirst
                    ?.joinToString(separator = separator)
                    ?: toString()
        }


/**
 * Creates a string where the digits are grouped by the given separator, like `10,000,000` or `12.345.678`
 *
 * @param separator What to put between groups, usually `","` or `"."`
 * @param groupSize _optional_ - How big the groups should be. Defaults to `3`.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Int32.toString(separator: String, groupSize: Int8 = 3): String = int64Value.toString(separator = separator, groupSize = groupSize)
