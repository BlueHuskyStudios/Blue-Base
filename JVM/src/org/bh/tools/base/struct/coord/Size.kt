package org.bh.tools.base.struct.coord

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.BHInt
import java.util.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
data class Size<out NumberType>(val width: NumberType, val height: NumberType) where NumberType: Number

typealias Dimension<NumberType> = Size<NumberType>

typealias ByteSize = Size<Byte>
typealias ShortSize = Size<Short>
typealias IntSize = Size<Int>
typealias LongSize = Size<Long>
typealias BHIntSize = Size<BHInt>
typealias IntegerSize = Size<BHInt>

typealias Float32Size = Size<Float>
typealias Float64Size = Size<Double>
typealias BHFloatSize = Size<BHFloat>
typealias FloatSize = Size<BHFloat>

val java.awt.Dimension.sizeValue: IntSize get() = IntSize(width = this.width, height = this.height)
val IntSize.awtValue: java.awt.Dimension get() = java.awt.Dimension(width, height)



// Operators

infix operator fun IntSize.div(rhs: IntSize): IntSize
        = IntSize(width / rhs.width, height / rhs.height)

infix operator fun IntSize.times(rhs: Int): IntSize
        = IntSize(width * rhs, height * rhs)
infix operator fun IntSize.times(rhs: IntSize): IntSize
        = IntSize(width * rhs.width, height * rhs.height)
infix operator fun IntSize.times(rhs: IntPoint): IntSize
        = IntSize(width * rhs.x, height * rhs.y)



// Silliness

@Suppress("UNCHECKED_CAST") // checked transitively
val <NumberType: Number> Size<NumberType>.randomPoint: Point<NumberType> get() {
    val random = Random()
    val x: NumberType
    val y: NumberType
    if (width is Int && height is Int) {
        x = random.nextInt(width + 1) as NumberType
        y = random.nextInt(height + 1) as NumberType
    } else if (width is Double && height is Double) {
        x = (random.nextDouble() * (width + 1)) as NumberType
        y = (random.nextDouble() * (height + 1)) as NumberType
    } else {
        print("Type not supported: ${width::class}")
        x = width
        y = height
    }

    return Point(x, y)
}
