@file:Suppress("unused")

package org.bh.tools.base.collections

/**
 * An array with extended capabilities. Designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
open class BHArray<T>(protected var backend: Array<T>) {
    /**
     * Returns the value stored at the given index
     *
     * @param index the location of a value in this array
     * @return The value at `index`
     */
    open fun get(index: Int): T {
        return backend[index]
    }

    /**
     * The number of items in this array.
     */
    public val length: Int get() = backend.size

    public fun toString(prefix: CharSequence = "", glue: CharSequence, suffix: CharSequence = ""): String {
        return this.backend.joinToString(separator = glue, prefix = prefix, postfix = suffix)
    }
}

public val BHArray<*>.size: Int get() = this.length
public val BHArray<*>.count: Int get() = this.length



/**
 * An mutable version of [BHArray]. Also designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
open class BHMutableArray<T>(backend: Array<T>) : BHArray<T>(backend) {
    /**
     * Changes the value at the given index in this array.
     *
     * @param index    The index to change
     * @param newValue The new value to store at `index`
     *
     * @return `this`
     */
    fun set(index: Int, newValue: T): BHMutableArray<T> {
        backend[index] = newValue
        return this
    }
}
