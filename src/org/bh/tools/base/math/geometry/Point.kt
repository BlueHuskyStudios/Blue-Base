@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.basics.Cloneable
import org.bh.tools.base.func.*
import org.bh.tools.base.math.*
import org.bh.tools.base.math.RoundingDirection.*
import kotlin.reflect.*


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A point which uses a number of any type
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
open class Point<out NumberType : Number>(val x: NumberType, val y: NumberType) : Cloneable<Point<NumberType>> {
    constructor(from: Point<NumberType>) : this(from.x, from.y)

    companion object {
        val zero: Point<*> = Point(0, 0)
    }


    val stringValue get() = toString()


    override fun toString(): String {
        return "($x, $y)"
    }


    override fun clone(): Point<NumberType> {
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

    fun toTuple() = tupleValue
    val tupleValue by lazy { tuple(x, y) }


    operator fun component1() = x
    operator fun component2() = y
}
/**
 * Any kind of point, of any subclass, using any kind of number
 */
typealias AnyPoint = Point<*>



typealias Coordinate<NumberType> = Point<NumberType>



val Coordinate<*>.row get() = y
val Coordinate<*>.column get() = x



val <NumberType : Number> Point<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair(x, y)

val <NumberType : Number> Point<NumberType>.integerValue: IntegerPoint get()
    = this as? IntegerPoint ?: IntegerPoint(x.integerValue, y.integerValue)

fun <NumberType : Number> Point<NumberType>.integerValue(rounding: RoundingDirection = RoundingDirection.default): IntegerPoint
        = this as? IntegerPoint
        ?: IntegerPoint(x.integerValue(rounding), y.integerValue(rounding))

val <NumberType : Number> Point<NumberType>.fractionValue: FractionPoint get() = this as? FractionPoint ?: FractionPoint(x.fractionValue, y.fractionValue)



// MARK: - Computations

abstract class ComputablePoint
    <NumberType>
    (x: NumberType, y: NumberType)
    : Point<NumberType>(x, y),
        TolerableEquality<ComputablePoint<NumberType>>
    where NumberType : Number,
        NumberType: Comparable<NumberType> {

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
     * @param other     The other point to compare
     * @param tolerance _optional_ - The distance on either axis by which the other point must be away from this one
     *                  before they are considered unequal
     *
     * @return `true` iff the two points are equal within the given tolerance
     */
    abstract override fun equals(other: ComputablePoint<NumberType>, tolerance: Tolerance): Boolean


    /**
     * Creates a copy of this line segment, optionally changing the values
     */
    abstract fun copy(x: NumberType = this.x, y: NumberType = this.y): ComputablePoint<NumberType>
}



private class PointOperatorUnavailableApology(
        operator: String,
        widthType: KClass<*>,
        heightType: KClass<*>,
        otherMainType: KClass<*> = Point::class,
        otherTypeA: KClass<*> = widthType,
        otherTypeAType: String = "x",
        otherTypeB: KClass<*> = heightType,
        otherTypeBType: String = "y")
    : UnsupportedOperationException("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of points with ${widthType.simpleName} x " +
        "and ${heightType.simpleName} y combined with ${otherMainType.simpleName} having " +
        "${otherTypeA.simpleName} $otherTypeAType and ${otherTypeB.simpleName} $otherTypeBType when I wrote it.")


private fun Point<*>.apology(type: String,
                             otherMainType: KClass<*> = Point::class,
                             otherTypeA: KClass<*> = x::class,
                             otherTypeAType: String = "x",
                             otherTypeB: KClass<*> = y::class,
                             otherTypeBType: String = "y"): PointOperatorUnavailableApology
        = PointOperatorUnavailableApology(type, x::class, y::class,
        otherMainType, otherTypeA, otherTypeAType, otherTypeB, otherTypeBType)



open class IntegerPoint(x: Integer, y: Integer) : ComputablePoint<Integer>(x, y) {
    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>): IntegerPoint = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>): IntegerPoint = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>): IntegerPoint = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>): IntegerPoint = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): IntegerPoint = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): IntegerPoint = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): IntegerPoint = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): IntegerPoint = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            when {
                rhs.first.isNativeInteger -> IntegerPoint(x + rhs.first.integerValue, y + rhs.second.integerValue)
                rhs.first.isNativeFraction -> IntegerPoint((x + rhs.first.fractionValue).clampedIntegerValue,
                                                           (y + rhs.second.fractionValue).clampedIntegerValue)
                else -> throw apology("addition",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            when {
                rhs.first.isNativeInteger -> IntegerPoint(x - rhs.first.integerValue, y - rhs.second.integerValue)
                rhs.first.isNativeFraction -> IntegerPoint((x - rhs.first.fractionValue).clampedIntegerValue,
                                                           (y - rhs.second.fractionValue).clampedIntegerValue)
                else -> throw apology("subtraction",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            when {
                rhs.first.isNativeInteger -> IntegerPoint(x * rhs.first.integerValue, y * rhs.second.integerValue)
                rhs.first.isNativeFraction -> IntegerPoint((x * rhs.first.fractionValue).clampedIntegerValue,
                                                           (y * rhs.second.fractionValue).clampedIntegerValue)
                else -> throw apology("multiplication",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): IntegerPoint =
            when {
                rhs.first.isNativeInteger -> IntegerPoint(x / rhs.first.integerValue, y / rhs.second.integerValue)
                rhs.first.isNativeFraction -> IntegerPoint((x / rhs.first.fractionValue).clampedIntegerValue,
                                                           (y / rhs.second.fractionValue).clampedIntegerValue)
                else -> throw apology("division",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override fun equals(rhs: ComputablePoint<Integer>): Boolean = equals(rhs, tolerance = defaultCalculationTolerance)


    override fun equals(other: ComputablePoint<Integer>, tolerance: Tolerance): Boolean =
            x.equals(other.x, tolerance = tolerance)
            && y.equals(other.y, tolerance = tolerance)


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
    constructor(x: Integer, y: Integer) : this(x.fractionValue, y.fractionValue)
    constructor(x: Int32, y: Int32) : this(x.fractionValue, y.fractionValue)

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
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x - rhs.first.integerValue, y - rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x - rhs.first.fractionValue),
                        (y - rhs.second.fractionValue))
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x * rhs.first.integerValue, y * rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x * rhs.first.fractionValue),
                        (y * rhs.second.fractionValue))
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): FractionPoint =
            if (rhs.first.isNativeInteger) {
                FractionPoint(x / rhs.first.integerValue, y / rhs.second.integerValue)
            } else if (rhs.first.isNativeFraction) {
                FractionPoint((x / rhs.first.fractionValue),
                        (y / rhs.second.fractionValue))
            } else {
                throw apology("division",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            }


    override fun equals(rhs: ComputablePoint<Fraction>): Boolean = equals(rhs, tolerance = defaultFractionCalculationTolerance)


    override fun equals(other: ComputablePoint<Fraction>, tolerance: Tolerance): Boolean =
            x.equals(other.x, tolerance = tolerance)
            && y.equals(other.y, tolerance = tolerance)


    override fun copy(x: Fraction, y: Fraction): FractionPoint {
        return FractionPoint(x = x, y = y)
    }



    companion object {
        val zero = FractionPoint(0, 0)
    }
}
typealias Float64Point = FractionPoint
typealias FloatPoint = FractionPoint






class Int8Point(x: Int8, y: Int8) : ComputablePoint<Int8>(x, y) {

    constructor(x: Int32, y: Int32) : this(x = x.int8Value, y = y.int8Value)


    override infix operator fun <OtherType : Number> plus(rhs: Point<OtherType>) = plus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> minus(rhs: Point<OtherType>) = minus(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> times(rhs: Point<OtherType>) = times(Pair(rhs.x, rhs.y))
    override infix operator fun <OtherType : Number> div(rhs: Point<OtherType>) = div(Pair(rhs.x, rhs.y))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType) = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType) = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType) = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType) = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Int8Point =
            when {
                rhs.first.isNativeInteger -> Int8Point((x + rhs.first.int8Value).int8Value,
                                                       (y + rhs.second.int8Value).int8Value)
                rhs.first.isNativeFraction -> Int8Point((x + rhs.first.fractionValue).clampedInt8Value,
                                                           (y + rhs.second.fractionValue).clampedInt8Value)
                else -> throw apology("addition",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Int8Point =
            when {
                rhs.first.isNativeInteger -> Int8Point((x - rhs.first.int8Value).int8Value,
                                                       (y - rhs.second.int8Value).int8Value)
                rhs.first.isNativeFraction -> Int8Point((x - rhs.first.fractionValue).clampedInt8Value,
                                                           (y - rhs.second.fractionValue).clampedInt8Value)
                else -> throw apology("subtraction",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Int8Point =
            when {
                rhs.first.isNativeInteger -> Int8Point((x * rhs.first.int8Value).int8Value,
                                                       (y * rhs.second.int8Value).int8Value)
                rhs.first.isNativeFraction -> Int8Point((x * rhs.first.fractionValue).clampedInt8Value,
                                                           (y * rhs.second.fractionValue).clampedInt8Value)
                else -> throw apology("multiplication",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Int8Point =
            when {
                rhs.first.isNativeInteger -> Int8Point((x / rhs.first.int8Value).int8Value,
                                                       (y / rhs.second.int8Value).int8Value)
                rhs.first.isNativeFraction -> Int8Point((x / rhs.first.fractionValue).clampedInt8Value,
                                                           (y / rhs.second.fractionValue).clampedInt8Value)
                else -> throw apology("division",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override fun equals(rhs: ComputablePoint<Int8>): Boolean = equals(rhs, tolerance = defaultCalculationTolerance)


    override fun equals(other: ComputablePoint<Int8>, tolerance: Tolerance): Boolean =
            x.equals(other.x, tolerance = tolerance)
            && y.equals(other.y, tolerance = tolerance)


    override fun copy(x: Int8, y: Int8): Int8Point {
        return Int8Point(x = x, y = y)
    }


    companion object {
        val zero = Int8Point(0, 0)
    }
}
