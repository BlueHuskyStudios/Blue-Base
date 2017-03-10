@file:Suppress("unused")

package org.bh.tools.base.struct

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.Index
import org.bh.tools.base.math.*

/**
 * OpenRange, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * Represents a range of values
 *
 * @author Kyli
 * @since 2016-10-24
 *
 * @param NumberType The type of number in this range. It doesn't technically have to be a number, just has to be
 *                   comparable to others of its type.
 *
 */
open class OpenRange<NumberType: Comparable<NumberType>>
    /**
     * Creates a new OpenRange, with the given start and end values. If the start is greater than the end, they are swapped.
     *
     * @param startInclusive The first value in this range, inclusive. Passing `null` indicates that this has no start; it is an open range.
     * @param endInclusive The second value in this range, inclusive. Passing `null` indicates that this has no end; it is an open range.
     */
    (startInclusive: NumberType?, endInclusive: NumberType?) {

    /** The first value of this range. `null` indicates that it is open and has no defined start. */
    val startInclusive: NumberType?

    /** The last value of this range. `null` indicates that it is open and has no defined end. */
    val endInclusive: NumberType?

    /** Indicates whether this range is empty; there is no stored value; no start or end. */
    open val isEmpty = false

    private val isLeftOpen = startInclusive == null
    private val isRightOpen = endInclusive == null

    init {
        @Suppress("LeakingThis")
        if (startInclusive != null && endInclusive != null && startInclusive > endInclusive) {
            this.startInclusive = endInclusive
            this.endInclusive = startInclusive
        } else {
            this.startInclusive = startInclusive
            this.endInclusive = endInclusive
        }
    }

    constructor(onlyValue: NumberType): this(startInclusive = onlyValue, endInclusive = onlyValue)

    /** Indicates whether either side of this range extends infinitely */
    open val isOpen: Boolean by lazy { isStartOpen || isEndOpen }

    /** Indicates whether this range extends to -∞ */
    open val isStartOpen: Boolean by lazy { startInclusive == null }

    /** Indicates whether this range extends to +∞ */
    open val isEndOpen: Boolean by lazy { endInclusive == null }

    /** Indicates whether this range extends from -∞ to +∞ */
    open val isInfinite: Boolean by lazy { isStartOpen && isEndOpen }

    /** Indicates whether this is just a single value; start and end are equal and it is not open */
    open val isPoint: Boolean by lazy { !isOpen && startInclusive == endInclusive }


    /**
     * Indicates whether the given value is in this range
     *
     * Fringe cases:
     *  - if `value` is [NaN][Double.NaN], this always returns `false`.
     *  - if `value` is [-∞][Double.NEGATIVE_INFINITY], [startInclusive] must be `null`
     *  - if `value` is [∞][Double.POSITIVE_INFINITY], [endInclusive] must be `null`
     */
    open fun contains(value: NumberType): Boolean {
        if (value is Number) {
            when {
                value.isNaN -> return false
                value.isNegativeInfinity -> return startInclusive == null
                value.isPositiveInfinity -> return endInclusive == null
            }
        }
        return when {
            startInclusive != null && value < startInclusive -> false
            endInclusive != null && value > endInclusive -> false
            else -> true
        }
    }


//    fun intersects(other: OpenRange<NumberType>): Boolean {
//        if (isInfinite) { // (-∞, ∞) ∩ (?, ?)
//            return true
//        }
///*                   */ when (startInclusive) {
///* (-∞, ?) ∩ ( ?, ?) */     null -> when (other.startInclusive) {
///* (-∞, ?) ∩ (-∞, ?) */         null -> return true
///* (-∞, ?) ∩ ( #, ?) */         else -> when (endInclusive) {
///* (-∞, ∞) ∩ ( #, ?) */             null -> when (other.endInclusive) {
///* (-∞, ∞) ∩ ( #, ∞) */                 null -> return true
///* (-∞, ∞) ∩ ( #, #) */                 else -> return true
///*                   */             }
///* (-∞, ∞) ∩ (-∞, #) */             else -> when (other.endInclusive) {
///* (-∞, #) ∩ (-∞, #) */                 null -> return true
///* (-∞, #) ∩ ( #, #) */                 else -> return endInclusive < other.startInclusive
///*                   */             }
///*                   */         }
///*                   */     }
///* ( #, ?) ∩ ( ?, ?) */     else -> when (other.startInclusive) {
///*                   */     }
///*                   */ }
//    }


    open fun intersects(other: OpenRange<NumberType>): Boolean {
        if (other.isEmpty) { // Empty ranges never intersect anything
            return false
        }

        if (startInclusive == null) { // (-∞, ?) ∩ ( ?, ?)
            if (endInclusive == null) { // (-∞, ∞) ∩ ( ?, ?)
                return true
            } else { // (-∞, #) ∩ ( ?, ?)
                if (other.startInclusive == null) { // (-∞, #) ∩ (-∞, ?)
                    return true
                } else { // (-∞, #) ∩ ( #, ?)
                    return other.startInclusive <= endInclusive
                }
            }
        } else { // ( #, ?) ∩ ( ?, ?)
            if (endInclusive == null) { // ( #, ∞) ∩ ( ?, ?)
                if (other.startInclusive == null) { // ( #, ∞) ∩ (-∞, ?)
                    if (other.endInclusive == null)  { // ( #, ∞) ∩ (-∞, ∞)
                        return true
                    } else { // ( #, ∞) ∩ (-∞, #)
                        return startInclusive <= other.endInclusive
                    }
                } else { // ( #, ∞) ∩ ( #, ?)
                    if (other.endInclusive == null)  { // ( #, ∞) ∩ ( #, ∞)
                        return true
                    } else { // ( #, ∞) ∩ ( #, #)
                        return startInclusive <= other.endInclusive
                    }
                }
            } else { // ( #, #) ∩ ( ?, ?)
                if (other.startInclusive == null) { // ( #, #) ∩ (-∞, ?)
                    if (other.endInclusive == null) { // ( #, #) ∩ (-∞, ∞)
                        return true
                    } else { // ( #, #) ∩ (-∞, #)
                        return startInclusive <= other.endInclusive
                    }
                } else { // ( #, #) ∩ ( #, ?)
                    if (other.endInclusive == null) { // ( #, #) ∩ ( #, ∞)
                        return other.startInclusive <= endInclusive
                    } else { // ( #, #) ∩ ( #, #)
                        return startInclusive <= other.endInclusive
                                && other.startInclusive <= endInclusive
                    }
                }
            }
        }
    }


    open fun union(other: OpenRange<NumberType>): OpenRange<NumberType> {
        val newStart: NumberType?
            = if (this.startInclusive != null && other.startInclusive != null) min(this.startInclusive, other.startInclusive)
            else null

        val newEnd: NumberType?
            = if (this.endInclusive != null && other.endInclusive != null) max(this.endInclusive, other.endInclusive)
            else null

        return OpenRange(startInclusive = newStart, endInclusive = newEnd)
    }


    open fun intersection(other: OpenRange<NumberType>): OpenRange<NumberType> {
        if (!intersects(other)) {
            return empty()
        }

        val newStart: NumberType?
            = if (this.startInclusive != null && other.startInclusive != null) max(this.startInclusive, other.startInclusive)
            else this.startInclusive ?: other.startInclusive

        val newEnd: NumberType?
            = if (this.endInclusive != null && other.endInclusive != null) min(this.endInclusive, other.endInclusive)
            else this.endInclusive ?: other.endInclusive

        return OpenRange(startInclusive = newStart, endInclusive = newEnd)
    }


    fun containsCompletely(other: OpenRange<NumberType>): Boolean = when {
        isLeftOpen -> when {
            isRightOpen -> true
            other.isRightOpen -> false
            else -> other.endInclusive!! <= this.endInclusive!! // FIXME: Possible NPE
        }
        isRightOpen -> when {
            other.isLeftOpen -> false
            else -> other.startInclusive!! >= this.startInclusive!! // FIXME: Possible NPE
        }
        else -> when {
            other.isLeftOpen || other.isRightOpen -> false
            else -> this.contains(other.startInclusive!!) && this.contains(other.endInclusive!!)
        }
    }

//    override operator fun compareTo(other: OpenRange<NumberType>): ComparisonResult.NativeType {
//        if (this.start == other.start) {
//            if (this.endInclusive == other.endInclusive) {
//                return 0
//            } else {
//                if (this.start == null) {
//                    if (this.end == null) {
//                        if (other.start == null) {
//                            if (other.end == null) {
//                                return ComparisonResult.same.nativeValue
//                            } else {
//                                return ComparisonResult.left.nativeValue
//                            }
//                        } else { // This is open, other is left-open
//                            if (other.end == null) {
//
//                            } else {
//
//                            }
//                        }
//                    } else {
//
//                    }
//                }
//                return if (this.endInclusive < other.endInclusive) -1 else 1
//            }
//        } else {
//            return if (this.start < other.start) -1 else 1
//        }
//    }

    override fun equals(other: Any?): Boolean {
        if (other is OpenRange<*>) {
            return other.startInclusive == this.startInclusive
                    && other.endInclusive == this.endInclusive
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var result = startInclusive?.hashCode() ?: Int.min
        result = 31 * result + (endInclusive?.hashCode() ?: Int.max)
        return result
    }


    override fun toString(): String {
        return "(${startInclusive ?: "-∞"}, ${endInclusive ?: "∞"})"
    }


    companion object {
        /** A special value that represents an open start of an open range */
        inline val openStart get() = openEnd
        /** A special value that represents an open end of an open range */
        inline val openEnd get() = null

        /** A range that covers all values */
        fun<NumberType: Comparable<NumberType>> infiniteRange(): OpenRange<NumberType> = OpenRange(openStart, openEnd)

        /** A special range that does not an can not contain any value */
        fun<NumberType: Comparable<NumberType>> empty(): OpenRange<NumberType> = _EmptyOpenRange()
    }
}

/** The inclusive start of this range */
inline val<NumberType: Comparable<NumberType>> OpenRange<NumberType>.start: NumberType? get() = startInclusive
/** The inclusive end of this range */
inline val<NumberType: Comparable<NumberType>> OpenRange<NumberType>.end: NumberType? get() = endInclusive



abstract class ComputableOpenRange<NumberType: Comparable<NumberType>>(startInclusive: NumberType?, endInclusive: NumberType?) : OpenRange<NumberType>(startInclusive, endInclusive) {



    /** Indicates whether this is just a single value; start and end are equal within the given `tolerange` and it is not open */
    abstract fun isPoint(tolerance: NumberType): Boolean

    /** Indicates whether this is just a single value; start and end are equal and it is not open */
    override abstract val isPoint: Boolean

    /**
     * Calls [equals(other, tolerance)] with a default tolerance
     */
    abstract fun equals(other: ComputableOpenRange<NumberType>): Boolean


    /**
     * Determines whether this range is equal to the other, within a given tolerance
     *
     * @param other     The other range which might be equal to this one
     * @param tolerance The distance by which this range and the other one can be off while still considered equal.
     *
     * @return `true` iff this and the other ranges are said to be equal
     */
    fun equals(other: ComputableOpenRange<NumberType>, tolerance: NumberType): Boolean {
        val startEquals = when (startInclusive) {
            null -> other.startInclusive == null
            else -> when (other.startInclusive) {
                null -> return false
                else -> isEqual(startInclusive, other.startInclusive, tolerance = tolerance)
            }
        }

        if (!startEquals) {
            return false
        }

        val endEquals = when (endInclusive) {
            null -> other.endInclusive == null
            else -> when (other.endInclusive) {
                null -> return false
                else -> isEqual(endInclusive, other.endInclusive, tolerance = tolerance)
            }
        }

        return endEquals
    }


    /**
     * Calls [intersects(other, tolerance)] with a default tolerance
     */
    abstract fun intersects(other: ComputableOpenRange<NumberType>): Boolean


    /**
     * Determines whether this range intersects the other, within a given tolerance
     *
     * @param other     The other range which might intersect this one
     * @param tolerance The distance by which this range and the other one can be off while still considered intersecting.
     *
     * @return `true` iff this and the other ranges are said to intersect
     */
    open fun intersects(other: ComputableOpenRange<NumberType>, tolerance: NumberType): Boolean {
        if (startInclusive == null) { // (-∞, ?) ∩ ( ?, ?)
            if (endInclusive == null) { // (-∞, ∞) ∩ ( ?, ?)
                return true
            } else { // (-∞, #) ∩ ( ?, ?)
                if (other.startInclusive == null) { // (-∞, #) ∩ (-∞, ?)
                    return true
                } else { // (-∞, #) ∩ ( #, ?)
                    return isLessThanOrEqual(other.startInclusive, endInclusive, tolerance = tolerance)
                }
            }
        } else { // ( #, ?) ∩ ( ?, ?)
            if (endInclusive == null) { // ( #, ∞) ∩ ( ?, ?)
                if (other.startInclusive == null) { // ( #, ∞) ∩ (-∞, ?)
                    if (other.endInclusive == null)  { // ( #, ∞) ∩ (-∞, ∞)
                        return true
                    } else { // ( #, ∞) ∩ (-∞, #)
                        return isLessThanOrEqual(startInclusive, other.endInclusive, tolerance = tolerance)
                    }
                } else { // ( #, ∞) ∩ ( #, ?)
                    if (other.endInclusive == null)  { // ( #, ∞) ∩ ( #, ∞)
                        return true
                    } else { // ( #, ∞) ∩ ( #, #)
                        return isLessThanOrEqual(startInclusive, other.endInclusive, tolerance = tolerance)
                    }
                }
            } else { // ( #, #) ∩ ( ?, ?)
                if (other.startInclusive == null) { // ( #, #) ∩ (-∞, ?)
                    if (other.endInclusive == null) { // ( #, #) ∩ (-∞, ∞)
                        return true
                    } else { // ( #, #) ∩ (-∞, #)
                        return isLessThanOrEqual(startInclusive, other.endInclusive, tolerance = tolerance)
                    }
                } else { // ( #, #) ∩ ( #, ?)
                    if (other.endInclusive == null) { // ( #, #) ∩ ( #, ∞)
                        return isLessThanOrEqual(other.startInclusive, endInclusive, tolerance = tolerance)
                    } else { // ( #, #) ∩ ( #, #)
                        return isLessThanOrEqual(startInclusive, other.endInclusive, tolerance = tolerance)
                                && isLessThanOrEqual(other.startInclusive, endInclusive, tolerance = tolerance)
                    }
                }
            }
        }
    }

    abstract fun intersection(other: ComputableOpenRange<NumberType>): ComputableOpenRange<NumberType>


    /**
     * Only used internally: Determines whether `lhs` equals `rhs` within the given `tolerance`
     */
    abstract protected fun isEqual(lhs: NumberType, rhs: NumberType, tolerance: NumberType): Boolean


    /**
     * Only used internally: Determines whether `lhs` is less than or equal to `rhs` within the given `tolerance`
     */
    abstract protected fun isLessThanOrEqual(lhs: NumberType, rhs: NumberType, tolerance: NumberType): Boolean
}


/**
 * A default implementation of [ComputableOpenRange] which uses [Fraction]s
 */
open class FractionOpenRange(startInclusive: Fraction?, endInclusive: Fraction?) : ComputableOpenRange<Fraction>(startInclusive, endInclusive) {

    constructor(onlyValue: Fraction): this(startInclusive = onlyValue, endInclusive = onlyValue)


    override val isPoint: Boolean by lazy { isPoint(tolerance = defaultFractionCalculationTolerance) }

    override fun isPoint(tolerance: Fraction): Boolean {
        return startInclusive != null && endInclusive != null && startInclusive.equals(endInclusive, tolerance = tolerance)
    }


    override fun equals(other: ComputableOpenRange<Fraction>): Boolean
            = equals(other, defaultFractionCalculationTolerance)


    override fun isEqual(lhs: Fraction, rhs: Fraction, tolerance: Fraction): Boolean
            = lhs.equals(rhs, tolerance = tolerance)


    override fun intersects(other: ComputableOpenRange<Fraction>): Boolean
            = intersects(other, tolerance = defaultFractionCalculationTolerance)


    override fun isLessThanOrEqual(lhs: Fraction, rhs: Fraction, tolerance: Fraction): Boolean
            = lhs.isLessThanOrEqualTo(rhs, tolerance = tolerance)


    override fun intersection(other: ComputableOpenRange<Fraction>): FractionOpenRange
            = FractionOpenRange.from(super.intersection(other))


    companion object {
        fun from(other: OpenRange<Fraction>): FractionOpenRange {
            if (other.isEmpty) {
                return empty()
            } else {
                return FractionOpenRange(startInclusive = other.startInclusive, endInclusive = other.endInclusive)
            }
        }


        /**
         * A special type of [FractionOpenRange] that contains no value, cannot intersect anything, is not open, is
         * not a point, etc.
         *
         * This is considered equal to any open range of the same type that is also empty.
         *
         * A union of this and another range results in the other range exactly.
         */
        fun empty(): FractionOpenRange = empty


        /**
         * A special type of [FractionOpenRange] that contains no value, cannot intersect anything, is not open, is
         * not a point, etc.
         *
         * This is considered equal to any open range of the same type that is also empty.
         *
         * A union of this and another range results in the other range exactly.
         */
        object empty: FractionOpenRange(null, null) {
            @Suppress("OVERRIDE_BY_INLINE")
            override inline val isOpen: Boolean get() = false

            @Suppress("OVERRIDE_BY_INLINE")
            override inline val isEmpty: Boolean get() = true

            override fun contains(value: Fraction): Boolean = false

            override fun intersects(other: OpenRange<Fraction>): Boolean = false

            override fun intersects(other: ComputableOpenRange<Fraction>): Boolean = false

            override fun intersects(other: ComputableOpenRange<Fraction>, tolerance: Fraction): Boolean = false

            override fun intersection(other: OpenRange<Fraction>): OpenRange<Fraction> = this

            override fun intersection(other: ComputableOpenRange<Fraction>): FractionOpenRange = this

            @Suppress("OVERRIDE_BY_INLINE")
            override inline val isStartOpen: Boolean get() = false

            @Suppress("OVERRIDE_BY_INLINE")
            override inline val isEndOpen: Boolean get() = false

            override val isInfinite: Boolean get() = false

            override val isPoint: Boolean get() = false

            /** @return `true` iff `other` is an empty open range of any type */
            override fun equals(other: Any?): Boolean = other is OpenRange<*> && other.isEmpty

            /** @return `other.isEmpty` */
            override fun equals(other: ComputableOpenRange<Fraction>): Boolean = other.isEmpty

            override fun hashCode(): Int = 1

            override fun isPoint(tolerance: Fraction): Boolean = false

            override fun toString(): String = "(empty)"

            override fun union(other: OpenRange<Fraction>): OpenRange<Fraction> = other
        }
    }
}

val <NumberType> OpenRange<NumberType>.fractionValue
        : FractionOpenRange
        where NumberType : Number, NumberType : Comparable<NumberType>
    get() =
        if (this is FractionOpenRange) this
        else FractionOpenRange(startInclusive = startInclusive?.fractionValue, endInclusive = endInclusive?.fractionValue)




// MARK: - ClosedRange extensions

typealias IndexRange = ClosedRange<Index>



/** The initial point of this range */
inline val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.location: NumberType get() = this.start

/** The distance between [start][ClosedRange.start] and [endInclusive][ClosedRange.endInclusive] */
inline val IndexRange.size: Int get() = this.endInclusive - this.start
/** Semantic alias for [size] */
inline val IndexRange.length: Int get() = this.size
/** Semantic alias for [size] */
inline val IndexRange.count: Int get() = this.size

/** The lowest value in a closed range */
inline val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.min: NumberType get() = this.start
/** The highest value in a closed range */
inline val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.max: NumberType get() = this.endInclusive

fun<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.containsCompletely(other: ClosedRange<NumberType>): Boolean
        = contains(other.start) && contains(other.endInclusive)


fun<NumberType: Comparable<NumberType>> NumberType.isWithin(range: OpenRange<NumberType>): Boolean
        = range.contains(this)

/** A range which contains nothing, has neither a start nor an end, and is not open. */
private class _EmptyOpenRange<NumberType: Comparable<NumberType>> : OpenRange<NumberType>(null, null) { // TODO: Test to make sure this can't break
    @Suppress("OVERRIDE_BY_INLINE")
    override inline val isOpen: Boolean get() = false

    @Suppress("OVERRIDE_BY_INLINE")
    override inline val isEmpty: Boolean get() = true

    override fun contains(value: NumberType): Boolean = false

    override fun intersects(other: OpenRange<NumberType>): Boolean = false

    override fun intersection(other: OpenRange<NumberType>): OpenRange<NumberType> = this

    @Suppress("OVERRIDE_BY_INLINE")
    override inline val isStartOpen: Boolean get() = false

    @Suppress("OVERRIDE_BY_INLINE")
    override inline val isEndOpen: Boolean get() = false

    override val isInfinite: Boolean get() = false

    override val isPoint: Boolean get() = false

    /** @return `true` iff `other` is an empty open range of any type */
    override fun equals(other: Any?): Boolean = other is OpenRange<*> && other.isEmpty

    override fun hashCode(): Int = 0

    override fun toString(): String = "(empty)"

    override fun union(other: OpenRange<NumberType>): OpenRange<NumberType> = other
}



// MARK: - ClosedRange extensions

fun <NumberType: Comparable<NumberType>> ClosedRange(onlyValue: NumberType): ClosedRange<NumberType>
        = onlyValue..onlyValue


fun <NumberType: Comparable<NumberType>> ClosedRange(start: NumberType, endInclusive: NumberType): ClosedRange<NumberType>
        = if (start > endInclusive) endInclusive..start
        else start..endInclusive


fun ClosedRange(location: Index, length: Int): IndexRange
        = if (length < 0) (location + length)..location
        else location..(location + length)


val <NumberType: Comparable<NumberType>> ClosedRange<NumberType>.lowerBound: NumberType
    get() = min(this.start, this.endInclusive)

val <NumberType: Comparable<NumberType>> ClosedRange<NumberType>.upperBound: NumberType
    get() = max(this.start, this.endInclusive)


operator fun <NumberType: Comparable<NumberType>> ClosedRange<NumberType>.compareTo(other: ClosedRange<NumberType>): Int
        = if (this.start == other.start) {
            if (this.endInclusive == other.endInclusive) {
                0
            } else {
                if (this.endInclusive < other.endInclusive) -1 else 1
            }
        } else {
            if (this.start < other.start) -1 else 1
        }


val ClosedRange<Integer>.size get() = this.max - this.min

val ClosedRange<Fraction>.size get() = this.max - this.min

val <NumberType> ClosedRange<NumberType>.fractionValue
        : ClosedRange<Fraction>
        where NumberType : Number, NumberType : Comparable<NumberType>
    get() = ClosedRange(start = start.fractionValue, endInclusive = endInclusive.fractionValue)



// This must be done because ClosedRange has a compareTo method but isn't Comparable. Because you can't do extensions conformity. |I
class SortClosedRanges<NumberType>
    : kotlin.Comparator<ClosedRange<NumberType>>
    where NumberType: Comparable<NumberType> {
    override fun compare(lhs: ClosedRange<NumberType>, rhs: ClosedRange<NumberType>): Int
        = lhs.compareTo(rhs)
}



val <T> ClosedRange<T>.int32Value: IntRange
    where T: Number, T: Comparable<T>
    get() = if (this is IntRange) this else IntRange(start = start.int32Value, endInclusive = endInclusive.int32Value)

val <T> ClosedRange<T>.integerValue: LongRange
    where T: Number, T: Comparable<T>
    get() = if (this is LongRange) this else LongRange(start = start.integerValue, endInclusive = endInclusive.integerValue)
