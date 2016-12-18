@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.firstOrNullComparingTriads
import org.bh.tools.base.math.*
import org.bh.tools.base.math.Comparator
import java.util.*

/**
 * @author Kyli
 * @since 017 2016 12 17.
 */

interface Path<out NumberType: Number> {
    val points: List<Point<NumberType>>
    val isClosed: Boolean
}

interface ComputablePath<NumberType: Number> : Path<NumberType> {
    /**
     * Indicates whether this path touches or crosses over itself at any point
     */
    val intersectsSelf: Boolean

    /**
     * Appends the given point to the end of the path
     */
    operator fun plus(rhs: Point<NumberType>): ComputablePath<NumberType>
}

class Int64Path(override val points: List<Point<Int64>> = listOf(), override val isClosed: Boolean = false) : ComputablePath<Int64> {

    override val intersectsSelf: Boolean get() = null != points.firstOrNullComparingTriads { (left, current, right) ->
        Int64LineSegment(left, current).intersects(Int64LineSegment(current, right))
    }

    override operator fun plus(rhs: Point<Int64>): Int64Path {
        return Int64Path(points + rhs)
    }
}
typealias BHIntPath = Int64Path
typealias IntegerPath = BHIntPath

class Float64Path(override val points: List<Point<Float64>> = listOf(), override val isClosed: Boolean = false) : ComputablePath<Float64> {

    override val intersectsSelf: Boolean get() = null != this.points.firstOrNullComparingTriads { (left, current, right) ->
        BHFloatLineSegment(left, current).intersects(BHFloatLineSegment(current, right))
    }

    override operator fun plus(rhs: Point<Float64>): Float64Path {
        return Float64Path(points + rhs)
    }
}
typealias BHFloatPath = Float64Path
typealias FloatPath = BHFloatPath

///**
// * The Bentley–Ottmann algorithm: https://en.wikipedia.org/wiki/Bentley–Ottmann_algorithm
// *
// * 1. Initialize a priority queue `Q` of potential future events, each associated with a point in the plane and
// *     prioritized by the x-coordinate of the point. Initially, `Q` contains an event for each of the endpoints of the
// *     input segments.
// * 2. Initialize a binary search tree `T` of the line segments that cross the sweep line `L`, ordered by the
// *     y-coordinates of the crossing points. Initially, `T` is empty.
// * 3. While `Q` is nonempty, find and remove the event from `Q` associated with a point `p` with minimum x-coordinate.
// *     Determine what type of event this is and process it according to the following case analysis:
// *     a. If `p` is the left endpoint of a line segment `s`, insert `s` into T. Find the segments `r` and `t` that are
// *         immediately below and above `s` in `T` (if they exist) and if their crossing forms a potential future event
// *         in the event queue, remove it. If `s` crosses `r` or `t`, add those crossing points as potential future
// *         events in the event queue.
// *     b. If `p` is the right endpoint of a line segment `s`, remove `s` from `T`. Find the segments `r` and `t` that
// *         were (prior to the removal of `s`) immediately above and below it in `T` (if they exist). If `r` and `t`
// *         cross, add that crossing point as a potential future event in the event queue.
// *     c. If `p` is the crossing point of two segments `s` and `t` (with `s` below `t` to the left of the crossing),
// *         swap the positions of `s` and `t` in `T`. Find the segments `r` and `u` (if they exist) that are
// *         immediately below and above `t` and `s` respectively (after the swap). Remove any crossing points `rs` and
// *         `tu` from the event queue, and, if `r` and `t` cross or `s` and `u` cross, add those crossing points to the
// *         event queue.
// */
//private val Path<BHInt>._bendleyOttmann: Boolean get() {
//
//    // 1. Initialize a priority queue `Q` of potential future events, each associated with a point in the plane and
//    //     prioritized by the x-coordinate of the point. Initially, `Q` contains an event for each of the endpoints of
//    //     the input segments.
//    // TODO: Is this sorted ascending or descending?
//    val Q = this.sortedQueueValue { lhs, rhs -> ComparisonResult.from(rhs.x - lhs.x) }
//
//    // 2. Initialize a binary search tree `T` of the line segments that cross the sweep line `L`, ordered by the
//    //     y-coordinates of the crossing points. Initially, `T` is empty.
//    val T = TreeSet<LineSegment<BHInt>>({ lhs, rhs -> (rhs.y - lhs.y).int32Value })
//
//    var previous: Point<BHInt>? = null
//
//    // 3. While `Q` is nonempty, find and remove the event from `Q` associated with a point `p` with minimum
//    //     x-coordinate. Determine what type of event this is and process it according to the following case analysis:
//    while (Q.isNotEmpty()) {
//        val p = Q.remove() // smallest X
//
//        // a. If `p` is the left endpoint of a line segment `s`, insert `s` into T.
//        val next = Q.peek()
//        if (next != null) {
//            val s = LineSegment(p, next)
//            T.add(s)
//            // Find the segments `r` and `t` that are immediately below and above `s` in `T` (if they exist)
//            val r = T.below(s)
//            val t = T.above(s)
//
//            // If their crossing forms a potential future event in the event queue, remove it.
//            val rtIntersection = r.intersection(t)
//            if (rtIntersection != null) {
//                Q.remove(rtIntersection)
//            }
//            // If `s` crosses `r` or `t`, add those crossing points as potential future events in the event queue.
//            val intersection = s.intersection(r) ?: s.intersection(t)
//
//            if (intersection != null) {
//                Q.add(intersection)
//            }
//        }
//
//        // b. If `p` is the right endpoint of a line segment `s`, remove `s` from `T`.
//        if (previous != null) {
//            val s = LineSegment(previous, p)
//            val r = T.above(s)
//            val t = T.below(s)
//            T.remove(s)
//            // Find the segments `r` and `t` that were (prior to the removal of `s`) immediately above and below it in
//            // `T` (if they exist).
//            if (r != null && t != null) {
//                // If `r` and `t` cross, add that crossing point as a potential future event in the event queue.
//                val rtIntersection = r.intersection(t)
//                if (rtIntersection != null) {
//                    Q.remove(rtIntersection)
//                }
//            }
//        }
//        // c. If `p` is the crossing point of two segments `s` and `t` (with `s` below `t` to the left of the crossing),
//        //     swap the positions of `s` and `t` in `T`. Find the segments `r` and `u` (if they exist) that are
//        //     immediately below and above `t` and `s` respectively (after the swap). Remove any crossing points `rs` and
//        //     `tu` from the event queue, and, if `r` and `t` cross or `s` and `u` cross, add those crossing points to the
//        //     event queue.
//
//        previous = p
//    }
//}

private fun <ContentType> List<ContentType>.sortedQueueValue(sorter: Comparator<ContentType>): Queue<ContentType> {
    val x = PriorityQueue<ContentType>({ lhs, rhs ->
        (
                if (lhs == null) ComparisonResult.right
                else if (rhs == null) ComparisonResult.left
                else sorter(lhs, rhs)
                ).nativeValue
    })
    x.addAll(this)
    return x
}

inline fun <ContentType> List<ContentType>.sorted(crossinline sorter: Comparator<ContentType>): List<ContentType> =
        this.sortedWith(kotlin.comparisons.Comparator<ContentType> { lhs, rhs ->
            (
                    if (lhs == null) ComparisonResult.right
                    else if (rhs == null) ComparisonResult.left
                    else sorter(lhs, rhs)
                    ).nativeValue
        })
