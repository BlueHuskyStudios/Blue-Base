@file:Suppress("unused")

package org.bh.tools.base.collections

import NotFound
import org.bh.tools.base.collections.ArrayPosition.end
import org.bh.tools.base.collections.ArrayPosition.start
import org.bh.tools.base.collections.SearchBehavior.*
import java.util.*
import java.util.stream.Stream

/**
 * BHArray, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * An array with extended capabilities. Designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
open class BHArray<ElementType> {
    protected var backend: Array<ElementType>

    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(vararg backend: ElementType) {
        this.backend = backend as Array<ElementType> // TODO: Check with unit tests
    }


    /**
     * Returns the value stored at the given index
     *
     * @param index the location of a value in this array
     * @return The value at `index`
     */
    open fun get(index: Int): ElementType {
        return backend[index]
    }

    /**
     * The number of items in this array.
     */
    val length: Int get() = backend.size

    fun toString(prefix: CharSequence = "", glue: CharSequence, suffix: CharSequence = ""): String {
        return this.backend.joinToString(separator = glue, prefix = prefix, postfix = suffix)
    }

    /**
     * @return a stream of all elements in this array, starting from the given position.
     *
     * @param from The position from which to start streaming
     */
    fun stream(from: ArrayPosition): Stream<ElementType> {
        return when (from) {
            start -> Stream.of(*backend)
            end -> Stream.of(*(backend.reversedArray()))
        }
    }

    /**
     * Indicates whether this array contains the given needle
     */
    fun contains(behavior: SearchBehavior, needle: ElementType): Boolean {
        return indexOf(needle = needle, behavior = behavior) != NotFound
    }
}

val BHArray<*>.size: Int get() = this.length
val BHArray<*>.count: Int get() = this.length

val BHArray<*>.allIndices: IndexSet get() = IndexSet(0..length)

/**
 * Returns the index of the given needle near the given position in the array. If the given needle can't be found at
 * all, [NotFound] is returned.
 *
 * @param near   the position to start searching from
 * @param needle the needle to search from
 *
 * @return the index of the given needle near the given position, or `null` if none can be found.
 */
fun <ElementType> BHArray<ElementType>.indexOf(needle: ElementType?,
                                               near: ArrayPosition = start,
                                               behavior: SearchBehavior = any): Index? { // TODO: Use behavior
    if (needle == null) {
        return indexOfNull(near)
    }
    when (near) {
        start -> run {
            var i = 0
            val l = length
            while (i < l) {
                if (needle == get(i)) {
                    return i
                }
                i++
            }
        }
        end -> for (i in length downTo 1) {
            if (needle == get(i)) {
                return i
            }
        }
    }
    return NotFound
}

fun <ElementType> BHArray<ElementType>.indicesOf(vararg needles: ElementType, near: ArrayPosition = start): IndexSet {
    var ret = IndexSet()
    needles.forEach {
        val index = indexOf(it, near)
        if (index != null) {
            ret = ret.union(index = index)
        }
    }
    return ret
}

private fun <ElementType> BHArray<ElementType>.indexOfNull(near: ArrayPosition): Int? {
    for (index in 0 until  length) {
        if (get(index) == null) {
            return index
        }
    }
    return NotFound
}


