package org.bh.tools.base.struct.coord

import org.bh.tools.base.abstraction.*
import java.util.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for BH Tic Tac Toe IntelliJ Project.
 *
 * A size which uses a number of any type
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
typealias Int8Size = Size<Int8>
typealias Int16Size = Size<Int16>
typealias Int32Size = Size<Int32>
typealias Int64Size = Size<Int64>

typealias Float32Size = Size<Float32>
typealias Float64Size = Size<Float64>
typealias BHFloatSize = Size<BHFloat>
typealias FloatSize = Size<BHFloat>

val java.awt.Dimension.sizeValue: IntSize get() = IntSize(width = this.width, height = this.height)
val IntSize.awtValue: java.awt.Dimension get() = java.awt.Dimension(width, height)



// Operators

private class SizeOperatorUnavailableApology(widthType: Class<Number>, heightType: Class<Number>, operator: String)
        : UnsupportedOperationException("Sorry, but because of JVM signature nonsense, $operator extensions " +
        "operators have to be done very explicitly, and I didn't think of sizes with $widthType widths and " +
        "$heightType heights when I wrote it.")


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Size<NumberType>.div(rhs: Size<NumberType>): Size<NumberType> {
    return (if (width is Int8 && height is Int8 && rhs.width is Int8 && rhs.height is Int8) {
        Int8Size((width / rhs.width).toByte(), (height / rhs.height).toByte())
    } else if (width is Int16 && height is Int16 && rhs.width is Int16 && rhs.height is Int16) {
        Int16Size((width / rhs.width).toShort(), (height / rhs.height).toShort())
    } else if (width is Int32 && height is Int32 && rhs.width is Int32 && rhs.height is Int32) {
        Int32Size(width / rhs.width, height / rhs.height)
    } else if (width is Int64 && height is Int64 && rhs.width is Int64 && rhs.height is Int64) {
        Int64Size(width / rhs.width, height / rhs.height)
    }

    else if (width is Float32 && height is Float32 && rhs.width is Float32 && rhs.height is Float32) {
        Float32Size(width / rhs.width, height / rhs.height)
    } else if (width is Float64 && height is Float64 && rhs.width is Float64 && rhs.height is Float64) {
        Float64Size(width / rhs.width, height / rhs.height)
    }

    else {
        throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "division")
    }) as Size<NumberType>
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Size<NumberType>.div(rhs: NumberType): Size<NumberType> {
    return (if (width is Int8 && height is Int8 && rhs is Int8 && rhs is Int8) {
        Int8Size((width / rhs).toByte(), (height / rhs).toByte())
    } else if (width is Int16 && height is Int16 && rhs is Int16 && rhs is Int16) {
        Int16Size((width / rhs).toShort(), (height / rhs).toShort())
    } else if (width is Int32 && height is Int32 && rhs is Int32 && rhs is Int32) {
        Int32Size(width / rhs, height / rhs)
    } else if (width is Int64 && height is Int64 && rhs is Int64 && rhs is Int64) {
        Int64Size(width / rhs, height / rhs)
    }

    else if (width is Float32 && height is Float32 && rhs is Float32 && rhs is Float32) {
        Float32Size(width / rhs, height / rhs)
    } else if (width is Float64 && height is Float64 && rhs is Float64 && rhs is Float64) {
        Float64Size(width / rhs, height / rhs)
    }

    else {
        throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "division")
    }) as Size<NumberType>
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Size<NumberType>.times(rhs: Size<NumberType>): Size<NumberType> {
    return (if (width is Int8 && height is Int8 && rhs.width is Int8 && rhs.height is Int8) {
        Int8Size((width * rhs.width).toByte(), (height * rhs.height).toByte())
    } else if (width is Int16 && height is Int16 && rhs.width is Int16 && rhs.height is Int16) {
        Int16Size((width * rhs.width).toShort(), (height * rhs.height).toShort())
    } else if (width is Int32 && height is Int32 && rhs.width is Int32 && rhs.height is Int32) {
        Int32Size(width * rhs.width, height * rhs.height)
    } else if (width is Int64 && height is Int64 && rhs.width is Int64 && rhs.height is Int64) {
        Int64Size(width * rhs.width, height * rhs.height)
    }

    else if (width is Float32 && height is Float32 && rhs.width is Float32 && rhs.height is Float32) {
        Float32Size(width * rhs.width, height * rhs.height)
    } else if (width is Float64 && height is Float64 && rhs.width is Float64 && rhs.height is Float64) {
        Float64Size(width * rhs.width, height * rhs.height)
    }

    else {
        throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "multiplication")
    }) as Size<NumberType>
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Size<NumberType>.times(rhs: NumberType): Size<NumberType> {
    return (if (width is Int8 && height is Int8 && rhs is Int8 && rhs is Int8) {
        Int8Size((width * rhs).toByte(), (height * rhs).toByte())
    } else if (width is Int16 && height is Int16 && rhs is Int16 && rhs is Int16) {
        Int16Size((width * rhs).toShort(), (height * rhs).toShort())
    } else if (width is Int32 && height is Int32 && rhs is Int32 && rhs is Int32) {
        Int32Size(width * rhs, height * rhs)
    } else if (width is Int64 && height is Int64 && rhs is Int64 && rhs is Int64) {
        Int64Size(width * rhs, height * rhs)
    }

    else if (width is Float32 && height is Float32 && rhs is Float32 && rhs is Float32) {
        Float32Size(width * rhs, height * rhs)
    } else if (width is Float64 && height is Float64 && rhs is Float64 && rhs is Float64) {
        Float64Size(width * rhs, height * rhs)
    }

    else {
        throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "multiplication")
    }) as Size<NumberType>
}


