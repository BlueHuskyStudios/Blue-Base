package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.struct.*
import java.util.*

/*
 * Random != funny
 * But [Random] == `fun` with this file!
 *
 * @author Ben Leggiero
 * @since 2017-02-19
 */

fun Random.nextFraction(bounds: OpenRange<Fraction>): Fraction {

    val closedRange = ClosedRange(bounds.startInclusive ?: Fraction.min, bounds.endInclusive ?: Fraction.max)

    val r = nextFraction() // 0.0 to 1.0
    return ((r * closedRange.size) + closedRange.min)
}


fun Random.nextInteger(minimumValue: Integer, maximumValue: Integer): Integer {
    return nextInteger(OpenRange(startInclusive = minimumValue, endInclusive = maximumValue))
}


fun Random.nextInteger(maximumValue: Integer): Integer = nextInteger(minimumValue = 0L, maximumValue = maximumValue)


fun Random.nextInteger(bounds: OpenRange<Integer>): Integer {
    return nextFraction(bounds.fractionValue).integerValue
}


fun Random.nextInteger() = nextInteger(bounds = OpenRange.infiniteRange())


@Suppress("NOTHING_TO_INLINE")
inline fun Random.nextFraction() = nextDouble()
