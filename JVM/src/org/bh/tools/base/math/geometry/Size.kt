package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Int64
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.math.*
import java.util.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
open class Size<out NumberType>(val width: NumberType, val height: NumberType) where NumberType : Number {

    val pairValue: Pair<NumberType, NumberType> get() = Pair(width, height)

    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            other !is Size<*> -> false
            other.height != this.height -> false
            other.width != this.width -> false
            else -> true
        }
    }

    override fun hashCode(): Int = 31 * width.hashCode() + height.hashCode()
}

typealias Dimension<NumberType> = Size<NumberType>


// MARK: - Computations

abstract class ComputableSize<out NumberType : Number>(width: NumberType, height: NumberType) : Size<NumberType>(width, height) {

    abstract infix operator fun <OtherType : Number> plus(rhs: Size<OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: Size<OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: Size<OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: Size<OtherType>): Size<NumberType>

    abstract infix operator fun <OtherType : Number> plus(rhs: OtherType): Size<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: OtherType): Size<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: OtherType): Size<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: OtherType): Size<NumberType>

    abstract infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Size<NumberType>
    abstract infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Size<NumberType>

    abstract val isEmpty: Boolean
}


// Operators

private class SizeOperatorUnavailableApology(
        operator: String,
        widthType: Class<*>,
        heightType: Class<*>,
        otherMainType: Class<*> = Size::class.java,
        otherTypeA: Class<*> = widthType,
        otherTypeAType: String = "width",
        otherTypeB: Class<*> = heightType,
        otherTypeBType: String = "height")
    : UnsupportedOperationException("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of sizes with ${widthType.simpleName} width " +
        "and ${heightType.simpleName} height combined with ${otherMainType.simpleName} having " +
        "${otherTypeA.simpleName} $otherTypeAType and ${otherTypeB.simpleName} $otherTypeBType when I wrote it.")

private fun Size<*>.apology(type: String,
                            otherMainType: Class<*> = Size::class.java,
                            otherTypeA: Class<*> = width::class.java,
                            otherTypeAType: String = "width",
                            otherTypeB: Class<*> = height::class.java,
                            otherTypeBType: String = "height"): SizeOperatorUnavailableApology
        = SizeOperatorUnavailableApology(type, width.javaClass, height.javaClass,
        otherMainType, otherTypeA, otherTypeAType, otherTypeB, otherTypeBType)


class IntegerSize(width: Int64, height: Int64) : ComputableSize<Int64>(width, height) {

    override val isEmpty: Boolean = this == zero


    override infix operator fun <OtherType : Number> plus(rhs: Size<OtherType>): IntegerSize = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> minus(rhs: Size<OtherType>): IntegerSize = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> times(rhs: Size<OtherType>): IntegerSize = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> div(rhs: Size<OtherType>): IntegerSize = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): IntegerSize = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): IntegerSize = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): IntegerSize = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): IntegerSize = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): IntegerSize =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width + (rhs.first.integerValue), height + (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width + (rhs.first.fractionValue)).clampedIntegerValue,
                        (height + (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })


    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): IntegerSize =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width - (rhs.first.integerValue), height - (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width - (rhs.first.fractionValue)).clampedIntegerValue,
                        (height - (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })


    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): IntegerSize =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width * (rhs.first.integerValue), height * (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width * (rhs.first.fractionValue)).clampedIntegerValue,
                        (height * (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })


    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): IntegerSize =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width / (rhs.first.integerValue), height / (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width / (rhs.first.fractionValue)).clampedIntegerValue,
                        (height / (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })


    companion object {
        val zero = IntegerSize(0, 0)
    }
}
typealias Int64Size = IntegerSize
typealias IntSize = IntegerSize

val java.awt.Dimension.sizeValue: IntegerSize get() = IntegerSize(width = width.integerValue, height = height.integerValue)
val IntegerSize.awtValue: java.awt.Dimension get() = java.awt.Dimension(width.int32Value, height.int32Value)



class FractionSize(width: Fraction, height: Fraction) : ComputableSize<Fraction>(width, height) {

    companion object {
        val zero = FractionSize(0, 0)
    }


    constructor(width: Integer, height: Integer) : this(width.fractionValue, height.fractionValue)


    override val isEmpty: Boolean = this == zero


    override infix operator fun <OtherType : Number> plus(rhs: Size<OtherType>): FractionSize = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> minus(rhs: Size<OtherType>): FractionSize = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> times(rhs: Size<OtherType>): FractionSize = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> div(rhs: Size<OtherType>): FractionSize = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): FractionSize = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): FractionSize = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): FractionSize = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): FractionSize = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): FractionSize =
            if (rhs.first.isNativeInteger) {
                FractionSize(width + (rhs.first.integerValue), height + (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FractionSize((width + rhs.first.fractionValue),
                        (height + rhs.second.fractionValue))
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): FractionSize =
            if (rhs.first.isNativeInteger) {
                FractionSize(width - (rhs.first.integerValue), height - (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FractionSize((width - rhs.first.fractionValue),
                        (height - rhs.second.fractionValue))
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): FractionSize =
            if (rhs.first.isNativeInteger) {
                FractionSize(width * (rhs.first.integerValue), height * (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FractionSize((width * rhs.first.fractionValue),
                        (height * rhs.second.fractionValue))
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): FractionSize =
            if (rhs.first.isNativeInteger) {
                FractionSize(width = width / (rhs.first.integerValue), height = height / (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FractionSize(width = (width / rhs.first.fractionValue),
                        height = (height / rhs.second.fractionValue))
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            }
}
typealias Float64Size = FractionSize
typealias FloatSize = FractionSize

val FractionSize.integerValue: IntegerSize get() = IntegerSize(width.integerValue, height.integerValue)



val <NumberType : Number> Size<NumberType>.fractionValue: FractionSize get() = FractionSize(width.fractionValue, height.fractionValue)


// Silliness

@Suppress("UNCHECKED_CAST") // checked transitively
val <NumberType : Number> Size<NumberType>.randomPoint: Point<NumberType> get() {
    val random = Random()
    val x: NumberType
    val y: NumberType
    if (width.isNativeInteger && height.isNativeInteger) {
        x = random.nextInt((width.integerValue + 1).int32Value) as NumberType
        y = random.nextInt((height.integerValue + 1).int32Value) as NumberType
    } else if (width.isNativeFraction && height.isNativeFraction) {
        x = (random.nextDouble() * (width.fractionValue + 1).float64Value) as NumberType
        y = (random.nextDouble() * (height.fractionValue + 1).float64Value) as NumberType
    } else {
        print("Type not supported: ${width::class} x ${width::class}")
        x = width
        y = height
    }

    return Point(x, y)
}
