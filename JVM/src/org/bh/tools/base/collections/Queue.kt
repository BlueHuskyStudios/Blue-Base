@file:Suppress("unused")

package org.bh.tools.base.collections

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.collections.extensions.firstOrNull
import org.bh.tools.base.collections.extensions.removeFirst
import org.bh.tools.base.math.int32Value
import java.util.*

/**
 * A basic queue of items, where new ones are placed on the back and items can only be read from the front
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */
data class Queue<Element>(internal var backingList: MutableList<Element> = mutableListOf()) : Collection<Element> {

    override val size: Int32
        get() = backingList.size

    override fun isEmpty(): Boolean = backingList.isEmpty()

    override fun contains(element: Element): Boolean = backingList.contains(element)

    override fun containsAll(elements: Collection<Element>): Boolean = backingList.containsAll(elements)

    override fun iterator(): Iterator<Element> = backingList.iterator()

    /**
     * The first (oldest) element in the queue. Equivalent to `peek()`
     */
    val front: Element?
        get() = backingList.firstOrNull


    /**
     * Places the given item at the back of the queue
     */
    fun pushOntoBack(newElement: Element): Queue<Element> {
        backingList.add(newElement)
        return this
    }


    /**
     * Removes the item from the front of the queue (the oldest item), and returns it
     */
    fun popFromFront(): Element? = backingList.removeFirst()


    /**
     * Executes the given action on all items, removing each one before it's handed off
     */
    fun pumpAll(processor: QueueElementProcessor<Element>) {
        var each = popFromFront()
        while (each != null) {
            processor(each)
            each = popFromFront()
        }
    }
}


/**
 * That which can process a queue element
 */
typealias QueueElementProcessor<Element> = (each: Element) -> Unit


/**
 * Returns the value of the item at the front of the queue, without removing it
 */
fun <Element> Queue<Element>.peekAtFront(): Element? = front


/**
 * Converts this queue into a Java
 */
fun <Element> Queue<Element>.toJavaQueue(): java.util.Queue<Element> {
    return object : java.util.Queue<Element> {
        override val size: Int
            get() = this@toJavaQueue.size

        override fun contains(element: Element): Boolean = this@toJavaQueue.backingList.contains(element)

        override fun containsAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.containsAll(elements)

        override fun addAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.addAll(elements)

        override fun clear() = this@toJavaQueue.backingList.clear()

        override fun iterator(): MutableIterator<Element> = this@toJavaQueue.backingList.listIterator()

        override fun remove(element: Element): Boolean = this@toJavaQueue.backingList.remove(element)

        override fun remove(): Element = this@toJavaQueue.popFromFront()!!

        override fun isEmpty(): Boolean = this@toJavaQueue.isEmpty()

        override fun removeAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.removeAll(elements)

        override fun retainAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.retainAll(elements)

        override fun add(element: Element): Boolean = this@toJavaQueue.backingList.add(element)

        override fun offer(e: Element): Boolean {
            this@toJavaQueue.pushOntoBack(e)
            return true
        }

        override fun poll(): Element? = this@toJavaQueue.popFromFront()

        override fun element(): Element = this@toJavaQueue.front!!

        override fun peek(): Element? = this@toJavaQueue.front
    }
}



// MARK: - Java extensions

/**
 * Places the given item at the back of the queue
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <Element> java.util.Queue<Element>.pushOntoBack(newElement: Element): Boolean = add(newElement)

/**
 * Removes the item from the front of the queue (the oldest item), and returns it
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <Element> java.util.Queue<Element>.popFromFront(): Element = remove()

/**
 * Executes the given action on all items, removing each one before it's handed off
 */
fun <Element> PriorityQueue<Element>.pumpAll(processor: QueueElementProcessor<Element>) {
    var each = popFromFront()
    while (each != null) {
        processor(each)
        each = popFromFront()
    }
}
