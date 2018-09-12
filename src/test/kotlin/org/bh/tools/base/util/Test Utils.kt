@file:kotlin.jvm.JvmName("TestUtils")
@file:Suppress("unused")

package org.bh.tools.base.util

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.func.StringSupplier
import org.bh.tools.base.math.*
import org.bh.tools.base.math.geometry.*
import org.bh.tools.base.util.Assertion.*
import org.bh.tools.base.util.TimeConversion.nanosecondsToTimeInterval
import org.junit.Assert.*
import kotlin.system.*
//import kotlin.test.*

/*
 * To aid in testing
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */


typealias TestMeasurementBlock = () -> Any



val defaultMeasurementTrialCount = 10L
val defaultWarmupTrialCount = 5L

/**
 * Measures the given block the given number of times and returns the average or total, depending on `mode`
 *
 * @param trials       The number of times to measure the given block
 * @param warmupTrials The number of times to execute the block before taking measurements, to decrease warmup bias
 * @param mode         The mode by which the measurement is taken, which affects the returned value
 * @param block        The block to measure
 */
//@JvmOverloads
inline fun measureTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                               warmupTrials: Integer = defaultWarmupTrialCount,
                               mode: TimeTrialMeasurementMode = TimeTrialMeasurementMode.average,
                               block: TestMeasurementBlock): TimeInterval =
        when (mode) {
            TimeTrialMeasurementMode.average -> averageTimeInterval(trials = trials, warmupTrials = warmupTrials, block = block)
            TimeTrialMeasurementMode.total -> totalTimeInterval(trials = trials, warmupTrials = warmupTrials, block = block)
        }



/**
 * Measures the given block the given number of times and returns the average
 *
 * @param trials       The number of times to measure the given block
 * @param warmupTrials The number of times to execute the block before taking measurements, to decrease warmup bias
 * @param block        The block to measure
 */
//@JvmOverloads
inline fun averageTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                               warmupTrials: Integer = defaultWarmupTrialCount,
                               block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first few tests to spin up caching
    for (i in 0..warmupTrials.clampToPositive) {
        blackHole(block())
    }

    val averager = Averager()

    for (i in 0..trials) {
        averager.average(nanosecondsToTimeInterval(measureNanoTime({blackHole(block())})))
    }

    return averager.currentAverage
}



/**
 * Measures the given block the given number of times and returns the total
 *
 * @param trials       The number of times to measure the given block
 * @param warmupTrials The number of times to execute the block before taking measurements, to decrease warmup bias
 * @param block        The block to measure
 */
//@JvmOverloads
inline fun totalTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                             warmupTrials: Integer = defaultWarmupTrialCount,
                             block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first few tests to spin up caching
    for (i in 0..warmupTrials.clampToPositive) {
        blackHole(block())
    }

    var total: TimeInterval = 0.0

    for (i in 0..trials) {
        total += nanosecondsToTimeInterval(measureNanoTime({blackHole(block())}))
    }

    return total
}



/** The mode by which a time trial is measured */
enum class TimeTrialMeasurementMode {
    /** Return the mean of all results */
    average,

    /** Return the sum of all results */
    total
}



private var blackHoleValueHolder: Any? = null
/**
 * Takes in the value you give it and places it into a black hole, from which it will never be accessed.
 *
 * Useful when testing to ensure code isn't optimized away
 */
fun <T> blackHole(x: T) {
    blackHoleValueHolder = x
    blackHoleValueHolder = null
}



// MARK: - Assertions

@Suppress("NOTHING_TO_INLINE")
//@JvmOverloads
inline fun assertionFailure(message: String? = null) = assertTrue(message, false)

inline fun assertionFailure(message: StringSupplier) = assertTrue(message(), false)


//@JvmOverloads
inline fun assertThrows(message: String? = null, possibleThrow: () -> Unit) {
    try {
        possibleThrow()
    } catch (_: Throwable) {
        return
    }
    assertionFailure(message)
}


/**
 * Asserts that each element in the given lists are equal, shallowly
 *
 * @param message _optional_ - The message sent on failure
 * @param listA   The first list to compare
 * @param listB   The second list to compare
 */
//@JvmOverloads
fun <Element,
        ElementA: Element,
        ElementB: Element,
        ListA: List<ElementA>,
        ListB: List<ElementB>>
        assertListEquals(message: String? = null, listA: ListA, listB: ListB) {
    if (listA.length == listB.length) {
        listA.zip(listB).forEach { (aElement, bElement) ->
            assertEquals(message, aElement, bElement)
        }
    } else {
        assertionFailure(message)
    }
}


/**
 * Asserts that each element in the given lists are equal, deeply; if any element is a list and the corresponding
 * element in the other list is also a list, this function is recursively called on those.
 *
 * @param message _optional_ - The message sent on failure
 * @param listA   The first list to compare
 * @param listB   The second list to compare
 */
//@JvmOverloads
fun <Element,
        ElementA: Element,
        ElementB: Element,
        ListA: List<ElementA>,
        ListB: List<ElementB>>
        assertListDeeplyEquals(message: String? = null, listA: ListA, listB: ListB) {
    if (listA.length == listB.length) {
        listA.zip(listB).forEach { (aElement, bElement) ->
            if (aElement is List<*>) {
                if (bElement is List<*>) {
                    assertListDeeplyEquals(message, aElement, bElement)
                } else {
                    assertionFailure(message)
                }
            } else {
                assertEquals(message, aElement, bElement)
            }
        }
    } else {
        assertionFailure(message)
    }
}


