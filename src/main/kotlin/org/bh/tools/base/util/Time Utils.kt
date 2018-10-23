@file:Suppress("unused")

package org.bh.tools.base.util

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import org.bh.tools.base.util.TimeConversion.daysPerYear
import org.bh.tools.base.util.TimeConversion.nanosecondsPerMillisecond
import org.bh.tools.base.util.TimeConversion.nanosecondsPerSecond

/*
 * To make time easier to deal with
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */


/**
 * An interval of time, by default in seconds
 */
typealias TimeInterval = Fraction

inline val Int8.timeIntervalValue: TimeInterval get() = fractionValue
inline val Int16.timeIntervalValue: TimeInterval get() = fractionValue
inline val Int32.timeIntervalValue: TimeInterval get() = fractionValue
inline val Int64.timeIntervalValue: TimeInterval get() = fractionValue
inline val Float32.timeIntervalValue: TimeInterval get() = fractionValue
inline val Float64.timeIntervalValue: TimeInterval get() = this



/**
 * A bunch of constants and functions that allow for easy conversion between timescales.
 *
 * For long timescales, [tropical years and ephemeral days][daysPerYear] are assumed. For more about these, read
 * [_Tropical year_ on Wikipedia](https://en.wikipedia.org/wiki/Tropical_year). **Leap seconds are not considered.**
 */
object TimeConversion {

    /** The number of days in a week */
    const val daysPerWeek: TimeInterval = 7.0
    /**
     * The mean number of SI days in a tropical Earth year, as of 2017: `365.242189`
     *
     * **See also:**
     *  - [_Tropical year_ - Wikipedia](https://en.wikipedia.org/wiki/Tropical_year)
     */
    const val daysPerYear: TimeInterval = 365.242189

    /** The number of weeks in a [tropical year][daysPerYear] */
    const val weeksPerYear: TimeInterval = daysPerYear / daysPerWeek

    /** The number of hours in a day */
    const val hoursPerDay: TimeInterval = 24.0
    /** The number of hours in a week */
    const val hoursPerWeek: TimeInterval = hoursPerDay * daysPerWeek
    /** The number of hours in a [tropical year][daysPerYear] */
    const val hoursPerYear: TimeInterval = hoursPerDay * daysPerYear

    /** The number of minutes in an hour */
    const val minutesPerHour: TimeInterval = 60.0
    /** The number of minutes in a day */
    const val minutesPerDay: TimeInterval = minutesPerHour * hoursPerDay
    /** The number of minutes in a week */
    const val minutesPerWeek: TimeInterval = minutesPerDay * daysPerWeek
    /** The number of minutes in a [tropical year][daysPerYear] */
    const val minutesPerYear: TimeInterval = minutesPerDay * daysPerYear

    /** The number of SI seconds in a minute */
    const val secondsPerMinute: TimeInterval = 60.0
    /** The number of SI seconds in an hour */
    const val secondsPerHour: TimeInterval = secondsPerMinute * minutesPerHour
    /** The number of SI seconds in a day */
    const val secondsPerDay: TimeInterval = secondsPerHour * hoursPerDay
    /** The number of SI seconds in a week */
    const val secondsPerWeek: TimeInterval = secondsPerDay * daysPerWeek
    /** The number of SI seconds in a [tropical year][daysPerYear] */
    const val secondsPerYear: TimeInterval = secondsPerDay * daysPerYear

    /** The number of milliseconds in an SI second */
    const val millisecondsPerSecond: TimeInterval = 1_000.0
    /** The number of milliseconds in a minute */
    const val millisecondsPerMinute: TimeInterval = millisecondsPerSecond * secondsPerMinute
    /** The number of milliseconds in an hour */
    const val millisecondsPerHour: TimeInterval = millisecondsPerSecond * secondsPerHour
    /** The number of milliseconds in a day */
    const val millisecondsPerDay: TimeInterval = millisecondsPerSecond * secondsPerDay
    /** The number of milliseconds in a week */
    const val millisecondsPerWeek: TimeInterval = millisecondsPerSecond * secondsPerWeek
    /** The number of milliseconds in a [tropical year][daysPerYear] */
    const val millisecondsPerYear: TimeInterval = millisecondsPerSecond * secondsPerYear

