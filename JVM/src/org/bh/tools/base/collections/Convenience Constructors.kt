@file:Suppress("NOTHING_TO_INLINE")

package org.bh.tools.base.collections


/*
 * Constructors that allow convenient creation of collections
 *
 * @author Ben Leggiero
 * @since 2017-07-02
 */



// MARK: - Sets

inline fun <Element> Set(): Set<Element> = setOf()
inline fun <Element> Set(element: Element): Set<Element> = setOf(element = element)
inline fun <Element> Set(vararg elements: Element): Set<Element> = setOf(elements = *elements)
inline fun <reified Element> Set(elements: List<Element>): Set<Element> = setOf<Element>(elements = *Array(elements))


// MARK: Mutable

fun <Element> mutableSetOf(element: Element): MutableSet<Element> = mutableSetOf(elements = element)

inline fun <Element> MutableSet(): MutableSet<Element> = mutableSetOf()
inline fun <Element> MutableSet(element: Element): MutableSet<Element> = mutableSetOf(element = element)
inline fun <Element> MutableSet(vararg elements: Element): MutableSet<Element> = mutableSetOf(elements = *elements)
inline fun <reified Element> MutableSet(elements: List<Element>): MutableSet<Element> = mutableSetOf(elements = *Array(elements))



// MARK: - Arrays

inline fun <reified Element> Array(): Array<Element> = arrayOf()
inline fun <reified Element> Array(vararg elements: Element): Array<Element> = arrayOf(elements = *elements)
inline fun <reified Element> Array(elements: List<Element>): Array<Element> = arrayOf(elements = *elements.toTypedArray())
inline fun <reified Element> Array(elements: Set<Element>): Array<Element> = arrayOf(elements = *elements.toTypedArray())



// MARK: - Lists

inline fun <Element> List(): List<Element> = listOf()
inline fun <Element> List(vararg elements: Element): List<Element> = listOf(elements = *elements)


// MARK: Mutable

inline fun <Element> MutableList(): MutableList<Element> = mutableListOf()
inline fun <Element> MutableList(vararg elements: Element): MutableList<Element> = mutableListOf(elements = *elements)



// MARK: - Maps

inline fun <Key, Value> Map(): Map<Key, Value> = mapOf()
inline fun <Key, Value> Map(pair: Pair<Key, Value>): Map<Key, Value> = mapOf(pair = pair)
inline fun <Key, Value> Map(vararg pairs: Pair<Key, Value>): Map<Key, Value> = mapOf(pairs = *pairs)


// MARK: Mutable

fun <Key, Value> mutableMapOf(pair: Pair<Key, Value>): MutableMap<Key, Value> = mutableMapOf(pairs = pair)

inline fun <Key, Value> MutableMap(): MutableMap<Key, Value> = mutableMapOf()
inline fun <Key, Value> MutableMap(pair: Pair<Key, Value>): MutableMap<Key, Value> = mutableMapOf(pair = pair)
inline fun <Key, Value> MutableMap(vararg pairs: Pair<Key, Value>): MutableMap<Key, Value> = mutableMapOf(pairs = *pairs)
