package org.bh.tools.base.math

import org.bh.tools.base.abstraction.BHInt
import javax.activation.UnsupportedDataTypeException

/* Comparisons, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For comparing stuffs.
 * Created by Kyli Rouge on 2016-10-28.
 */

open class ComparableComparator<T: Comparable<T>>: kotlin.comparisons.Comparator<T> {
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
     * This enum as a native type
     */
    typealias NativeType = Int
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
