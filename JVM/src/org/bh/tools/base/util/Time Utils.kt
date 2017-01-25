@file:Suppress("unused")

package org.bh.tools.base.util

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.floatValue
import org.bh.tools.base.util.TimeConversion.Companion.nanosecondsPerMillisecond
import org.bh.tools.base.util.TimeConversion.Companion.nanosecondsPerSecond
import java.time.Instant
import java.util.*

/*
 * To make time easier to deal with
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */


/**
 * An interval of time, by default in seconds
 */
typealias TimeInterval = BHFloat

inline val Int8.timeIntervalValue: TimeInterval get() = floatValue
inline val Int16.timeIntervalValue: TimeInterval get() = floatValue
inline val Int32.timeIntervalValue: TimeInterval get() = floatValue
inline val Int64.timeIntervalValue: TimeInterval get() = floatValue
inline val Float32.timeIntervalValue: TimeInterval get() = floatValue
inline val Float64.timeIntervalValue: TimeInterval get() = this



sealed class TimeConversion {
    companion object {
        val daysPerWeek: TimeInterval = 7.0
        val daysPerYear: TimeInterval = 365.24219

        val hoursPerDay: TimeInterval = 24.0

        val minutesPerHour: TimeInterval = 60.0

        val secondsPerMinute: TimeInterval = 60.0
        val secondsPerHour: TimeInterval = secondsPerMinute * minutesPerHour
        val secondsPerDay: TimeInterval = secondsPerHour * hoursPerDay
        val secondsPerWeek: TimeInterval = secondsPerDay * daysPerWeek
        val secondsPerYear: TimeInterval = secondsPerDay * daysPerYear

        val millisecondsPerSecond: TimeInterval = 1_000.0

        val nanosecondsPerMicrosecond: TimeInterval = 1_000.0
        val nanosecondsPerMillisecond: TimeInterval = 1_000_000.0
        val nanosecondsPerSecond: TimeInterval = 1_000_000_000.0


        fun nanosecondsToTimeInterval(nanoseconds: BHInt): TimeInterval = nanoseconds.floatValue / nanosecondsPerSecond
    }
}


/**
 * The number of seconds since the Java epoch: January 1st, 1970 at 00:00:00.0000
 */
val Instant.timeIntervalSinceJavaEpoch: TimeInterval get() {
    val millisecondsSinceJavaEpoch = toEpochMilli().floatValue
    val nanosecondsSinceLastSecond = nano.floatValue

    val nanosecondsSinceJavaEpoch = (millisecondsSinceJavaEpoch * nanosecondsPerMillisecond) + nanosecondsSinceLastSecond

    val secondsSinceJavaEpoch = nanosecondsSinceJavaEpoch / nanosecondsPerSecond

    return secondsSinceJavaEpoch
}


/**
 * The number of seconds since the Java epoch: January 1st, 1970 at 00:00:00.0000
 */
val Date.timeIntervalSinceJavaEpoch: TimeInterval get() = toInstant().timeIntervalSinceJavaEpoch



fun Date.timeIntervalSince(other: Date): TimeInterval {
    val thisIntervalSinceJavaEpoch = this.timeIntervalSinceJavaEpoch
    val otherIntervalSinceJavaEpoch = other.timeIntervalSinceJavaEpoch

    return thisIntervalSinceJavaEpoch - otherIntervalSinceJavaEpoch
}
