@file:Suppress("unused")

package org.bh.tools.base.struct.coord

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NumberType
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.clampedIntegerValue
import org.bh.tools.base.math.isNativeFraction
import org.bh.tools.base.math.isNativeInteger

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

val <NumberType : Number> Point<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair<NumberType, NumberType>(x, y)

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

typealias Coordinate<NumberType> = Point<NumberType>

class Int64Point(x: Int64, y: Int64) : ComputablePoint<Int64>(x, y) {
    private val appology: PointOperatorUnavailableApology by lazy {
        PointOperatorUnavailableApology("division", x.javaClass, y.javaClass)
    }

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
                throw appology
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x - (rhs.first as BHInt), y - (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x - (rhs.first as BHFloat)).clampedIntegerValue,
                        (y - (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw appology
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x * (rhs.first as BHInt), y * (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x * (rhs.first as BHFloat)).clampedIntegerValue,
                        (y * (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw appology
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Int64Point =
            (if (rhs.first.isNativeInteger) {
                Int64Point(x / (rhs.first as BHInt), y / (rhs.second as BHInt))
            } else if (rhs.first.isNativeFraction) {
                Int64Point((x / (rhs.first as BHFloat)).clampedIntegerValue,
                        (y / (rhs.second as BHFloat)).clampedIntegerValue)
            } else {
                throw appology
            })
}

class Float64Point(x: Float64, y: Float64) : Point<Float64>(x, y)


// Operators

private class PointOperatorUnavailableApology(
        operator: String,
        widthType: Class<Number>,
        heightType: Class<Number>,
        otherMainType: Class<*> = Point::class.java,
        otherTypeA: Class<Number> = widthType,
        otherTypeAType: String = "width",
        otherTypeB: Class<Number> = heightType,
        otherTypeBType: String = "height")
: UnsupportedOperationException("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of points with ${widthType.simpleName} width " +
        "and ${heightType.simpleName} height combined with ${otherMainType.simpleName} having " +
        "${otherTypeA.simpleName} $otherTypeAType and ${otherTypeB.simpleName} $otherTypeBType when I wrote it.")


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Point<NumberType>.div(rhs: Point<NumberType>): Point<NumberType> {
    val appology: PointOperatorUnavailableApology by lazy {
        PointOperatorUnavailableApology("division", x.javaClass, y.javaClass)
    }
    try {
        return (if (x.isNativeInteger) {
            val thisX = (x as BHInt)
            val thisY = (y as BHInt)
            if (rhs.x.isNativeInteger) {
                val rhsX = (rhs.x as BHInt)
                val rhsY = (rhs.y as BHInt)
                Point(thisX / rhsX, thisY / rhsY)
            } else if (rhs.x.isNativeFraction) {
                val rhsX = (rhs.x as BHFloat)
                val rhsY = (rhs.y as BHFloat)
                Point(thisX / rhsX, thisY / rhsY)
            } else {
                throw appology
            }
        } else if (x.isNativeFraction) {
            val thisX = (x as BHFloat)
            val thisY = (y as BHFloat)
            if (rhs.x.isNativeInteger) {
                val rhsX = (rhs.x as BHInt)
                val rhsY = (rhs.y as BHInt)
                Point(thisX / rhsX, thisY / rhsY)
            } else if (rhs.x.isNativeFraction) {
                val rhsX = (rhs.x as BHFloat)
                val rhsY = (rhs.y as BHFloat)
                Point(thisX / rhsX, thisY / rhsY)
            } else {
                throw appology
            }
        } else {
            throw appology
        }) as Point<NumberType>
    } catch (t: Throwable) {
        throw appology
    }
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Point<NumberType>.div(rhs: NumberType): Point<NumberType> {
    val appology: PointOperatorUnavailableApology by lazy {
        PointOperatorUnavailableApology("division", x.javaClass, y.javaClass)
    }
    try {
        return (if (x.isNativeInteger) {
            val thisX = (x as BHInt)
            val thisY = (y as BHInt)
            if (rhs.isNativeInteger) {
                val rhsInt = (rhs as BHInt)
                Point(thisX / rhsInt, thisY / rhsInt)
            } else if (rhs.isNativeFraction) {
                val rhsFloat = (rhs as BHFloat)
                Point(thisX / rhsFloat, thisY / rhsFloat)
            } else {
                throw appology
            }
        } else if (x.isNativeFraction) {
            val thisX = (x as BHFloat)
            val thisY = (y as BHFloat)
            if (rhs.isNativeInteger) {
                val rhsInt = (rhs as BHInt)
                Point(thisX / rhsInt, thisY / rhsInt)
            } else if (rhs.isNativeFraction) {
                val rhsFloat = (rhs as BHFloat)
                Point(thisX / rhsFloat, thisY / rhsFloat)
            } else {
                throw appology
            }
        } else {
            throw appology
        }) as Point<NumberType>
    } catch (t: Throwable) {
        throw appology
    }
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Point<NumberType>.times(rhs: Point<NumberType>): Point<NumberType> {
    val appology: PointOperatorUnavailableApology by lazy {
        PointOperatorUnavailableApology("division", x.javaClass, y.javaClass)
    }
    try {
        return (if (x.isNativeInteger) {
            val thisX = (x as BHInt)
            val thisY = (y as BHInt)
            if (rhs.x.isNativeInteger) {
                val rhsX = (rhs.x as BHInt)
                val rhsY = (rhs.y as BHInt)
                Point(thisX * rhsX, thisY * rhsY)
            } else if (rhs.x.isNativeFraction) {
                val rhsX = (rhs.x as BHFloat)
                val rhsY = (rhs.y as BHFloat)
                Point(thisX * rhsX, thisY * rhsY)
            } else {
                throw appology
            }
        } else if (x.isNativeFraction) {
            val thisX = (x as BHFloat)
            val thisY = (y as BHFloat)
            if (rhs.x.isNativeInteger) {
                val rhsX = (rhs.x as BHInt)
                val rhsY = (rhs.y as BHInt)
                Point(thisX * rhsX, thisY * rhsY)
            } else if (rhs.x.isNativeFraction) {
                val rhsX = (rhs.x as BHFloat)
                val rhsY = (rhs.y as BHFloat)
                Point(thisX * rhsX, thisY * rhsY)
            } else {
                throw appology
            }
        } else {
            throw appology
        }) as Point<NumberType>
    } catch (t: Throwable) {
        throw appology
    }
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Point<NumberType>.times(rhs: NumberType): Point<NumberType> {
    return (if (x is Int8 && y is Int8 && rhs is Int8 && rhs is Int8) {
        Int8Point((x * rhs).toByte(), (y * rhs).toByte())
    } else if (x is Int16 && y is Int16 && rhs is Int16 && rhs is Int16) {
        Int16Point((x * rhs).toShort(), (y * rhs).toShort())
    } else if (x is Int32 && y is Int32 && rhs is Int32 && rhs is Int32) {
        Int32Point(x * rhs, y * rhs)
    } else if (x is Int64 && y is Int64 && rhs is Int64 && rhs is Int64) {
        Int64Point(x * rhs, y * rhs)
    } else if (x is Float32 && y is Float32 && rhs is Float32 && rhs is Float32) {
        Float32Point(x * rhs, y * rhs)
    } else if (x is Float64 && y is Float64 && rhs is Float64 && rhs is Float64) {
        Float64Point(x * rhs, y * rhs)
    } else {
        throw PointOperatorUnavailableApology("multiplication", x.javaClass, y.javaClass)
    }) as Point<NumberType>
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Point<NumberType>.times(rhs: Size<NumberType>): Point<NumberType> {
    return (if (x is Int8 && y is Int8 && rhs.width is Int8 && rhs.height is Int8) {
        Int8Point((x * rhs.width).toByte(), (y * rhs.height).toByte())
    } else if (x is Int16 && y is Int16 && rhs.width is Int16 && rhs.height is Int16) {
        Int16Point((x * rhs.width).toShort(), (y * rhs.height).toShort())
    } else if (x is Int32 && y is Int32 && rhs.width is Int32 && rhs.height is Int32) {
        Int32Point(x * rhs.width, y * rhs.height)
    } else if (x is Int64 && y is Int64 && rhs.width is Int64 && rhs.height is Int64) {
        Int64Point(x * rhs.width, y * rhs.height)
    } else if (x is Float32 && y is Float32 && rhs.width is Float32 && rhs.height is Float32) {
        Float32Point(x * rhs.width, y * rhs.height)
    } else if (x is Float64 && y is Float64 && rhs.width is Float64 && rhs.height is Float64) {
        Float64Point(x * rhs.width, y * rhs.height)
    } else {
        throw PointOperatorUnavailableApology("multiplication", x.javaClass, y.javaClass)
    }) as Point<NumberType>
}


val <NumberType : Number> Point<NumberType>.floatValue: FloatPoint get() {
    return (
            if (x is Int8 && y is Int8) FloatPoint(x.floatValue, y.floatValue)
            else if (x is Int16 && y is Int16) FloatPoint(x.floatValue, y.floatValue)
            else if (x is Int32 && y is Int32) FloatPoint(x.floatValue, y.floatValue)
            else if (x is Int64 && y is Int64) FloatPoint(x.floatValue, y.floatValue)
            else if (x is Float32 && y is Float32) FloatPoint(x.floatValue, y.floatValue)
            else if (x is Float64 && y is Float64) FloatPoint(x.floatValue, y.floatValue)
            else throw PointOperatorUnavailableApology("multiplication", x.javaClass, y.javaClass)
            )
}


//infix operator fun IntPoint.times(rhs: IntPoint): IntPoint
//        = IntPoint(x * rhs.x, y * rhs.y)
//infix operator fun IntPoint.times(rhs: IntSize): IntPoint
//        = IntPoint(x * rhs.width, y * rhs.height)
//infix operator fun IntPoint.times(rhs: Int): IntPoint
//        = IntPoint(x * rhs, y * rhs)
