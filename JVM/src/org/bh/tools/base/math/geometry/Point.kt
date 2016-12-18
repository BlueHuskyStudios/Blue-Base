@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Float64
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.math.*
import java.awt.geom.Point2D

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A point which uses a number of any type
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
open class Point<out NumberType : Number>(val x: NumberType, val y: NumberType) : Cloneable {
    constructor(from: Point<NumberType>) : this(from.x, from.y)

    companion object {
        val zero: Point<*> = Point(0, 0)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    val stringValue get() = toString()

    override public fun clone(): Point<NumberType> {
        return Point(x, y)
    }
}

typealias Coordinate<NumberType> = Point<NumberType>

val <NumberType : Number> Point<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair(x, y)
val <NumberType : Number> Point<NumberType>.integerValue: BHIntPoint get() = if (this is BHIntPoint) this else BHIntPoint(x.integerValue, y.integerValue)
val <NumberType : Number> Point<NumberType>.floatValue: BHFloatPoint get() = if (this is BHFloatPoint) this else BHFloatPoint(x.floatValue, y.floatValue)


// MARK: - Computations

abstract class ComputablePoint<out NumberType : Number>(x: NumberType, y: NumberType) : Point<NumberType>(x, y) {

    abstract infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): Point<NumberType>

    abstract infix operator fun <OtherType : Number> plus(rhs: OtherType): Point<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: OtherType): Point<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: OtherType): Point<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: OtherType): Point<NumberType>

    abstract infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Point<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Point<NumberType>
}

private class PointOperatorUnavailableApology(
        operator: String,
        widthType: Class<*>,
        heightType: Class<*>,
        otherMainType: Class<*> = Point::class.java,
        otherTypeA: Class<*> = widthType,
        otherTypeAType: String = "x",
        otherTypeB: Class<*> = heightType,
        otherTypeBType: String = "y")
    : UnsupportedOperationException("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of points with ${widthType.simpleName} x " +
        "and ${heightType.simpleName} y combined with ${otherMainType.simpleName} having " +
        "${otherTypeA.simpleName} $otherTypeAType and ${otherTypeB.simpleName} $otherTypeBType when I wrote it.")

private fun Point<*>.apology(type: String,
                             otherMainType: Class<*> = Point::class.java,
                             otherTypeA: Class<*> = x::class.java,
                             otherTypeAType: String = "x",
                             otherTypeB: Class<*> = y::class.java,
                             otherTypeBType: String = "y"): PointOperatorUnavailableApology
        = PointOperatorUnavailableApology(type, x.javaClass, y.javaClass,
        otherMainType, otherTypeA, otherTypeAType, otherTypeB, otherTypeBType)

class Int64Point(x: Int64, y: Int64) : ComputablePoint<Int64>(x, y) {
    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): Int64Point = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): Int64Point = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): Int64Point = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): Int64Point = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): Int64Point = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): Int64Point = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): Int64Point = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): Int64Point = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x + rhs.first.integerValue, y + rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x + rhs.first.floatValue).clampedIntegerValue,
                        (y + rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x - rhs.first.integerValue, y - rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x - rhs.first.floatValue).clampedIntegerValue,
                        (y - rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x * rhs.first.integerValue, y * rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x * rhs.first.floatValue).clampedIntegerValue,
                        (y * rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x / rhs.first.integerValue, y / rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x / rhs.first.floatValue).clampedIntegerValue,
                        (y / rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    companion object
}
typealias BHIntPoint = Int64Point
typealias IntPoint = BHIntPoint

class Float64Point(x: Float64, y: Float64) : ComputablePoint<Float64>(x, y) {
    constructor(x: Int64, y: Int64) : this(x.floatValue, y.floatValue)
    constructor(awtValue: Point2D) : this(awtValue.x, awtValue.y)

    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): Float64Point = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): Float64Point = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): Float64Point = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): Float64Point = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): Float64Point = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): Float64Point = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): Float64Point = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): Float64Point = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Float64Point =
            (if (rhs.first.isNativeInteger) {
                Float64Point(x + rhs.first.integerValue, y + rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Float64Point((x + rhs.first.floatValue).clampedIntegerValue,
                        (y + rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Float64Point =
            (if (rhs.first.isNativeInteger) {
                Float64Point(x - rhs.first.integerValue, y - rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Float64Point((x - rhs.first.floatValue).clampedIntegerValue,
                        (y - rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Float64Point =
            (if (rhs.first.isNativeInteger) {
                Float64Point(x * rhs.first.integerValue, y * rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Float64Point((x * rhs.first.floatValue).clampedIntegerValue,
                        (y * rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Float64Point =
            (if (rhs.first.isNativeInteger) {
                Float64Point(x / rhs.first.integerValue, y / rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                Float64Point((x / rhs.first.floatValue).clampedIntegerValue,
                        (y / rhs.second.floatValue).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })
}
typealias BHFloatPoint = Float64Point
typealias FloatPoint = BHFloatPoint


//infix operator fun IntPoint.times(rhs: IntPoint): IntPoint
//        = IntPoint(x * rhs.x, y * rhs.y)
//infix operator fun IntPoint.times(rhs: IntSize): IntPoint
//        = IntPoint(x * rhs.width, y * rhs.height)
//infix operator fun IntPoint.times(rhs: Int): IntPoint
//        = IntPoint(x * rhs, y * rhs)
