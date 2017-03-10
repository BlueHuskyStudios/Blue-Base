@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import java.awt.Rectangle
import java.awt.geom.Rectangle2D


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
open class Rect<out NumberType : Number, out PointType: Point<NumberType>, out SizeType: Size<NumberType>>(val origin: PointType, val size: SizeType) {

    val x get() = this.origin.x
    val y get() = this.origin.y
    val width get() = this.size.width
    val height get() = this.size.height

    override fun toString(): String {
        return "{origin: $origin, size: $size}"
    }

    val stringValue get() = toString()
}


typealias AnyRect = Rect<*, *, *>



/**
 * A type of [Rect] that can be used in computations and have computations performed on it
 */
abstract class ComputableRect<NumberType : Number, PointType: ComputablePoint<NumberType>,
        SizeType: ComputableSize<NumberType>>(origin: PointType, size: SizeType)
    : Rect<NumberType, PointType, SizeType>(origin, size) {

    /** The lowest X value of this rectangle */
    abstract val minX: NumberType
    /** The center X value of this rectangle */
    abstract val midX: NumberType
    /** The highest X value of this rectangle */
    abstract val maxX: NumberType

    /** The lowest Y value of this rectangle */
    abstract val minY: NumberType
    /** The center Y value of this rectangle */
    abstract val midY: NumberType
    /** The highest Y value of this rectangle */
    abstract val maxY: NumberType

    /** The coordinates of the point at the lowest X value and lowest Y value of this rectangle */
    abstract val minXminY: PointType
    /** The coordinates of the point at the lowest X value and center Y value of this rectangle */
    abstract val minXmidY: PointType
    /** The coordinates of the point at the lowest X value and highest Y value of this rectangle */
    abstract val minXmaxY: PointType

    /** The coordinates of the point at the center X value and lowest Y value of this rectangle */
    abstract val midXminY: PointType
    /** The coordinates of the point at the center X value and center Y value of this rectangle */
    abstract val midXmidY: PointType
    /** The coordinates of the point at the center X value and highest Y value of this rectangle */
    abstract val midXmaxY: PointType

    /** The coordinates of the point at the highest X value and lowest Y value of this rectangle */
    abstract val maxXminY: PointType
    /** The coordinates of the point at the highest X value and center Y value of this rectangle */
    abstract val maxXmidY: PointType
    /** The coordinates of the point at the highest X value and highest Y value of this rectangle */
    abstract val maxXmaxY: PointType

    /**
     * Indicates whether this rectangle has a `0` area.
     * By default this is simply whether [size].[isEmpty][ComputableSize.isEmpty]
     */
    inline val isEmpty: Boolean get() = size.isEmpty


    /**
     * Indicates whether this rectangle completely contains the given [other] one, within the given tolerance.
     *
     * @param other     The other rectangle
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can each be outside this
     *                  one before it is no longer considered completely contained within this one.
     *
     * @return `true` iff the other rectangle is completely contained within this one.
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun contains(other: ComputableRect<NumberType, PointType, SizeType>): Boolean

    /**
     * Indicates whether this rectangle completely contains the given [other] one, within the given tolerance.
     *
     * @param other     The other rectangle
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can each be outside this
     *                  one before it is no longer considered completely contained within this one.
     *
     * @return `true` iff the other rectangle is completely contained within this one.
     */
    abstract fun contains(other: ComputableRect<NumberType, PointType, SizeType>, tolerance: NumberType): Boolean


    /**
     * Indicates whether this rectangle at all intersects the given [other] one, within the given tolerance. If only
     * their edges or vertices touch, that is considered an intersection.
     *
     * @param other     The other rectangle
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can be away from this one
     *                  before it is no longer considered intersecting this one at all.
     *
     * @return `true` iff the other rectangle is completely contained within this one.
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun intersects(other: ComputableRect<NumberType, PointType, SizeType>): Boolean

    /**
     * Indicates whether this rectangle at all intersects the given [other] one, within the given tolerance. If only
     * their edges or vertices touch, that is considered an intersection.
     *
     * @param other     The other rectangle
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can be away from this one
     *                  before it is no longer considered intersecting this one at all.
     *
     * @return `true` iff the other rectangle is completely contained within this one.
     */
    abstract fun intersects(other: ComputableRect<NumberType, PointType, SizeType>, tolerance: NumberType): Boolean


    /**
     * Finds the portion of this rectangle that intersects the given [other] one, within the given tolerance. If only
     * their edges or vertices touch, that is considered an intersection, despite having `0` area.
     *
     * @param other     The other rectangle
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can be away from this one
     *                  before it is no longer considered intersecting this one at all.
     *
     * @return The rectangular intersection of this and the given rectangles, or `null` that doesn't exist.
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun intersection(other: ComputableRect<NumberType, PointType, SizeType>): ComputableRect<NumberType, PointType, SizeType>?

    /**
     * Finds the portion of this rectangle that intersects the given [other] one, within the given tolerance. If only
     * their edges or vertices touch, that is considered an intersection, despite having `0` area.
     *
     * @param other     The other rectangle which might
     * @param tolerance _optional_ - The amount by which the sides of the [other] rectangle can be away from this one
     *                  before it is no longer considered intersecting this one at all.
     *
     * @return The rectangular intersection of this and the given rectangles, or `null` that doesn't exist.
     */
    abstract fun intersection(other: ComputableRect<NumberType, PointType, SizeType>, tolerance: NumberType): ComputableRect<NumberType, PointType, SizeType>?


    /**
     * Finds and returns the rectangle that contains both this and the given [other] one
     */
    abstract fun union(other: ComputableRect<NumberType, PointType, SizeType>): ComputableRect<NumberType, PointType, SizeType>
}