/**
 * Asserts that the given `condition` must be `true`, else `assertionFailure` is passed `message`.
 */
inline fun assertTrue(message: StringSupplier, condition: Boolean) {
    if (!condition) {
        assertionFailure(message)
    }
}


/**
 * Allows you to expect the result of multiple assertions in a compact API call, and get back any failure that occurs
 * with a descriptive message, or a success if no failure occurs.
 *
 * @param kind       The kind of thing we're testing against. Will be used in the English message output like "this
 *                   should be a valid `kind`"
 * @param processor  The function that will be called on each assertion, until the first failure. `null` return
 *                   indicates that the given value was invalid, which could be expected depending on the current
 *                   assertion.
 * @param assertions All assertions you want to be checked
 *
 * @return A descriptive result of the assertion processing. Contains an English message and a boolean indicating
 *         whether all assertions were successful.
 *
 * @see Assertion
 * @see ExpectationResult
 */
fun <Raw, Processed> expect(kind: String, processor: (Raw) -> Processed?, assertions: List<Assertion<Raw, Processed>>): ExpectationResult {
    for (assertion in assertions) {
        when (assertion) {
            is valid -> {
                val processed = processor(assertion.raw)
                if (null == processed) {
                    return ExpectationResult("null - Expected ${assertion.raw} to be a valid $kind", wasSuccessful = false)
                }
                else if (processed != assertion.expectation) {
                    return ExpectationResult("$processed - Expected ${assertion.raw} to be ${assertion.expectation}, a valid $kind", wasSuccessful = false)
                }
            }
            is invalid -> {
                val processed = processor(assertion.raw)
                if (null != processed) {
                    return ExpectationResult("$processed - Expected ${assertion.raw} to be an invalid $kind", wasSuccessful = false)
                }
            }
        }
    }

    return ExpectationResult("All good", wasSuccessful = true)
}


/**
 * A message/wasSuccessful pair returned from [expect]
 *
 * @param message A descriptive English message about the result of one or more assertions
 * @param wasSuccessful `true` iff all assertions passed
 */
data class ExpectationResult(
        /** A descriptive English message about the result of one or more assertions */
        val message: String,

        /** `true` iff all assertions passed */
        val wasSuccessful: Boolean
)


/** Defines an expectation about the success state of an assertion */
sealed class Assertion<out Raw, out Processed> {
    /** A raw value is expected to be validly processed into a specific non-`null` result */
    data class valid<out Raw, out Processed>(
            /** The value before processing */
            val raw: Raw,

            /** The expected value after processing */
            val expectation: Processed

    ): Assertion<Raw, Processed>()

    /** A raw value is expected to be invalid, so that after it's processed, the result should be `null` */
    data class invalid<out Raw>(val raw: Raw): Assertion<Raw, Nothing>()
}


/**
 * Asserts that the given two items of tolerable equality are equal within a given tolerance
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <TE: TolerableEquality<TE>> assertEquals(expected: TE,
                                                    actual: TE,
                                                    tolerance: Tolerance,
                                                    message: String = "Expected no farther than ($tolerance) from ($expected), but got ($actual)")
        = assert(expected.equals(actual, tolerance = tolerance), message)



// MARK: -

interface TestCase
interface TestResult

@Suppress("unused", "MemberVisibilityCanPrivate")
data class FractionFunctionTestCase(val input: Fraction,
                                    val expectedOutput: Fraction,
                                    val tolerance: Tolerance = defaultCalculationTolerance,
                                    val message: String = "Input of $input should yield $expectedOutput (within a tolerance of $tolerance)"
) : TestCase {

    // TODO: Place expected output here:
    fun test(specialTolerance: Fraction = tolerance,
             block: (Fraction) -> Fraction): FractionFunctionTestResult {
        val actualOutput = block(input)
        return FractionFunctionTestResult(testCase = this,
                                          specialTolerance = specialTolerance,
                                          actualOutput = actualOutput,
                                          success = expectedOutput.equals(actualOutput, specialTolerance)
        )
    }
}


data class FractionFunctionTestResult(
        val testCase: FractionFunctionTestCase,
        val specialTolerance: Fraction,
        val actualOutput: Fraction,
        val success: Boolean,
        val message: String = "Input of ${testCase.input} should yield ${testCase.expectedOutput} (within a tolerance of ${specialTolerance}), actually yielded $actualOutput"
) : TestResult

    fun FractionFunctionTestResult.humanReadableString(): String = "${if (success) "[OK]" else "[FAIL]"}: $message"




fun Collection<FractionFunctionTestResult>.humanReadableString(): String =
        "[\n\t${this.joinToString(separator = ",\n\t", prefix = "", postfix = "", transform = FractionFunctionTestResult::humanReadableString)}\n]"



/**
 * Asserts that the given element is in the given collection
 *
 * @param message    _optional_ - The message sent on failure
 * @param collection The collection that should contain the element
 * @param element    The element that should be in the collection
 */
//@JvmOverloads
fun <C, Element> assertContains(message: String? = null, collection: C, element: Element)
        where C: Collection<Element> {
    assertTrue(message, collection.contains(element))
}


/**
 * Asserts that the given element is in the given size
 *
 * @param message _optional_ - The message sent on failure
 * @param size    The size that should contain the point
 * @param point   The point that should be in the size
 */
//@JvmOverloads
fun <Size, Point, NumberType> assertContains(message: String? = null, size: Size, point: Point)
        where Size: ComputableSize<NumberType>,
              Point : ComputablePoint<NumberType>,
              NumberType : Number,
              NumberType : Comparable<NumberType> {
    assertTrue(message, size.contains(point))
}
