package org.bh.tools.base.math

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.BHInt
import org.bh.tools.base.abstraction.Float32
import org.bh.tools.base.abstraction.Float64
import java.lang.StrictMath.abs

/* Comparisons, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For comparing stuffs.
 * Created by Kyli Rouge on 2016-10-28.
 */

open class ComparableComparator<T: Comparable<T>>: kotlin.Comparator<T> {
    override fun compare(lhs: T, rhs: T): Int {
        return lhs.compareTo(rhs)
    }
}



typealias Comparator<ComparedType> = (lhs: ComparedType, rhs: ComparedType) -> ComparisonResult



/**
 * The result of a comparison
 */
enum class ComparisonResult(
        /**
         * The value of this result as an [Int]
         */
        val intValue: Int
) {
    /**
     * Indicates that the left item is less/lower than the right item (the value ascends from left to right)
     */
    ascending(-1),

    /**
     * Indicates that the left item is the same as the right
     */
    same(0),

    /**
     * Indicates that the left item is greater/higher than the right item (the value descends from left to right)
     */
    descending(1);

    /**
     * The value of this result as a native type
     */
    val nativeValue: Int get() = intValue

    companion object {

        fun from(raw: Number): ComparisonResult {
            return from(object : Comparable<Number> {
                override fun compareTo(other: Number): Int {
                    val backupResult: Int
                    if (raw.isNativeInteger) {
                        val rawInt = raw.integerValue
                        if (other.isNativeInteger) {
                            val otherInt = other.integerValue
                            return (otherInt - rawInt).clampedInt32Value
                        } else if (other.isNativeFraction) {
                            val otherFloat = other.floatValue
                            return (otherFloat - rawInt).clampedInt32Value
                        } else {
                            backupResult = (other.floatValue - rawInt).clampedInt32Value
                        }
                    } else if (raw.isNativeFraction) {
                        val rawFloat = raw.floatValue
                        if (other.isNativeInteger) {
                            val otherInt = other.integerValue
                            return (otherInt - rawFloat).clampedInt32Value
                        } else if (other.isNativeFraction) {
                            val otherFloat = other.floatValue
                            return (otherFloat - rawFloat).clampedInt32Value
                        } else {
                            backupResult = (other.floatValue - rawFloat).clampedInt32Value
                        }
                    } else {
                        backupResult = (other.floatValue - raw.floatValue).clampedInt32Value
                    }
                    print("Sorry; I hadn't thought about subtracting a ${other.javaClass} from a ${raw.javaClass}...")
                    print("I'll attempt to convert them to floats and do the math from there!")
                    return backupResult
                }
            })
        }


        /**
         * Creates a ComparisonResult based on how the given object can be compared to 0
         */
        fun from(comparable: Comparable<Number>): ComparisonResult {
            return if (comparable < 0) ascending else if (comparable > 0) descending else equal
        }

        /**
         * Indicates that the **left** item is greater/higher than the right
         */
        val left: ComparisonResult get() = descending

        /**
         * Indicates that **neither** item is greater/higher than the other
         */
        val equal: ComparisonResult get() = same

        /**
         * Indicates that the **right** item is greater/higher than the left
         */
        val right: ComparisonResult get() = ascending


        /**
         * Indicates that the **left** item is greater/higher than the right
         */
        val greaterThan: ComparisonResult get() = descending

        /**
         * Indicates that the **left** item is less/lower than the right
         */
        val lessThan: ComparisonResult get() = ascending
    }
}




/**
 * The default amount by which 32-bit floating-point calculations and comparisons can be off
 */
val defaultFloat32CalculationTolerance: Float32 get() = 0.0001f

/**
 * The default amount by which 64-bit floating-point calculations and comparisons can be off
 */
val defaultFloat64CalculationTolerance: Float64 get() = 0.0001

/**
 * The default amount by which fractional calculations and comparisons can be off
 */
val defaultFractionCalculationTolerance: BHFloat get() = 0.0001

/**
 * The default amount by which integer calculations and comparisons can be off
 */
val defaultIntegerCalculationTolerance: BHInt get() = 0


/**
 * Determines whether this fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun BHFloat.equals(rhs: BHFloat, tolerance: BHFloat = defaultFractionCalculationTolerance): Boolean {
    return abs(rhs - this) < tolerance
}


/**
 * If you use this, you're being silly. Anyway, this determines whether this integer is equal to the other, within a
 * given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultIntegerCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun BHInt.equals(rhs: BHInt, tolerance: BHInt = defaultIntegerCalculationTolerance): Boolean {
    return abs(rhs - this) < tolerance
}
