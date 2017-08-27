package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.RoundingDirection.*
import org.bh.tools.base.math.RoundingThreshold.*
import org.junit.Test

import org.junit.Assert.*

/**
 * @author Kyli Rouge
 * *
 * @since 2017-02-04 004.
 */
class FractionUtilitiesTest {
    @Test
    fun getHasFractionComponent() {

    }

    @Test
    fun getComponents() {

    }

    @Test
    fun rounded() {

        // MARK: Basics

        assertRounded(expected = 0.0, beforeRounding = 0.0)
        assertRounded(expected = -0.0, beforeRounding = -0.0)


        // MARK: - Threshold: Halfway


        // MARK: up

        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = up, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = up, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.5, direction = up, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = up, threshold = halfway)

        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = up, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = up, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.5, direction = up, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = up, threshold = halfway)

        // MARK: down

        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = down, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = down, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.5, direction = down, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = down, threshold = halfway)

        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = down, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = down, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.5, direction = down, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = down, threshold = halfway)

        // MARK: away from zero

        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.5, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = awayFromZero, threshold = halfway)

        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.5, direction = awayFromZero, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = awayFromZero, threshold = halfway)

        // MARK: toward zero

        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = towardZero, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = towardZero, threshold = halfway)
        assertRounded(expected = 5.0, beforeRounding = 5.5, direction = towardZero, threshold = halfway)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = towardZero, threshold = halfway)

        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = towardZero, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = towardZero, threshold = halfway)
        assertRounded(expected = -5.0, beforeRounding = -5.5, direction = towardZero, threshold = halfway)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = towardZero, threshold = halfway)


        // MARK: - Threshold: Integer


        // MARK: up

        assertRounded(expected = 5.0, beforeRounding = 4.9, direction = up, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = up, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.4, direction = up, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.5, direction = up, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = up, threshold = integer)

        assertRounded(expected = -4.0, beforeRounding = -4.9, direction = up, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = up, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = up, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.5, direction = up, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.6, direction = up, threshold = integer)

        // MARK: down

        assertRounded(expected = 4.0, beforeRounding = 4.9, direction = down, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = down, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = down, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.5, direction = down, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.6, direction = down, threshold = integer)

        assertRounded(expected = -5.0, beforeRounding = -4.9, direction = down, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = down, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.4, direction = down, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.5, direction = down, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = down, threshold = integer)

        // MARK: away from zero

        assertRounded(expected = 5.0, beforeRounding = 4.9, direction = awayFromZero, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = awayFromZero, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.4, direction = awayFromZero, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.5, direction = awayFromZero, threshold = integer)
        assertRounded(expected = 6.0, beforeRounding = 5.6, direction = awayFromZero, threshold = integer)

        assertRounded(expected = -5.0, beforeRounding = -4.9, direction = awayFromZero, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = awayFromZero, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.4, direction = awayFromZero, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.5, direction = awayFromZero, threshold = integer)
        assertRounded(expected = -6.0, beforeRounding = -5.6, direction = awayFromZero, threshold = integer)

        // MARK: toward zero

        assertRounded(expected = 4.0, beforeRounding = 4.9, direction = towardZero, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.0, direction = towardZero, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.4, direction = towardZero, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.5, direction = towardZero, threshold = integer)
        assertRounded(expected = 5.0, beforeRounding = 5.6, direction = towardZero, threshold = integer)

        assertRounded(expected = -4.0, beforeRounding = -4.9, direction = towardZero, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.0, direction = towardZero, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.4, direction = towardZero, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.5, direction = towardZero, threshold = integer)
        assertRounded(expected = -5.0, beforeRounding = -5.6, direction = towardZero, threshold = integer)
    }


    @Test
    fun ceil_shifting() {
        assertCeil_shifting(expected = 5.0, beforeRounding = 4.9)
        assertCeil_shifting(expected = 5.0, beforeRounding = 5.0)
        assertCeil_shifting(expected = 6.0, beforeRounding = 5.4)
        assertCeil_shifting(expected = 6.0, beforeRounding = 5.5)
        assertCeil_shifting(expected = 6.0, beforeRounding = 5.6)

        assertCeil_shifting(expected = -5.0, beforeRounding = -4.9)
        assertCeil_shifting(expected = -5.0, beforeRounding = -5.0)
        assertCeil_shifting(expected = -6.0, beforeRounding = -5.4)
        assertCeil_shifting(expected = -6.0, beforeRounding = -5.5)
        assertCeil_shifting(expected = -6.0, beforeRounding = -5.6)
    }


    @Test
    fun ceil() {
        assertCeil(expected = 5.0, beforeRounding = 4.9)
        assertCeil(expected = 5.0, beforeRounding = 5.0)
        assertCeil(expected = 6.0, beforeRounding = 5.4)
        assertCeil(expected = 6.0, beforeRounding = 5.5)
        assertCeil(expected = 6.0, beforeRounding = 5.6)

        assertCeil(expected = -5.0, beforeRounding = -4.9)
        assertCeil(expected = -5.0, beforeRounding = -5.0)
        assertCeil(expected = -6.0, beforeRounding = -5.4)
        assertCeil(expected = -6.0, beforeRounding = -5.5)
        assertCeil(expected = -6.0, beforeRounding = -5.6)
    }


    @Test
    fun floor_shifting() {
        assertFloor_shifting(expected = 4.0, beforeRounding = 4.9)
        assertFloor_shifting(expected = 5.0, beforeRounding = 5.0)
        assertFloor_shifting(expected = 5.0, beforeRounding = 5.4)
        assertFloor_shifting(expected = 5.0, beforeRounding = 5.5)
        assertFloor_shifting(expected = 5.0, beforeRounding = 5.6)

        assertFloor_shifting(expected = -4.0, beforeRounding = -4.9)
        assertFloor_shifting(expected = -5.0, beforeRounding = -5.0)
        assertFloor_shifting(expected = -5.0, beforeRounding = -5.4)
        assertFloor_shifting(expected = -5.0, beforeRounding = -5.5)
        assertFloor_shifting(expected = -5.0, beforeRounding = -5.6)
    }


    @Test
    fun floor_truncating() {
        assertFloor_truncating(expected = 4.0, beforeRounding = 4.9)
        assertFloor_truncating(expected = 5.0, beforeRounding = 5.0)
        assertFloor_truncating(expected = 5.0, beforeRounding = 5.4)
        assertFloor_truncating(expected = 5.0, beforeRounding = 5.5)
        assertFloor_truncating(expected = 5.0, beforeRounding = 5.6)

        assertFloor_truncating(expected = -4.0, beforeRounding = -4.9)
        assertFloor_truncating(expected = -5.0, beforeRounding = -5.0)
        assertFloor_truncating(expected = -5.0, beforeRounding = -5.4)
        assertFloor_truncating(expected = -5.0, beforeRounding = -5.5)
        assertFloor_truncating(expected = -5.0, beforeRounding = -5.6)
    }


    @Test
    fun floor() {
        assertFloor(expected = 4.0, beforeRounding = 4.9)
        assertFloor(expected = 5.0, beforeRounding = 5.0)
        assertFloor(expected = 5.0, beforeRounding = 5.4)
        assertFloor(expected = 5.0, beforeRounding = 5.5)
        assertFloor(expected = 5.0, beforeRounding = 5.6)

        assertFloor(expected = -4.0, beforeRounding = -4.9)
        assertFloor(expected = -5.0, beforeRounding = -5.0)
        assertFloor(expected = -5.0, beforeRounding = -5.4)
        assertFloor(expected = -5.0, beforeRounding = -5.5)
        assertFloor(expected = -5.0, beforeRounding = -5.6)
    }
}

