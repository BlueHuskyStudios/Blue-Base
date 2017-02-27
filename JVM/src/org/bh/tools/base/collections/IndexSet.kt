@file:Suppress("unused")

package org.bh.tools.base.collections

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.abstraction.Int32Array
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.math.integerValue
import org.bh.tools.base.struct.*

typealias Index = Int32
typealias IndexArray = Int32Array

/**
 * IndexSet, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * An array with extended capabilities. Designed to be subclassed! NOT designed to be mutated.
 *
 * @author Kyli
 * @since 2016-10-24
 */
open class IndexSet : Cloneable {
    /**
     * All distinct ranges in this set
     */
    @Volatile
    protected var _ranges: Array<IndexRange> = arrayOf()

    /**
     * The running number of indexes in this set
     */
    @Volatile
    protected var _count: Int = _ranges.map { it.size }.reduce(Int::plus)

    /**
     * The number of indexes in this set
     */
    open val size: Int get() = _count

    /**
     * All distinct ranges in this index set. None of these overlap or touch.
     */
    val ranges: Array<IndexRange> get() = _ranges

    constructor(vararg ranges: ClosedRange<Index>) {
        ranges.forEach { union(it) }
    }

    constructor(integers: IndexArray) {
        integers.forEach { union(it) }
    }

    constructor(integers: Iterable<Index>) {
        integers.forEach { union(it) }
    }

    constructor(index: Index): this(ClosedRange(index))



    fun intersects(index: Index): Boolean = _ranges.any { it.contains(index) }

    /**
     * Without mutating, unions all ranges in the given set with those in this one. The returned set will be a copy
     * whose ranges do not overlap, and cover all values in this and the given index sets.
     */
    fun union(other: IndexSet): IndexSet {
        var newSet = IndexSet()
        other._ranges.forEach { range -> newSet = newSet.union(range = range) }
        return newSet
    }

    /**
     * Without mutating, unions the given range with those in this one. The returned set will be a copy
     * whose ranges do not overlap, and cover all values in this index set as well as those in the given range.
     */
    fun union(range: ClosedRange<Index>): IndexSet {
        if (_ranges.any { it.containsCompletely(range) }) {
            return this
        }

        // The following is adapted from NSIndexSet:
        // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift

        if (range.length <= 0) {
            return this
        }

        val copy = clone()

        val addEnd = range.max
        val startRangeIndex = copy._indexOfRangeBeforeOrContainingIndex(range.location) ?: 0
        var replacedRangeIndex: Int? = null
        var rangeIndex = startRangeIndex
        while (rangeIndex < _ranges.count) {
            val curRange = _ranges[rangeIndex]
            val curEnd = curRange.max
            if (addEnd < curRange.location) {
                copy._insertRange(range, index = rangeIndex)
                // Done. No need to merge
                return this
            } else if (range.location < curRange.location && addEnd >= curRange.location) {
                if (addEnd > curEnd) {
                    copy._replaceRangeAtIndex(rangeIndex, range)
                } else {
                    copy._replaceRangeAtIndex(rangeIndex, ClosedRange(location = range.location, length =
                    curEnd - range.location))
                }
                replacedRangeIndex = rangeIndex
                // Proceed to merging
                break
            } else if (range.location >= curRange.location && addEnd < curEnd) {
                // Nothing to add
                return this
            } else if (range.location >= curRange.location && range.location <= curEnd && addEnd > curEnd) {
                copy._replaceRangeAtIndex(rangeIndex, ClosedRange(location = curRange.location,
                        length = addEnd - curRange.location))
                replacedRangeIndex = rangeIndex
                // Proceed to merging
                break
            }
            rangeIndex += 1
        }
        if (replacedRangeIndex != null) {
            copy._mergeOverlappingRangesStartingAtIndex(replacedRangeIndex)
        } else {
            copy._insertRange(range, index = _ranges.count)
        }

        return copy
    }

    override fun clone(): IndexSet = IndexSet(*_ranges)

    fun union(index: Index): IndexSet = union(ClosedRange(onlyValue = index))
    fun union(n: Number): IndexSet = union(n.integerValue)


    // Adapted from NSIndexSet:
    // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift
    internal fun _indexOfRangeBeforeOrContainingIndex(idx: Index): Index? {
        val (rIdx, range) = _indexAndRangeAdjacentToOrContainingIndex(idx) ?: Pair(null, null)
        if (rIdx != null && range != null) {
            if (range.location <= idx) {
                return rIdx
            } else if (rIdx > 0) {
                return rIdx - 1
            } else {
                return null
            }
        } else {
            return null
        }
    }

    // Adapted from NSIndexSet:
    // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift
    internal fun _indexAndRangeAdjacentToOrContainingIndex(idx: Index): Pair<Index, ClosedRange<Index>>? {
        val count = _ranges.count
        if (count <= 0) {
            return null
        }

        var min = 0
        var max = count - 1
        while (min < max) {
            val rIdx = (min + max) / 2
            val range = _ranges[rIdx]
            if (range.location > idx) {
                max = rIdx
            } else if (range.max - 1 < idx) {
                min = rIdx + 1
            } else {
                return Pair(rIdx, range)
            }
        }
        return Pair(min, _ranges[min])
    }

    // Adapted from NSIndexSet:
    // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift
    internal fun _insertRange(range: IndexRange, index: Int) {
        _ranges = _ranges.inserting(range, index)
        _count += range.length
    }

    // Adapted from NSIndexSet:
    // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift
    internal fun _replaceRangeAtIndex(index: Int, range: IndexRange?) {
        val oldRange = _ranges[index]
        if (range != null) {
            _ranges[index] = range
            _count += range.length - oldRange.length
        } else {
            _ranges = _ranges.removing(index)
            _count -= oldRange.length
        }
    }

    // Adapted from NSIndexSet:
    // https://github.com/apple/swift-corelibs-foundation/blob/swift-DEVELOPMENT-SNAPSHOT-2016-10-25-a/Foundation/NSIndexSet.swift
    internal fun _mergeOverlappingRangesStartingAtIndex(index: Int) {
        var rangeIndex = index
        while (_ranges.count > 0 && rangeIndex < _ranges.count - 1) {
            val curRange = _ranges[rangeIndex]
            val nextRange = _ranges[rangeIndex + 1]
            val curEnd = curRange.max
            val nextEnd = nextRange.max
            if (curEnd >= nextRange.location) {
                // overlaps
                if (curEnd < nextEnd) {
                    this._replaceRangeAtIndex(rangeIndex, ClosedRange(location = nextEnd - curRange.location, length = curRange.length))
                    rangeIndex += 1
                }
                this._replaceRangeAtIndex(rangeIndex + 1, range = null)
            } else {
                break
            }
        }
    }
}


fun Index.isWithin(set: IndexSet): Boolean {
    return set.intersects(this)
}

val IntArray.indexSetValue: IndexSet get() = IndexSet(this)
val Set<Int>.indexSetValue: IndexSet get() = IndexSet(this)
val Array<Int>.indexSetValue: IndexSet get() = IndexSet(this.toIntArray())
val List<Int>.indexSetValue: IndexSet get() = IndexSet(this)
val Iterable<Int>.indexSetValue: IndexSet get() = IndexSet(this.toList())
