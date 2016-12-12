package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.BHInt
import org.bh.tools.base.abstraction.Int64
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
open class Size<out NumberType>(val width: NumberType, val height: NumberType) where NumberType : Number

val <NumberType : Number> Size<NumberType>.pairValue: Pair<NumberType, NumberType> get() = Pair(width, height)

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


class Int64Size(width: Int64, height: Int64) : ComputableSize<Int64>(width, height) {
    override infix operator fun <OtherType : Number> plus(rhs: Size<OtherType>): Int64Size = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> minus(rhs: Size<OtherType>): Int64Size = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> times(rhs: Size<OtherType>): Int64Size = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> div(rhs: Size<OtherType>): Int64Size = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): Int64Size = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): Int64Size = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): Int64Size = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): Int64Size = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): Int64Size =
            (if (rhs.first.isNativeInteger) {
                Int64Size(width + (rhs.first.integerValue), height + (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                Int64Size((width + (rhs.first.floatValue)).clampedIntegerValue,
                        (height + (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): Int64Size =
            (if (rhs.first.isNativeInteger) {
                Int64Size(width - (rhs.first.integerValue), height - (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                Int64Size((width - (rhs.first.floatValue)).clampedIntegerValue,
                        (height - (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): Int64Size =
            (if (rhs.first.isNativeInteger) {
                Int64Size(width * (rhs.first.integerValue), height * (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                Int64Size((width * (rhs.first.floatValue)).clampedIntegerValue,
                        (height * (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): Int64Size =
            (if (rhs.first.isNativeInteger) {
                Int64Size(width / (rhs.first.integerValue), height / (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                Int64Size((width / (rhs.first.floatValue)).clampedIntegerValue,
                        (height / (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    companion object
}
typealias BHIntSize = Int64Size
typealias IntSize = BHIntSize

val java.awt.Dimension.sizeValue: IntSize get() = IntSize(width = width.integerValue, height = height.integerValue)
val IntSize.awtValue: java.awt.Dimension get() = java.awt.Dimension(width.int32Value, height.int32Value)



class FloatSize(width: BHFloat, height: BHFloat) : ComputableSize<BHFloat>(width, height) {
    constructor(width: BHInt, height: BHInt) : this(width.floatValue, height.floatValue)

    override infix operator fun <OtherType : Number> plus(rhs: Size<OtherType>): FloatSize = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> minus(rhs: Size<OtherType>): FloatSize = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> times(rhs: Size<OtherType>): FloatSize = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType : Number> div(rhs: Size<OtherType>): FloatSize = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType : Number> plus(rhs: OtherType): FloatSize = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> minus(rhs: OtherType): FloatSize = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> times(rhs: OtherType): FloatSize = times(Pair(rhs, rhs))
    override infix operator fun <OtherType : Number> div(rhs: OtherType): FloatSize = div(Pair(rhs, rhs))


    override infix operator fun <OtherType : Number> plus(rhs: Pair<OtherType, OtherType>): FloatSize =
            (if (rhs.first.isNativeInteger) {
                FloatSize(width + (rhs.first.integerValue), height + (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FloatSize((width + (rhs.first.floatValue)).clampedIntegerValue,
                        (height + (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("addition",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> minus(rhs: Pair<OtherType, OtherType>): FloatSize =
            (if (rhs.first.isNativeInteger) {
                FloatSize(width - (rhs.first.integerValue), height - (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FloatSize((width - (rhs.first.floatValue)).clampedIntegerValue,
                        (height - (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> times(rhs: Pair<OtherType, OtherType>): FloatSize =
            (if (rhs.first.isNativeInteger) {
                FloatSize(width * (rhs.first.integerValue), height * (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FloatSize((width * (rhs.first.floatValue)).clampedIntegerValue,
                        (height * (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })

    override infix operator fun <OtherType : Number> div(rhs: Pair<OtherType, OtherType>): FloatSize =
            (if (rhs.first.isNativeInteger) {
                FloatSize(width / (rhs.first.integerValue), height / (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                FloatSize((width / (rhs.first.floatValue)).clampedIntegerValue,
                        (height / (rhs.second.floatValue)).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class.java,
                        otherTypeA = rhs.first::class.java,
                        otherTypeB = rhs.second::class.java)
            })
}
typealias Float64Size = FloatSize
typealias BHFloatSize = Float64Size

val FloatSize.integerValue: IntSize get() = IntSize(width.integerValue, height.integerValue)



val <NumberType : Number> Size<NumberType>.floatValue: FloatSize get() = FloatSize(width.floatValue, height.floatValue)


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
        x = (random.nextDouble() * (width.floatValue + 1).float64Value) as NumberType
        y = (random.nextDouble() * (height.floatValue + 1).float64Value) as NumberType
    } else {
        print("Type not supported: ${width::class} x ${width::class}")
        x = width
        y = height
    }

    return Point(x, y)
}
