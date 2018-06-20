@file:Suppress("MemberVisibilityCanPrivate", "unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*

/* Comparisons, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For comparing stuffs.
 * Created by Kyli Rouge on 2016-10-28.
 */


/**
 * Finds the smallest of these two
 */
fun <T : Comparable<T>> min(lhs: T, rhs: T): T = if (lhs < rhs) lhs else rhs


/**
 * Finds the smallest of any number of values
 *
 * If only 2 are given, [min] is called.
 */
fun <T : Comparable<T>> min(a: T, b: T, vararg n: T): T = when {
    n.isEmpty() -> min(lhs = a, rhs = b)
    else -> min(lhs = a, rhs = min(lhs = b, rhs = n.min()))
}


/**
 * Finds the smallest element in this array
 */
fun <T: Comparable<T>> Array<T>.min(): T = when (size) {
    1 -> this[0]
    2 -> min(lhs = this[0], rhs = this[1])
    else -> reduce { x, y -> min(lhs = x, rhs = y) }
}


/**
 * Finds the largest of these two
 */
fun <T : Comparable<T>> max(lhs: T, rhs: T): T = if (rhs < lhs) lhs else rhs


/**
 * Finds the largest of any number of values
 *
 * If only 2 are given, [max] is called.
 */
fun <T : Comparable<T>> max(a: T, b: T, vararg n: T): T = when {
    n.isEmpty() -> max(lhs = a, rhs = b)
    else -> max(lhs = a, rhs = max(lhs = b, rhs = n.max()))
}


/**
 * Finds the largest element in this array
 */
fun <T: Comparable<T>> Array<T>.max(): T = when (size) {
    1 -> this[0]
    2 -> max(lhs = this[0], rhs = this[1])
    else -> reduce { x, y -> max(lhs = x, rhs = y) }
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



open class ComparableComparator<in T: Comparable<T>>: Comparator<T> {
    override fun compare(lhs: T, rhs: T): ComparisonResult {
        return ComparisonResult(lhs.compareTo(rhs))
    }
}



typealias ComparatorBlock<ComparedType> = (lhs: ComparedType, rhs: ComparedType) -> ComparisonResult
interface Comparator<in ComparedType> { fun compare(lhs: ComparedType, rhs: ComparedType): ComparisonResult }



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

        operator fun invoke(raw: Number): ComparisonResult = from(raw)


        fun from(raw: Number): ComparisonResult {
            return from(object : Comparable<Number> {
                override fun compareTo(other: Number): Int32 {
                    val backupResult: Int32
                    when {
                        raw.isNativeInteger -> {
                            val rawInt = raw.integerValue
                            when {
                                other.isNativeInteger -> return (other.integerValue - rawInt).clampedInt32Value
                                other.isNativeFraction -> return (other.fractionValue - rawInt).clampedInt32Value
                                else -> backupResult = (other.fractionValue - rawInt).clampedInt32Value
                            }
                        }
                        raw.isNativeFraction -> {
                            val rawFloat = raw.fractionValue
                            when {
                                other.isNativeInteger -> return (other.integerValue - rawFloat).clampedInt32Value
                                other.isNativeFraction -> return (other.fractionValue - rawFloat).clampedInt32Value
                                else -> backupResult = (other.fractionValue - rawFloat).clampedInt32Value
                            }
                        }
                        else -> backupResult = (other.fractionValue - raw.fractionValue).clampedInt32Value
                    }
                    println("Sorry; I hadn't thought about subtracting a ${other::class.simpleName} from a ${raw::class.simpleName}...")
                    println("I'll attempt to convert them to fractions and do the math from there!")
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



typealias Tolerance = Fraction



/**
 * The default amount by which 32-bit floating-point calculations and comparisons can be off
 */
const val defaultFloat32CalculationTolerance: Float32 = 0.0001f

/**
 * The default amount by which 64-bit floating-point calculations and comparisons can be off
 */
const val defaultFloat64CalculationTolerance: Float64 = 0.0001

/**
 * The default amount by which fractional calculations and comparisons can be off
 */
const val defaultFractionCalculationTolerance: Fraction = 0.0001

/**
 * The default amount by which integer calculations and comparisons can be off
 */
const val defaultIntegerCalculationTolerance: Integer = 0

/**
 * The default amount by which calculations and comparisons can be off
 */
inline val defaultCalculationTolerance: Tolerance get() = defaultFractionCalculationTolerance


/**
 *
 */
interface TolerableEquality<Self: TolerableEquality<Self>> {
    fun equals(other: Self, tolerance: Tolerance = defaultCalculationTolerance): Boolean
}

/**
 * An unambiguous way to check if two things are approximately qual
 */
fun <Self: TolerableEquality<Self>> TolerableEquality<Self>.isApproximately(other: Self, tolerance: Tolerance = defaultCalculationTolerance): Boolean
        = this.equals(other, tolerance = tolerance)



/**
 * Determines whether this 64-bit fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float64.equals(rhs: Float64, tolerance: Float64 = defaultFloat64CalculationTolerance) = when {
    tolerance == 0.0 -> this == rhs
    this > Float64.greatestFiniteMagnitude && rhs > Float64.greatestFiniteMagnitude -> true
    this < -Float64.greatestFiniteMagnitude && rhs < 0 && rhs.isInfinite -> true
    else -> (rhs - this).absoluteValue <= tolerance
}



/**
 * Determines whether this 64-bit fraction is approximately equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float64.isApproximately(rhs: Float64, tolerance: Float64 = defaultFloat64CalculationTolerance)
        = this.equals(rhs, tolerance = tolerance)


/**
 * Determines whether this 32-bit fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFloat32CalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.equals(rhs: Float32, tolerance: Float32 = defaultFloat32CalculationTolerance): Boolean = when {
    tolerance == 0.0f -> this == rhs
    this > Float32.greatestFiniteMagnitude && rhs > Float32.greatestFiniteMagnitude -> true
    this < -Float32.greatestFiniteMagnitude && rhs < 0 && rhs.isInfinite -> true
    else -> (rhs - this).absoluteValue <= tolerance
}


/**
 * Determines whether this 32-bit fraction is equal to the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFloat32CalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.isApproximately(rhs: Float32, tolerance: Float32 = defaultFloat32CalculationTolerance): Boolean
        = this.equals(rhs, tolerance = tolerance)


/**
 * Determines whether this 64-bit fraction is equal to the other 32-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float64.equals(rhs: Float32, tolerance: Tolerance = defaultCalculationTolerance): Boolean = when {
    tolerance == 0.0 -> this.toFloat() == rhs
    this > Float32.greatestFiniteMagnitude && rhs > 0 && rhs.isInfinite -> true
    this < -Float32.greatestFiniteMagnitude && rhs < 0 && rhs.isInfinite -> true
    else -> abs(rhs - this) <= tolerance
}


/**
 * Determines whether this 64-bit fraction is equal to the other 32-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float64.isApproximately(rhs: Float32, tolerance: Tolerance = defaultCalculationTolerance): Boolean
        = this.equals(rhs, tolerance = tolerance)


/**
 * Determines whether this 32-bit fraction is equal to the other 64-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.equals(rhs: Float64, tolerance: Tolerance = defaultCalculationTolerance): Boolean
        = rhs.equals(this, tolerance = tolerance)


/**
 * Determines whether this 32-bit fraction is equal to the other 64-bit one, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Float32.isApproximately(rhs: Float64, tolerance: Tolerance = defaultCalculationTolerance): Boolean
        = this.equals(rhs, tolerance = tolerance)


/**
 * This determines whether this integer is equal to the other, within a given tolerance.
 *
 * @param rhs       The other integer to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Integer.equals(rhs: Integer, tolerance: Tolerance = defaultCalculationTolerance): Boolean = abs(rhs - this) <= tolerance


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
fun Fraction.isLessThan(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = !equals(rhs, tolerance) && (this - tolerance) < rhs


/**
 * Determines whether this fraction is greater than the other, within a given tolerance.
 *
 * @param rhs       The other fraction to compare to this one
 * @param tolerance `optional` - The amount by which the values can be off. It's nonsense to make this negative.
 *                  Defaults to [defaultFractionCalculationTolerance]
 * @return `true` iff this value and the other are equal within the given tolerance
 */
fun Fraction.isGreaterThan(rhs: Fraction, tolerance: Fraction = defaultFractionCalculationTolerance): Boolean = !equals(rhs, tolerance) && (this + tolerance) > rhs


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
