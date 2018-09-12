package org.bh.tools.base.math


import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.strings.*
import org.bh.tools.base.util.*
import org.junit.*


/**
 * @author Ben Leggiero
 * @since 2017-03-22
 */
open class NumberConversionKtTest {

    val sampleInt8s: List<Int8> = listOf(0, 1, -1, Int8.min, Int8.max, (Int8.min / 2).toByte(), (Int8.max / 2).toByte())
    val sampleInt16s: List<Int16> = sampleInt8s.map(Int8::toShort) + listOf(Int16.min, Int16.max, (Int16.min / 2).toShort(), (Int16.max / 2).toShort())
    val sampleInt32s: List<Int32> = sampleInt16s.map(Int16::toInt) + listOf(Int32.min, Int32.max, (Int32.min / 2), (Int32.max / 2))
    val sampleInt64s: List<Int64> = sampleInt32s.map(Int32::toLong) + listOf(Int64.min, Int64.max, (Int64.min / 2), (Int64.max / 2))

    val sampleFloat32s: List<Float32> = sampleInt64s.map(Int64::toFloat) + listOf(Float32.leastNonzeroMagnitude, Float32.greatestFiniteMagnitude, -Float32.leastNonzeroMagnitude, -Float32.greatestFiniteMagnitude)
    val sampleFloat64s: List<Float64> = sampleFloat32s.map(Float32::toDouble) + listOf(Float64.leastNonzeroMagnitude, Float64.greatestFiniteMagnitude, -Float64.leastNonzeroMagnitude, -Float64.greatestFiniteMagnitude)

//    BigDecimals and BigIntegers in Blue Base/JVM

    val allSamples = (listOf<Number>() +
            sampleInt8s + sampleInt16s + sampleInt32s + sampleInt64s +
            sampleFloat32s + sampleFloat64s).toMutableList()

    val sampleInt16sAndBelow: List<Number> = sampleInt8s + sampleInt16s
    val sampleInt32sAndBelow: List<Number> = sampleInt16sAndBelow + sampleInt32s
    val sampleInt64sAndBelow: List<Number> = sampleInt32sAndBelow + sampleInt64s

    init {
        @Suppress("LeakingThis")
        println("Evaluating ${allSamples.length.toString(separator = ",")} sample numbers...")
    }


    @Test
    open fun Number_float32Value() {
        allSamples.forEach {
            assertNumbersClose("Original number should equal to its float 32 value",
                               it, it.float32Value
            )
        }
    }


    @Test
    open fun Number_float64Value() {
        allSamples.forEach {
            assertNumbersClose("Original number should equal to its float 64 value",
                               it, it.float64Value
            )
        }
    }


    @Test
    open fun Number_fractionValue() {
        allSamples.forEach {
            assertNumbersClose("Original number should equal to its fraction value",
                               it, it.fractionValue)
        }
    }


    @Test
    open fun toInt8Checked() {
        sampleInt8s.forEach {
            assertNumbersClose("Original number should equal to its Int8 value",
                               it, it.toInt8Checked())
        }
    }


    @Test
    open fun toInt16Checked() {
        sampleInt16sAndBelow.forEach {
            assertNumbersClose("Original number should equal to its Int16 value",
                               it, it.toInt16Checked())
        }
    }


    @Test
    open fun toInt32Checked() {
        sampleInt32sAndBelow.forEach {
            assertNumbersClose("Original number should equal to its Int32 value",
                               it, it.toInt32Checked())
        }
    }


    @Test
    open fun toInt64Checked() {
        sampleInt64sAndBelow.forEach {
            assertNumbersClose("Original number should equal to its Int64 value",
                               it, it.toInt64Checked())
        }
    }

    // TODO: All these:

    @Test
    open fun getInt8Value() {

    }


    @Test
    open fun getInt16Value() {

    }


    @Test
    open fun getInt32Value() {

    }


    @Test
    open fun getInt64Value() {

    }


    @Test
    open fun getIntegerValue() {

    }


    @Test
    open fun integerValue() {

    }


    @Test
    open fun isNativeInteger() {

    }


