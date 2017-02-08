@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.*
import org.bh.tools.base.math.Comparator
import org.bh.tools.base.math.ComparisonResult
import org.bh.tools.base.math.geometry.IntegerPath.Companion.pathFromGenericSegments
import java.util.*
import java.util.Queue

/**
 * A path comprised of a set of points
 *
 * @author Kyli
 * @since 2016-12-17
 */
interface Path<out NumberType: Number, out PointType: Point<NumberType>, out SegmentType: LineSegment<NumberType, PointType>> {
    /**
     * The segments in the path
     */
    val segments: List<SegmentType>

    /**
     * The points in the path
     */
    val points: List<PointType>

    /**
     * Indicates whether every point in this path is connected to another
     */
    val isClosed: Boolean
}
typealias AnyPath = Path<*, *, *>



/**
 * A function used to compare each and every segment against each and every other segment
 */
typealias EachSegmentComparator<SegmentType> = (currentSegment: SegmentType, otherSegment: SegmentType, relationship: OtherSegmentRelationshipToCurrent) -> Boolean



/** Describes the relationship between two segments of a path, from the perspective of one */
enum class OtherSegmentRelationshipToCurrent {
    /**
     * The other segment is the left neighbor of this one.
     * This means its end vertex should touch current's start vertex.
     */
    otherIsLeftNeighborOfCurrent,

    /**
     * The other segment is actually the current segment. They are the same object in memory.
     */
    otherIsCurrent,

    /**
     * The other segment is the right neighbor of this one.
     * This means its start vertex should touch current's end vertex.
     */
    otherIsRightNeighborOfCurrent,

    /**
     * The other segment is neither a neighbor of nor the same as the current one.
     */
    otherDoesNotTouchCurrent;

    constructor(currentSegmentIndex: Index, otherSegmentIndex: Index) {
        return when (currentSegmentIndex - otherSegmentIndex) {
            -1 -> otherIsLeftNeighborOfCurrent
            0 -> otherIsCurrent
            1 -> otherIsRightNeighborOfCurrent
            else -> otherDoesNotTouchCurrent
        }
    }
}



interface ComputablePath
    <NumberType, PointType, out SegmentType>
    : Path<NumberType, PointType, SegmentType>
    where NumberType: Number, PointType: ComputablePoint<NumberType>, SegmentType: ComputableLineSegment<NumberType, PointType> {


    /**
     * Indicates whether this path touches or crosses over itself at any point
     */
    val intersectsSelf: Boolean

    /**
     * The first point in the path
     */
    val startPoint: PointType?

    /**
     * The last point in the path
     */
    val endPoint: PointType?


    /**
     * Appends the given point to the end of the path
     */
    operator fun plus(rhs: PointType): ComputablePath<NumberType, PointType, SegmentType>


    fun anyTwoSegments(comparator: EachSegmentComparator<ComputableSegment<NumberType, PointType>>): Boolean {
        if (segments.length < 2) {
            return false
        }

        for (currentSegmentIndex in (0 until segments.length)) {
            val currentSegment = segments[currentSegmentIndex]
            for (otherSegmentIndex in (0 until segments.length)) {
                val otherSegment = segments[otherSegmentIndex]
                if (comparator(currentSegment, otherSegment, OtherSegmentRelationshipToCurrent(currentSegmentIndex, otherSegmentIndex))) {
                    return true
                }
            }
        }

        return false
    }



    companion object {
        /**
         * Determines if the two given segments intersect. Vertices touching doesn't count.
         */
        fun <NumberType, PointType, SegmentType>
            doSegmentsIntersect(left: SegmentType, right: SegmentType)
            : Boolean
            where NumberType : Number, PointType : ComputablePoint<NumberType>, SegmentType : ComputableLineSegment<NumberType, PointType>
            = when (left.describeIntersection(right)) {
                is IntersectionDescription.none,
                is IntersectionDescription.leftVertexTouchesRightVertex<*>
                    -> false

                is IntersectionDescription.completeOverlap,
                is IntersectionDescription.leftVertexTouchesRightEdge<*>,
                is IntersectionDescription.rightVertexTouchesLeftEdge<*>,
                is IntersectionDescription.edgesCross<*>
                    -> true
            }
    }
}
typealias AnyComputablePath = ComputablePath<*, *, *>



