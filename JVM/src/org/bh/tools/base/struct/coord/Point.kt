@file:Suppress("unused")

package org.bh.tools.base.struct.coord

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.abstraction.BHInt

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
data class Point<out NumberType>(val x: NumberType, val y: NumberType) where NumberType: Number {
    companion object {
        val zero: Point<Number> get() = _zero
    }
}

private val _zero: Point<Number> = Point(0, 0)

typealias Coordinate<NumberType> = Point<NumberType>

typealias IntPoint = Point<Int>
typealias LongPoint = Point<Long>
typealias BHIntPoint = Point<BHInt>
typealias IntegerPoint = Point<BHInt>

typealias Float32Point = Point<Float>
typealias Float64Point = Point<Double>
typealias BHFloatPoint = Point<BHFloat>
typealias FloatPoint = Point<BHFloat>



infix operator fun IntPoint.times(rhs: IntPoint): IntPoint
        = IntPoint(x * rhs.x, y * rhs.y)
infix operator fun IntPoint.times(rhs: IntSize): IntPoint
        = IntPoint(x * rhs.width, y * rhs.height)
infix operator fun IntPoint.times(rhs: Int): IntPoint
        = IntPoint(x * rhs, y * rhs)
