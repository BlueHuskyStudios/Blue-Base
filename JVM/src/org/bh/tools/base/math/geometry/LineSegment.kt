@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.func.*
import org.bh.tools.base.math.*
import java.awt.geom.AffineTransform
import java.lang.Math.abs


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
open class LineSegment<NumberType : Number, out PointType : Point<NumberType>>(
        /** The point at the start of the line segment */
        open val start: PointType,
        /** The point at the end of the line segment */
        open val end: PointType)
    : Cloneable, TupleConvertible<Tuple2<PointType, PointType>> {

    /** The first point's X coordinate */
    inline val x1 get() = start.x
    /** The first point's Y coordinate */
    inline val y1 get() = start.y
    /** The second point's X coordinate */
    inline val x2 get() = end.x
    /** The second point's Y coordinate */
    inline val y2 get() = end.y

    override fun toString(): String {
        return "{start: $start, end: $end}"
    }

    val stringValue get() = toString()

    override public fun clone(): LineSegment<NumberType, Point<NumberType>> {
        return LineSegment(start.clone(), end.clone())
    }

    /**
     * Converts this line segment into a tuple of `(start, end)`
     * @see start
     * @see end
     */
    override val tupleValue by lazy { tuple(start, end) }
}



typealias AnyLineSegment = LineSegment<*, *>



