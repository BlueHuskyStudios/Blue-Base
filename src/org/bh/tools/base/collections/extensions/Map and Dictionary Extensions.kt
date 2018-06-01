package org.bh.tools.base.collections.extensions

import org.bh.tools.base.func.*

/*
 * Makes dictionaries and maps easier
 *
 * @author Ben Leggiero
 * @since 2017-02-26
 */


fun <Key, Value> Map<Key, Value>.firstOrNull(evaluator: (Key, Value?) -> Boolean): Tuple2<Key, Value>? {
    val key = keys.firstOrNull { key -> evaluator(key, this[key]) }
    if (key != null) {
        val value = this[key]
        if (value != null) {
            return Tuple2(key, value)
        }
    }
    return null
}


@Throws
fun <Key, Value> Map<Key, Value>.firstOrCrash(evaluator: (Key, Value?) -> Boolean): Tuple2<Key, Value>? {
    return this.firstOrNull(evaluator) ?: throw NoSuchElementException("No element pleases the evaluator")
}
