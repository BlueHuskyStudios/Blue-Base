@file:Suppress("unused")

package org.bh.tools.base.collections

import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.math.int32Value
import org.bh.tools.base.math.integerValue

/**
 * A basic queue of items, where new ones are placed on the back and items can only be read from the front
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */
data class Queue<Element>(internal var backingList: MutableList<Element> = mutableListOf()) {
    val front: Element?
        get() = backingList.firstOrNull

    val size: Integer
        get() = backingList.size.integerValue

    val isEmpty: Boolean = size == 0L


    fun pushOntoBack(newElement: Element): Queue<Element> {
        backingList.add(newElement)
        return this
    }


    fun popFromFront(): Element? = backingList.removeFirst()


    fun pumpAll(processor: QueueElementProcessor<Element>) {
        var each = popFromFront()
        while (each != null) {
            processor(each)
            each = popFromFront()
        }
    }
}


typealias QueueElementProcessor<Element> = (each: Element) -> Unit


fun <Element> Queue<Element>.peekAtFront(): Element? = front




fun <Element> Queue<Element>.toJavaQueue(): java.util.Queue<Element> {
    return object: java.util.Queue<Element> {
        override val size: Int
            get() = this@toJavaQueue.size.int32Value

        override fun contains(element: Element): Boolean = this@toJavaQueue.backingList.contains(element)

        override fun containsAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.containsAll(elements)

        override fun addAll(elements: Collection<Element>): Boolean = this@toJavaQueue.backingList.addAll(elements)

        override fun clear() = this@toJavaQueue.backingList.clear()

        override fun iterator(): MutableIterator<Element> = this@toJavaQueue.backingList.listIterator()

        override fun remove(element: Element): Boolean = this@toJavaQueue.backingList.remove(element)

        override fun remove(): Element = this@toJavaQueue.popFromFront()!!

        override fun isEmpty(): Boolean = this@toJavaQueue.isEmpty

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
