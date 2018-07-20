@file:Suppress("unused")

package org.bh.tools.base.collections.extensions

import org.bh.tools.base.collections.Index
import org.bh.tools.base.math.int32Value
import org.bh.tools.base.struct.IntegerRange
import org.bh.tools.base.util.*

/*
 * To help you work with lists
 *
 * @author Ben Leggiero
 * @since 2017-02-17
 */


/**
 * @param newElement The element to add to the resulting list
 * @param index      The index at which to add the element
 *
 * @return a new list with the given element added at the given index
 */
fun <ElementType> List<ElementType>.adding(index: Index, newElement: ElementType): List<ElementType> {
    val newList = this.toMutableList()
    newList.add(index, newElement)
    return newList
}


/**
 * @param index The index of the element to exclude from the resulting list
 *
 * @return a new list with the given element added at the given index
 */
fun <ElementType> List<ElementType>.removingAt(index: Index): List<ElementType> {
    val newList = this.toMutableList()
    safeTry { newList.removeAt(index) }
    return newList
}


fun <Element> List(): List<Element> = listOf()
fun <Element> List(vararg initialElements: Element): List<Element> = listOf(*initialElements)


fun <Element> MutableList(): MutableList<Element> = mutableListOf()
fun <Element> MutableList(vararg initialElements: Element): MutableList<Element> = mutableListOf(*initialElements)


/**
 * If this list is large enough to contain an item at the given [index], that item is returned. Else, [backup] is returned
 *
 * @param index  The index of the item to get
 * @param backup The block that will be passed [index] if the list is too short to contain [index]. Its return value
 *               will then be returned from this function.
 *
 * @return The item at [index] if [count] is less than [index]. Else, the result of [backup]
 */
operator fun <Element> List<Element>.get(index: Index, backup: (Index) -> Element) = getOrElse(index, backup)


/**
 * If there is an element [at] the given index, and that index is within range of this list, then that element is
 * returned. Else, this list is padded out until said index is within range, by using [paddingGenerator]. Once
 * sufficiently padded, the new element is placed at the given index using [newElement].
 *
 * @param at               The index of the element to return, andor the element to insert
 * @param newElement       The block which can generate the new element to place [at] the given index, if necessary
 * @param paddingGenerator The generator which, if required, will generate dummy content to pad out this list until the
 *                         given index is reachable. This will be called at least `0` and at most `n-1` times, where
 *                         `n` is the distance outside the array [at] which the given index lies.
 */
fun <Element> MutableList<Element>.getOrSet(at: Index, newElement: (Index) -> Element, paddingGenerator: (Index) -> Element): Element {
    val index = at
    getOrNull(index)?.let {
        return it
    }

    val backupValue = newElement(index)

    if (this.size <= index) {
        while (this.size < index) {
            this.add(paddingGenerator(index))
        }
        this.add(backupValue)
    }
    else {
        this[index] = backupValue
    }

    return backupValue
}


/**
 * If this list is large enough to contain an item at the given [index], that item is returned. Else, [backup] is returned
 *
 * @param index The index of the item to get
 * @param backup The value that will be returned if the list is too short to contain [index]
 *
 * @return The item at [index] if [count] is less than [index]. Else, [backup]
 */
operator fun <Element> List<Element>.get(index: Index, backup: Element) = get(index, { backup })


@Suppress("NOTHING_TO_INLINE")
inline fun <ElementType, IterableType: List<ElementType>> IterableType.nonEmpty(): IterableType? = if (isEmpty()) null else this
inline val <ElementType, IterableType: List<ElementType>> IterableType.nonEmpty get() = nonEmpty()



fun <Element> List<Element>.subList(range: IntegerRange): List<Element>
        = subList(fromIndex = range.first.int32Value, toIndex = range.last.int32Value)


//inline fun <reified Element> MutableList<Element>.copy(): MutableList<Element> = MutableList(*toTypedArray())


//inline fun <reified Element> List<Element>.copy(): List<Element> = List(*toTypedArray())



fun List<*>.humanReadableList(conjunction: Conjunction): String {
    return when (this.size) {
        0 -> ""
        1 -> "$firstOrNull"
        2 -> "${this[0]} $conjunction ${this[1]}"
        else -> "${this.subList(0, this.size - 1).joinToString(separator = ", ")}, $conjunction $lastOrNull"
    }
}



enum class Conjunction {
    and,
    or,
    andor,
    ;

    override fun toString(): String {
        return when (this) {
            and -> "and"
            or -> "or"
            andor -> "andor"
        }
    }
}
