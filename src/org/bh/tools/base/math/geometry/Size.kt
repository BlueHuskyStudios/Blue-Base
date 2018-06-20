@file:Suppress("unused")

package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import kotlin.reflect.*


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

    override fun toString(): String {
        return "$width × $height"
    }
}

typealias Dimension<NumberType> = Size<NumberType>


// MARK: - Computations

abstract class ComputableSize
    <NumberType>
    (width: NumberType, height: NumberType)
    : Size<NumberType>(width, height)
    where NumberType : Number,
        NumberType : Comparable<NumberType> {

    /** Indicates whether this size has a `0` area. */
    abstract val isEmpty: Boolean

    /** The smaller of the two dimensions */
    abstract val minDimension: NumberType
    /** The larger of the two dimensions */
    abstract val maxDimension: NumberType


    /** Always `0` */
    abstract val minX: NumberType
    /** Always `width / 2` */
    abstract val midX: NumberType
    /** Always `width` */
    abstract val maxX: NumberType

    /** Always `0` */
    abstract val minY: NumberType
    /** Always `height / 2` */
    abstract val midY: NumberType
    /** Always `height` */
    abstract val maxY: NumberType


    abstract val minXminY: ComputablePoint<NumberType>
    abstract val minXmidY: ComputablePoint<NumberType>
    abstract val minXmaxY: ComputablePoint<NumberType>

    abstract val midXminY: ComputablePoint<NumberType>
    abstract val midXmidY: ComputablePoint<NumberType>
    abstract val midXmaxY: ComputablePoint<NumberType>

    abstract val maxXminY: ComputablePoint<NumberType>
    abstract val maxXmidY: ComputablePoint<NumberType>
    abstract val maxXmaxY: ComputablePoint<NumberType>


    abstract infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> minus(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> times(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> div(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>

    abstract infix operator fun <OtherType> plus(rhs: OtherType): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> minus(rhs: OtherType): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> times(rhs: OtherType): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> div(rhs: OtherType): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>

    abstract infix operator fun <OtherType> plus(rhs: Pair<OtherType, OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> minus(rhs: Pair<OtherType, OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> times(rhs: Pair<OtherType, OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    abstract infix operator fun <OtherType> div(rhs: Pair<OtherType, OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
}





fun <NumberType> ComputableSize<NumberType>.contains(point: ComputablePoint<NumberType>): Boolean
    where NumberType : Number,
    NumberType : Comparable<NumberType> {
    return this.minX <= point.x
        && this.minY <= point.y
        && this.maxX >= point.x
        && this.maxY >= point.y
}



// Operators

private class SizeOperatorUnavailableApology(
        operator: String,
        widthType: KClass<*>,
        heightType: KClass<*>,
        otherMainType: KClass<*> = Size::class,
        otherTypeA: KClass<*> = widthType,
        otherTypeAType: String = "width",
        otherTypeB: KClass<*> = heightType,
        otherTypeBType: String = "height")
    : /*UnsupportedOperationException*/Throwable("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of sizes with ${widthType.simpleName} width " +
        "and ${heightType.simpleName} height combined with ${otherMainType.simpleName} having " +
        "${otherTypeA.simpleName} $otherTypeAType and ${otherTypeB.simpleName} $otherTypeBType when I wrote it.")

private fun Size<*>.apology(type: String,
                            otherMainType: KClass<*> = Size::class,
                            otherTypeA: KClass<*> = width::class,
                            otherTypeAType: String = "width",
                            otherTypeB: KClass<*> = height::class,
                            otherTypeBType: String = "height"): SizeOperatorUnavailableApology
        = SizeOperatorUnavailableApology(type, width::class, height::class,
        otherMainType, otherTypeA, otherTypeAType, otherTypeB, otherTypeBType)


class IntegerSize(width: Integer, height: Integer) : ComputableSize<Integer>(width, height) {

    companion object {
        val zero = IntegerSize(0, 0)
    }


    override val isEmpty: Boolean = this == zero


    override val minX: Integer by lazy { 0L }
    override val midX: Integer by lazy { width / 2 }
    override val maxX: Integer by lazy { width }

    override val minY: Integer by lazy { 0L }
    override val midY: Integer by lazy { height / 2 }
    override val maxY: Integer by lazy { height }


    override val minXminY: IntegerPoint by lazy { IntegerPoint(minX, minY) }
    override val minXmidY: IntegerPoint by lazy { IntegerPoint(minX, midY) }
    override val minXmaxY: IntegerPoint by lazy { IntegerPoint(minX, maxY) }

    override val midXminY: IntegerPoint by lazy { IntegerPoint(midX, minY) }
    override val midXmidY: IntegerPoint by lazy { IntegerPoint(midX, midY) }
    override val midXmaxY: IntegerPoint by lazy { IntegerPoint(midX, maxY) }

    override val maxXminY: IntegerPoint by lazy { IntegerPoint(maxX, minY) }
    override val maxXmidY: IntegerPoint by lazy { IntegerPoint(maxX, midY) }
    override val maxXmaxY: IntegerPoint by lazy { IntegerPoint(maxX, maxY) }


    constructor(width: Int32, height: Int32) : this(width = width.integerValue, height = height.integerValue)
    constructor(squareSide: Integer) : this(width = squareSide, height = squareSide)

//  abstract infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    override infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> minus(rhs: ComputableSize<OtherType>): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> times(rhs: ComputableSize<OtherType>): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> div(rhs: ComputableSize<OtherType>): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType> plus(rhs: OtherType): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType> minus(rhs: OtherType): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType> times(rhs: OtherType): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = times(Pair(rhs, rhs))
    override infix operator fun <OtherType> div(rhs: OtherType): IntegerSize where OtherType : Number, OtherType : Comparable<OtherType>
            = div(Pair(rhs, rhs))


    override infix operator fun <OtherType> plus(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            =
            when {
                rhs.first.isNativeInteger -> IntegerSize(width + (rhs.first.integerValue),
                                                         height + (rhs.second.integerValue))
                rhs.first.isNativeFraction -> IntegerSize((width + (rhs.first.fractionValue)).clampedIntegerValue,
                                                          (height + (rhs.second.fractionValue)).clampedIntegerValue)
                else -> throw apology("addition",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> minus(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width - (rhs.first.integerValue), height - (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width - (rhs.first.fractionValue)).clampedIntegerValue,
                        (height - (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("subtraction",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            })


    override infix operator fun <OtherType> times(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width * (rhs.first.integerValue), height * (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width * (rhs.first.fractionValue)).clampedIntegerValue,
                        (height * (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("multiplication",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            })


    override infix operator fun <OtherType> div(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            =
            (if (rhs.first.isNativeInteger) {
                IntegerSize(width / (rhs.first.integerValue), height / (rhs.second.integerValue))
            } else if (rhs.first.isNativeFraction) {
                IntegerSize((width / (rhs.first.fractionValue)).clampedIntegerValue,
                        (height / (rhs.second.fractionValue)).clampedIntegerValue)
            } else {
                throw apology("division",
                        otherMainType = Pair::class,
                        otherTypeA = rhs.first::class,
                        otherTypeB = rhs.second::class)
            })


    override val minDimension: Integer by lazy { min(width, height) }
    override val maxDimension: Integer by lazy { max(width, height) }
}

typealias Int64Size = IntegerSize
typealias IntSize = IntegerSize



class FractionSize(width: Fraction, height: Fraction) : ComputableSize<Fraction>(width, height) {

    companion object {
        val zero = FractionSize(0, 0)
    }


    constructor(width: Integer, height: Integer) : this(width.fractionValue, height.fractionValue)
    constructor(width: Int32, height: Int32) : this(width.fractionValue, height.fractionValue)
    constructor(squareSide: Fraction) : this(squareSide, squareSide)
    constructor(squareSide: Integer) : this(squareSide = squareSide.fractionValue)


    override val isEmpty: Boolean by lazy { this == zero }


    override val minX: Fraction by lazy { 0.0 }
    override val midX: Fraction by lazy { width / 2 }
    override val maxX: Fraction by lazy { width }

    override val minY: Fraction by lazy { 0.0 }
    override val midY: Fraction by lazy { height / 2 }
    override val maxY: Fraction by lazy { height }


    override val minXminY: FractionPoint by lazy { FractionPoint(minX, minY) }
    override val minXmidY: FractionPoint by lazy { FractionPoint(minX, midY) }
    override val minXmaxY: FractionPoint by lazy { FractionPoint(minX, maxY) }

    override val midXminY: FractionPoint by lazy { FractionPoint(midX, minY) }
    override val midXmidY: FractionPoint by lazy { FractionPoint(midX, midY) }
    override val midXmaxY: FractionPoint by lazy { FractionPoint(midX, maxY) }

    override val maxXminY: FractionPoint by lazy { FractionPoint(maxX, minY) }
    override val maxXmidY: FractionPoint by lazy { FractionPoint(maxX, midY) }
    override val maxXmaxY: FractionPoint by lazy { FractionPoint(maxX, maxY) }


    override infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): FractionSize
            where OtherType : Number, OtherType: Comparable<OtherType>
            = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> minus(rhs: ComputableSize<OtherType>): FractionSize
            where OtherType : Number, OtherType: Comparable<OtherType>
            = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> times(rhs: ComputableSize<OtherType>): FractionSize
            where OtherType : Number, OtherType: Comparable<OtherType>
            = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> div(rhs: ComputableSize<OtherType>): FractionSize
            where OtherType : Number, OtherType: Comparable<OtherType>
            = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType> plus(rhs: OtherType): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType> minus(rhs: OtherType): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType> times(rhs: OtherType): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = times(Pair(rhs, rhs))
    override infix operator fun <OtherType> div(rhs: OtherType): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = div(Pair(rhs, rhs))


    override infix operator fun <OtherType> plus(rhs: Pair<OtherType, OtherType>): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> FractionSize(width = width + (rhs.first.integerValue),
                                                          height = height + (rhs.second.integerValue))
                rhs.first.isNativeFraction -> FractionSize(width = (width + rhs.first.fractionValue),
                                                           height = (height + rhs.second.fractionValue))
                else -> throw apology("addition",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }

    override infix operator fun <OtherType> minus(rhs: Pair<OtherType, OtherType>): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> FractionSize(width = width - (rhs.first.integerValue),
                                                          height = height - (rhs.second.integerValue))
                rhs.first.isNativeFraction -> FractionSize(width = (width - rhs.first.fractionValue),
                                                           height = (height - rhs.second.fractionValue))
                else -> throw apology("subtraction",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }

    override infix operator fun <OtherType> times(rhs: Pair<OtherType, OtherType>): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> FractionSize(width = width * (rhs.first.integerValue),
                                                          height = height * (rhs.second.integerValue))
                rhs.first.isNativeFraction -> FractionSize(width = (width * rhs.first.fractionValue),
                                                           height = (height * rhs.second.fractionValue))
                else -> throw apology("multiplication",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }

    override infix operator fun <OtherType> div(rhs: Pair<OtherType, OtherType>): FractionSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> FractionSize(width = width / (rhs.first.integerValue),
                                                          height = height / (rhs.second.integerValue))
                rhs.first.isNativeFraction -> FractionSize(width = (width / rhs.first.fractionValue),
                                                           height = (height / rhs.second.fractionValue))
                else -> throw apology("division",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override val minDimension: Fraction by lazy { min(width, height) }
    override val maxDimension: Fraction by lazy { max(width, height) }
}

typealias Float64Size = FractionSize
typealias FloatSize = FractionSize

val FractionSize.integerValue: IntegerSize get() = IntegerSize(width.integerValue, height.integerValue)



inline val <NumberType : Number> Size<NumberType>.fractionValue: FractionSize
    get() = if (this is FractionSize) this else FractionSize(width.fractionValue, height.fractionValue)
