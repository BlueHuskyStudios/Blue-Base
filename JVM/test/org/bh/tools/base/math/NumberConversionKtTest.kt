package org.bh.tools.base.math


import junit.framework.TestCase.assertTrue
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.length
import org.bh.tools.base.func.tuple
import org.bh.tools.base.strings.differingCharacters
import org.bh.tools.base.strings.toString
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger


/**
 * @author Ben Leggiero
 * @since 2017-03-22
 */
class NumberConversionKtTest {

    val sampleInt8s: List<Int8> = listOf(0, 1, -1, Int8.min, Int8.max, (Int8.min / 2).toByte(), (Int8.max / 2).toByte())
    val sampleInt16s: List<Int16> = sampleInt8s.map(Int8::toShort) + listOf(Int16.min, Int16.max, (Int16.min / 2).toShort(), (Int16.max / 2).toShort())
    val sampleInt32s: List<Int32> = sampleInt16s.map(Int16::toInt) + listOf(Int32.min, Int32.max, (Int32.min / 2), (Int32.max / 2))
    val sampleInt64s: List<Int64> = sampleInt32s.map(Int32::toLong) + listOf(Int64.min, Int64.max, (Int64.min / 2), (Int64.max / 2))

    val sampleFloat32s: List<Float32> = sampleInt64s.map(Int64::toFloat) + listOf<Float32>(Float32.leastNonzeroMagnitude, Float32.greatestFiniteMagnitude, -Float32.leastNonzeroMagnitude, -Float32.greatestFiniteMagnitude)
    val sampleFloat64s: List<Float64> = sampleFloat32s.map(Float32::toDouble) + listOf(Float64.leastNonzeroMagnitude, Float64.greatestFiniteMagnitude, -Float64.leastNonzeroMagnitude, -Float64.greatestFiniteMagnitude)

    val sampleBigDecimals: List<BigDecimal> = sampleFloat64s.map { BigDecimal(it.toString()) }
    val sampleBigIntegers: List<BigInteger> = sampleBigDecimals.map { it.toBigInteger() }

    val allSamples: List<Number> = listOf<Number>() +
            sampleInt8s + sampleInt16s + sampleInt32s + sampleInt64s +
            sampleFloat32s + sampleFloat64s +
            sampleBigIntegers + sampleBigDecimals

    init {
        println("Evaluating ${allSamples.length.toString(separator = ",")} sample numbers...")
    }


    @Test
    fun Number_float32Value() {
        allSamples
                .map { tuple(it, it.float32Value) }
                .forEach { (original, float32Value) ->
                    assertNumbersClose("Original number should equal float 32 value",
                            original,
                            float32Value
                    )
                }
    }


    @Test
    fun Number_float64Value() {
        allSamples
                .map { tuple(it, it.float64Value) }
                .forEach { (original, float64Value) ->
                    assertNumbersClose("Original number should equal float 64 value",
                            original,
                            float64Value
                    )
                }
    }

    // TODO: All these:

    @Test
    fun Number_fractionValue() {

    }


    @Test
    fun toByteChecked() {

    }


    @Test
    fun toShortChecked() {

    }


    @Test
    fun toIntChecked() {

    }


    @Test
    fun toLongChecked() {

    }


    @Test
    fun getInt8Value() {

    }


    @Test
    fun getInt16Value() {

    }


    @Test
    fun getInt32Value() {

    }


    @Test
    fun getInt64Value() {

    }


    @Test
    fun getIntegerValue() {

    }


    @Test
    fun integerValue() {

    }


    @Test
    fun isNativeInteger() {

    }


    @Test
    fun isNativeFraction() {

    }


    @Test
    fun getClampedIntegerValue() {

    }


    @Test
    fun getClampedInt64Value() {

    }


    @Test
    fun getClampedInt32Value() {

    }


    @Test
    fun getClampedInt162Value() {

    }


    @Test
    fun getClampedInt8Value() {

    }


    @Test
    fun getClampedInt32Value1() {

    }


    @Test
    fun getClampedInt16Value() {

    }


    @Test
    fun getClampedInt8Value1() {

    }


    @Test
    fun getClampToPositive() {

    }


    @Test
    fun getClampToPositive1() {

    }


    @Test
    fun getClampToPositive2() {

    }


    @Test
    fun getClampToPositive3() {

    }


    @Test
    fun getClampToPositive4() {

    }


    @Test
    fun getClampToPositive5() {

    }


    @Test
    fun int8ArrayOf() {

    }


    @Test
    fun int16ArrayOf() {

    }


    @Test
    fun int32ArrayOf() {

    }


    @Test
    fun int64ArrayOf() {

    }


    @Test
    fun integerArrayOf() {

    }


    @Test
    fun float32ArrayOf() {

    }


    @Test
    fun float64ArrayOf() {

    }


    @Test
    fun fractionArrayOf() {

    }


    @Test
    fun toInt8Array() {

    }


    @Test
    fun toInt16Array() {

    }


    @Test
    fun toInt32Array() {

    }


    @Test
    fun toInt64Array() {

    }


    @Test
    fun toIntegerArray() {

    }


    @Test
    fun toFloat32Array() {

    }


    @Test
    fun toFloat64Array() {

    }


    @Test
    fun toFractionArray() {

    }


    @Test
    fun getInt8Value1() {

    }


    @Test
    fun getInt8Value2() {

    }


    @Test
    fun getInt8Value3() {

    }


    @Test
    fun getInt8Value4() {

    }


    @Test
    fun getInt16Value1() {

    }


    @Test
    fun getInt16Value2() {

    }


    @Test
    fun getInt16Value3() {

    }


    @Test
    fun getInt16Value4() {

    }


    @Test
    fun getInt32Value1() {

    }


    @Test
    fun getInt32Value2() {

    }


    @Test
    fun getInt32Value3() {

    }


    @Test
    fun getInt32Value4() {

    }


    @Test
    fun getInt64Value1() {

    }


    @Test
    fun getInt64Value2() {

    }


    @Test
    fun getInt64Value3() {

    }


    @Test
    fun getInt64Value4() {

    }


    @Test
    fun getIntegerValue1() {

    }


    @Test
    fun getIntegerValue2() {

    }


    @Test
    fun getIntegerValue3() {

    }


    @Test
    fun getIntegerValue4() {

    }


    @Test
    fun getFloat32Value1() {

    }


    @Test
    fun getFloat32Value2() {

    }


    @Test
    fun getFloat64Value1() {

    }


    @Test
    fun getFloat64Value2() {

    }


    @Test
    fun getFractionValue1() {

    }


    @Test
    fun getFractionValue2() {

    }
}


@Suppress("NOTHING_TO_INLINE")
fun assertNumbersClose(message: String, original: Number, processed: Number) {
    val originalString = original.toDouble().toString()
    val processedString = processed.toDouble().toString()
    val differingCharacters = originalString.differingCharacters(processedString)
    val allowedDifference = when (originalString.length) {
        in 0..5 -> 0
        in 6..12 -> 1
        else -> 2
    }
    assertTrue(message, differingCharacters.length <= allowedDifference)
}
