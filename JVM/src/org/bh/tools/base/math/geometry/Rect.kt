@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Int64
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



abstract class ComputableRect<NumberType : Number, PointType: ComputablePoint<NumberType>,
        SizeType: ComputableSize<NumberType>>(origin: PointType, size: SizeType)
    : Rect<NumberType, PointType, SizeType>(origin, size) {

    abstract val minX: NumberType
    abstract val midX: NumberType
    abstract val maxX: NumberType

    abstract val minY: NumberType
    abstract val midY: NumberType
    abstract val maxY: NumberType

    abstract val minXminY: PointType
    abstract val minXmidY: PointType
    abstract val minXmaxY: PointType

    abstract val midXminY: PointType
    abstract val midXmidY: PointType
    abstract val midXmaxY: PointType

    abstract val maxXminY: PointType
    abstract val maxXmidY: PointType
    abstract val maxXmaxY: PointType

    val isEmpty: Boolean get() = size.isEmpty

    abstract fun contains(other: ComputableRect<NumberType, PointType, SizeType>): Boolean
    abstract fun intersects(other: ComputableRect<NumberType, PointType, SizeType>): Boolean
    abstract fun intersection(other: ComputableRect<NumberType, PointType, SizeType>): ComputableRect<NumberType, PointType, SizeType>?
    abstract fun union(other: ComputableRect<NumberType, PointType, SizeType>): ComputableRect<NumberType, PointType, SizeType>
}



private typealias Int64RectBaseType = ComputableRect<Int64, ComputablePoint<Int64>, ComputableSize<Int64>>



class IntegerRect(origin: ComputablePoint<Int64>, size: ComputableSize<Int64>)
    : Int64RectBaseType(origin, size) {

    companion object {
        val zero = IntegerRect(IntegerPoint.zero, IntegerSize.zero)
    }

    constructor(x: Int64, y: Int64, width: Int64, height: Int64) : this(IntPoint(x, y), IntegerSize(width, height))

    override val minX get() = if (width < 0) x + width else x
    override val midX get() = (minX + maxX) / 2
    override val maxX get() = if (width < 0) x else x + width

    override val minY get() = if (height < 0) y + height else y
    override val midY get() = (minY + maxY) / 2
    override val maxY get() = if (height < 0) y else y + height

    override val minXminY get() = IntegerPoint(minX, minY)
    override val minXmidY get() = IntegerPoint(minX, midY)
    override val minXmaxY get() = IntegerPoint(minX, maxY)

    override val midXminY get() = IntegerPoint(midX, minY)
    override val midXmidY get() = IntegerPoint(midX, midY)
    override val midXmaxY get() = IntegerPoint(midX, maxY)

    override val maxXminY get() = IntegerPoint(maxX, minY)
    override val maxXmidY get() = IntegerPoint(maxX, midY)
    override val maxXmaxY get() = IntegerPoint(maxX, maxY)



    val awtValue: Rectangle get() = Rectangle(x.int32Value, y.int32Value, width.int32Value, height.int32Value)

    override fun contains(other: Int64RectBaseType): Boolean
            = this.minX <= x
            && this.minY <= y
            && this.maxX >= x
            && this.maxY >= y


    override fun intersects(other: Int64RectBaseType): Boolean {
        return intersection(other) != null
    }


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
        val x = org.bh.tools.base.math.min(this.minX, other.minX)
        val y = min(this.minY, other.minY)
        val width = max(this.maxX, other.maxX) - x
        val height = max(this.maxY, other.maxY) - y
        return IntegerRect(x, y, width, height)
    }


    override fun intersection(other: Int64RectBaseType): Int64RectBaseType? {
        // Adapted from https://github.com/apple/swift-corelibs-foundation/blob/87815eab0cff7d971f1fbdbfbe98729ec92dbe3d/Foundation/NSGeometry.swift#L604

        if (this.maxX <= other.minX || other.maxX <= this.minX || this.maxY <= other.minY || other.maxY <= this.minY) {
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

val AnyRect.intValue: IntegerRect get() = IntegerRect(x = x.integerValue, y = y.integerValue, width = width.integerValue, height = height.integerValue)



private typealias FractionRectBaseType = ComputableRect<Fraction, ComputablePoint<Fraction>, ComputableSize<Fraction>>



class FractionRect(origin: ComputablePoint<Fraction>, size: ComputableSize<Fraction>) : FractionRectBaseType(origin, size) {

    companion object {
        val zero = FractionRect(FractionPoint.zero, Float64Size.zero)
    }

    constructor(x: Fraction, y: Fraction, width: Fraction, height: Fraction) : this(FractionPoint(x, y), FractionSize(width, height))

    constructor(awtValue: Rectangle2D) : this(awtValue.x, awtValue.y, awtValue.width, awtValue.height)

    override val minX get() = if (width < 0) x + width else x
    override val midX get() = (minX + maxX) / 2
    override val maxX get() = if (width < 0) x else x + width

    override val minY get() = if (height < 0) y + height else y
    override val midY get() = (minY + maxY) / 2
    override val maxY get() = if (height < 0) y else y + height

    override val minXminY get() = FractionPoint(minX, minY)
    override val minXmidY get() = FractionPoint(minX, midY)
    override val minXmaxY get() = FractionPoint(minX, maxY)

    override val midXminY get() = FractionPoint(midX, minY)
    override val midXmidY get() = FractionPoint(midX, midY)
    override val midXmaxY get() = FractionPoint(midX, maxY)

    override val maxXminY get() = FractionPoint(maxX, minY)
    override val maxXmidY get() = FractionPoint(maxX, midY)
    override val maxXmaxY get() = FractionPoint(maxX, maxY)

    val awtValue: Rectangle2D get() = Rectangle2D.Double(x, y, width, height)

    override fun contains(other: FractionRectBaseType): Boolean
            = this.minX <= x
            && this.minY <= y
            && this.maxX >= x
            && this.maxY >= y


    override fun intersects(other: FractionRectBaseType): Boolean {
        return intersection(other) != null
    }


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


    override fun intersection(other: FractionRectBaseType): FractionRectBaseType? {
        if (this.maxX <= other.minX || other.maxX <= this.minX || this.maxY <= other.minY || other.maxY <= this.minY) {
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


infix operator fun FractionRect.times(size: FractionSize): FractionRect {
    return FractionRect(
            x = x * size.width,
            y = y * size.height,
            width = width * size.width,
            height = height * size.height
    )
}
