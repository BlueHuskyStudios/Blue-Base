package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.double_π

/*
 * Makes geometry easier
 *
 * @author Ben Leggiero
 * @since 2017-03-15
 */


/**
 * Converts any amount of radians, whether it be positive, negative, within 2pi, or past 2pi, and converts it to a
 * "normalized" form, which is positive radians between 0 and 2pi
 */
fun anyRadiansToNormalizedRadians(radians: Fraction): Fraction {
    if (radians >= 0) {
        return radians % Fraction.double_π
    } else {
        return (radians % Fraction.double_π) + Fraction.double_π
    }
}
