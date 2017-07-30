package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*
import java.lang.StrictMath.abs

/* Comparisons, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For comparing stuffs.
 * Created by Kyli Rouge on 2016-10-28.
 */

fun <T : Comparable<T>> min(lhs: T, rhs: T): T = if (lhs < rhs) lhs else rhs


fun <T : Comparable<T>> min(a: T, b: T, vararg n: T): T {
    if (n.isEmpty()) {
        return min(lhs = a, rhs = b)
    }

    val lowestN: T
    if (n.size == 1) {
        lowestN = n[0]
    } else if (n.size == 2) {
        lowestN = min(lhs = n[0], rhs = n[1])
    } else {
        lowestN = n.reduce { x, y -> min(lhs = x, rhs = y) }
    }

    return min(lhs = a, rhs = min(lhs = b, rhs = lowestN))
}


fun <T : Comparable<T>> max(lhs: T, rhs: T): T = if (rhs < lhs) lhs else rhs


fun <T : Comparable<T>> max(a: T, b: T, vararg n: T): T {
    if (n.isEmpty()) {
        return max(lhs = a, rhs = b)
    }

    val highestN: T
    if (n.size == 1) {
        highestN = n[0]
    } else if (n.size == 2) {
        highestN = max(lhs = n[0], rhs = n[1])
    } else {
        highestN = n.reduce { x, y -> max(lhs = x, rhs = y) }
    }

    return max(lhs = a, rhs = max(lhs = b, rhs = highestN))
}


/**
 * Returns the clamped value between `low` and `high`, such that ideally `value` is returned, but never will the
 * returned number be less than `low` or greater than `high`.
 */
fun <T : Comparable<T>> clamp(low: T, value: T, high: T): T
        = max(low, min(value, high))


/**
 * Returns the clamped fraction between `low` and `high`, such that ideally `value` is returned, but never will the
 * returned number be less than `low` or greater than `high`.
 *
 * If `value` [is not a number][isNaN], it is returned verbatim, since it cannot be deemed higher than, equal to, or lower than anything else.
 */
fun clamp(low: Fraction, value: Fraction, high: Fraction): Fraction =
        when {
            value.isNaN() -> value // NaN cannot be compared as higher, equal, or lower
            else -> clamp<Fraction>(low, value, high)
        }



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
                            val otherFloat = other.fractionValue
                            return (otherFloat - rawInt).clampedInt32Value
                        } else {
                            backupResult = (other.fractionValue - rawInt).clampedInt32Value
                        }
                    } else if (raw.isNativeFraction) {
                        val rawFloat = raw.fractionValue
                        if (other.isNativeInteger) {
                            val otherInt = other.integerValue
                            return (otherInt - rawFloat).clampedInt32Value
                        } else if (other.isNativeFraction) {
                            val otherFloat = other.fractionValue
                            return (otherFloat - rawFloat).clampedInt32Value
                        } else {
                            backupResult = (other.fractionValue - rawFloat).clampedInt32Value
                        }
                    } else {
                        backupResult = (other.fractionValue - raw.fractionValue).clampedInt32Value
                    }
                    print("Sorry; I hadn't thought about subtracting a ${other::class.java} from a ${raw::class.java}...")
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
inline val defaultFloat32CalculationTolerance: Float32 get() = 0.0001f

/**
 * The default amount by which 64-bit floating-point calculations and comparisons can be off
 */
inline val defaultFloat64CalculationTolerance: Float64 get() = 0.0001

/**
 * The default amount by which fractional calculations and comparisons can be off
 */
inline val defaultFractionCalculationTolerance: Fraction get() = 0.0001

/**
 * The default amount by which integer calculations and comparisons can be off
 */
inline val defaultIntegerCalculationTolerance: Integer get() = 0


/**
 * Determines whether this fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.equals(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean
        = if (tolerance == 0.0) this == rhs
        else abs(rhs - this) <= tolerance


/**
 * Determines whether this 32-bit fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.equals(rhs: Float32, tolerance: Float32 = defaultFloat32CalculationTolerance): Boolean {
    if (tolerance == 0.0f) {
        return this == rhs
    }
    if (this > Float32.greatestFiniteMagnitude && rhs > Float32.greatestFiniteMagnitude) {
        return true
    } else if (this < -Float32.greatestFiniteMagnitude && rhs < 0 && rhs.isInfinite) {
        return true
    } else {
        return abs(rhs - this) <= tolerance
    }
}


/**
 * Determines whether this 64-bit fraction is equal to the other 32-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float64.equals(rhs: Float32, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean {
    if (tolerance == 0.0) {
        return this.toFloat() == rhs
    }
    if (this > Float32.greatestFiniteMagnitude && rhs > 0 && rhs.isInfinite) {
        return true
    } else if (this < -Float32.greatestFiniteMagnitude && rhs < 0 && rhs.isInfinite) {
        return true
    } else {
        return abs(rhs - this) <= tolerance
    }
}


/**
 * Determines whether this 32-bit fraction is equal to the other 64-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.equals(rhs: Float64, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean
        = rhs.equals(this, tolerance = tolerance)


/**
 * This determines whether this integer is equal to the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultIntegerCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.equals(rhs: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean = abs(rhs - this) <= tolerance


/**
 * Determines whether this fraction is less than or equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.isLessThanOrEqualTo(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = (this - tolerance) <= rhs


/**
 * Determines whether this fraction is greater than or equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.isGreaterThanOrEqualTo(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = (this + tolerance) >= rhs



/**
 * Determines whether this fraction is less than the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. Positive means this is more lenient,
 *                  negative means it's less. `0.0` means it functions just like `<`. Defaults to
 *                  [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.isLessThan(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = (this - tolerance) < rhs


/**
 * Determines whether this fraction is greater than the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.isGreaterThan(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = (this + tolerance) > rhs


/**
 * Determines whether this integer is less than or equal to the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.isLessThanOrEqualTo(rhs: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean = (this - tolerance) <= rhs


/**
 * Determines whether this integer is greater than or equal to the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.isGreaterThanOrEqualTo(rhs: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean = (this + tolerance) >= rhs


/**
 * Determines whether this integer is less than the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.isLessThan(rhs: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean = (this - tolerance) < rhs


/**
 * Determines whether this integer is greater than the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.isGreaterThan(rhs: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean = (this + tolerance) > rhs


/**
 * Determines whether this integer is between `a` and `b`, within the given tolerance
 */
fun Integer.isBetween(a: Integer, b: Integer, tolerance: Integer = defaultIntegerCalculationTolerance): Boolean {
    val largest = max(a, b)
    val smallest = min(a, b)
    return (this + tolerance) > smallest && (this - tolerance) < largest
}


/**
 * Determines whether this fraction is between `a` and `b`, within the given tolerance
 */
fun Fraction.isBetween(a: Fraction, b: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean {
    val largest = max(a, b)
    val smallest = min(a, b)
    return (this + tolerance) > smallest && (this - tolerance) < largest
}
