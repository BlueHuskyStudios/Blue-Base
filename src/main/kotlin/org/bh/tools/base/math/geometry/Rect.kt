@file:Suppress("unused", "KDocMissingDocumentation")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import org.bh.tools.base.struct.*


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IDEA Project.
 *
 * A size which uses a number of any type
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
abstract class Rect<NumberType : Number, PointType: Point<NumberType>, SizeType: Size<NumberType>>(val origin: PointType, val size: SizeType) {

    val x get() = this.origin.x
    val y get() = this.origin.y
    val width get() = this.size.width
    val height get() = this.size.height

    override fun toString(): String {
        return "{origin: $origin, size: $size}"
    }

    val stringValue get() = toString()


    abstract fun copy(newOrigin: PointType = this.origin, newSize: SizeType = this.size): Rect<NumberType, PointType, SizeType>

    abstract fun copy(newX: NumberType = this.x, newY: NumberType = this.y, newWidth: NumberType = this.width, newHeight: NumberType = this.height): Rect<NumberType, PointType, SizeType>
}


/** For matching any sort of [Rect] */
typealias AnyRect = Rect<*, *, *>



/**
 * A type of [Rect] that can be used in computations and have computations performed on it
 */
abstract class ComputableRect
    <NumberType, PointType, SizeType>
    (origin: PointType, size: SizeType)
    : Rect<NumberType, PointType, SizeType>(origin, size),
        TolerableEquality<ComputableRect<NumberType, PointType, SizeType>>
    where NumberType : Number,
          NumberType: Comparable<NumberType>,
          PointType: ComputablePoint<NumberType>,
          SizeType: ComputableSize<NumberType>
{

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


    /**
     * Returns a rectangle that is the same size as this one, but whose position is this one given an offset to its
     * origin. Note that the given point is **not a _new_ point**, but an `(x, y)` offset, whose components will be
     * added to this rectangle's origin's components
     */
    fun offset(offset: PointType): ComputableRect<NumberType, PointType, SizeType> = offset(xOffset = offset.x, yOffset =  offset.y)


    /**
     * Returns a rectangle that is the same size as this one, but whose position is this one given an offset to its origin
     */
    abstract fun offset(xOffset: NumberType, yOffset: NumberType): ComputableRect<NumberType, PointType, SizeType>


    /**
     * Returns a copy of this rectangle that is resized so that it contains the given extremes
     *
     * @param newMinX The new lowest X value of this rectangle (left edge in a Euclidean plane)
     * @param newMinY The new lowest Y value of this rectangle (bottom edge in a Euclidean plane)
     * @param newMaxX The new highest X value of this rectangle (right edge in a Euclidean plane)
     * @param newMaxY The new highest Y value of this rectangle (top edge in a Euclidean plane)
     */
    abstract fun copyWithExtremes(newMinX: NumberType = minX,
                                  newMinY: NumberType = minY,
                                  newMaxX: NumberType = maxX,
                                  newMaxY: NumberType = maxY): ComputableRect<NumberType, PointType, SizeType>


    /**
     * Returns a copy of this rect which is expanded in size to ensure it contains the given point
     */
    open fun expanded(toInclude: PointType) : ComputableRect<NumberType, PointType, SizeType> {
        @Suppress("UnnecessaryVariable")
        val includedPoint = toInclude
        val newMinX: NumberType
        val newMinY: NumberType
        val newMaxX: NumberType
        val newMaxY: NumberType

        newMinX = if (includedPoint.x < this.minX) includedPoint.x else minX
        newMinY = if (includedPoint.y < this.minY) includedPoint.y else minY
        newMaxX = if (includedPoint.x > this.maxX) includedPoint.x else maxX
        newMaxY = if (includedPoint.y > this.maxY) includedPoint.y else maxY

        return copyWithExtremes(newMinX = newMinX, newMaxX = newMaxX, newMinY = newMinY, newMaxY = newMaxY)
    }


    abstract fun forEach(scanningApproach: RectangleScanningApproach = RectangleScanningApproach.default,
                         iterator: (coordinate: PointType, didRollOver: Boolean) -> Unit)
}



