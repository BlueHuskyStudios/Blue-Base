@file:Suppress("unused")

package org.bh.tools.base.struct

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.collections.Index
import org.bh.tools.base.math.*
import kotlin.reflect.isSuperclassOf

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

    /**
     * The first value of this range. `null` indicates that it is open and has no defined start.
     */
    val startInclusive: NumberType?

    /**
     * The last value of this range. `null` indicates that it is open and has no defined end.
     */
    val endInclusive: NumberType?

    init {
        if (startInclusive != null && endInclusive != null && startInclusive > endInclusive) {
            this.startInclusive = endInclusive
            this.endInclusive = startInclusive
        } else {
            this.startInclusive = startInclusive
            this.endInclusive = endInclusive
        }
    }

    constructor(onlyValue: NumberType): this(startInclusive = onlyValue, endInclusive = onlyValue)

    open val isOpen: Boolean get() = startInclusive == null || endInclusive == null

    /**
     * Indicates whether the given value is in this range
     */
    open fun contains(value: NumberType): Boolean {
        if (startInclusive != null && value < startInclusive) {
            return false
        }
        if (endInclusive != null && value > endInclusive) {
            return false
        }
        return true
    }

    fun intersects(other: OpenRange<NumberType>): Boolean
            = (other.startInclusive != null && contains(other.startInclusive))
            || (other.endInclusive != null && contains(other.endInclusive))

    fun union(other: OpenRange<NumberType>): OpenRange<NumberType> {
        if (!intersects(other)) {
            return emptyRange()
        }
        val newStart: NumberType?
            = if (this.startInclusive != null && other.startInclusive != null) min(this.startInclusive, other.startInclusive)
            else null

        val newEnd: NumberType?
            = if (this.endInclusive != null && other.endInclusive != null) max(this.endInclusive, other.endInclusive)
            else null

        return OpenRange(newStart, newEnd)
    }

    fun containsCompletely(other: OpenRange<NumberType>): Boolean = when {
        this.startInclusive == null -> when {
            this.endInclusive == null -> true
            other.endInclusive == null -> false
            else -> other.endInclusive <= this.endInclusive
        }
        this.endInclusive == null -> when {
            other.startInclusive == null -> false
            else -> other.startInclusive >= this.startInclusive
        }
        else -> when {
            other.startInclusive == null || other.endInclusive == null -> false
            else -> this.contains(other.startInclusive) && this.contains(other.endInclusive)
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
}

val<NumberType: Comparable<NumberType>> OpenRange<NumberType>.start: NumberType? get() = startInclusive
val<NumberType: Comparable<NumberType>> OpenRange<NumberType>.end: NumberType? get() = endInclusive



typealias IndexRange = ClosedRange<Index>

val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.location: NumberType get() = this.start

val IndexRange.size: Int get() = this.endInclusive - this.start
val IndexRange.length: Int get() = this.size
val IndexRange.count: Int get() = this.size

val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.min: NumberType get() = this.start
val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.max: NumberType get() = this.endInclusive

fun<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.containsCompletely(other: ClosedRange<NumberType>): Boolean
        = contains(other.start) && contains(other.endInclusive)

fun<NumberType: Comparable<NumberType>> infiniteRange(): OpenRange<NumberType> = OpenRange(null, null)
fun<NumberType: Comparable<NumberType>> emptyRange(): OpenRange<NumberType> = _EmptyOpenRange()

fun<NumberType: Comparable<NumberType>> NumberType.isWithin(range: OpenRange<NumberType>): Boolean
        = range.contains(this)

/**
 * A range which contains nothing, has neither a start nor an end, and is not open.
 */
private class _EmptyOpenRange<NumberType: Comparable<NumberType>> : OpenRange<NumberType>(null, null) {
    override val isOpen: Boolean get() = false
    override fun contains(value: NumberType): Boolean {
        return false
    }
}



// MARK: - ClosedRange extensions



fun<NumberType: Comparable<NumberType>> ClosedRange(onlyValue: NumberType): ClosedRange<NumberType>
        = onlyValue..onlyValue

fun<NumberType: Comparable<NumberType>> ClosedRange(start: NumberType, endInclusive: NumberType): ClosedRange<NumberType>
        = if (start > endInclusive) endInclusive..start else start..endInclusive

fun ClosedRange(location: Index, length: Int): IndexRange
        = if (length < 0) (location + length)..location else location..(location + length)

val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.lowerBound: NumberType
    get() = min(this.start, this.endInclusive)
val<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.upperBound: NumberType
    get() = max(this.start, this.endInclusive)

operator fun<NumberType: Comparable<NumberType>> ClosedRange<NumberType>.compareTo(other: ClosedRange<NumberType>): Int {
    if (this.start == other.start) {
        if (this.endInclusive == other.endInclusive) {
            return 0
        } else {
            return if (this.endInclusive < other.endInclusive) -1 else 1
        }
    } else {
        return if (this.start < other.start) -1 else 1
    }
}

// This must be done because ClosedRange has a compareTo method but isn't Comparable. Because you can't do extensions conformity. |I
class SortClosedRanges<NumberType>
    : kotlin.Comparator<ClosedRange<NumberType>>
    where NumberType: Comparable<NumberType> {
    override fun compare(lhs: ClosedRange<NumberType>, rhs: ClosedRange<NumberType>): Int
        = lhs.compareTo(rhs)
}

//class SortOpenRanges<NumberType>: ComparableComparator<OpenRange<NumberType>>() where NumberType: Comparable<NumberType>



val <T> ClosedRange<T>.int32Value: IntRange
    where T: Number, T: Comparable<T>
    get() = if (this is IntRange) this else IntRange(start = start.int32Value, endInclusive = endInclusive.int32Value)

val <T> ClosedRange<T>.integerValue: LongRange
    where T: Number, T: Comparable<T>
    get() = if (this is LongRange) this else LongRange(start = start.integerValue, endInclusive = endInclusive.integerValue)
