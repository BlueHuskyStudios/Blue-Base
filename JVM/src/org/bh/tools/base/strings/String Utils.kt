@file:Suppress("unused")

package org.bh.tools.base.strings

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.reduceTo
import org.bh.tools.base.math.components
import org.bh.tools.base.math.hasFractionComponent
import org.bh.tools.base.math.integerValue
import org.bh.tools.base.math.rounded
import org.bh.tools.base.struct.int32Value

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * Helps you with strings
 *
 * @author Ben C. Leggiero
 * @since 2016-10-09
 */

/**
 * Converts this string to an abbreviation, optionally with a delimiter after each character of the result. For
 * example, `"Quick brown fox"` would become `"QBC"`.
 *
 * @param delimiter (optional) - The sequence to place after each character of the result. By default, this is the
 * empty string, so `"Quick brown fox"` becomes `"QBC"`, but if you specify `"."`, then it becomes `"Q.B.C."`
 *
 * @return A [CharSequence] representing this one, but abbreviated
 */
fun CharSequence.toAbbreviation(delimiter: CharSequence = ""): CharSequence {
    val ret = StringBuilder()
    var shouldAdd = false
    chars().forEach { c ->
        if (Character.isWhitespace(c)) {
            shouldAdd = true
        } else if (shouldAdd || Character.isUpperCase(c)) {
            ret += Character.toUpperCase(c) + delimiter
            shouldAdd = false
        }
    }
    return ret
}

/**
 * Allows `+=` to be used as shorthand for [StringBuilder.append]
 */
infix operator fun StringBuilder.plusAssign(rhs: Any): Unit {
    this.append(rhs)
}

/**
 * Allows the `+` operator to convert any left-hand-side value to a character sequence prepended onto the
 * right-hand-side
 */
infix operator fun Any.plus(rhs: String): String = this.toString().plus(rhs)

/**
 * Allows the `+` operator to convert any left-hand-side value to a character sequence prepended onto the
 * right-hand-side. The exact type of the returned value is not guaranteed.
 */
infix operator fun Any.plus(rhs: CharSequence): CharSequence = concat(this, rhs)

/**
 * Concatenates the [CharSequence] value of `lhs` before `rhs` and returns the result. The exact type of the returned
 * value is not guaranteed.
 */
fun concat(lhs: Any, rhs: CharSequence): CharSequence {
    if (lhs is String) {
        return lhs.plus(rhs)
    } else if (lhs is StringBuilder) {
        return lhs.append(rhs)
    } else {
        return StringBuilder().append(lhs).append(rhs)
    }
}

/**
 * Indicates whether this character sequence contains the other, ignoring case
 */
fun CharSequence.containsIgnoreCase(cs: CharSequence): Boolean
        = this.toString().toLowerCase().contains(cs.toString().toLowerCase())


/**
 * Repeats the given string `rhs` times.
 */
operator fun String.times(rhs: Integer): String = (0..rhs).map { this }.reduce { old, current -> old + current }


operator fun String.times(rhs: Fraction): String {
    if (!rhs.hasFractionComponent) {
        return times(rhs.integerValue)
    }
    val partialRepeatingString = (1..(rhs.integerValue - 1)).reduceTo(this) { concatenatedString, _ ->
        /*return*/ concatenatedString + this
    }

    val remainingCharacterCount = (rhs.components.fractionPart * length).rounded().integerValue

    if (remainingCharacterCount <= 0L || remainingCharacterCount >= length) {
        return partialRepeatingString + this
    } else {
        return partialRepeatingString + substring((0..remainingCharacterCount).int32Value)
    }
}
