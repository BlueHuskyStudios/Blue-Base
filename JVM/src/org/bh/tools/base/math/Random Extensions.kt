package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Integer
import java.util.*

/*
 * Random != funny
 * But [Random] == `fun` with this file!
 *
 * @author Ben Leggiero
 * @since 2017-02-19
 */

fun Random.nextInteger(bound: Integer): Integer {
    if (bound <= 0)
        throw IllegalArgumentException("bound must be greater than 0")

    val r = (nextFraction() - 0.5) / 2 // -1.0 to 1.0
    return (r * Integer.max).clampedIntegerValue
}


@Suppress("NOTHING_TO_INLINE")
inline fun Random.nextFraction() = nextDouble()
