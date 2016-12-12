package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Float64
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.math.floatValue
import org.bh.tools.base.math.int32Value
import org.bh.tools.base.math.integerValue
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
open class Rect<out NumberType : Number>(val origin: Point<NumberType>, val size: Size<NumberType>) {

    companion object {
        val zero: Rect<*> = Rect(0, 0, 0, 0)
    }

    constructor(x: NumberType, y: NumberType, width: NumberType, height: NumberType) : this(Point(x, y), Size(width, height))

    val x get() = this.origin.x
    val y get() = this.origin.y
    val width get() = this.size.width
    val height get() = this.size.height

    override fun toString(): String {
        return "{origin: $origin, size: $size}"
    }

    val stringValue get() = toString()
}


abstract class ComputableRect<NumberType : Number>(origin: Point<NumberType>, size: Size<NumberType>) : Rect<NumberType>(origin, size) {
    abstract val minX: NumberType
    abstract val midX: NumberType
    abstract val maxX: NumberType

    abstract val minY: NumberType
    abstract val midY: NumberType
    abstract val maxY: NumberType

    val minXminY: Point<NumberType> get() = Point(minX, minY)
    val minXmidY: Point<NumberType> get() = Point(minX, midY)
    val minXmaxY: Point<NumberType> get() = Point(minX, maxY)

    val midXminY: Point<NumberType> get() = Point(midX, minY)
    val midXmidY: Point<NumberType> get() = Point(midX, midY)
    val midXmaxY: Point<NumberType> get() = Point(midX, maxY)

    val maxXminY: Point<NumberType> get() = Point(maxX, minY)
    val maxXmidY: Point<NumberType> get() = Point(maxX, midY)
    val maxXmaxY: Point<NumberType> get() = Point(maxX, maxY)

    abstract fun contains(other: ComputableRect<NumberType>): Boolean
}


class Int64Rect(origin: Point<Int64>, size: Size<Int64>) : ComputableRect<Int64>(origin, size) {

    constructor(x: Int64, y: Int64, width: Int64, height: Int64) : this(Point(x, y), Size(width, height))

    override val minX: Int64 get() = if (width < 0) x + width else x
    override val midX: Int64 get() = (minX + maxX) / 2
    override val maxX: Int64 get() = if (width < 0) x else x + width

    override val minY: Int64 get() = if (height < 0) y + height else y
    override val midY: Int64 get() = (minY + maxY) / 2
    override val maxY: Int64 get() = if (height < 0) y else y + height

    val awtValue: Rectangle get() = Rectangle(x.int32Value, y.int32Value, width.int32Value, height.int32Value)

    override fun contains(other: ComputableRect<Int64>): Boolean
            = this.minX <= x
            && this.minY <= y
            && this.maxX >= x
            && this.maxY >= y
}
typealias BHIntRect = Int64Rect
typealias IntRect = BHIntRect

val Rect<*>.intValue: IntRect get() = IntRect(x = x.integerValue, y = y.integerValue, width = width.integerValue, height = height.integerValue)


class Float64Rect(origin: Point<Float64>, size: Size<Float64>) : ComputableRect<Float64>(origin, size) {

    constructor(x: Float64, y: Float64, width: Float64, height: Float64) : this(Point(x, y), Size(width, height))

    constructor(awtValue: Rectangle2D) : this(awtValue.x, awtValue.y, awtValue.width, awtValue.height)

    override val minX: Float64 get() = if (width < 0) x + width else x
    override val midX: Float64 get() = (minX + maxX) / 2
    override val maxX: Float64 get() = if (width < 0) x else x + width

    override val minY: Float64 get() = if (height < 0) y + height else y
    override val midY: Float64 get() = (minY + maxY) / 2
    override val maxY: Float64 get() = if (height < 0) y else y + height

    val awtValue: Rectangle2D get() = Rectangle2D.Double(x, y, width, height)

    override fun contains(other: ComputableRect<Float64>): Boolean
            = this.minX <= x
            && this.minY <= y
            && this.maxX >= x
            && this.maxY >= y
}
typealias BHFloatRect = Float64Rect
typealias FloatRect = BHFloatRect

val Rect<*>.floatValue: FloatRect get() = FloatRect(x = x.floatValue, y = y.floatValue, width = width.floatValue, height = height.floatValue)