fun assertRounded(expected: Fraction, beforeRounding: Fraction, direction: RoundingDirection = RoundingDirection.default, threshold: RoundingThreshold = RoundingThreshold.default) {
    val message = "$beforeRounding rounded ${direction.humanReadableTestString} with $threshold threshold should be $expected"
    assertEquals(message, expected, beforeRounding.rounded(direction, threshold), defaultFractionCalculationTolerance)
}

fun assertCeil_shifting(expected: Fraction, beforeRounding: Fraction) {
    assertFracFunc(expected, beforeRounding, "ceil_shifting", Fraction::ceil_shifting)
}

fun assertCeil(expected: Fraction, beforeRounding: Fraction) {
    assertFracFunc(expected, beforeRounding, "ceil", Fraction::ceil)
}

fun assertFracFunc(expected: Fraction, beforeRounding: Fraction, funcName: String, ceilFun: FracFun) {
    val actual = ceilFun(beforeRounding)
    assertEquals("Expected $beforeRounding.$funcName() to be $expected, but was $actual", expected, actual, defaultFractionCalculationTolerance)
}

fun assertFloor_truncating(expected: Fraction, beforeRounding: Fraction) {
    assertFracFunc(expected, beforeRounding, "floor_truncating", Fraction::floor_truncating)
}

fun assertFloor_shifting(expected: Fraction, beforeRounding: Fraction) {
    assertFracFunc(expected, beforeRounding, "floor_shifting", Fraction::floor_shifting)
}

fun assertFloor(expected: Fraction, beforeRounding: Fraction) {
    assertFracFunc(expected, beforeRounding, "floor", Fraction::floor)
}

typealias FracFun = Fraction.() -> Fraction

val RoundingDirection.humanReadableTestString: String get() = when (this) {
    up -> "up"
    down -> "down"
    awayFromZero -> "away from zero"
    towardZero -> "toward zero"
}