    /** The number of microseconds in a millisecond */
    const val microsecondsPerMillisecond: TimeInterval = 1_000.0
    /** The number of microseconds in an SI second */
    const val microsecondsPerSecond: TimeInterval = 1_000_000.0
    /** The number of microseconds in a minute */
    const val microsecondsPerMinute: TimeInterval = microsecondsPerSecond * secondsPerMinute
    /** The number of microseconds in an hour */
    const val microsecondsPerHour: TimeInterval = microsecondsPerSecond * secondsPerHour
    /** The number of microseconds in a day */
    const val microsecondsPerDay: TimeInterval = microsecondsPerSecond * secondsPerDay
    /** The number of microseconds in a week */
    const val microsecondsPerWeek: TimeInterval = microsecondsPerSecond * secondsPerWeek
    /** The number of microseconds in a [tropical year][daysPerYear] */
    const val microsecondsPerYear: TimeInterval = microsecondsPerSecond * secondsPerYear

    /** The number of nanoseconds in a microsecond */
    const val nanosecondsPerMicrosecond: TimeInterval = 1_000.0
    /** The number of nanoseconds in a millisecond */
    const val nanosecondsPerMillisecond: TimeInterval = 1_000_000.0
    /** The number of nanoseconds in an SI second */
    const val nanosecondsPerSecond: TimeInterval = 1_000_000_000.0
    /** The number of nanoseconds in a minute */
    const val nanosecondsPerMinute: TimeInterval = nanosecondsPerSecond * secondsPerMinute
    /** The number of nanoseconds in an hour */
    const val nanosecondsPerHour: TimeInterval = nanosecondsPerSecond * secondsPerHour
    /** The number of nanoseconds in a day */
    const val nanosecondsPerDay: TimeInterval = nanosecondsPerSecond * secondsPerDay
    /** The number of nanoseconds in a week */
    const val nanosecondsPerWeek: TimeInterval = nanosecondsPerSecond * secondsPerWeek
    /** The number of nanoseconds in a [tropical year][daysPerYear] */
    const val nanosecondsPerYear: TimeInterval = nanosecondsPerSecond * secondsPerYear


    /** The number of [tropical years][daysPerYear] in a nanosecond */
    const val yearsPerNanosecond: TimeInterval = 1.0 / nanosecondsPerYear
    /** The number of [tropical years][daysPerYear] in a microsecond */
    const val yearsPerMicrosecond: TimeInterval = 1.0 / microsecondsPerYear
    /** The number of [tropical years][daysPerYear] in a millisecond */
    const val yearsPerMillisecond: TimeInterval = 1.0 / millisecondsPerYear
    /** The number of [tropical years][daysPerYear] in a second */
    const val yearsPerSecond: TimeInterval = 1.0 / secondsPerYear
    /** The number of [tropical years][daysPerYear] in a minute */
    const val yearsPerMinute: TimeInterval = 1.0 / minutesPerYear
    /** The number of [tropical years][daysPerYear] in an hour */
    const val yearsPerHour: TimeInterval = 1.0 / hoursPerYear
    /** The number of [tropical years][daysPerYear] in a day */
    const val yearsPerDay: TimeInterval = 1.0 / daysPerYear
    /** The number of [tropical years][daysPerYear] in a week */
    const val yearsPerWeek: TimeInterval = 1.0 / weeksPerYear

    /** The number of weeks in a nanosecond */
    const val weeksPerNanosecond: TimeInterval = 1.0 / nanosecondsPerWeek
    /** The number of weeks in a microsecond */
    const val weeksPerMicrosecond: TimeInterval = 1.0 / microsecondsPerWeek
    /** The number of weeks in a millisecond */
    const val weeksPerMillisecond: TimeInterval = 1.0 / millisecondsPerWeek
    /** The number of weeks in a second */
    const val weeksPerSecond: TimeInterval = 1.0 / secondsPerWeek
    /** The number of weeks in a minute */
    const val weeksPerMinute: TimeInterval = 1.0 / minutesPerWeek
    /** The number of weeks in an hour */
    const val weeksPerHour: TimeInterval = 1.0 / hoursPerWeek
    /** The number of weeks in a day */
    const val weeksPerDay: TimeInterval = 1.0 / daysPerWeek