    @Test
    open fun isNativeFraction() {

    }


    @Test
    open fun getClampedIntegerValue() {

    }


    @Test
    open fun getClampedInt64Value() {

    }


    @Test
    open fun getClampedInt32Value() {

    }


    @Test
    open fun getClampedInt162Value() {

    }


    @Test
    open fun getClampedInt8Value() {

    }


    @Test
    open fun getClampedInt32Value1() {

    }


    @Test
    open fun getClampedInt16Value() {

    }


    @Test
    open fun getClampedInt8Value1() {

    }


    @Test
    open fun getClampToPositive() {

    }


    @Test
    open fun getClampToPositive1() {

    }


    @Test
    open fun getClampToPositive2() {

    }


    @Test
    open fun getClampToPositive3() {

    }


    @Test
    open fun getClampToPositive4() {

    }


    @Test
    open fun getClampToPositive5() {

    }


    @Test
    open fun int8ArrayOf() {

    }


    @Test
    open fun int16ArrayOf() {

    }


    @Test
    open fun int32ArrayOf() {

    }


    @Test
    open fun int64ArrayOf() {

    }


    @Test
    open fun integerArrayOf() {

    }


    @Test
    open fun float32ArrayOf() {

    }


    @Test
    open fun float64ArrayOf() {

    }


    @Test
    open fun fractionArrayOf() {

    }


    @Test
    open fun toInt8Array() {

    }


    @Test
    open fun toInt16Array() {

    }


    @Test
    open fun toInt32Array() {

    }


    @Test
    open fun toInt64Array() {

    }


    @Test
    open fun toIntegerArray() {

    }


    @Test
    open fun toFloat32Array() {

    }


    @Test
    open fun toFloat64Array() {

    }


    @Test
    open fun toFractionArray() {

    }


    @Test
    open fun getInt8Value1() {

    }


    @Test
    open fun getInt8Value2() {

    }


    @Test
    open fun getInt8Value3() {

    }


    @Test
    open fun getInt8Value4() {

    }


    @Test
    open fun getInt16Value1() {

    }


    @Test
    open fun getInt16Value2() {

    }


    @Test
    open fun getInt16Value3() {

    }


    @Test
    open fun getInt16Value4() {

    }


    @Test
    open fun getInt32Value1() {

    }


    @Test
    open fun getInt32Value2() {

    }


    @Test
    open fun getInt32Value3() {

    }


    @Test
    open fun getInt32Value4() {

    }


    @Test
    open fun getInt64Value1() {

    }


    @Test
    open fun getInt64Value2() {

    }


    @Test
    open fun getInt64Value3() {

    }


    @Test
    open fun getInt64Value4() {

    }


    @Test
    open fun getIntegerValue1() {

    }


    @Test
    open fun getIntegerValue2() {

    }


    @Test
    open fun getIntegerValue3() {

    }


    @Test
    open fun getIntegerValue4() {

    }


    @Test
    open fun getFloat32Value1() {

    }


    @Test
    open fun getFloat32Value2() {

    }


    @Test
    open fun getFloat64Value1() {

    }


    @Test
    open fun getFloat64Value2() {

    }


    @Test
    open fun getFractionValue1() {

    }


    @Test
    open fun getFractionValue2() {

    }
}


@Suppress("NOTHING_TO_INLINE")
fun assertNumbersClose(message: String, original: Number, processed: Number) {
    if (original.toFloat().equals(processed.toFloat(), defaultFloat32CalculationTolerance)) {
        return
    }
    val originalString = original.toDouble().toString()
    val processedString = processed.toDouble().toString()
    val differingCharacters = originalString.differingCharacters(processedString)
    val allowedDifference = when (originalString.length) {
        in 0..5 -> 0
        in 6..12 -> 1
        else -> 2
    }
    assertTrue({
        "$message\r\n\r\n" +
                "\tExpected:   \t$originalString\r\n" +
                "\tActual:     \t$processedString\r\n" +
                "\tDifference: \t$differingCharacters\r\n"
    },
            differingCharacters.length <= allowedDifference)
}
