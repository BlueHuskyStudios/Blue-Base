@file:Suppress("unused")

package org.bh.tools.base.collections.extensions

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.*
import org.bh.tools.base.math.*
import org.bh.tools.base.struct.*

/*
 * MutableArray Extensions, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * This replaces `BHArray` (previously `ArrayPP`), which was used in BHToolbox and Blue Base from 2011 to 2017
 *
 * For when the standard arrays aren't good enough.
 * Created by Kyli Rouge on 2016-10-24.
 */


/*fun <ElementType> MutableArray<ElementType>.removing(at: IndexSet): MutableArray<ElementType> {
    val oldArray = this
    val oldArrayLength = oldArray.size
    val newArrayLength = oldArray.size - at.size
    val newArray = arrayOfNulls(newArrayLength)
    var newArrayIndex = 0
    for (oldArrayIndex in 0 until oldArrayLength) {
        if (at.intersects(oldArrayIndex)) {
            continue
        } else {
            newArray[newArrayIndex] = oldArray[oldArrayIndex]
            newArrayIndex += 1
        }
    }
    return newArray
}*/

inline val MutableArray<*>.count: Int get() = this.size
inline val MutableArray<*>.length: Int get() = this.size

inline val Collection<*>.count: Int get() = this.size
inline val Collection<*>.length: Int get() = this.size


fun <ElementType> MutableArray<ElementType>.inserting(elements: ElementType, index: Index): MutableArray<ElementType> {
    val left = this.sliceArray(IntRange(start = 0, endInclusive = index - 1))
    val right = this.sliceArray(kotlin.ranges.IntRange(start = index - 1, endInclusive = length - 1))

    return left + elements + right
}


fun <ElementType> MutableArray<ElementType>.removing(index: Index): MutableArray<ElementType> {
    return removing(ClosedRange(onlyValue = index))
}


fun <ElementType> MutableArray<ElementType>.removing(range: IndexRange): MutableArray<ElementType> {
    val left = this.sliceArray(IntRange(start = 0, endInclusive = max(0, range.start - 1)))
    val right = this.sliceArray(IntRange(start = min(length - 1, range.endInclusive - 1), endInclusive = length - 1))

    return left + right
}


fun <ElementType> MutableArray<ElementType>.removing(indices: IndexSet): MutableArray<ElementType> {
    var ret = this.copyOf()
    indices.ranges.forEach { ret = ret.removing(it) }
    return ret
}


fun <ElementType> MutableArray<ElementType>.removing(indices: IntArray): MutableArray<ElementType> {
    return removing(indices.indexSetValue)
}



/**
 * Returns a `MutableArray` where those you decide to keep are kept, and then transformed as you decide to transform
 * them. Like `flatMap` but allows you to keep `null`s
 */
fun <ElementType, OutputType>
        MutableArray<ElementType>.filterMap(predicateTransform: (ElementType) -> Pair<Boolean, () -> OutputType?>)
        : List<OutputType?>
        = this
        .map { predicateTransform(it) }
        .filter(Pair<Boolean, *>::first)
        .map { it.second() }