/**
 * BHMutableArray, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * An mutable version of [BHArray]. Also designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
open class BHMutableArray<ElementType>(vararg backend: ElementType) : BHArray<ElementType>(*backend) {
    private var mutationListeners = BHMutableArray<ArrayMutationListener<ElementType>>()

    /**
     * Changes the value at the given index in this array.
     *
     * @param index    The index to change
     * @param newValue The new value to store at `index`
     *
     * @return `this`
     */
    fun set(index: Int, newValue: ElementType): BHMutableArray<ElementType> {
        backend[index] = newValue
        return this
    }

    /**
     * Appends the given values to the end (starting at index `length`) of this array.
     *
     * @param newVals the new values to append
     *
     * @return `this`
     */
    fun append(vararg newVals: ElementType): BHMutableArray<ElementType> {
        backend += newVals
        return this
    }

    /**
     * Prepends the given values to the beginning (starting at index `0`) of this array.
     *
     * @param newVals the new values to append
     *
     * @return `this`
     */
    fun prepend(vararg newVals: ElementType): BHMutableArray<ElementType> {
        val thisSize = newVals.size
        val arraySize = backend.size
        val result = Arrays.copyOf(newVals, thisSize + arraySize)
        System.arraycopy(backend, 0, result, thisSize, arraySize)
        backend = result
        return this
    }
    /**
     * Removes the given item from the array, using the given behaviors.
     *
     * @param behavior The behavior by which to search
     * <ul>
     *     <li>If {@link ArrayPP.SearchBehavior#any any}, removes the first found matching object at an index close
     *         to `position` . If none is found, nothing is changed.</li>
     *     <li>If {@link ArrayPP.SearchBehavior#all all}, removes each and every found matching object. If none is
     *         found, nothing is changed.</li>
     *     <li>If {@link ArrayPP.SearchBehavior#solely solely}, first determines if the array consists solely of
     *         matching objects. If it does, {@link #clear()} is called.</li>
     * </ul>
     * @param oldVal   The value to remove
     * @param position Used if `behavior` is [any][SearchBehavior.any].
     *
     * @return {@code this}
     */
    fun remove(behavior: SearchBehavior, oldVal: ElementType, position: ArrayPosition): BHMutableArray<ElementType> {
        when (behavior) {
            any -> {
                val index = indexOf(oldVal, position)
                if (index != null) {
                    remove(index = index)
                }
            }

            all -> for (i in 0..backend.length - 1) {
                if (oldVal === backend[i] || Objects.equals(oldVal, backend[i])) {
                    remove(i)
                }
            }

            solely -> if (this.contains(solely, oldVal)) {
                clear()
            }
        }
        return this
    }

    private fun clear(): BHMutableArray<ElementType> {
        backend = backend.removing(allIndices)
        return this
    }

    fun remove(vararg elements: ElementType): BHMutableArray<ElementType> {
        return remove(indices = indicesOf(*elements))
    }

    fun remove(index: Index): BHMutableArray<ElementType> = remove(IndexSet(index))

    fun remove(indices: IndexSet): BHMutableArray<ElementType> {
        backend = backend.removing(indices = indices)
        return this
    }


    /**
     * Returns a new, empty array of type `T` with the given size.
     *
     * @param newLength the length of the new array
     *
     * @return a new, empty array of type `T` and size `newLength`.
     */
    private fun emptyArrayOfLength(newLength: Int): Array<ElementType?> {
        val copy = backend.copyOf(newSize = newLength)
        for (index in 0 until copy.size) {
            copy[index] = null
        }
        return copy
    }

    fun addMutationListener(mutationListener: ArrayMutationListener<ElementType>) {
        mutationListeners += mutationListener
    }

    fun removeMutationListener(mutationListener: ArrayMutationListener<ElementType>) {
        mutationListeners -= mutationListener
    }
}

private infix operator fun <ElementType> BHMutableArray<ElementType>.minusAssign(mutationListener: ElementType) {
    this.remove(mutationListener)
}

private infix operator fun <ElementType> BHMutableArray<ElementType>.plusAssign(mutationListener: ElementType) {
    this.append(mutationListener)
}


/**
 * Adds the given values to the given position in the array, defaulting to the end.
 *
 * @param position `optional` - The position in the array at which to add the new values. Defaults to `end`.
 * @param newVals  The values to add to the array
 *
 * @return `this`
 */
fun <ElementType> BHMutableArray<ElementType>.add(position: ArrayPosition = end, vararg newVals: ElementType)
        : BHMutableArray<ElementType> {
    when (position) {
        end -> return append(*newVals)
        start -> return prepend(*newVals)
    }
}

interface ArrayMutationListener<ElementType> {
    fun willMutate(array: BHMutableArray<ElementType>)
    fun didMutate(array: BHMutableArray<ElementType>)
}


///// AUXILIARY CLASSES /////



/**
 * Basic, universal positions in an array
 */
enum class ArrayPosition {
    /**
     * Represents the beginning of the array (index `0`)
     */
    start,
    /**
     * Represents the end of the array (index `length`)
     */
    end
}

/**
 * Indicates what kind of behavior to exhibit when searching.
 */
enum class SearchBehavior {
    /**
     * Indicates that search methods should stop after the first find
     */
    any,
    /**
     * Indicates that search methods should guarantee that all given items are in the array
     */
    all,
    /**
     * Indicates that search methods should guarantee that the the array consists solely of the given items
     */
    solely
}
