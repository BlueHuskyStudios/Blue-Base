@file:JvmName("MathBasics")
@file:Suppress("unused")

package org.bh.tools.base.math

/* Basic Math Functions, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * To help you do math!
 *
 * Created by Kyli Rouge on 2016-10-24.
 */


fun<NumberType: Comparable<NumberType>> min(lhs: NumberType, rhs: NumberType): NumberType
        = if (lhs < rhs) lhs else rhs

fun<NumberType: Comparable<NumberType>> min(a: NumberType, b: NumberType, vararg n: NumberType): NumberType {
    if (n.isEmpty()) {
        return min(lhs = a, rhs = b)
    }

    val lowestN: NumberType
    if (n.size == 1) {
        lowestN = n[0]
    } else if (n.size == 2) {
        lowestN = min(lhs = n[0], rhs = n[1])
    } else {
        lowestN = n.reduce { x, y -> min(lhs = x, rhs = y) }
    }

    return min(lhs = a, rhs = min(lhs = b, rhs = lowestN))
}

fun<NumberType: Comparable<NumberType>> max(lhs: NumberType, rhs: NumberType): NumberType
        = if (lhs > rhs) lhs else rhs

fun<NumberType: Comparable<NumberType>> max(a: NumberType, b: NumberType, vararg n: NumberType): NumberType {
    if (n.isEmpty()) {
        return max(lhs = a, rhs = b)
    }

    val highestN: NumberType
    if (n.size == 1) {
        highestN = n[0]
    } else if (n.size == 2) {
        highestN = max(lhs = n[0], rhs = n[1])
    } else {
        highestN = n.reduce { x, y -> max(lhs = x, rhs = y) }
    }

    return max(lhs = a, rhs = max(lhs = b, rhs = highestN))
}

/**
 * Returns the clamped value between `low` and `high`, such that ideally `value` is returned, but never will the
 * returned number be less than `low` or greater than `high`.
 */
fun<NumberType: Comparable<NumberType>> clamp(low: NumberType, value: NumberType, high: NumberType): NumberType
    = max(low, min(value, high))