    /** The number of days in a nanosecond */
    const val daysPerNanosecond: TimeInterval = 1.0 / nanosecondsPerDay
    /** The number of days in a microsecond */
    const val daysPerMicrosecond: TimeInterval = 1.0 / microsecondsPerDay
    /** The number of days in a millisecond */
    const val daysPerMillisecond: TimeInterval = 1.0 / millisecondsPerDay
    /** The number of days in a second */
    const val daysPerSecond: TimeInterval = 1.0 / secondsPerDay
    /** The number of days in a minute */
    const val daysPerMinute: TimeInterval = 1.0 / minutesPerDay
    /** The number of days in an hour */
    const val daysPerHour: TimeInterval = 1.0 / hoursPerDay

    /** The number of hours in a nanosecond */
    const val hoursPerNanosecond: TimeInterval = 1.0 / nanosecondsPerHour
    /** The number of hours in a microsecond */
    const val hoursPerMicrosecond: TimeInterval = 1.0 / microsecondsPerHour
    /** The number of hours in a millisecond */
    const val hoursPerMillisecond: TimeInterval = 1.0 / millisecondsPerHour
    /** The number of hours in a second */
    const val hoursPerSecond: TimeInterval = 1.0 / secondsPerHour
    /** The number of hours in a minute */
    const val hoursPerMinute: TimeInterval = 1.0 / minutesPerHour

    /** The number of minutes in a nanosecond */
    const val minutesPerNanosecond: TimeInterval = 1.0 / nanosecondsPerMinute
    /** The number of minutes in a microsecond */
    const val minutesPerMicrosecond: TimeInterval = 1.0 / microsecondsPerMinute
    /** The number of minutes in a millisecond */
    const val minutesPerMillisecond: TimeInterval = 1.0 / millisecondsPerMinute
    /** The number of minutes in a second */
    const val minutesPerSecond: TimeInterval = 1.0 / secondsPerMinute

    /** The number of seconds in a nanosecond */
    const val secondsPerNanosecond: TimeInterval = 1.0 / nanosecondsPerSecond
    /** The number of seconds in a microsecond */
    const val secondsPerMicrosecond: TimeInterval = 1.0 / microsecondsPerSecond
    /** The number of seconds in a millisecond */
    const val secondsPerMillisecond: TimeInterval = 1.0 / millisecondsPerSecond

    /** The number of milliseconds in a nanosecond */
    const val millisecondsPerNanosecond: TimeInterval = 1.0 / nanosecondsPerMillisecond
    /** The number of milliseconds in a microsecond */
    const val millisecondsPerMicrosecond: TimeInterval = 1.0 / microsecondsPerMillisecond

    /** The number of microseconds in a nanosecond */
    const val microsecondsPerNanosecond: TimeInterval = 1.0 / nanosecondsPerMicrosecond


    // MARK: Optimized oft-used conversions

    /** Converts an integer number of nanoseconds into SI seconds as a [TimeInterval] */
    @Suppress("NOTHING_TO_INLINE")
    inline fun nanosecondsToTimeInterval(nanoseconds: Integer): TimeInterval = nanoseconds.fractionValue / nanosecondsPerSecond

    /** Converts a number of nanoseconds into SI seconds as a [TimeInterval] */
    @Suppress("NOTHING_TO_INLINE")
    inline fun nanosecondsToTimeInterval(nanoseconds: Fraction): TimeInterval = nanoseconds / nanosecondsPerSecond

    /** Converts an integer number of milliseconds into SI seconds as a [TimeInterval] */
    @Suppress("NOTHING_TO_INLINE")
    inline fun millisecondsToTimeInterval(milliseconds: Integer): TimeInterval = milliseconds.fractionValue / millisecondsPerSecond

