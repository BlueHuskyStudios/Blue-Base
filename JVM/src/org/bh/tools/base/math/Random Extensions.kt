@file:Suppress("unused")

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

/**
 * Finds a pseudorandom fraction value in the given bounds, inclusively
 */
fun Random.nextFraction(bounds: OpenRange<Fraction>): Fraction {

    val closedRange = ClosedRange(bounds.startInclusive ?: Fraction.min, bounds.endInclusive ?: Fraction.max)

    val r = nextFraction() // 0.0 to 1.0
    return ((r * closedRange.size) + closedRange.min)
}


/**
 * Finds a pseudorandom integer between the given minimum and maximum values, inclusively
 */
fun Random.nextInteger(minimumValue: Integer, maximumValue: Integer): Integer {
    return nextInteger(OpenRange(startInclusive = minimumValue, endInclusive = maximumValue))
}


/**
 * Finds a pseudorandom integer between `0` and the given maximum value, inclusively.
 */
fun Random.nextInteger(maximumValue: Integer): Integer = nextInteger(minimumValue = 0L, maximumValue = maximumValue)


/**
 * Finds a pseudorandom integer within the given bounds, inclusively.
 */
fun Random.nextInteger(bounds: OpenRange<Integer>): Integer {
    return nextFraction(bounds.fractionValue).integerValue
}


/**
 * Finds a pseudorandom integer, not kept within any bounds; between the lowest and the highest possible values.
 *
 * @see Int.MIN_VALUE
 * @see Int.MAX_VALUE
 */
fun Random.nextInteger() = nextInteger(bounds = OpenRange.infiniteRange())


/**
 * @see Random.nextDouble
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Random.nextFraction() = nextDouble()
