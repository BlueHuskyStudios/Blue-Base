package org.bh.tools.base.collections

import org.bh.tools.base.math.max
import org.bh.tools.base.math.min
import org.bh.tools.base.struct.ClosedRange
import org.bh.tools.base.struct.IndexRange

/* Array Extensions, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For when the standard arrays aren't good enough.
 * Created by Kyli Rouge on 2016-10-24.
 */


/*fun <ElementType> Array<ElementType>.removing(at: IndexSet): Array<ElementType> {
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

val Array<*>.count: Int get() = this.size
val Array<*>.length: Int get() = this.size

val List<Any>.count: Int get() = this.size
val List<Any>.length: Int get() = this.size

fun <ElementType> Array<ElementType>.inserting(elements: ElementType, index: Index): Array<ElementType> {
    val left = this.sliceArray(IntRange(start = 0, endInclusive = index-1))
    val right = this.sliceArray(kotlin.ranges.IntRange(start = index-1, endInclusive = length-1))

    return left + elements + right
}

fun <ElementType> Array<ElementType>.removing(index: Index): Array<ElementType> {
    return removing(ClosedRange(onlyValue = index))
}

fun <ElementType> Array<ElementType>.removing(range: IndexRange): Array<ElementType> {
    val left = this.sliceArray(IntRange(start = 0, endInclusive = max(0, range.start-1)))
    val right = this.sliceArray(IntRange(start = min(length-1, range.endInclusive-1), endInclusive = length-1))

    return left + right
}

fun <ElementType> Array<ElementType>.removing(indices: IndexSet): Array<ElementType> {
    var ret = clone()
    indices.ranges.forEach { ret = ret.removing(it) }
    return ret
}

fun <ElementType> Array<ElementType>.removing(indices: IntArray): Array<ElementType> {
    return removing(indices.indexSetValue)
}