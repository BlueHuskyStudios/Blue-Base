package org.bh.tools.base.collections

import org.bh.tools.base.collections.extensions.*



open class Stack<Element>
(
        internal open val core: List<Element> = List()
)
    : Collection<Element>
{
    fun pushing(newElement: Element) = Stack(core.adding(0, newElement))

    fun popped() = Pair(peek(), Stack(core.removingAt(0)))

    fun peek() = firstOrNull


    // MARK: Collection

    override val size: Int get() = core.size

    override fun contains(element: Element) = core.contains(element)

    override fun containsAll(elements: Collection<Element>) = core.containsAll(elements)

    override fun isEmpty() = core.isEmpty()

    override fun iterator() = core.iterator()
}



open class MutableStack<Element>
(
        override val core: MutableList<Element> = MutableList()
)
    : Stack<Element>(core)
{
    fun push(newElement: Element): Unit = core.add(0, newElement)

    fun pop(): Element = core.removeAt(0)

    fun clear(): Unit = core.clear()


    // MARK: Collection

    override val size: Int get() = core.size

    override fun contains(element: Element) = core.contains(element)

    override fun containsAll(elements: Collection<Element>) = core.containsAll(elements)

    override fun isEmpty() = core.isEmpty()

    override fun iterator() = core.iterator()
}

