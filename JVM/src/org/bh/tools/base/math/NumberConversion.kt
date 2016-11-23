package org.bh.tools.base.math

import org.bh.tools.base.abstraction.*

/*
 * NumberConversion, made for BHToolbox, is copyright Blue Husky Software ©2016 BH-1-PS
 *
 * @author Kyli and Ben of Blue Husky Software
 * @since 2016-10-21
 */

val Number.isNativeInteger: Boolean get()
    = when (this) {
        is Int8, is Int16, is Int32, is Int64 -> true
        else -> false
    }

val Number.isNativeFraction: Boolean get()
    = when (this) {
        is Float32, is Float64 -> true
        else -> false
    }

val BHFloat.clampedIntegerValue: BHInt get()
    = clamp(low = BHInt.min.floatValue, value = this, high = BHInt.max.floatValue).integerValue
