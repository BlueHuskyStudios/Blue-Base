@file:JvmName("SemanticNumbers")
@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.extensions.reduceTo
import java.util.*

/**
 * Represents a single die, to be rolled for a random result
 *
 * @author Ben Leggiero
 * @since 2017-03-20
 */
class Die(
        /** Indicates the number of sides on this die. It's nonsense to make this negative. */
        val numberOfSides: Integer
) {
    private val _randomizer = Random()

    /**
     * Rolls the die and returns the result; between `1` and [numberOfSides]
     */
    fun roll(): Integer = _randomizer.nextInteger(1, numberOfSides)


    /**
     * Rolls the die [numberOfRolls] times and returns the average result; between `1` and [numberOfSides]
     */
    fun roll(numberOfRolls: Integer): Fraction {
        val averager = Averager()
        return (0..numberOfRolls).reduceTo(averager.currentAverage) { _, _ ->
            averager.average(roll().fractionValue).currentAverage
        }
    }
}



/**
 * A semantic name for [Die], for those who want it plural
 */
typealias Dice = Die