private typealias IntegerRectBaseType = ComputableRect<Integer, ComputablePoint<Integer>, ComputableSize<Integer>>



/**
 * A default implementation of [ComputableRect] using [Integer]s
 */
open class IntegerRect(origin: ComputablePoint<Integer>, size: ComputableSize<Integer>)
    : IntegerRectBaseType(origin, size) {

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


    override fun contains(other: IntegerRectBaseType): Boolean
            = contains(other, defaultIntegerCalculationTolerance)

    override fun contains(other: IntegerRectBaseType, tolerance: Integer): Boolean
            = this.minX.isLessThanOrEqualTo(x, tolerance = tolerance)
            && this.minY.isLessThanOrEqualTo(y, tolerance = tolerance)
            && this.maxX.isGreaterThanOrEqualTo(x, tolerance = tolerance)
            && this.maxY.isGreaterThanOrEqualTo(y, tolerance = tolerance)


    override fun intersects(other: IntegerRectBaseType): Boolean
            = intersection(other) != null

    override fun intersects(other: IntegerRectBaseType, tolerance: Integer): Boolean
            = intersection(other, tolerance = tolerance) != null


    override fun union(other: IntegerRectBaseType): IntegerRectBaseType {
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


    override fun intersection(other: IntegerRectBaseType): IntegerRect?
            = intersection(other, tolerance = -defaultIntegerCalculationTolerance)


    override fun intersection(other: IntegerRectBaseType, tolerance: Integer): IntegerRect? {
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


    override fun offset(xOffset: Integer, yOffset: Integer): IntegerRect {
        return IntegerRect(x = x + xOffset, y = y + yOffset, width = width, height = height)
    }


    override fun copy(newOrigin: ComputablePoint<Integer>, newSize: ComputableSize<Integer>)
            = IntegerRect(newOrigin, newSize)


    override fun copy(newX: Integer, newY: Integer, newWidth: Integer, newHeight: Integer)
            = IntegerRect(newX, newY, newWidth, newHeight)


    override fun copyWithExtremes(newMinX: Integer, newMinY: Integer, newMaxX: Integer, newMaxY: Integer)
            = IntegerRect(origin = IntegerPoint(x = newMinX, y = newMinY),
                            size = IntegerSize(width = newMaxX - newMinX, height = newMaxY - newMinY))


    override fun expanded(toInclude: ComputablePoint<Integer>): IntegerRect = super.expanded(toInclude).integerValue


    override fun equals(other: IntegerRectBaseType, tolerance: Tolerance): Boolean
            = origin.equals(other.origin, tolerance = tolerance)


    inline val integerValue: IntegerRect get() = this


    override fun forEach(scanningApproach: RectangleScanningApproach,
                         iterator: (coordinate: ComputablePoint<Integer>, didRollOver: Boolean) -> Unit) =
            rectangularIteratorTemplate(scanningApproach,
                                        yIterator = minY until maxY,
                                        xIterator = minX until maxX,
                                        pointGenerator = ::IntegerPoint,
                                        iterator = iterator)
}
/** A [Rect] that uses [Int64]s */
typealias Int64Rect = IntegerRect
/** A [Rect] that uses native integers */
typealias IntRect = IntegerRect

/** Returns this rectangle as an [IntegerRect] */
val AnyRect.integerValue: IntegerRect
    get() = this as? IntegerRect ?: IntegerRect(x = x.integerValue, y = y.integerValue, width = width.integerValue, height = height.integerValue)
/** Creates a new [IntegerRect] from this AWT Rectangle */



private typealias FractionRectBaseType = ComputableRect<Fraction, ComputablePoint<Fraction>, ComputableSize<Fraction>>



/**
 * A default implementation of [ComputableRect] using [Fraction]s
 */
open class FractionRect(origin: ComputablePoint<Fraction>, size: ComputableSize<Fraction>)
    : FractionRectBaseType(origin, size) {

    companion object {
        val zero = FractionRect(FractionPoint.zero, Float64Size.zero)
    }

    constructor(x: Fraction, y: Fraction, width: Fraction, height: Fraction) : this(FractionPoint(x, y), FractionSize(width, height))
    constructor(x: Integer, y: Integer, width: Integer, height: Integer) : this(FractionPoint(x, y), FractionSize(width, height))
    constructor(x: Int32, y: Int32, width: Int32, height: Int32)  : this(FractionPoint(x, y), FractionSize(width, height))

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


    override fun contains(other: FractionRectBaseType): Boolean
            = contains(other, defaultFractionCalculationTolerance)

    override fun contains(other: FractionRectBaseType, tolerance: Tolerance): Boolean
            = this.minX.isLessThanOrEqualTo(x, tolerance = tolerance)
            && this.minY.isLessThanOrEqualTo(y, tolerance = tolerance)
            && this.maxX.isGreaterThanOrEqualTo(x, tolerance = tolerance)
            && this.maxY.isGreaterThanOrEqualTo(y, tolerance = tolerance)


    override fun intersects(other: FractionRectBaseType): Boolean
            = intersection(other) != null

    override fun intersects(other: FractionRectBaseType, tolerance: Tolerance): Boolean
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
        val x = min(this.minX, other.minX)
        val y = min(this.minY, other.minY)
        val width = max(this.maxX, other.maxX) - x
        val height = max(this.maxY, other.maxY) - y
        return FractionRect(x, y, width, height)
    }


    override fun intersection(other: FractionRectBaseType): FractionRectBaseType?
            = intersection(other, tolerance = -defaultFractionCalculationTolerance)


    override fun intersection(other: FractionRectBaseType, tolerance: Tolerance): FractionRectBaseType? {
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


    override fun offset(xOffset: Fraction, yOffset: Fraction): FractionRect
            = FractionRect(x = x + xOffset, y = y + yOffset, width = width, height = height)


    override fun copy(newOrigin: ComputablePoint<Fraction>, newSize: ComputableSize<Fraction>): FractionRect
            = FractionRect(newOrigin, newSize)


    override fun copy(newX: Fraction, newY: Fraction, newWidth: Fraction, newHeight: Fraction): FractionRect
            = FractionRect(newX, newY, newWidth, newHeight)


    override fun copyWithExtremes(newMinX: Fraction, newMinY: Fraction, newMaxX: Fraction, newMaxY: Fraction)
            = FractionRect(origin = FractionPoint(x = newMinX, y = newMinY),
                          size = FractionSize(width = newMaxX - newMinX, height = newMaxY - newMinY))


    override fun expanded(toInclude: ComputablePoint<Fraction>): FractionRect = super.expanded(toInclude).fractionValue


    override fun equals(other: FractionRectBaseType, tolerance: Tolerance): Boolean
            = origin.equals(other.origin, tolerance = tolerance)


    inline val fractionValue: FractionRect get() = this


    override fun forEach(scanningApproach: RectangleScanningApproach,
                         iterator: (coordinate: ComputablePoint<Fraction>, didRollOver: Boolean) -> Unit) =
            rectangularIteratorTemplate(scanningApproach,
                                        yIterator = minY.roundedIntegerValue until maxY.roundedIntegerValue,
                                        xIterator = minX.roundedIntegerValue until maxX.roundedIntegerValue,
                                        pointGenerator = ::FractionPoint,
                                        iterator = iterator)
}
/** A [Rect] that uses [Float64]s */
typealias Float64Rect = FractionRect
/** A [Rect] that uses native floating-point numbers */
typealias FloatRect = FractionRect


/** Returns this rectangle as a [FractionRect] */
val AnyRect.fractionValue: FractionRect
    get() = this as? FractionRect
            ?: FractionRect(x = x.fractionValue, y = y.fractionValue, width = width.fractionValue, height = height.fractionValue)
/** Creates a new [FractionRect] from this AWT Rectangle */


infix operator fun FractionRect.times(size: FractionSize): FractionRect {
    return FractionRect(
            x = x * size.width,
            y = y * size.height,
            width = width * size.width,
            height = height * size.height
    )
}
