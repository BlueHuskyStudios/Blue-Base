package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.Float64
import org.bh.tools.base.math.max
import org.bh.tools.base.math.min
import java.awt.geom.AffineTransform
import org.bh.tools.base.math.geometry.Float64Line.ThreePointOrientation.*
import java.lang.Math.abs


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
open class Line<out NumberType : Number>(val start: Point<NumberType>, val end: Point<NumberType>) : Cloneable {

    companion object {
        val zero: Line<*> = Line(0, 0, 0, 0)
    }

    constructor(x1: NumberType, y1: NumberType, x2: NumberType, y2: NumberType) : this(Point(x1, y1), Point(x2, y2))

    val x1 get() = start.x
    val y1 get() = start.y
    val x2 get() = end.x
    val y2 get() = end.y

    override fun toString(): String {
        return "{start: $start, end: $end}"
    }

    val stringValue get() = toString()

    override public fun clone(): Line<NumberType> {
        return Line(start.clone(), end.clone())
    }
}

abstract class ComputableLine<out NumberType : Number>(start: Point<NumberType>, end: Point<NumberType>) : Line<NumberType>(start, end) {
    abstract fun contains(point: Point<BHFloat>, tolerance: BHFloat = 0.0001): Boolean
}

class Float64Line(start: Point<Float64>, end: Point<Float64>) : ComputableLine<Float64>(start, end) {

    /**
     * Indicates whether the given point lies on this line
     *
     * @param point     The point to test
     * @param tolerance _optional_ - How far the point can be before it's not considered on the line.
     *
     * @return `true` iff the given point lies on this line
     */
    override fun contains(point: Point<BHFloat>, tolerance: BHFloat): Boolean {
        val m = (end.y - start.y) / (end.x - start.x)
        val b = start.y - (m * start.x)
        return Math.abs(point.y - ((m * point.x) + b)) < tolerance // derived from y=mx+b
    }

    /**
     * The smallest rectangle that contains all points in this line
     */
    val bounds: FloatRect = FloatRect(Point(x1, y1), Size(x2 - x1, y2 - y1))

    fun transformed(transform: AffineTransform?): Float64Line {
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

    /**
     * Given three possibly collinear points p, q, r, the function checks if point q lies on line segment 'pr'
     */
    private fun onSegment(pr: Line<Float64>, q: Point<Float64>): Boolean
            = (q.x <= max(pr.start.x, pr.end.x) && q.x >= min(pr.start.x, pr.end.x)
            && q.y <= max(pr.start.y, pr.end.y) && q.y >= min(pr.start.y, pr.end.y))

    /**
     * To find orientation of ordered triplet (p, q, r).
     *
     * @see - http://www.geeksforgeeks.org/orientation-3-ordered-points/
     */
    private fun orientation(p: Point<Float64>, q: Point<Float64>, r: Point<Float64>, tolerance: BHFloat = 0.0001): ThreePointOrientation {
        val ret = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)

        return when {
            abs(ret) <= tolerance -> collinear
            ret > 0 -> clockwise
            else -> counterclockwise
        }
    }

    enum class ThreePointOrientation {
        collinear, clockwise, counterclockwise
    }

    /**
     * The main function that returns true if line segment 'p1q1' and 'p2q2' intersect.
     */
    private fun doIntersect(p1q1: Line<Float64>, p2q2: Line<Float64>): Boolean {
        val p1 = p1q1.start
        val q1 = p1q1.end
        val p2 = p2q2.start
        val q2 = p2q2.end

        // Find the four orientations needed for general and
        // special cases
        val o1 = orientation(p1, q1, p2)
        val o2 = orientation(p1, q1, q2)
        val o3 = orientation(p2, q2, p1)
        val o4 = orientation(p2, q2, q1)

        // General case
        if (o1 != o2 && o3 != o4)
            return true

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == collinear && onSegment(p1q1, p2)) return true

        // p1, q1 and p2 are collinear and q2 lies on segment p1q1
        if (o2 == collinear && onSegment(p1q1, q2)) return true

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == collinear && onSegment(p2q2, p1)) return true

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == collinear && onSegment(p2q2, q1)) return true

        return false // Doesn't fall in any of the above cases
    }

    // END: Translated code


    fun intersects(other: Float64Line): Boolean {
        return doIntersect(this, other)
    }
}
typealias BHFloatLine = Float64Line
typealias FloatLine = BHFloatLine

val Line<*>.floatValue: FloatLine get() = FloatLine(start.floatValue, end.floatValue)
