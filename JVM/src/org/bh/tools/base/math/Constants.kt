@file:Suppress("unused")

package org.bh.tools.base.math

import org.bh.tools.base.abstraction.Float32
import org.bh.tools.base.abstraction.Float64

/*
 * Some constants at convenient places
 *
 * @author Ben Leggiero
 * @since 2017-03-12
 */


// MARK: - Standard mathematical constants

// Most of the following are from standard C++ libraries:

private const val Float64_e              = 2.7182818284590452354   /* e */
private const val Float64_log2_e         = 1.4426950408889634074   /* log_2(e) */
private const val Float64_log10_e        = 0.43429448190325182765  /* log_10(e) */
private const val Float64_ln_2           = 0.69314718055994530942  /* log_e(2) */
private const val Float64_ln_10          = 2.30258509299404568402  /* log_e(10) */

private const val Float64_quarter_pi     = 0.78539816339744830962  /* pi/4 */
private const val Float64_half_pi        = 1.57079632679489661923  /* pi/2 */
private const val Float64_3_quarter_pi   = 2.35619449019234492884  /* 3 pi/4 */
private const val Float64_pi             = 3.14159265358979323846  /* pi */
private const val Float64_5_quarter_pi   = 3.92699081698724154807  /* 5 pi/4 */
private const val Float64_7_quarter_pi   = 5.49778714378213816730  /* 7 pi/4 */
private const val Float64_double_pi      = 6.28318530717958647692  /* 2 pi */
private const val Float64_quadruple_pi   = 12.5663706143591729538  /* 4 pi */
private const val Float64_inverse_pi     = 0.31830988618379067154  /* 1/pi */
private const val Float64_2_over_pi      = 0.63661977236758134308  /* 2/pi */
private const val Float64_2_over_sqrt_pi = 1.12837916709551257390  /* 2/sqrt(pi) */

private const val Float64_sqrt_2         = 1.41421356237309504880  /* sqrt(2) */
private const val Float64_inverse_sqrt_2 = 0.70710678118654752440  /* 1/sqrt(2) */


private const val Float32_e              = 2.7182818284590452354f   /* e */
private const val Float32_log2_e         = 1.4426950408889634074f   /* log_2(e) */
private const val Float32_log10_e        = 0.43429448190325182765f  /* log_10(e) */
private const val Float32_ln_2           = 0.69314718055994530942f  /* log_e(2) */
private const val Float32_ln_10          = 2.30258509299404568402f  /* log_e(10) */

private const val Float32_pi             = 3.14159265358979323846f  /* pi */
private const val Float32_half_pi        = 1.57079632679489661923f  /* pi/2 */
private const val Float32_quarter_pi     = 0.78539816339744830962f  /* pi/4 */
private const val Float32_3_quarter_pi   = 2.35619449019234492884f  /* 3 pi/4 */
private const val Float32_5_quarter_pi   = 3.92699081698724154807f  /* 5 pi/4 */
private const val Float32_7_quarter_pi   = 5.49778714378213816730f  /* 7 pi/4 */
private const val Float32_double_pi      = 6.28318530717958647692f  /* 2 pi */
private const val Float32_quadruple_pi   = 12.5663706143591729538f  /* 4 pi */
private const val Float32_inverse_pi     = 0.31830988618379067154f  /* 1/pi */
private const val Float32_2_over_pi      = 0.63661977236758134308f  /* 2/pi */
private const val Float32_2_over_sqrt_pi = 1.12837916709551257390f  /* 2/sqrt(pi) */

private const val Float32_sqrt_2         = 1.41421356237309504880f  /* sqrt(2) */
private const val Float32_inverse_sqrt_2 = 0.70710678118654752440f  /* 1/sqrt(2) */


// MARK: 64-bit companion extensions

/** The mathematical constant _e_ */
val Double.Companion.e: Float64 get() = Float64_e

/** log base `2` of [e] */
val Double.Companion.log2_e: Float64 get() = Float64_log2_e

/** log base `10` of [e] */
val Double.Companion.log10_e: Float64 get() = Float64_log10_e

/** The natural log (log base [e]) of `2` */
val Double.Companion.ln_2: Float64 get() = Float64_ln_2

/** The natural log (log base [e]) of `10` */
val Double.Companion.ln_10: Float64 get() = Float64_ln_10


/** The mathematical constant &pi; */
val Double.Companion.pi: Float64 get() = Float64_pi
/** The mathematical constant &pi; */
inline val Double.Companion.π: Float64 get() = pi

/** [&pi;][pi] / 2 */
val Double.Companion.half_pi: Float64 get() = Float64_half_pi
/** [&pi;][pi] / 2 */
inline val Double.Companion.half_π: Float64 get() = half_pi

/** [&pi;][pi] / 4 */
val Double.Companion.quarter_pi: Float64 get() = Float64_quarter_pi
/** [&pi;][pi] / 4 */
inline val Double.Companion.quarter_π: Float64 get() = quarter_pi

/** 3[&pi;][pi] / 4 */
val Double.Companion.three_quarter_pi: Float64 get() = Float64_3_quarter_pi
/** 3[&pi;][pi] / 4 */
inline val Double.Companion.three_quarter_π: Float64 get() = three_quarter_pi

/** 5[&pi;][pi] / 4 */
val Double.Companion.five_quarter_pi: Float64 get() = Float64_5_quarter_pi
/** 5[&pi;][pi] / 4 */
inline val Double.Companion.five_quarter_π: Float64 get() = five_quarter_pi