@Suppress("UNCHECKED_CAST") // types checked manually
infix operator fun <NumberType : Number> Size<NumberType>.times(rhs: Point<NumberType>): Size<NumberType> {
    return (if (width is Int8 && height is Int8 && rhs.x is Int8 && rhs.y is Int8) {
        Int8Size((width * rhs.x).toByte(), (height * rhs.y).toByte())
    } else if (width is Int16 && height is Int16 && rhs.x is Int16 && rhs.y is Int16) {
        Int16Size((width * rhs.x).toShort(), (height * rhs.y).toShort())
    } else if (width is Int32 && height is Int32 && rhs.x is Int32 && rhs.y is Int32) {
        Int32Size(width * rhs.x, height * rhs.y)
    } else if (width is Int64 && height is Int64 && rhs.x is Int64 && rhs.y is Int64) {
        Int64Size(width * rhs.x, height * rhs.y)
    }

    else if (width is Float32 && height is Float32 && rhs.x is Float32 && rhs.y is Float32) {
        Float32Size(width * rhs.x, height * rhs.y)
    } else if (width is Float64 && height is Float64 && rhs.x is Float64 && rhs.y is Float64) {
        Float64Size(width * rhs.x, height * rhs.y)
    }

    else {
        throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "multiplication")
    }) as Size<NumberType>
}


val <NumberType : Number> Size<NumberType>.floatValue: FloatSize get() {
    return (
        if (width is Int8 && height is Int8)            FloatSize(width.floatValue, height.floatValue)
        else if (width is Int16 && height is Int16)     FloatSize(width.floatValue, height.floatValue)
        else if (width is Int32 && height is Int32)     FloatSize(width.floatValue, height.floatValue)
        else if (width is Int64 && height is Int64)     FloatSize(width.floatValue, height.floatValue)
        else if (width is Float32 && height is Float32) FloatSize(width.floatValue, height.floatValue)
        else if (width is Float64 && height is Float64) FloatSize(width.floatValue, height.floatValue)
        else throw SizeOperatorUnavailableApology(width.javaClass, height.javaClass, "multiplication")
    )
}

//infix operator fun FloatSize.div(rhs: FloatSize): FloatSize
//        = FloatSize(width / rhs.width, height / rhs.height)
//
//infix operator fun FloatSize.times(rhs: BHFloat): FloatSize
//        = FloatSize(width * rhs, height * rhs)
//infix operator fun FloatSize.times(rhs: FloatSize): FloatSize
//        = FloatSize(width * rhs.width, height * rhs.height)
//infix operator fun FloatSize.times(rhs: IntPoint): FloatSize
//        = FloatSize(width * rhs.x, height * rhs.y)



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
