package org.bh.tools.base.collections.extensions

import org.bh.tools.base.collections.Index

/*
 * To help you work with lists
 *
 * @author Ben Leggiero
 * @since 2017-02-17
 */


/**
 * @param newElement The element to add
 * @param index      The index
 *
 * @return a new list with the given element added at the given index
 */
fun <ElementType> List<ElementType>.adding(index: Index, newElement: ElementType): List<ElementType> {
    val newList = this.toMutableList()
    newList.add(index, newElement)
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