abstract class ComputableLineSegment
    <NumberType, PointType>
    (start: PointType, end: PointType)
    : LineSegment<NumberType, PointType>(start, end)
    where NumberType: Number, PointType: ComputablePoint<NumberType>{

    abstract val bounds: ComputableRect<NumberType, ComputablePoint<NumberType>, ComputableSize<NumberType>>

    val flipped: ComputableLineSegment<NumberType, PointType> by lazy { copy(start = end, end = start) }


    /**
     * Creates a copy of this line segment, optionally changing the values
     */
    @Suppress("UNCHECKED_CAST")
    abstract fun copy(start: PointType = this.start.copy() as PointType, end: PointType = this.end.copy() as PointType): ComputableLineSegment<NumberType, PointType>


    /**
     * Indicates whether the given point lies on this line segment
     *
     * @param point     The point to test
     * @param tolerance _optional_ - How far the point can be before it's not considered on the line segment
     *
     * @return `true` iff the given point lies on this line segment
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun contains(point: Point<NumberType>): Boolean


    /**
     * Indicates whether the given point lies on this line segment
     *
     * @param point     The point to test
     * @param tolerance _optional_ - How far the point can be before it's not considered on the line segment
     *
     * @return `true` iff the given point lies on this line
     */
    abstract fun contains(point: Point<NumberType>, tolerance: NumberType): Boolean


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
    abstract fun equals(other: ComputableLineSegment<NumberType, PointType>): Boolean


    /**
     * Indicates whether this line segment equals the other. That is, each point on this segment directly corresponds
     * to each point on the other.
     *
     * @param other     The other line segment to test
     * @param tolerance _optional_ - How far the other segment can be before it's not considered equal to this one
     *
     * @return `true` iff the given line segment is equal to this one
     */
    abstract fun equals(other: ComputableLineSegment<NumberType, PointType>, tolerance: NumberType): Boolean


    protected abstract fun orientation(p: ComputablePoint<NumberType>, q: ComputablePoint<NumberType>, r: ComputablePoint<NumberType>): ThreePointOrientation


    protected enum class ThreePointOrientation {
        collinear, clockwise, counterclockwise
    }


    /**
     * Calls [rawIntersection(other, tolerance)] with default tolerance
     */
    abstract fun rawIntersection(other: ComputableLineSegment<NumberType, PointType>): ComputablePoint<NumberType>?


    /**
     * Finds where this line segment intersects the other one. Returns `null` if there is no such intersection.
     */
    abstract fun rawIntersection(other: ComputableLineSegment<NumberType, PointType>, tolerance: NumberType): ComputablePoint<NumberType>?


    /**
     * Calls [describeIntersection(other: ComputableLineSegment<NumberType>, tolerance: NumberType)] with default tolerance
     */
    abstract fun describeIntersection(other: ComputableLineSegment<NumberType, PointType>): IntersectionDescription


    /**
     * Discovers whether and how this line segments intersects another.
     * This requires [contains] and [rawIntersection] to be implemented properly.
     *
     * @param
     */
    fun describeIntersection(other: ComputableLineSegment<NumberType, PointType>, tolerance: NumberType): IntersectionDescription {

        // Easy win: If they're equal, they completely overlap and thus always intersect

        if (this.equals(other, tolerance = tolerance)) {
            return IntersectionDescription.completeOverlap(isStartAndEndFlipped = false)
        } else if (this.equals(other.flipped, tolerance = tolerance)) {
            return IntersectionDescription.completeOverlap(isStartAndEndFlipped = true)
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
    abstract fun intersects(other: ComputableLineSegment<NumberType, PointType>): Boolean


    /**
     * Indicates whether this line intersects the other one, with the given tolerance
     *
     * @param other     The other line to inspect
     * @param tolerance _optional_ - The distance this line can be from the other before they're not considered intersecting
     *
     * @param `true` iff the two lines intersect with the given tolerance
     */
    fun intersects(other: ComputableLineSegment<NumberType, PointType>, tolerance: NumberType): Boolean {
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
    object none : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other != null && other is none
        }


        override fun hashCode(): Int {
            return super.hashCode()
        }
    }


    /**
     * One of the left line segment's vertices touches one of the right one's
     *
     * @param verticesLocation The location of the touching vertices
     * @param isLeftStartVertex `true` iff the left line segment's vertex is its starting vertex
     * @param isRightStartVertex `true` iff the right line segment's vertex is its starting vertex
     */
    class leftVertexTouchesRightVertex<out NumberType : Number>(val verticesLocation: Point<NumberType>, val isLeftStartVertex: Boolean, val isRightStartVertex: Boolean) : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other is leftVertexTouchesRightVertex<*>
                    && verticesLocation == other.verticesLocation
                    && isLeftStartVertex == other.isLeftStartVertex
                    && isRightStartVertex == other.isRightStartVertex
        }


        override fun hashCode(): Int {
            return (super.hashCode()
                    xor verticesLocation.hashCode()
                    xor isLeftStartVertex.hashCode()
                    xor isRightStartVertex.hashCode())
        }


        override fun toString(): String {
            return "{ ${javaClass.simpleName}: { verticesLocation: $verticesLocation, isLeftStartVertex: $isLeftStartVertex, isRightStartVertex: $isRightStartVertex } }"
        }
    }


    /**
     * One of the left line segment's vertices touches the right one's edge
     *
     * @property leftVertexLocation The location of the touching vertex
     * @property isLeftStartVertex `true` iff the left line segment's touching vertex is its starting vertex
     */
    class leftVertexTouchesRightEdge<out NumberType : Number>(val leftVertexLocation: Point<NumberType>, val isLeftStartVertex: Boolean) : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other is leftVertexTouchesRightEdge<*>
                    && isLeftStartVertex == other.isLeftStartVertex
        }


        override fun hashCode(): Int {
            return super.hashCode() xor isLeftStartVertex.hashCode()
        }


        override fun toString(): String {
            return "{ ${javaClass.simpleName}: { leftVertexLocation: $leftVertexLocation, isLeftStartVertex: $isLeftStartVertex } }"
        }
    }


    /**
     * One of the right line segment's vertices touches the left one's edge
     *
     * @param rightVertexLocation The location of the touching vertex
     * @param isRightStartVertex `true` iff the right line segment's touching vertex is its starting vertex
     */
    class rightVertexTouchesLeftEdge<out NumberType : Number>(val rightVertexLocation: Point<NumberType>, val isRightStartVertex: Boolean) : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other is rightVertexTouchesLeftEdge<*>
                    && isRightStartVertex == other.isRightStartVertex
        }


        override fun hashCode(): Int {
            return super.hashCode() xor isRightStartVertex.hashCode()
        }


        override fun toString(): String {
            return "{ ${javaClass.simpleName}: { rightVertexLocation: $rightVertexLocation isRightStartVertex: $isRightStartVertex } }"
        }
    }


    /**
     * The left line segment and the right one cross, but their vertices don't touch
     *
     * @param crossingLocation The location of the crossing point
     */
    class edgesCross<out NumberType : Number>(val crossingLocation: Point<NumberType>) : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other is edgesCross<*>
                    && crossingLocation == other.crossingLocation
        }


        override fun hashCode(): Int {
            return super.hashCode() xor crossingLocation.hashCode()
        }


        override fun toString(): String {
            return "{ ${javaClass.simpleName}: { crossingLocation: $crossingLocation } }"
        }
    }


    /**
     * The line segments completely overlap; both vertices on both line segments touch eachother and all points along
     * both segments are shared exactly
     *
     * @param isStartAndEndFlipped `true` iff the starts touch ends and ends touch starts; `false` if the starts touch
     *                             starts and ends touch ends.
     */
    class completeOverlap(val isStartAndEndFlipped: Boolean) : IntersectionDescription() {
        override fun equals(other: Any?): Boolean {
            return other is completeOverlap
                    && isStartAndEndFlipped == other.isStartAndEndFlipped
        }


        override fun hashCode(): Int {
            return super.hashCode() xor isStartAndEndFlipped.hashCode()
        }


        override fun toString(): String {
            return "{ ${javaClass.simpleName}: { isStartAndEndFlipped: $isStartAndEndFlipped } }"
        }
    }
}


