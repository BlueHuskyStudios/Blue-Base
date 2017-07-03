@file:JvmName("TestUtils")

package org.bh.tools.base.util

import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.extensions.length
import org.bh.tools.base.func.StringSupplier
import org.bh.tools.base.math.Averager
import org.bh.tools.base.math.clampToPositive
import org.bh.tools.base.util.TimeConversion.nanosecondsToTimeInterval
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import kotlin.system.measureNanoTime

/*
 * To aid in testing
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */


typealias TestMeasurementBlock = () -> Unit



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
@JvmOverloads
inline fun measureTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                               warmupTrials: Integer = defaultWarmupTrialCount,
                               mode: TimeTrialMeasurementMode = TimeTrialMeasurementMode.average,
                               block: TestMeasurementBlock): TimeInterval {
    return when (mode) {
        TimeTrialMeasurementMode.average -> averageTimeInterval(trials = trials, warmupTrials = warmupTrials, block = block)
        TimeTrialMeasurementMode.total -> totalTimeInterval(trials = trials, warmupTrials = warmupTrials, block = block)
    }
}



/**
 * Measures the given block the given number of times and returns the average
 *
 * @param trials       The number of times to measure the given block
 * @param warmupTrials The number of times to execute the block before taking measurements, to decrease warmup bias
 * @param block        The block to measure
 */
@JvmOverloads
inline fun averageTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                               warmupTrials: Integer = defaultWarmupTrialCount,
                               block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first few tests to spin up caching
    for (i in 0..warmupTrials.clampToPositive) {
        block()
    }

    val averager = Averager()

    for (i in 0..trials) {
        averager.average(nanosecondsToTimeInterval(measureNanoTime(block)))
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
@JvmOverloads
inline fun totalTimeInterval(trials: Integer = defaultMeasurementTrialCount,
                             warmupTrials: Integer = defaultWarmupTrialCount,
                             block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first few tests to spin up caching
    for (i in 0..warmupTrials.clampToPositive) {
        block()
    }

    var total: TimeInterval = 0.0

    for (i in 0..trials) {
        total += nanosecondsToTimeInterval(measureNanoTime(block))
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



// MARK: - Assertions

@Suppress("NOTHING_TO_INLINE")
@JvmOverloads
inline fun assertionFailure(message: String? = null) = assertTrue(message, false)

inline fun assertionFailure(message: StringSupplier) = assertTrue(message(), false)


@JvmOverloads
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
@JvmOverloads
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
@JvmOverloads
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


inline fun assertTrue(message: StringSupplier, condition: Boolean) {
    if (!condition) {
        assertionFailure(message)
    }
}
