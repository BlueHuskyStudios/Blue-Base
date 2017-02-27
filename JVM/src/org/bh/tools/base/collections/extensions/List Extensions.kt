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
