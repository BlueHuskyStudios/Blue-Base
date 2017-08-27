@file:Suppress("unused")

package org.bh.tools.base.collections

import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.math.int32Value
import org.bh.tools.base.math.integerValue

/**
 * A basic queue of items, where new ones are placed on the back and items can only be read from the front
 *
 * @author Kyli Rouge
 * @since 2017-01-24
 */
open class Queue<Element>(protected var backingList: MutableList<Element> = mutableListOf()) {
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


    fun listValue(): List<Element> = backingList.toList()
}



typealias QueueElementProcessor<Element> = (each: Element) -> Unit



fun <Element> Queue<Element>.peekAtFront(): Element? = front
