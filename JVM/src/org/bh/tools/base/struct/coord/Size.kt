package org.bh.tools.base.struct.coord

import org.bh.tools.base.abstraction.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * @author Ben Leggiero
 * @since 2016-09-29
 */
data class Size<out NumberType>(val width: NumberType, val height: NumberType) where NumberType: Number

typealias Dimension<NumberType> = Size<NumberType>

typealias IntSize = Size<Int>
typealias LongSize = Size<Long>
typealias BHIntSize = Size<BHInt>
typealias IntegerSize = Size<BHInt>

typealias Float32Size = Size<Float>
typealias Float64Size = Size<Double>
typealias BHFloatSize = Size<BHFloat>
typealias FloatSize = Size<BHFloat>
