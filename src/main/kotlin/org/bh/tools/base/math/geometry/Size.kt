@file:Suppress("unused")

package BlueBase

import BlueBase.RectangleScanningApproach.Companion.xUpThenYUp
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


    operator fun component1() = width
    operator fun component2() = height
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


    abstract fun forEach(scanningApproach: RectangleScanningApproach = RectangleScanningApproach.default,
                         iterator: (coordinate: ComputablePoint<NumberType>, didRollOver: Boolean) -> Unit)
}



internal fun
        <NumberType, PointType, IterableNumber, IterableType>
        rectangularIteratorTemplate(scanningApproach: RectangleScanningApproach,
                                    xIterator: IterableType, yIterator: IterableType,
                                    pointGenerator: (x: IterableNumber, y: IterableNumber) -> PointType,
                                    iterator: (coordinate: PointType, didRollOver: Boolean) -> Unit)
        where NumberType: Number, NumberType: Comparable<NumberType>, PointType: ComputablePoint<NumberType>, IterableType : Iterable<IterableNumber>
{
    when (scanningApproach) {
        xUpThenYUp -> {
            var didRollOver: Boolean
            yIterator.forEach { y ->
                didRollOver = true
                xIterator.forEach { x ->
                    iterator(pointGenerator(x, y), didRollOver)
                    didRollOver = false
                }
            }
        }

        else -> TODO("Scanning approaches other than CARA are not yet supported")
    }
}



fun <NumberType> ComputableSize<NumberType>.contains(point: ComputablePoint<NumberType>): Boolean
    where NumberType : Number,
    NumberType : Comparable<NumberType> {
    return this.minX <= point.x
        && this.minY <= point.y
        && this.maxX >= point.x
        && this.maxY >= point.y
}



/**
 * Describes an approach on how to traverse every single pixel in a rectangle.
 * If you don't care, use [`default`][RectangleScanningApproach.default].
 */
enum class RectangleScanningApproach {
    /**
     * Columns start at their min and are incremented until hitting their max, before being reset to their min and the
     * row is incremented. This continues until both columns and rows hit their max.
     */
    columnsAscendingThenRowsAscending,

    /**
     * Rows start at their min and are incremented until hitting their max, before being reset to their min and the
     * column is incremented. This continues until both columns and rows hit their max.
     */
    rowsAscendingThenColumnsAscending,


    /**
     * Columns start at their min and are incremented until hitting their max, before being reset to their min and the
     * row is decremented. This continues until columns hit their max _and_ rows hit their min.
     */
    columnsAscendingThenRowsDescending,

    /**
     * Rows start at their min and are incremented until hitting their max, before being reset to their min and the
     * column is decremented. This continues until columns hit their max _and_ rows hit their min.
     */
    rowsAscendingThenColumnsDescending,


    /**
     * Columns start at their max and are decremented until hitting their min, before being reset to their max and the
     * row is incremented. This continues until columns hit their min _and_ rows hit their max.
     */
    columnsDescendingThenRowsAscending,

    /**
     * Rows start at their max and are decremented until hitting their min, before being reset to their max and the
     * column is incremented. This continues until rows hit their min _and_ columns hit their max.
     */
    rowsDescendingThenColumnsAscending,


    /**
     * Columns start at their max and are decremented until hitting their min, before being reset to their max and the
     * row is decremented. This continues until both columns and rows hit their min.
     */
    columnsDescendingThenRowsDescending,

    /**
     * Rows start at their max and are decremented until hitting their min, before being reset to their max and the
     * column is decremented. This continues until both columns and rows hit their min.
     */
    rowsDescendingThenColumnsDescending,
    ;

