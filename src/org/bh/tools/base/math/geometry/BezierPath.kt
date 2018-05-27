@file:Suppress("unused", "PropertyName")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*

/**
 * A path comprised of Bézier points. For behavior, see: https://en.wikipedia.org/wiki/B%C3%A9zier_curve
 *
 * @author Kyli Rouge
 * @since 2017-01-29
 */
data class BezierPath(override val segments: List<CubicBezierPathSegment>): FractionPath(segments = segments) {
    companion object
}



/**
 * A segment in a Bézier path. For behavior, see: https://en.wikipedia.org/wiki/B%C3%A9zier_curve
 *
 * This is comprised of four points: two anchors which describe where the path starts and ends ("anchor" points), and
 * two which describe how the curve flows ("control" points).
 *
 * This implementation allows control points to be `null`, which signifies that they always lie atop their anchor
 * points and do not contribute to the flow of the curve. A Bézier Path Segment with only `null` control points is a line.
 */
data class CubicBezierPathSegment(
        /**
         * The first anchor point of the curve
         */
        override val start: FractionPoint,

        /**
         * The control point that describes the start of the curve
         */
        val startControlPoint: FractionPoint?,

        /**
         * The control point that describes the end of the curve
         */
        val endControlPoint: FractionPoint?,

        /**
         * The last anchor point of the curve
         */
        override val end: FractionPoint
): FractionLineSegment(start = start, end = end) {
    /**
     * The first anchor point of the curve
     */
    inline val startAnchorPoint get() = start

    /**
     * The start control point, made non-null for easy translation into libraries that require one
     */
    inline val nonNullStartControlPoint get() = startControlPoint ?: start

    /**
     * The last anchor point of the curve
     */
    inline val endAnchorPoint get() = end

    /**
     * The end control point, made non-null for easy translation into libraries that require one
     */
    inline val nonNullEndControlPoint get() = endControlPoint ?: end


    override fun contains(point: Point<Fraction>, tolerance: Fraction): Boolean {
        return super.contains(point, tolerance)
    }



    companion object
}
/**
 * @see CubicBezierPathSegment
 */
typealias CubicBézierPathSegment = CubicBezierPathSegment



/**
 * @see BezierPath
 */
typealias BézierPath = BezierPath


/**
 * An object that can be converted into a [BezierPath]
 */
interface BezierPathConvertible {
    /**
     * This, converted to a [BezierPath]
     */
    val bezierPathValue: BezierPath

    /**
     * This, converted to a [BézierPath]
     */
    val bézierPathValue: BézierPath get() = bezierPathValue
}