    /** Converts a number of milliseconds into SI seconds as a [TimeInterval] */
    @Suppress("NOTHING_TO_INLINE")
    inline fun millisecondsToTimeInterval(milliseconds: Fraction): TimeInterval = milliseconds / millisecondsPerSecond


    // MARK: General conversion

    /**
     * Converts any number of any unit of time into the corresponding number of another unit.
     *
     * For instance, `convert(`[`TimeAmount`][TimeAmount]`(5, `[`minutes`][TimeUnit.minutes]`), `[`seconds`][TimeUnit.seconds]`) == TimeAmount(300, seconds)`
     */
    fun convert(oldAmount: TimeAmount, newUnit: TimeUnit): TimeAmount {
        return TimeAmount(
                amount = oldAmount.amount * newUnit.per(oldAmount.unit),
                unit = newUnit
        )
    }
}


enum class TimeUnit: Comparable<TimeUnit> {
    /** Nanoseconds: One billionth of a [second][seconds] */
    nanoseconds,
    /** Microseconds: One millionth of a [second][seconds] */
    microseconds,
    /** Milliseconds: One thousandth of a [second][seconds] */
    milliseconds,
    /** Seconds: The SI base unit of time, 60 of which make up a [minute][minutes] */
    seconds,
    /** Minutes: 60 SI [seconds], 60 of which make up an [hour][hours] */
    minutes,
    /** Hours: 3,600 SI [seconds], 24 of which make up a [day][days] */
    hours,
    /** Days: 86,400 SI [seconds], 7 of which make up a [week][weeks], `365.242188792` of which make up a [year][years]; 24 [hours] */
    days,
    /** Weeks: 172,800 [seconds]; 7 [days] */
    weeks,
    /** Years: 1 Mean Tropical Year; 365.24219 [days] */
    years;