    companion object {
        /**
         * The default scanning approach.
         * Use this when you don't care what approach is used so long as all pixels are traversed
         */
        inline val default get() = columnsAscendingThenRowsAscending

        inline val breadthFirst get() = columnsAscendingThenRowsAscending
        inline val depthFirst get() = rowsAscendingThenColumnsAscending

        inline val xThenY get() = columnsAscendingThenRowsAscending
        inline val yThenX get() = rowsAscendingThenColumnsAscending
        inline val xUpThenYUp get() = columnsAscendingThenRowsAscending
        inline val yUpThenXUp get() = rowsAscendingThenColumnsAscending
        inline val xUpThenYDown get() = columnsAscendingThenRowsDescending
        inline val yUpThenXDown get() = rowsAscendingThenColumnsDescending
        inline val xDownThenYUp get() = columnsDescendingThenRowsAscending
        inline val yDownThenXUp get() = rowsDescendingThenColumnsAscending
        inline val xDownThenYDown get() = columnsDescendingThenRowsDescending
        inline val yDownThenXDown get() = rowsDescendingThenColumnsDescending
    }
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



open class IntegerSize(width: Integer, height: Integer) : ComputableSize<Integer>(width, height) {

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
            = when {
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
            = when {
                rhs.first.isNativeInteger -> IntegerSize(width - (rhs.first.integerValue),
                                                         height - (rhs.second.integerValue))
                rhs.first.isNativeFraction -> IntegerSize((width - (rhs.first.fractionValue)).clampedIntegerValue,
                                                          (height - (rhs.second.fractionValue)).clampedIntegerValue)
                else -> throw apology("subtraction",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> times(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> IntegerSize(width * (rhs.first.integerValue),
                                                         height * (rhs.second.integerValue))
                rhs.first.isNativeFraction -> IntegerSize((width * (rhs.first.fractionValue)).clampedIntegerValue,
                                                          (height * (rhs.second.fractionValue)).clampedIntegerValue)
                else -> throw apology("multiplication",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> div(rhs: Pair<OtherType, OtherType>): IntegerSize
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> IntegerSize(width / (rhs.first.integerValue),
                                                         height / (rhs.second.integerValue))
                rhs.first.isNativeFraction -> IntegerSize((width / (rhs.first.fractionValue)).clampedIntegerValue,
                                                          (height / (rhs.second.fractionValue)).clampedIntegerValue)
                else -> throw apology("division",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override val minDimension: Integer by lazy { min(width, height) }
    override val maxDimension: Integer by lazy { max(width, height) }


    override fun forEach(scanningApproach: RectangleScanningApproach,
                         iterator: (coordinate: ComputablePoint<Integer>, didRollOver: Boolean) -> Unit) =
            rectangularIteratorTemplate(scanningApproach,
                                        yIterator = minY until maxY,
                                        xIterator = minX until maxX,
                                        pointGenerator = ::IntegerPoint,
                                        iterator = iterator)
}

typealias Int64Size = IntegerSize
typealias IntSize = IntegerSize



open class Int8Size(width: Int8, height: Int8) : ComputableSize<Int8>(width, height) {

    companion object {
        val zero = Int8Size(0, 0)
    }


    override val isEmpty: Boolean = this == zero


    override val minX: Int8 by lazy { 0.int8Value }
    override val midX: Int8 by lazy { (width / 2).int8Value }
    override val maxX: Int8 by lazy { width }

    override val minY: Int8 by lazy { 0.int8Value }
    override val midY: Int8 by lazy { (height / 2).int8Value }
    override val maxY: Int8 by lazy { height }


    override val minXminY: Int8Point by lazy { Int8Point(minX, minY) }
    override val minXmidY: Int8Point by lazy { Int8Point(minX, midY) }
    override val minXmaxY: Int8Point by lazy { Int8Point(minX, maxY) }

    override val midXminY: Int8Point by lazy { Int8Point(midX, minY) }
    override val midXmidY: Int8Point by lazy { Int8Point(midX, midY) }
    override val midXmaxY: Int8Point by lazy { Int8Point(midX, maxY) }

    override val maxXminY: Int8Point by lazy { Int8Point(maxX, minY) }
    override val maxXmidY: Int8Point by lazy { Int8Point(maxX, midY) }
    override val maxXmaxY: Int8Point by lazy { Int8Point(maxX, maxY) }


    constructor(width: Int32, height: Int32) : this(width = width.int8Value, height = height.int8Value)
    constructor(squareSide: Int8) : this(width = squareSide, height = squareSide)

    //  abstract infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): ComputableSize<NumberType> where OtherType : Number, OtherType : Comparable<OtherType>
    override infix operator fun <OtherType> plus(rhs: ComputableSize<OtherType>): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = plus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> minus(rhs: ComputableSize<OtherType>): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = minus(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> times(rhs: ComputableSize<OtherType>): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = times(Pair(rhs.width, rhs.height))
    override infix operator fun <OtherType> div(rhs: ComputableSize<OtherType>): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = div(Pair(rhs.width, rhs.height))


    override infix operator fun <OtherType> plus(rhs: OtherType): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = plus(Pair(rhs, rhs))
    override infix operator fun <OtherType> minus(rhs: OtherType): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = minus(Pair(rhs, rhs))
    override infix operator fun <OtherType> times(rhs: OtherType): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = times(Pair(rhs, rhs))
    override infix operator fun <OtherType> div(rhs: OtherType): Int8Size where OtherType : Number, OtherType : Comparable<OtherType>
            = div(Pair(rhs, rhs))


    override infix operator fun <OtherType> plus(rhs: Pair<OtherType, OtherType>): Int8Size
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> Int8Size(width + (rhs.first.int8Value),
                                                      height + (rhs.second.int8Value))
                rhs.first.isNativeFraction -> Int8Size((width + (rhs.first.fractionValue)).clampedInt8Value,
                                                       (height + (rhs.second.fractionValue)).clampedInt8Value)
                else -> throw apology("addition",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> minus(rhs: Pair<OtherType, OtherType>): Int8Size
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> Int8Size(width - (rhs.first.int8Value),
                                                      height - (rhs.second.int8Value))
                rhs.first.isNativeFraction -> Int8Size((width - (rhs.first.fractionValue)).clampedInt8Value,
                                                       (height - (rhs.second.fractionValue)).clampedInt8Value)
                else -> throw apology("subtraction",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> times(rhs: Pair<OtherType, OtherType>): Int8Size
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> Int8Size(width * (rhs.first.int8Value),
                                                      height * (rhs.second.int8Value))
                rhs.first.isNativeFraction -> Int8Size((width * (rhs.first.fractionValue)).clampedInt8Value,
                                                       (height * (rhs.second.fractionValue)).clampedInt8Value)
                else -> throw apology("multiplication",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override infix operator fun <OtherType> div(rhs: Pair<OtherType, OtherType>): Int8Size
            where OtherType : Number, OtherType : Comparable<OtherType>
            = when {
                rhs.first.isNativeInteger -> Int8Size(width / (rhs.first.int8Value),
                                                      height / (rhs.second.int8Value))
                rhs.first.isNativeFraction -> Int8Size((width / (rhs.first.fractionValue)).clampedInt8Value,
                                                       (height / (rhs.second.fractionValue)).clampedInt8Value)
                else -> throw apology("division",
                                      otherMainType = Pair::class,
                                      otherTypeA = rhs.first::class,
                                      otherTypeB = rhs.second::class)
            }


    override val minDimension: Int8 by lazy { min(width, height) }
    override val maxDimension: Int8 by lazy { max(width, height) }


    override fun forEach(scanningApproach: RectangleScanningApproach,
                         iterator: (coordinate: ComputablePoint<Int8>, didRollOver: Boolean) -> Unit) =
            rectangularIteratorTemplate(scanningApproach,
                                        xIterator =  minX until maxX,
                                        yIterator =  minY until maxY,
                                        pointGenerator = ::Int8Point,
                                        iterator =  iterator)
}


open class FractionSize(width: Fraction, height: Fraction) : ComputableSize<Fraction>(width, height) {

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


    override fun forEach(scanningApproach: RectangleScanningApproach,
                         iterator: (coordinate: ComputablePoint<Fraction>, didRollOver: Boolean) -> Unit) =
            rectangularIteratorTemplate(scanningApproach,
                                        yIterator = minY.roundedIntegerValue until maxY.roundedIntegerValue,
                                        xIterator = minX.roundedIntegerValue until maxX.roundedIntegerValue,
                                        pointGenerator = ::FractionPoint,
                                        iterator = iterator)
}

typealias Float64Size = FractionSize
typealias FloatSize = FractionSize

val FractionSize.integerValue: IntegerSize get() = IntegerSize(width.integerValue, height.integerValue)



inline val <NumberType : Number> Size<NumberType>.fractionValue: FractionSize
    get() = if (this is FractionSize) this else FractionSize(width.fractionValue, height.fractionValue)