/** 7[&pi;][pi] / 4 */
val Double.Companion.seven_quarter_pi: Float64 get() = Float64_7_quarter_pi
/** 7[&pi;][pi] / 4 */
inline val Double.Companion.seven_quarter_π: Float64 get() = seven_quarter_pi

/** 2 [&pi;][pi] */
val Double.Companion.double_pi: Float64 get() = Float64_double_pi
/** 2 [&pi;][pi] */
inline val Double.Companion.double_π: Float64 get() = double_pi

/** 4 [&pi;][pi] */
val Double.Companion.quadruple_pi: Float64 get() = Float64_quadruple_pi
/** 4 [&pi;][pi] */
inline val Double.Companion.quadruple_π: Float64 get() = quadruple_pi

/** 1 / [&pi;][pi] */
val Double.Companion.inverse_pi: Float64 get() = Float64_inverse_pi
/** 1 / [&pi;][pi] */
inline val Double.Companion.inverse_π: Float64 get() = inverse_pi

/** 2 / [&pi;][pi] */
val Double.Companion.two_over_pi: Float64 get() = Float64_2_over_pi
/** 2 / [&pi;][pi] */
inline val Double.Companion.two_over_π: Float64 get() = two_over_pi

/** 2 / &radic;[&pi;][pi] */
val Double.Companion.two_over_sqrt_pi: Float64 get() = Float64_2_over_sqrt_pi
/** 2 / &radic;[&pi;][pi] */
inline val Double.Companion.two_over_sqrt_π: Float64 get() = two_over_sqrt_pi


/** &radic;2 */
val Double.Companion.sqrt_2: Float64 get() = Float64_sqrt_2

/** 1 / &radic;2 */
val Double.Companion.inverse_sqrt_2: Float64 get() = Float64_inverse_sqrt_2


// MARK: 32-bit companion objects

/** The mathematical constant _e_ */
val Float.Companion.e: Float32 get() = Float32_e

/** log base `2` of [e] */
val Float.Companion.log2_e: Float32 get() = Float32_log2_e

/** log base `10` of [e] */
val Float.Companion.log10_e: Float32 get() = Float32_log10_e

/** The natural log (log base [e]) of `2` */
val Float.Companion.ln_2: Float32 get() = Float32_ln_2

/** The natural log (log base [e]) of `10` */
val Float.Companion.ln_10: Float32 get() = Float32_ln_10


/** The mathematical constant &pi; */
val Float.Companion.pi: Float32 get() = Float32_pi
/** The mathematical constant &pi; */
inline val Float.Companion.π: Float32 get() = pi

/** [&pi;][pi] / 2 */
val Float.Companion.half_pi: Float32 get() = Float32_half_pi
/** [&pi;][pi] / 2 */
inline val Float.Companion.half_π: Float32 get() = half_pi

/** [&pi;][pi] / 4 */
val Float.Companion.quarter_pi: Float32 get() = Float32_quarter_pi
/** [&pi;][pi] / 4 */
inline val Float.Companion.quarter_π: Float32 get() = quarter_pi

/** 3[&pi;][pi] / 4 */
val Float.Companion.three_quarter_pi: Float32 get() = Float32_3_quarter_pi
/** 3[&pi;][pi] / 4 */
inline val Float.Companion.three_quarter_π: Float32 get() = three_quarter_pi

/** 5[&pi;][pi] / 4 */
val Float.Companion.five_quarter_pi: Float32 get() = Float32_5_quarter_pi
/** 5[&pi;][pi] / 4 */
inline val Float.Companion.five_quarter_π: Float32 get() = five_quarter_pi

/** 7[&pi;][pi] / 4 */
val Float.Companion.seven_quarter_pi: Float32 get() = Float32_7_quarter_pi
/** 7[&pi;][pi] / 4 */
inline val Float.Companion.seven_quarter_π: Float32 get() = seven_quarter_pi

/** 2 [&pi;][pi] */
val Float.Companion.double_pi: Float32 get() = Float32_double_pi
/** 2 [&pi;][pi] */
inline val Float.Companion.double_π: Float32 get() = double_pi

/** 4 [&pi;][pi] */
val Float.Companion.quadruple_pi: Float32 get() = Float32_quadruple_pi
/** 4 [&pi;][pi] */
inline val Float.Companion.quadruple_π: Float32 get() = quadruple_pi

/** 1 / [&pi;][pi] */
val Float.Companion.inverse_pi: Float32 get() = Float32_inverse_pi
/** 1 / [&pi;][pi] */
inline val Float.Companion.inverse_π: Float32 get() = inverse_pi

/** 2 / [&pi;][pi] */
val Float.Companion.two_over_pi: Float32 get() = Float32_2_over_pi
/** 2 / [&pi;][pi] */
inline val Float.Companion.two_over_π: Float32 get() = two_over_pi

/** 2 / &radic;[&pi;][pi] */
val Float.Companion.two_over_sqrt_pi: Float32 get() = Float32_2_over_sqrt_pi
/** 2 / &radic;[&pi;][pi] */
inline val Float.Companion.two_over_sqrt_π: Float32 get() = two_over_sqrt_pi


/** &radic;2 */
val Float.Companion.sqrt_2: Float32 get() = Float32_sqrt_2

/** 1 / &radic;2 */
val Float.Companion.inverse_sqrt_2: Float32 get() = Float32_inverse_sqrt_2