/**
 * An implementation of [ComputableLineSegment] using [Integer]s
 */
class IntegerLineSegment
    (start: IntegerPoint, end: IntegerPoint)
    : ComputableLineSegment<Integer, IntegerPoint>(start, end) {

    override fun contains(point: Point<Integer>): Boolean = contains(point, tolerance = defaultIntegerCalculationTolerance)

    override fun contains(point: Point<Integer>, tolerance: Integer): Boolean {
        point.integerValue.let { point ->
            if (start.equals(point, tolerance = tolerance)
                    || end.equals(point, tolerance = tolerance)) {
                return true
            }
        }
        return if (start.x.equals(end.x, tolerance = tolerance)) { // it's vertical
            point.x.equals(start.x, tolerance = tolerance) // just compare the horizontal
                    && point.y.isBetween(start.y, end.y, tolerance = tolerance)
        } else if (start.y.equals(end.y, tolerance = tolerance)) { // it's horizontal
            point.y.equals(start.y, tolerance = tolerance) // just compare the vertical
                    && point.x.isBetween(start.x, end.x, tolerance = tolerance)
        } else {
            val m = (end.y - start.y) / (end.x - start.x)
            val b = start.y - (m * start.x)
            point.y.equals(((m * point.x) + b), tolerance = tolerance) // derived from y=mx+b
        }
    }


    /**
     * The smallest rectangle that contains all points in this line
     */
    override val bounds: IntegerRect by lazy { IntegerRect(start, IntegerSize(x2 - x1, y2 - y1)) }


    /**
     * Not yet supported. That said, if the given `transform` is `null` or the identity, [fractionValue] is returned.
     */
    @Deprecated("Not yet supported")
    fun transformed(transform: AffineTransform?): FractionLineSegment {
        if (transform == null || transform.isIdentity) {
            return fractionValue
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

    override fun orientation(p: ComputablePoint<Integer>, q: ComputablePoint<Integer>, r: ComputablePoint<Integer>): ThreePointOrientation {
        val ret = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            ret == defaultIntegerCalculationTolerance -> ThreePointOrientation.collinear
            ret > 0 -> ThreePointOrientation.clockwise
            else -> ThreePointOrientation.counterclockwise
        }
    }

    // END: Translated code


    override fun equals(other: ComputableLineSegment<Integer, IntegerPoint>): Boolean
            = equals(other, tolerance = defaultIntegerCalculationTolerance)


    override fun equals(other: ComputableLineSegment<Integer, IntegerPoint>, tolerance: Integer): Boolean
            = this.start.equals(other.start, tolerance = tolerance)
            && this.end.equals(other.end, tolerance = tolerance)


    override fun copy(start: IntegerPoint, end: IntegerPoint): IntegerLineSegment {
        return IntegerLineSegment(start = start, end = end)
    }


    override fun intersects(other: ComputableLineSegment<Integer, IntegerPoint>): Boolean
            = intersects(other, tolerance = defaultIntegerCalculationTolerance)


    override fun describeIntersection(other: ComputableLineSegment<Integer, IntegerPoint>): IntersectionDescription
            = describeIntersection(other, tolerance = defaultIntegerCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Integer, IntegerPoint>): ComputablePoint<Integer>?
            = rawIntersection(other, tolerance = defaultIntegerCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Integer, IntegerPoint>, tolerance: Integer): ComputablePoint<Integer>?
            = findLineIntersection(this, other)


    companion object {
        /**
         * Finds the intersection point of the two given lines, within the given tolerance.
         *
         * Currently, this is done via [FractionLineSegment].[rawIntersection][FractionLineSegment.rawIntersection]
         * using [fractionValue] and [integerValue][FractionPoint.integerValue]
         *
         * @param line1     The first line which might intersect the second
         * @param line2     The second line which might intersect the first
         * @param tolerance The amount by which the vertices of the given lines can be apart by which they are still considered touching.
         *
         * @return The point at which the lines intersect, or `null` if they do not.
         */
        fun findLineIntersection(line1: ComputableLineSegment<Integer, IntegerPoint>,
                                 line2: ComputableLineSegment<Integer, IntegerPoint>,
                                 tolerance: Integer = defaultIntegerCalculationTolerance): ComputablePoint<Integer>?
                = line1.fractionValue.rawIntersection(line2.fractionValue, tolerance = tolerance.fractionValue)?.integerValue
    }
}
typealias Int64LineSegment = IntegerLineSegment
typealias IntLineSegment = IntegerLineSegment

val AnyLineSegment.integerValue: IntegerLineSegment get() = this as? IntegerLineSegment ?: IntegerLineSegment(start = this.start.integerValue, end = this.end.integerValue)


open class FractionLineSegment(start: FractionPoint, end: FractionPoint) : ComputableLineSegment<Fraction, FractionPoint>(start, end) {

    constructor(x1: Fraction, y1: Fraction, x2: Fraction, y2: Fraction)
            : this(FractionPoint(x1, y1), FractionPoint(x2, y2))


    override fun contains(point: Point<Fraction>): Boolean {
        return contains(point, tolerance = defaultFractionCalculationTolerance)
    }

    override fun contains(point: Point<Fraction>, tolerance: Fraction): Boolean {
        point.fractionValue.let { point ->
            if (start.equals(point, tolerance = tolerance)
                    || end.equals(point, tolerance = tolerance)) {
                return true
            }
        }
        return if (start.x.equals(end.x, tolerance = tolerance)) { // it's vertical
            point.x.equals(start.x, tolerance = tolerance) // just compare the horizontal
                    && point.y.isBetween(start.y, end.y, tolerance = tolerance)
        } else if (start.y.equals(end.y, tolerance = tolerance)) { // it's horizontal
            point.y.equals(start.y, tolerance = tolerance) // just compare the vertical
                    && point.x.isBetween(start.x, end.x, tolerance = tolerance)
        } else {
            val m = (end.y - start.y) / (end.x - start.x)
            val b = start.y - (m * start.x)
            point.y.equals(((m * point.x) + b), tolerance = tolerance) // derived from y=mx+b
        }
    }

    /**
     * The smallest rectangle that contains all points in this line
     */
    override val bounds: FractionRect by lazy { FractionRect(FractionPoint(x1, y1), FractionSize(x2 - x1, y2 - y1)) }

    /**
     * Not yet supported. That said, if the given `transform` is `null` or the identity, [fractionValue] is returned.
     */
    fun transformed(transform: AffineTransform?): FractionLineSegment {
        if (transform == null || transform.isIdentity) {
            return fractionValue
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

    override fun orientation(p: ComputablePoint<Fraction>, q: ComputablePoint<Fraction>, r: ComputablePoint<Fraction>): ThreePointOrientation {
        val ret = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            abs(ret) <= defaultFractionCalculationTolerance -> ThreePointOrientation.collinear
            ret > 0 -> ThreePointOrientation.clockwise
            else -> ThreePointOrientation.counterclockwise
        }
    }

    // END: Translated code


    override fun equals(other: ComputableLineSegment<Fraction, FractionPoint>): Boolean = equals(other, tolerance = defaultFractionCalculationTolerance)


    override fun equals(other: ComputableLineSegment<Fraction, FractionPoint>, tolerance: Fraction): Boolean
            = this.start.equals(other.start, tolerance = tolerance)
            && this.end.equals(other.end, tolerance = tolerance)


    override fun copy(start: FractionPoint, end: FractionPoint): FractionLineSegment {
        return FractionLineSegment(start = start, end = end)
    }


    override fun intersects(other: ComputableLineSegment<Fraction, FractionPoint>): Boolean
            = intersects(other, tolerance = defaultFractionCalculationTolerance)


    override fun describeIntersection(other: ComputableLineSegment<Fraction, FractionPoint>): IntersectionDescription
            = describeIntersection(other, tolerance = defaultFractionCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Fraction, FractionPoint>): ComputablePoint<Fraction>?
            = rawIntersection(other, tolerance = defaultFractionCalculationTolerance)


    override fun rawIntersection(other: ComputableLineSegment<Fraction, FractionPoint>, tolerance: Fraction): ComputablePoint<Fraction>?
            = findLineIntersection(this, other)

    companion object {
        /**
         * Finds the intersection of the two given lines to the given tolerance, if it exists.
         *
         * This currently uses a Kotlin version of the algorithm described here:
         * http://stackoverflow.com/a/1968345/3939277 which was derived from the comment-linked F# translation here:
         * http://pastebin.com/nf56MHP7
         *
         * The intersection is currently preceded by a rudamentary check for collinear segments' vertices touching,
         * which may or may not have a general solution. If it does, that will hopefully be documented here:
         * http://math.stackexchange.com/q/2177005/317419
         * If such a general solution is found, this will be updated to use that.
         *
         * @param line1     The first line which might intersect the second
         * @param line2     The second line which might intersect the first
         * @param tolerance _optional_ - The amount by which the vertices of the given lines can be apart by which they are still considered touching.
         *
         * @return The point at which the lines intersect, or `null` if they do not.
         */
        fun findLineIntersection(line1: ComputableLineSegment<Fraction, FractionPoint>,
                                 line2: ComputableLineSegment<Fraction, FractionPoint>,
                                 tolerance: Fraction = defaultFractionCalculationTolerance): ComputablePoint<Fraction>? {

            // There may not be any algorithm that detects collinear vertex-only intersections, so always perform this primitive check first:

            if (line1.equals(line2, tolerance = tolerance)) {
                return line1.start
            } else if (line1.start.equals(line2.start, tolerance = tolerance) || line1.start.equals(line2.end, tolerance = tolerance)) {
                return line1.start
            } else if (line1.end.equals(line2.start, tolerance = tolerance) || line1.end.equals(line2.end, tolerance = tolerance)) {
                return line1.end
            }

            // Also check for basic bounding box intersection; if the boxes don't intersect then the lines cannot: http://math.stackexchange.com/q/2177005/317419

            else if (!line1.bounds.intersects(line2.bounds)) {
                return null
            }

            // Otherwise, use a more general algorithm:

            val line1XDelta = line1.start.x - line1.end.x
            val line1YDelta = line1.end.y - line1.start.y
            val line1Delta = (line1XDelta * line1.start.y) + (line1YDelta * line1.start.x)

            val line2XDelta = line2.start.x - line2.end.x
            val line2YDelta = line2.end.y - line2.start.y
            val line2Delta = (line2XDelta * line2.start.y) + (line2YDelta * line2.start.x)

            val angularDifference = abs((line2XDelta * line1YDelta) - (line1XDelta * line2YDelta))
            val areParallel = angularDifference <= tolerance

            return if (areParallel) { // parallel line segments can still intersect if they share a vertex
                if (line1.start == line2.start
                        || line1.start == line2.end) {
                    line1.start
                } else if (line1.end == line2.start
                        || line1.end == line2.end) {
                    line1.end
                } else {
                    null
                }
            } else {
                val x = ((line2XDelta * line1Delta) - (line1XDelta * line2Delta)) / angularDifference
                val y = ((line1YDelta * line2Delta) - (line2YDelta * line1Delta)) / angularDifference

                if (x.isNaN() || y.isNaN()) {
                    null
                } else {
                    FractionPoint(x, y)
                }
            }
        }
    }
}
typealias Float64LineSegment = FractionLineSegment
typealias FloatLineSegment = FractionLineSegment

val AnyLineSegment.fractionValue: FractionLineSegment get() = this as? FractionLineSegment ?: FractionLineSegment(start.fractionValue, end.fractionValue)
