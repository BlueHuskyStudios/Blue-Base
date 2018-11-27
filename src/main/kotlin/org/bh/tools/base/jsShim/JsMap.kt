package org.bh.tools.base.jsShim

import kotlin.js.*
import kotlin.reflect.*

/**
 * A JavaScript dictionary, with strict Kotlin typing
 *
 * @author Ben Leggiero
 * @since 2018-03-11
 */
class JsMap<Key, Value: Any>(
        values: Set<Pair<Key, Value>>,
        private val valueClass: KClass<Value>
) {

    private val internal = json(*(values.map { it.first.toString() to it.second }.toTypedArray()))

    override fun toString(): String {
        return internal.toString()
    }


    operator fun get(key: Key): Value? {
        val v = internal[key.toString()] ?: return undefined

        @Suppress("UNCHECKED_CAST")
        return v `as?` valueClass ?: undefined
    }


    operator fun set(key: Key, value: Value?) {
        internal[key.toString()] = value
    }
}


@Suppress("UNCHECKED_CAST", "RemoveRedundantBackticks")
@JsName("asOrNull")
infix fun <T: Any> Any.`as?`(targetClass: KClass<T>): T? {
    return if (targetClass.isInstance(this)) this as T
    else null
}
