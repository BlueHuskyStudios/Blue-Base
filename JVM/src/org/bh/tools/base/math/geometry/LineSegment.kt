@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.Float64
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.math.defaultFractionCalculationTolerance
import org.bh.tools.base.math.defaultIntegerCalculationTolerance
import org.bh.tools.base.math.equals
import org.bh.tools.base.math.floatValue
import java.awt.geom.AffineTransform
import java.lang.Math.*


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
open class LineSegment<NumberType : Number, out PointType : Point<NumberType>>(val start: PointType, val end: PointType) : Cloneable {

    inline val x1 get() = start.x
    inline val y1 get() = start.y
    inline val x2 get() = end.x
    inline val y2 get() = end.y

    override fun toString(): String {
        return "{start: $start, end: $end}"
    }

    val stringValue get() = toString()

    override public fun clone(): LineSegment<NumberType, Point<NumberType>> {
        return LineSegment(start.clone(), end.clone())
    }
}



typealias AnyLineSegment = LineSegment<*, *>



abstract class ComputableLineSegment<NumberType : Number>(start: ComputablePoint<NumberType>, end: ComputablePoint<NumberType>) : LineSegment<NumberType, ComputablePoint<NumberType>>(start, end) {

    abstract val bounds: ComputableRect<NumberType, ComputablePoint<NumberType>, ComputableSize<NumberType>>

    /**
     * Indicates whether the given point lies on this line segment
     *
     * @param point     The point to test
     * @param tolerance _optional_ - How far the point can be before it's not considered on the line segment
     *
     * @return `true` iff the given point lies on this line segment
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun contains(point: ComputablePoint<NumberType>): Boolean


    /**
     * Indicates whether the given point lies on this line segment
     *
     * @param point     The point to test
     * @param tolerance _optional_ - How far the point can be before it's not considered on the line segment
     *
     * @return `true` iff the given point lies on this line
     */
    abstract fun contains(point: ComputablePoint<NumberType>, tolerance: NumberType): Boolean


    /**
     * Indicates whether this line segment equals the other. That is, each point on this segment directly corresponds
     * to each point on the other.
     *
     * @param other     The other line segment to test
     * @param tolerance _optional_ - How far the other segment can be before it's not considered equal to this one
     *
     * @return `true` iff the given line segment is equal to this one
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun equals(other: ComputableLineSegment<NumberType>): Boolean


    /**
     * Indicates whether this line segment equals the other. That is, each point on this segment directly corresponds
     * to each point on the other.
     *
     * @param other     The other line segment to test
     * @param tolerance _optional_ - How far the other segment can be before it's not considered equal to this one
     *
     * @return `true` iff the given line segment is equal to this one
     */
    abstract fun equals(other: ComputableLineSegment<NumberType>, tolerance: NumberType): Boolean


    protected abstract fun orientation(p: ComputablePoint<NumberType>, q: ComputablePoint<NumberType>, r: ComputablePoint<NumberType>): ThreePointOrientation


    protected enum class ThreePointOrientation {
        collinear, clockwise, counterclockwise
    }


    /**
     * Calls [rawIntersection(other, tolerance)] with default tolerance
     */
    abstract fun rawIntersection(other: ComputableLineSegment<NumberType>): ComputablePoint<NumberType>?


    /**
     * Finds where this line segment intersects the other one. Returns `null` if there is no such intersection.
     */
    abstract fun rawIntersection(other: ComputableLineSegment<NumberType>, tolerance: NumberType): ComputablePoint<NumberType>?


    /**
     * Calls [describeIntersection(other: ComputableLineSegment<NumberType>, tolerance: NumberType)] with default tolerance
     */
    abstract fun describeIntersection(other: ComputableLineSegment<NumberType>): IntersectionDescription