private typealias Int64RectBaseType = ComputableRect<Int64, ComputablePoint<Int64>, ComputableSize<Int64>>



/**
 * A default implementation of [ComputableRect] using [Integer]s
 */
class IntegerRect(origin: ComputablePoint<Int64>, size: ComputableSize<Int64>)
    : Int64RectBaseType(origin, size) {

    companion object {
        val zero by lazy { IntegerRect(IntegerPoint.zero, IntegerSize.zero) }
    }

    constructor(x: Int64, y: Int64, width: Int64, height: Int64) : this(IntPoint(x, y), IntegerSize(width, height))

    override val minX by lazy { if (width < 0) x + width else x }
    override val midX by lazy { (minX + maxX) / 2 }
    override val maxX by lazy { if (width < 0) x else x + width }

    override val minY by lazy { if (height < 0) y + height else y }
    override val midY by lazy { (minY + maxY) / 2 }
    override val maxY by lazy { if (height < 0) y else y + height }

    override val minXminY by lazy { IntegerPoint(minX, minY) }
    override val minXmidY by lazy { IntegerPoint(minX, midY) }
    override val minXmaxY by lazy { IntegerPoint(minX, maxY) }

    override val midXminY by lazy { IntegerPoint(midX, minY) }
    override val midXmidY by lazy { IntegerPoint(midX, midY) }
    override val midXmaxY by lazy { IntegerPoint(midX, maxY) }

    override val maxXminY by lazy { IntegerPoint(maxX, minY) }
    override val maxXmidY by lazy { IntegerPoint(maxX, midY) }
    override val maxXmaxY by lazy { IntegerPoint(maxX, maxY) }



    val awtValue: Rectangle by lazy { Rectangle(x.int32Value, y.int32Value, width.int32Value, height.int32Value) }

    override fun contains(other: Int64RectBaseType): Boolean
            = contains(other, defaultIntegerCalculationTolerance)

    override fun contains(other: Int64RectBaseType, tolerance: Integer): Boolean
            = this.minX.isLessThanOrEqualTo(x, tolerance = tolerance)
            && this.minY.isLessThanOrEqualTo(y, tolerance = tolerance)
            && this.maxX.isGreaterThanOrEqualTo(x, tolerance = tolerance)
            && this.maxY.isGreaterThanOrEqualTo(y, tolerance = tolerance)


    override fun intersects(other: Int64RectBaseType): Boolean
            = intersection(other) != null

    override fun intersects(other: Int64RectBaseType, tolerance: Integer): Boolean
            = intersection(other, tolerance = tolerance) != null


    override fun union(other: Int64RectBaseType): Int64RectBaseType {
        // Adapted from https://github.com/apple/swift-corelibs-foundation/blob/87815eab0cff7d971f1fbdbfbe98729ec92dbe3d/Foundation/NSGeometry.swift#L587

        val isEmptyFirstRect = this.isEmpty
        val isEmptySecondRect = other.isEmpty
        if (isEmptyFirstRect && isEmptySecondRect) {
            return zero
        } else if (isEmptyFirstRect) {
            return other
        } else if (isEmptySecondRect) {
            return this
        }
        val x = min(this.minX, other.minX)
        val y = min(this.minY, other.minY)
        val width = max(this.maxX, other.maxX) - x
        val height = max(this.maxY, other.maxY) - y
        return IntegerRect(x, y, width, height)
    }


    override fun intersection(other: Int64RectBaseType): Int64RectBaseType?
            = intersection(other, tolerance = -defaultIntegerCalculationTolerance)


    override fun intersection(other: Int64RectBaseType, tolerance: Integer): Int64RectBaseType? {
        // Adapted from https://github.com/apple/swift-corelibs-foundation/blob/87815eab0cff7d971f1fbdbfbe98729ec92dbe3d/Foundation/NSGeometry.swift#L604

        if (this.maxX.isLessThanOrEqualTo(other.minX, tolerance = tolerance)
                || other.maxX.isLessThanOrEqualTo(this.minX, tolerance = tolerance)
                || this.maxY.isLessThanOrEqualTo(other.minY, tolerance = tolerance)
                || other.maxY.isLessThanOrEqualTo(this.minY, tolerance = tolerance)) {
            return null
        }
        val x = max(this.minX, other.minX)
        val y = max(this.minY, other.minY)
        val width = min(this.maxX, other.maxX) - x
        val height = min(this.maxY, other.maxY) - y
        return IntegerRect(x, y, width, height)
    }
}
typealias Int64Rect = IntegerRect
typealias IntRect = IntegerRect

