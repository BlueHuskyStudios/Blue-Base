package org.bh.tools.base.util

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
 * Measures the given block the given number of times and returns the average
 *
 * @param trials The number of times to measure the given block
 * @param block  The block to measure
 */
inline fun measureTimeInterval(trials: Integer = defaultMeasurementTrialCount, block: TestMeasurementBlock): TimeInterval {
    if (trials <= 0) {
        return TimeInterval.NaN
    }

    // Throw away the first test to spin up caching
    block()

    val averager = Averager()

    for (i in 0..trials) {
        averager.average(nanosecondsToTimeInterval(measureNanoTime(block)))
    }

    return averager.current()
}