open class IntegerPath(override val segments: List<IntegerLineSegment> = listOf())
    : ComputablePath<Integer, IntegerPoint, IntegerLineSegment> {

    companion object {
        fun segmentsFromGenericPoints(points: List<ComputablePoint<*>>, isClosed: Boolean): List<IntegerLineSegment> {

            if (points.size < 2) {
                return listOf()
            }

            val allButFirstTwoPoints = points.subList(fromIndex = 2, toIndex = points.size) // toIndex is exclusive
            val segments = allButFirstTwoPoints.reduceTo(mutableListOf(IntegerLineSegment(start = points[0].integerValue, end = points[1].integerValue))) {
                previousSegments: MutableList<IntegerLineSegment>,
                currentPoint: ComputablePoint<*> ->

                previousSegments.add(IntegerLineSegment(start = previousSegments.last.end, end = currentPoint.integerValue))
                /*return*/ previousSegments
            }


            if (isClosed) {
                segments += IntegerLineSegment(start = points.last.integerValue, end = points.first.integerValue)
            }

            return segments
        }


        fun pathFromGenericSegments(segments: List<ComputableLineSegment<*, *>>): IntegerPath {
            return IntegerPath(segments = segments.reduceTo(mutableListOf()) { translatedSegments, currentSegment ->
                val integerSegment = currentSegment.integerValue
                translatedSegments.add(integerSegment)
                /*return*/ translatedSegments
            })
        }


        fun pathFromGenericPoints(points: List<ComputablePoint<*>>, isClosed: Boolean): IntegerPath {
            return pathFromGenericSegments(segmentsFromGenericPoints(points, isClosed))
        }
    }

    constructor(points: List<IntegerPoint>, isClosed: Boolean): this(segments = segmentsFromGenericPoints(points, isClosed = isClosed))


    fun getAllPoints(): List<IntegerPoint> {
        return segments.reduceTo(mutableListOf(segments.first.start)) {
            previousPoints: MutableList<IntegerPoint>,
            currentSegment: IntegerLineSegment ->

            previousPoints.add(currentSegment.end)
            /*return*/ previousPoints
        }
    }


    override val points: List<IntegerPoint> by lazy { getAllPoints() }

    override val startPoint get() = segments.firstOrNull?.start

    override val endPoint get() = segments.lastOrNull?.end


    fun findIsClosed(): Boolean {
        return null != segments.firstOrNullComparingPairs { (previous, current) ->
            /*return*/ previous.end != current.start
        }
    }


    override val isClosed: Boolean by lazy { findIsClosed() }


    override val intersectsSelf: Boolean by lazy {
        /*return*/ anyTwoSegments { currentSegment, otherSegment, relationship ->

        }
    }


    override operator fun plus(rhs: IntegerPoint): IntegerPath {
        return IntegerPath(segments = segments + IntegerLineSegment(start = segments.last.end, end = rhs))
    }
}
typealias Int64Path = IntegerPath
typealias IntPath = IntegerPath


val AnyComputablePath.integerValue: IntegerPath get() = this as? IntegerPath ?: pathFromGenericSegments(segments)



open class FractionPath(override val segments: List<FractionLineSegment> = listOf())
    : ComputablePath<Fraction, FractionPoint, FractionLineSegment> {

    companion object {
        fun segmentsFromPoints(points: List<FractionPoint>, isClosed: Boolean): List<FractionLineSegment> {

            if (points.size < 2) {
                return listOf()
            }

            val allButFirstPoint = points.subList(1, toIndex = points.size) // toIndex is exclusive
            val segments = allButFirstPoint.reduceTo(mutableListOf(FractionLineSegment(start = points[0], end = points[1]))) {
                previousSegments: MutableList<FractionLineSegment>,
                currentPoint: FractionPoint ->

                previousSegments.add(FractionLineSegment(start = previousSegments.last.end, end = currentPoint))
                /*return*/ previousSegments
            }

            if (isClosed) {
                segments += FractionLineSegment(start = points.last, end = points.first)
            }

            return segments
        }
    }


    /**
     * Creates a new path out of lines connecting the given points. Each point will connect to the one immediately
     * before and after it. If `isClosed` is `true`, the final point will also connect to the first.
     *
     * @param points The points in the new path
     * @param isClosed if `true`, the last point will connect with the first. If `false`, it will not
     */
    constructor(points: List<FractionPoint>, isClosed: Boolean): this(segments = segmentsFromPoints(points, isClosed = isClosed))


    fun getAllPoints(): List<FractionPoint> {
        return segments.reduceTo(mutableListOf(segments.first.start)) {
            previousPoints: MutableList<FractionPoint>,
            currentSegment: FractionLineSegment ->

            previousPoints.add(currentSegment.end)
            /*return*/ previousPoints
        }
    }


    override val points: List<FractionPoint> by lazy { getAllPoints() }

    override val startPoint get() = segments.firstOrNull?.start

    override val endPoint get() = segments.lastOrNull?.end


    fun findIsClosed(): Boolean {
        return segments.firstOrNullComparingPairs { (previous, current) ->
            /*return*/ previous.end != current.start
        } != null
    }


    override val isClosed: Boolean by lazy { findIsClosed() }


    override val intersectsSelf: Boolean get() = null != this.points.firstOrNullComparingTriads { (left, current, right) ->
        return@firstOrNullComparingTriads when (FractionLineSegment(left, current).describeIntersection(FractionLineSegment(current, right))) {
            is IntersectionDescription.none -> false
            else -> true
        }
    }


    override operator fun plus(rhs: FractionPoint): FractionPath {
        return FractionPath(segments = segments + FractionLineSegment(start = segments.last.end, end = rhs))
    }
}
typealias Float64Path = FractionPath
typealias FloatPath = FractionPath



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
//private val Path<Integer>._bendleyOttmann: Boolean get() {
//
//    // 1. Initialize a priority queue `Q` of potential future events, each associated with a point in the plane and
//    //     prioritized by the x-coordinate of the point. Initially, `Q` contains an event for each of the endpoints of
//    //     the input segments.
//    // TODO: Is this sorted ascending or descending?
//    val Q = this.sortedQueueValue { lhs, rhs -> ComparisonResult.from(rhs.x - lhs.x) }
//
//    // 2. Initialize a binary search tree `T` of the line segments that cross the sweep line `L`, ordered by the
//    //     y-coordinates of the crossing points. Initially, `T` is empty.
//    val T = TreeSet<LineSegment<Integer>>({ lhs, rhs -> (rhs.y - lhs.y).int32Value })
//
//    var previous: Point<Integer>? = null
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
        this.sortedWith(kotlin.Comparator<ContentType> { lhs, rhs ->
            (
                    if (lhs == null) ComparisonResult.right
                    else if (rhs == null) ComparisonResult.left
                    else sorter(lhs, rhs)
                    ).nativeValue
        })
