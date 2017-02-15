package org.bh.tools.base.util

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.math.Averager
import org.bh.tools.base.util.TimeConversion.Companion.nanosecondsToTimeInterval
import kotlin.system.measureNanoTime

/*
 * To aid in testing
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */


typealias TestMeasurementBlock = () -> Unit



val defaultMeasurementTrialCount = 10L

/**
 * Measures the given block the given number of times and returns the average or total, depending on `mode`
 *
 * @param trials The number of times to measure the given block
 * @param mode   The mode by which the measurement is taken, which affects the returned value
 * @param block  The block to measure
 */
inline fun measureTimeInterval(trials: Integer = defaultMeasurementTrialCount, mode: TimeTrialMeasurementMode = TimeTrialMeasurementMode.average, block: TestMeasurementBlock): TimeInterval {
    return when (mode) {
        TimeTrialMeasurementMode.average -> averageTimeInterval(trials = trials, block = block)
        TimeTrialMeasurementMode.total -> totalTimeInterval(trials = trials, block = block)
    }
}



/**
 * Measures the given block the given number of times and returns the average
 *
 * @param trials The number of times to measure the given block
 * @param block  The block to measure
 */
inline fun averageTimeInterval(trials: Integer = defaultMeasurementTrialCount, block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first test to spin up caching
    block()

    val averager = Averager()

    for (i in 0..trials) {
        averager.average(nanosecondsToTimeInterval(measureNanoTime(block)))
    }

    return averager.current
}



/**
 * Measures the given block the given number of times and returns the total
 *
 * @param trials The number of times to measure the given block
 * @param block  The block to measure
 */
inline fun totalTimeInterval(trials: Integer = defaultMeasurementTrialCount, block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first test to spin up caching
    block()

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