/** Returns this rectangle as an [IntegerRect] */
val AnyRect.integerValue: IntegerRect
    get() = if (this is IntegerRect) this
    else IntegerRect(x = x.integerValue, y = y.integerValue, width = width.integerValue, height = height.integerValue)



private typealias FractionRectBaseType = ComputableRect<Fraction, ComputablePoint<Fraction>, ComputableSize<Fraction>>



/**
 * A default implementation of [ComputableRect] using [Fraction]s
 */
class FractionRect(origin: ComputablePoint<Fraction>, size: ComputableSize<Fraction>)
    : FractionRectBaseType(origin, size) {

    companion object {
        val zero = FractionRect(FractionPoint.zero, Float64Size.zero)
    }

    constructor(x: Fraction, y: Fraction, width: Fraction, height: Fraction) : this(FractionPoint(x, y), FractionSize(width, height))
    constructor(x: Integer, y: Integer, width: Integer, height: Integer) : this(FractionPoint(x, y), FractionSize(width, height))
    constructor(x: Int32, y: Int32, width: Int32, height: Int32)  : this(FractionPoint(x, y), FractionSize(width, height))

    constructor(awtValue: Rectangle2D) : this(awtValue.x, awtValue.y, awtValue.width, awtValue.height)

    override val minX by lazy { if (width < 0) x + width else x }
    override val midX by lazy { (minX + maxX) / 2 }
    override val maxX by lazy { if (width < 0) x else x + width }

    override val minY by lazy { if (height < 0) y + height else y }
    override val midY by lazy { (minY + maxY) / 2 }
    override val maxY by lazy { if (height < 0) y else y + height }

    override val minXminY by lazy { FractionPoint(minX, minY) }
    override val minXmidY by lazy { FractionPoint(minX, midY) }
    override val minXmaxY by lazy { FractionPoint(minX, maxY) }

    override val midXminY by lazy { FractionPoint(midX, minY) }
    override val midXmidY by lazy { FractionPoint(midX, midY) }
    override val midXmaxY by lazy { FractionPoint(midX, maxY) }

    override val maxXminY by lazy { FractionPoint(maxX, minY) }
    override val maxXmidY by lazy { FractionPoint(maxX, midY) }
    override val maxXmaxY by lazy { FractionPoint(maxX, maxY) }

    val awtValue: Rectangle2D get() = Rectangle2D.Double(x, y, width, height)

    override fun contains(other: FractionRectBaseType): Boolean
            = contains(other, defaultFractionCalculationTolerance)

    override fun contains(other: FractionRectBaseType, tolerance: Fraction): Boolean
            = this.minX.isLessThanOrEqualTo(x, tolerance = tolerance)
            && this.minY.isLessThanOrEqualTo(y, tolerance = tolerance)
            && this.maxX.isGreaterThanOrEqualTo(x, tolerance = tolerance)
            && this.maxY.isGreaterThanOrEqualTo(y, tolerance = tolerance)


    override fun intersects(other: FractionRectBaseType): Boolean
            = intersection(other) != null

    override fun intersects(other: FractionRectBaseType, tolerance: Fraction): Boolean
            = intersection(other, tolerance = tolerance) != null


    override fun union(other: FractionRectBaseType): FractionRectBaseType {
        // Adapted from https://github.com/apple/swift-corelibs-foundation/blob/87815eab0cff7d971f1fbdbfbe98729ec92dbe3d/Foundation/NSGeometry.swift#L587

        val isEmptyFirstRect = this.isEmpty
        val isEmptySecondRect = other.isEmpty
        if (isEmptyFirstRect && isEmptySecondRect) {
            return zero
        } else if (isEmptyFirstRect) {
            return other
        } else if (isEmptySecondRect) {
            return this
        }
        val x = org.bh.tools.base.math.min(this.minX, other.minX)
        val y = min(this.minY, other.minY)
        val width = max(this.maxX, other.maxX) - x
        val height = max(this.maxY, other.maxY) - y
        return FractionRect(x, y, width, height)
    }


    override fun intersection(other: FractionRectBaseType): FractionRectBaseType?
            = intersection(other, tolerance = -defaultFractionCalculationTolerance)

    override fun intersection(other: FractionRectBaseType, tolerance: Fraction): FractionRectBaseType? {
        if (this.maxX.isLessThan(other.minX, tolerance = tolerance)
                || other.maxX.isLessThan(this.minX, tolerance = tolerance)
                || this.maxY.isLessThan(other.minY, tolerance = tolerance)
                || other.maxY.isLessThan(this.minY, tolerance = tolerance)) {
            return null
        }
        val x = max(this.minX, other.minX)
        val y = max(this.minY, other.minY)
        val width = min(this.maxX, other.maxX) - x
        val height = min(this.maxY, other.maxY) - y
        return FractionRect(x, y, width, height)
    }
}
typealias Float64Rect = FractionRect
typealias FloatRect = FractionRect


val AnyRect.fractionValue: FractionRect get() = FractionRect(x = x.fractionValue, y = y.fractionValue, width = width.fractionValue, height = height.fractionValue)
val java.awt.Rectangle.fractionValue: FractionRect get() = FractionRect(x = x, y = y, width = width, height = height)


infix operator fun FractionRect.times(size: FractionSize): FractionRect {
    return FractionRect(
            x = x * size.width,
            y = y * size.height,
            width = width * size.width,
            height = height * size.height
    )
}