    /**
     * Discovers whether and how this line segments intersects another.
     * This requires [contains] and [rawIntersection] to be implemented properly.
     *
     * @param
     */
    fun describeIntersection(other: ComputableLineSegment<NumberType>, tolerance: NumberType): IntersectionDescription {

        // Easy win: If they're equal, they completely overlap and thus always intersect

        if (this.equals(other, tolerance = tolerance)) {
            return IntersectionDescription.completeOverlap(isStartAndEndFlipped = !this.start.equals(other.start, tolerance = tolerance))
        }

        val rawIntersection = this.rawIntersection(other, tolerance = tolerance)
                ?: return IntersectionDescription.none

//        // Check for a partial overlap
//
//        if (this.contains(other.start, tolerance = tolerance)) {
//            if (this.contains(other.end, tolerance = tolerance)) {
//                return IntersectionDescription.partialOverlap
//            }
//        }

        // Check if the vertices intersect

        if (this.start.equals(other.start, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightVertex(verticesLocation = this.start,
                    isLeftStartVertex = true, isRightStartVertex = true)
        }
        if (this.start.equals(other.end, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightVertex(verticesLocation = this.start,
                    isLeftStartVertex = true, isRightStartVertex = false)
        }
        if (this.end.equals(other.start, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightVertex(verticesLocation = this.end,
                    isLeftStartVertex = false, isRightStartVertex = true)
        }
        if (this.end.equals(other.end, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightVertex(verticesLocation = this.end,
                    isLeftStartVertex = false, isRightStartVertex = false)
        }

        // Check if a vertex touches an edge

        if (other.contains(this.start, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightEdge(this.start, isLeftStartVertex = true)
        }
        if (other.contains(this.end, tolerance = tolerance)) {
            return IntersectionDescription.leftVertexTouchesRightEdge(this.end, isLeftStartVertex = false)
        }
        if (this.contains(other.start, tolerance = tolerance)) {
            return IntersectionDescription.rightVertexTouchesLeftEdge(other.start, isRightStartVertex = true)
        }
        if (this.contains(other.end, tolerance = tolerance)) {
            return IntersectionDescription.rightVertexTouchesLeftEdge(other.end, isRightStartVertex = false)
        }

        // If we've gotten this far, it's your basic intersection

        return IntersectionDescription.edgesCross(crossingLocation = rawIntersection)
    }


    /**
     * Calls [intersects(other, tolerance)] with default tolerance
     *
     * @param other     The other line to inspect
     * @param tolerance _optional_ - The distance this line can be from the other before they're not considered intersecting
     *
     * @param `true` iff the two lines intersect
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun intersects(other: ComputableLineSegment<NumberType>): Boolean


    /**
     * Indicates whether this line intersects the other one, with the given tolerance
     *
     * @param other     The other line to inspect
     * @param tolerance _optional_ - The distance this line can be from the other before they're not considered intersecting
     *
     * @param `true` iff the two lines intersect with the given tolerance
     */
    fun intersects(other: ComputableLineSegment<NumberType>, tolerance: NumberType): Boolean {
        return rawIntersection(other, tolerance = tolerance) != null
    }
}


/**
 * Describes how and where two line segments intersect, if at all
 */
@Suppress("UNUSED_PARAMETER")
sealed class IntersectionDescription {
    /**
     * The line segments do not intersect
     */
    object none : IntersectionDescription()

    /**
     * One of the left line segment's vertices touches one of the right one's
     *
     * @param verticesLocation The location of the touching vertices
     * @param isLeftStartVertex `true` iff the left line segment's vertex is its starting vertex
     * @param isRightStartVertex `true` iff the right line segment's vertex is its starting vertex
     */
    class leftVertexTouchesRightVertex<out NumberType : Number>(val verticesLocation: Point<NumberType>, isLeftStartVertex: Boolean, isRightStartVertex: Boolean) : IntersectionDescription()

    /**
     * One of the left line segment's vertices touches the right one's edge
     *
     * @param leftVertexLocation The location of the touching vertex
     * @param isLeftStartVertex `true` iff the left line segment's touching vertex is its starting vertex
     */
    class leftVertexTouchesRightEdge<out NumberType : Number>(val leftVertexLocation: Point<NumberType>, isLeftStartVertex: Boolean) : IntersectionDescription()

    /**
     * One of the right line segment's vertices touches the left one's edge
     *
     * @param rightVertexLocation The location of the touching vertex
     * @param isRightStartVertex `true` iff the right line segment's touching vertex is its starting vertex
     */
    class rightVertexTouchesLeftEdge<out NumberType : Number>(val rightVertexLocation: Point<NumberType>, isRightStartVertex: Boolean) : IntersectionDescription()

    /**
     * The left line segment and the right one cross, but their vertices don't touch
     *
     * @param crossingLocation The location of the crossing point
     */
    class edgesCross<out NumberType : Number>(val crossingLocation: Point<NumberType>) : IntersectionDescription()

    /**
     * The line segments completely overlap; both vertices on both line segments touch eachother and all points along
     * both segments are shared exactly
     *
     * @param isStartAndEndFlipped `true` iff the starts touch ends and ends touch starts; `false` if the starts touch
     *                             starts and ends touch ends.
     */
    class completeOverlap(val isStartAndEndFlipped: Boolean) : IntersectionDescription()
}


class Int64LineSegment(start: ComputablePoint<Int64>, end: ComputablePoint<Int64>) : ComputableLineSegment<Int64>(start, end) {

    override fun contains(point: ComputablePoint<Int64>): Boolean = contains(point, tolerance = 0)

    override fun contains(point: ComputablePoint<Int64>, tolerance: Int64): Boolean
            = (point.x <= max(start.x, end.x) && point.x >= min(start.x, end.x)
            && point.y <= max(start.y, end.y) && point.y >= min(start.y, end.y))
    /* TODO: Evaluate whether the following approach would be better:
    {
        val m = (end.y - start.y) / (end.x - start.x)
        val b = start.y - (m * start.x)
        return Math.abs(point.y - ((m * point.x) + b)) < tolerance // derived from y=mx+b
    }*/

    /**
     * The smallest rectangle that contains all points in this line
     */
    override val bounds: Int64Rect = Int64Rect(start, Int64Size(x2 - x1, y2 - y1))


    /**
     * Not yet supported. That said, if the given `transform` is `null` or the identity, [floatValue] is returned.
     */
    @Deprecated("Not yet supported")
    fun transformed(transform: AffineTransform?): FloatLineSegment {
        if (transform == null || transform.isIdentity) {
            return floatValue
        }

        TODO("Transformations are not yet supported")

//        var x1 = this.x1
//        var x2 = this.x2
//        var y1 = this.y1
//        var y2 = this.y2
//
//        x1 *= transform.scaleX
//        x2 *= transform.scaleX
//        y1 *= transform.scaleY
//        y2 *= transform.scaleY
//
//        x1 += transform.translateX
//        x2 += transform.translateX
//        y1 += transform.translateY
//        y2 += transform.translateY
    }


    // BEGIN: Code translated from http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

    override fun orientation(p: ComputablePoint<Int64>, q: ComputablePoint<Int64>, r: ComputablePoint<Int64>): ThreePointOrientation {
        val ret = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            ret == defaultIntegerCalculationTolerance -> ThreePointOrientation.collinear
            ret > 0 -> ThreePointOrientation.clockwise
            else -> ThreePointOrientation.counterclockwise
        }
    }

    // END: Translated code


    override fun equals(other: ComputableLineSegment<Int64>): Boolean
            = equals(other, tolerance = defaultIntegerCalculationTolerance)


    override fun equals(other: ComputableLineSegment<Int64>, tolerance: Int64): Boolean
            = this.start.equals(other.start, tolerance = tolerance)
            && this.end.equals(other.end, tolerance = tolerance)


    override fun intersects(other: ComputableLineSegment<Int64>): Boolean
            = intersects(other, tolerance = defaultIntegerCalculationTolerance)


    override fun describeIntersection(other: ComputableLineSegment<Int64>): IntersectionDescription
            = describeIntersection(other, tolerance = defaultIntegerCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Int64>): ComputablePoint<Int64>?
            = rawIntersection(other, tolerance = defaultIntegerCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Int64>, tolerance: Int64): ComputablePoint<Int64>?
            = findLineIntersection(this, other)

    companion object {
        fun findLineIntersection(line1: ComputableLineSegment<Int64>,
                                 line2: ComputableLineSegment<Int64>,
                                 tolerance: Int64 = defaultIntegerCalculationTolerance): ComputablePoint<Int64>? {

            if (line1.equals(line2, tolerance = tolerance)) {
                return line1.start
            }

            val line1XDelta = line1.start.x - line1.end.x
            val line1YDelta = line1.end.y - line1.start.y
            val line1Delta = (line1XDelta * line1.start.y) + (line1YDelta * line1.start.x)

            val line2XDelta = line2.start.x - line2.end.x
            val line2YDelta = line2.end.y - line2.start.y
            val line2Delta = (line2XDelta * line2.start.y) + (line2YDelta * line2.start.x)

            val angularDifference = abs((line2XDelta * line1YDelta) - (line1XDelta * line2YDelta))
            val areParallel = angularDifference <= tolerance

            return if (areParallel) {
                null
            } else {
                Int64Point(((line2XDelta * line1Delta) - (line1XDelta * line2Delta)) / angularDifference,
                           ((line1YDelta * line2Delta) - (line2YDelta * line1Delta)) / angularDifference)
            }
        }
    }
}
typealias BHIntLineSegment = Int64LineSegment
typealias IntegerLineSegment = BHIntLineSegment


class Float64LineSegment(start: ComputablePoint<Float64>, end: ComputablePoint<Float64>) : ComputableLineSegment<Float64>(start, end) {


    override fun contains(point: ComputablePoint<Float64>): Boolean {
        return contains(point, tolerance = defaultFractionCalculationTolerance)
    }

    override fun contains(point: ComputablePoint<BHFloat>, tolerance: BHFloat): Boolean
            = (point.x <= max(start.x, end.x) && point.x >= min(start.x, end.x)
            && point.y <= max(start.y, end.y) && point.y >= min(start.y, end.y))
    /* TODO: Evaluate whether the following approach would be better:
    {
        val m = (end.y - start.y) / (end.x - start.x)
        val b = start.y - (m * start.x)
        return Math.abs(point.y - ((m * point.x) + b)) < tolerance // derived from y=mx+b
    }*/

    /**
     * The smallest rectangle that contains all points in this line
     */
    override val bounds: Float64Rect = Float64Rect(Float64Point(x1, y1), Float64Size(x2 - x1, y2 - y1))

    /**
     * Not yet supported. That said, if the given `transform` is `null` or the identity, [floatValue] is returned.
     */
    fun transformed(transform: AffineTransform?): Float64LineSegment {
        if (transform == null || transform.isIdentity) {
            return floatValue
        }

        TODO("Transformations are not yet supported")

//        var x1 = this.x1
//        var x2 = this.x2
//        var y1 = this.y1
//        var y2 = this.y2
//
//        x1 *= transform.scaleX
//        x2 *= transform.scaleX
//        y1 *= transform.scaleY
//        y2 *= transform.scaleY
//
//        x1 += transform.translateX
//        x2 += transform.translateX
//        y1 += transform.translateY
//        y2 += transform.translateY
    }


    // BEGIN: Code translated from http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

    override fun orientation(p: ComputablePoint<Float64>, q: ComputablePoint<Float64>, r: ComputablePoint<Float64>): ThreePointOrientation {
        val ret = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            abs(ret) <= defaultFractionCalculationTolerance -> ThreePointOrientation.collinear
            ret > 0 -> ThreePointOrientation.clockwise
            else -> ThreePointOrientation.counterclockwise
        }
    }

    // END: Translated code


    override fun equals(other: ComputableLineSegment<Float64>): Boolean = equals(other, tolerance = defaultFractionCalculationTolerance)


    override fun equals(other: ComputableLineSegment<Float64>, tolerance: Float64): Boolean
            = this.start.equals(other.start, tolerance = tolerance)
            && this.end.equals(other.end, tolerance = tolerance)


    override fun intersects(other: ComputableLineSegment<Float64>): Boolean
            = intersects(other, tolerance = defaultFractionCalculationTolerance)


    override fun describeIntersection(other: ComputableLineSegment<Float64>): IntersectionDescription
            = describeIntersection(other, tolerance = defaultFractionCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Float64>): ComputablePoint<Float64>?
            = rawIntersection(other, tolerance = defaultFractionCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Float64>, tolerance: Float64): ComputablePoint<Float64>?
            = findLineIntersection(this, other)

    companion object {
        fun findLineIntersection(line1: ComputableLineSegment<Float64>,
                                 line2: ComputableLineSegment<Float64>,
                                 tolerance: Float64 = defaultFractionCalculationTolerance): ComputablePoint<Float64>? {

            if (line1.equals(line2, tolerance = tolerance)) {
                return line1.start
            }

            val line1XDelta = line1.start.x - line1.end.x
            val line1YDelta = line1.end.y - line1.start.y
            val line1Delta = (line1XDelta * line1.start.y) + (line1YDelta * line1.start.x)

            val line2XDelta = line2.start.x - line2.end.x
            val line2YDelta = line2.end.y - line2.start.y
            val line2Delta = (line2XDelta * line2.start.y) + (line2YDelta * line2.start.x)

            val angularDifference = abs((line2XDelta * line1YDelta) - (line1XDelta * line2YDelta))
            val areParallel = angularDifference <= tolerance

            return if (areParallel) {
                null
            } else {
                Float64Point(((line2XDelta * line1Delta) - (line1XDelta * line2Delta)) / angularDifference,
                             ((line1YDelta * line2Delta) - (line2YDelta * line1Delta)) / angularDifference)
            }
        }
    }
}
typealias BHFloatLineSegment = Float64LineSegment
typealias FloatLineSegment = BHFloatLineSegment

val AnyLineSegment.floatValue: FloatLineSegment get() = FloatLineSegment(start.floatValue, end.floatValue)