    /** Finds the number of `this` units in an [other]. For instance, [`seconds`][seconds]`.`[`per`][per]`(`[`minute`][minute]`)` == `60.0` */
    infix fun per(other: TimeUnit) = when (this) {
        nanoseconds -> when (other) {
            nanoseconds -> 1.0
            microseconds -> TimeConversion.nanosecondsPerMicrosecond
            milliseconds -> nanosecondsPerMillisecond
            seconds -> nanosecondsPerSecond
            minutes -> TimeConversion.nanosecondsPerMinute
            hours -> TimeConversion.nanosecondsPerHour
            days -> TimeConversion.nanosecondsPerDay
            weeks -> TimeConversion.nanosecondsPerWeek
            years -> TimeConversion.nanosecondsPerYear
        }
        microseconds -> when (other) {
            nanoseconds -> TimeConversion.microsecondsPerNanosecond
            microseconds -> 1.0
            milliseconds -> TimeConversion.microsecondsPerMillisecond
            seconds -> TimeConversion.microsecondsPerSecond
            minutes -> TimeConversion.microsecondsPerMinute
            hours -> TimeConversion.microsecondsPerHour
            days -> TimeConversion.microsecondsPerDay
            weeks -> TimeConversion.microsecondsPerWeek
            years -> TimeConversion.microsecondsPerYear
        }
        milliseconds -> when (other) {
            nanoseconds -> TimeConversion.millisecondsPerNanosecond
            microseconds -> TimeConversion.millisecondsPerMicrosecond
            milliseconds -> 1.0
            seconds -> TimeConversion.millisecondsPerSecond
            minutes -> TimeConversion.millisecondsPerMinute
            hours -> TimeConversion.millisecondsPerHour
            days -> TimeConversion.millisecondsPerDay
            weeks -> TimeConversion.millisecondsPerWeek
            years -> TimeConversion.millisecondsPerYear
        }
        seconds -> when (other) {
            nanoseconds -> TimeConversion.secondsPerNanosecond
            microseconds -> TimeConversion.secondsPerMicrosecond
            milliseconds -> TimeConversion.secondsPerMillisecond
            seconds -> 1.0
            minutes -> TimeConversion.secondsPerMinute
            hours -> TimeConversion.secondsPerHour
            days -> TimeConversion.secondsPerDay
            weeks -> TimeConversion.secondsPerWeek
            years -> TimeConversion.secondsPerYear
        }
        minutes -> when (other) {
            nanoseconds -> TimeConversion.minutesPerNanosecond
            microseconds -> TimeConversion.minutesPerMicrosecond
            milliseconds -> TimeConversion.minutesPerMillisecond
            seconds -> TimeConversion.minutesPerSecond
            minutes -> 1.0
            hours -> TimeConversion.minutesPerHour
            days -> TimeConversion.minutesPerDay
            weeks -> TimeConversion.minutesPerWeek
            years -> TimeConversion.minutesPerYear
        }
        hours -> when (other) {
            nanoseconds -> TimeConversion.hoursPerNanosecond
            microseconds -> TimeConversion.hoursPerMicrosecond
            milliseconds -> TimeConversion.hoursPerMillisecond
            seconds -> TimeConversion.hoursPerSecond
            minutes -> TimeConversion.hoursPerMinute
            hours -> 1.0
            days -> TimeConversion.hoursPerDay
            weeks -> TimeConversion.hoursPerWeek
            years -> TimeConversion.hoursPerYear
        }
        days -> when (other) {
            nanoseconds -> TimeConversion.daysPerNanosecond
            microseconds -> TimeConversion.daysPerMicrosecond
            milliseconds -> TimeConversion.daysPerMillisecond
            seconds -> TimeConversion.daysPerSecond
            minutes -> TimeConversion.daysPerMinute
            hours -> TimeConversion.daysPerHour
            days -> 1.0
            weeks -> TimeConversion.daysPerWeek
            years -> TimeConversion.daysPerYear
        }
        weeks -> when (other) {
            nanoseconds -> TimeConversion.weeksPerNanosecond
            microseconds -> TimeConversion.weeksPerMicrosecond
            milliseconds -> TimeConversion.weeksPerMillisecond
            seconds -> TimeConversion.weeksPerSecond
            minutes -> TimeConversion.weeksPerMinute
            hours -> TimeConversion.weeksPerHour
            days -> TimeConversion.weeksPerDay
            weeks -> 1.0
            years -> TimeConversion.weeksPerYear
        }
        years -> when (other) {
            nanoseconds -> TimeConversion.yearsPerNanosecond
            microseconds -> TimeConversion.yearsPerMicrosecond
            milliseconds -> TimeConversion.yearsPerMillisecond
            seconds -> TimeConversion.yearsPerSecond
            minutes -> TimeConversion.yearsPerMinute
            hours -> TimeConversion.yearsPerHour
            days -> TimeConversion.yearsPerDay
            weeks -> TimeConversion.yearsPerWeek
            years -> 1.0
        }
    }

    companion object {
        /** Syntactic alias for [nanoseconds] */
        inline val nanosecond get() = nanoseconds

        /** Syntactic alias for [microseconds] */
        inline val microsecond get() = microseconds

        /** Syntactic alias for [milliseconds] */
        inline val millisecond get() = milliseconds

        /** Syntactic alias for [seconds] */
        inline val second get() = seconds

        /** Syntactic alias for [minutes] */
        inline val minute get() = minutes

        /** Syntactic alias for [hours] */
        inline val hour get() = hours

        /** Syntactic alias for [days] */
        inline val day get() = days

        /** Syntactic alias for [weeks] */
        inline val week get() = weeks

        /** Syntactic alias for [years] */
        inline val year get() = years



        /** Syntactic alias for [seconds] */
        inline val interval get() = seconds

        /** Syntactic alias for [seconds] */
        inline val timeInterval get() = seconds
    }
}


data class TimeAmount(val amount: TimeInterval, val unit: TimeUnit)



val TimeInterval.asMilliseconds get() = TimeAmount(this, TimeUnit.milliseconds)
val TimeInterval.asNanoseconds get() = TimeAmount(this, TimeUnit.nanoseconds)


val TimeAmount.seconds get() = this.convert(to= TimeUnit.seconds)
val TimeAmount.timeIntervalValue get() = this.convert(to= TimeUnit.timeInterval).amount


fun TimeAmount.convert(to: TimeUnit): TimeAmount = TimeConversion.convert(this, to)
