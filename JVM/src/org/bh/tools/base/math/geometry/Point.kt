@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.abstraction.Integer
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


    val stringValue get() = toString()


    override fun toString(): String {
        return "($x, $y)"
    }


    override public fun clone(): Point<NumberType> {
        return Point(x, y)
    }


    override fun equals(other: Any?): Boolean {
        return other is Point<*>
                && x == other.x
                && y == other.y
    }


    override fun hashCode(): Int {
        return super.hashCode() xor x.hashCode() xor y.hashCode()
    }
}



typealias Coordinate<NumberType> = Point<NumberType>



val <NumberType : Number> Point<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair(x, y)
val <NumberType : Number> Point<NumberType>.integerValue: IntegerPoint get() = this as? IntegerPoint ?: IntegerPoint(x.integerValue, y.integerValue)
val <NumberType : Number> Point<NumberType>.fractionValue: FractionPoint get() = this as? FractionPoint ?: FractionPoint(x.fractionValue, y.fractionValue)



// MARK: - Computations

abstract class ComputablePoint<NumberType : Number>(x: NumberType, y: NumberType) : Point<NumberType>(x, y) {

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

    /**
     * Calls [equals] with default tolerance
     *
     * @param rhs       The other point to compare
     * @param tolerance _optional_ - The distance on either axis by which the other point must be away from this one
     *                  before they are considered unequal
     *
     * @return `true` iff the two points are equal within the given tolerance
     */
    @Suppress("KDocUnresolvedReference")
    abstract fun equals(rhs: ComputablePoint<NumberType>): Boolean


    /**
     * Determines whether this point is equal to another of the same type within a certain tolerance
     *
     * @param rhs       The other point to compare
     * @param tolerance _optional_ - The distance on either axis by which the other point must be away from this one
     *                  before they are considered unequal
     *
     * @return `true` iff the two points are equal within the given tolerance
     */
    abstract fun equals(rhs: ComputablePoint<NumberType>, tolerance: NumberType): Boolean


    /**
     * Creates a copy of this line segment, optionally changing the values
     */
    abstract fun copy(x: NumberType = this.x, y: NumberType = this.y): ComputablePoint<NumberType>
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

class IntegerPoint(x: Integer, y: Integer) : ComputablePoint<Integer>(x, y) {
    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): IntegerPoint = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): IntegerPoint = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): IntegerPoint = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): IntegerPoint = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): IntegerPoint = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): IntegerPoint = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): IntegerPoint = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): IntegerPoint = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            if (rhs.first.isNativeInteger) {
                IntegerPoint(x + rhs.first.integerValue, y + rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                IntegerPoint((x + rhs.first.fractionValue).clampedIntegerValue,
                        (y + rhs.second.fractionValue).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            if (rhs.first.isNativeInteger) {
                IntegerPoint(x - rhs.first.integerValue, y - rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                IntegerPoint((x - rhs.first.fractionValue).clampedIntegerValue,
                        (y - rhs.second.fractionValue).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            if (rhs.first.isNativeInteger) {
                IntegerPoint(x * rhs.first.integerValue, y * rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                IntegerPoint((x * rhs.first.fractionValue).clampedIntegerValue,
                        (y * rhs.second.fractionValue).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            if (rhs.first.isNativeInteger) {
                IntegerPoint(x / rhs.first.integerValue, y / rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                IntegerPoint((x / rhs.first.fractionValue).clampedIntegerValue,
                        (y / rhs.second.fractionValue).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override fun equals(rhs: ComputablePoint<Integer>): Boolean = equals(rhs, tolerance = defaultIntegerCalculationTolerance)


    override fun equals(rhs: ComputablePoint<Integer>, tolerance: Integer): Boolean =
            x.equals(rhs.x, tolerance = tolerance)
            && y.equals(rhs.y, tolerance = tolerance)


    override fun copy(x: Integer, y: Integer): IntegerPoint {
        return IntegerPoint(x = x, y = y)
    }


    companion object {
        val zero = IntegerPoint(0, 0)
    }
}
typealias Int64Point = IntegerPoint
typealias IntPoint = IntegerPoint

open class FractionPoint(x: Fraction, y: Fraction) : ComputablePoint<Fraction>(x, y) {
    constructor(x: Int64, y: Int64) : this(x.fractionValue, y.fractionValue)
    constructor(awtValue: Point2D) : this(awtValue.x, awtValue.y)

    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): FractionPoint = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): FractionPoint = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): FractionPoint = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): FractionPoint = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): FractionPoint = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): FractionPoint = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): FractionPoint = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): FractionPoint = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x + rhs.first.integerValue, y + rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x + rhs.first.fractionValue),
                        (y + rhs.second.fractionValue))
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x - rhs.first.integerValue, y - rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x - rhs.first.fractionValue),
                        (y - rhs.second.fractionValue))
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x * rhs.first.integerValue, y * rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x * rhs.first.fractionValue),
                        (y * rhs.second.fractionValue))
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x / rhs.first.integerValue, y / rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x / rhs.first.fractionValue),
                        (y / rhs.second.fractionValue))
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }


    override fun equals(rhs: ComputablePoint<Fraction>): Boolean = equals(rhs, tolerance = defaultFractionCalculationTolerance)


    override fun equals(rhs: ComputablePoint<Fraction>, tolerance: Fraction): Boolean =
            x.equals(rhs.x, tolerance = tolerance)
            && y.equals(rhs.y, tolerance = tolerance)


    override fun copy(x: Fraction, y: Fraction): FractionPoint {
        return FractionPoint(x = x, y = y)
    }



    companion object {
        val zero = FractionPoint(0, 0)
    }
}
typealias Float64Point = FractionPoint
typealias FloatPoint = FractionPoint


//infix operator fun IntPoint.times(rhs: IntPoint): IntPoint
//        = IntPoint(x * rhs.x, y * rhs.y)
//infix operator fun IntPoint.times(rhs: IntSize): IntPoint
//        = IntPoint(x * rhs.width, y * rhs.height)
//infix operator fun IntPoint.times(rhs: Int): IntPoint
//        = IntPoint(x * rhs, y * rhs)
