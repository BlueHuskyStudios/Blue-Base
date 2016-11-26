@file:Suppress("unused")

package org.bh.tools.base.struct.coord

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
open class Point<out NumberType : Number>(val x: NumberType, val y: NumberType) {
    @Suppress("UNCHECKED_CAST") // types checked manually
    constructor(from: Point<*>) : this(from.x as NumberType, from.y as NumberType) {
//        if (from.x is Int8 && from.y is Int8) {
//            Int8Point(from.x, from.y)
//        } else if (from.x is Int16 && from.y is Int16) {
//            Int16Point(from.x, from.y)
//        } else if (from.x is Int32 && from.y is Int32) {
//            Int32Point(from.x, from.y)
//        } else if (from.x is Int64 && from.y is Int64) {
//            Int64Point(from.x, from.y)
//        }
//
//        else if (from.x is Float32 && from.y is Float32) {
//            Float32Point(from.x, from.y)
//        } else if (from.x is Float64 && from.y is Float64) {
//            Float64Point(from.x, from.y)
//        }
//
//        else {
//            throw PointOperatorUnavailableApology("multiplication", x.javaClass, y.javaClass)
//        }
    }

    companion object {
        val zero: Point<*> = Point(0, 0)
    }
}

typealias Coordinate<NumberType> = Point<NumberType>

val <NumberType : Number> Point<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair(x, y)
val <NumberType : Number> Point<NumberType>.integerValue: IntPoint get() = IntPoint(x.integerValue, y.integerValue)
val <NumberType : Number> Point<NumberType>.floatValue: FloatPoint get() = FloatPoint(x.floatValue, y.floatValue)


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
                Int64Point(x + (rhs.first as BHInt), y + (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x + (rhs.first as BHFloat)).clampedIntegerValue,
                        (y + (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x - (rhs.first as BHInt), y - (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x - (rhs.first as BHFloat)).clampedIntegerValue,
                        (y - (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x * (rhs.first as BHInt), y * (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x * (rhs.first as BHFloat)).clampedIntegerValue,
                        (y * (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x / (rhs.first as BHInt), y / (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x / (rhs.first as BHFloat)).clampedIntegerValue,
                        (y / (rhs.second as BHFloat)).clampedIntegerValue)
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

class FloatPoint(x: BHFloat, y: BHFloat) : ComputablePoint<BHFloat>(x, y) {
    constructor(x: BHInt, y: BHInt) : this(x.floatValue, y.floatValue)

    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): FloatPoint = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): FloatPoint = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): FloatPoint = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): FloatPoint = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): FloatPoint = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): FloatPoint = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): FloatPoint = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): FloatPoint = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): FloatPoint =
            (if (rhs.first.isNativeInteger) {
                FloatPoint(x + (rhs.first as BHInt), y + (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                FloatPoint((x + (rhs.first as BHFloat)).clampedIntegerValue,
                        (y + (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): FloatPoint =
            (if (rhs.first.isNativeInteger) {
                FloatPoint(x - (rhs.first as BHInt), y - (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                FloatPoint((x - (rhs.first as BHFloat)).clampedIntegerValue,
                        (y - (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): FloatPoint =
            (if (rhs.first.isNativeInteger) {
                FloatPoint(x * (rhs.first as BHInt), y * (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                FloatPoint((x * (rhs.first as BHFloat)).clampedIntegerValue,
                        (y * (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): FloatPoint =
            (if (rhs.first.isNativeInteger) {
                FloatPoint(x / (rhs.first as BHInt), y / (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                FloatPoint((x / (rhs.first as BHFloat)).clampedIntegerValue,
                        (y / (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })
}

//infix operator fun IntPoint.times(rhs: IntPoint): IntPoint
//        = IntPoint(x * rhs.x, y * rhs.y)
//infix operator fun IntPoint.times(rhs: IntSize): IntPoint
//        = IntPoint(x * rhs.width, y * rhs.height)
//infix operator fun IntPoint.times(rhs: Int): IntPoint
//        = IntPoint(x * rhs, y * rhs)
