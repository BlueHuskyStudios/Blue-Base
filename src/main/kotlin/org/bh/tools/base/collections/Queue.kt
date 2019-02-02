@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package BlueBase

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

    val isEmpty: Boolean
        get() = size == 0L


    fun pushOntoBack(newElement: Element): Queue<Element> {
        backingList.add(newElement)
        return this
    }


    fun popFromFront(): Element? = backingList.removeFirst()


    /**
     * Goes through this queue, removing each item and then sending it to `processor`.
     * After the call to this completes, the queue will be empty.
     *
     * @param processor The function which will be called on each item after it's removed from the queue
     */
    fun pumpAll(processor: QueueElementProcessor<Element>) {
        var each = popFromFront()
        while (each != null) {
            processor(each)
            each = popFromFront()
        }
    }


    fun listValue() = backingList.toList()
    fun mutableListValue() = backingList.toMutableList()
}



typealias QueueElementProcessor<Element> = (each: Element) -> Unit



fun <Element> Queue<Element>.peekAtFront(): Element? = front
