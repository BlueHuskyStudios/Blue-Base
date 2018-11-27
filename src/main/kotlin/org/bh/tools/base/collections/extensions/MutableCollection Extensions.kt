package org.bh.tools.base.collections.extensions

import org.bh.tools.base.abstraction.*


/*
 * @author Ben Leggiero
 * @since 2018-10-21
 */



fun <Element, Self> Self.removeDuplicates(): Self
where Self: MutableList<Element>
{
    val tracker = mutableSetOf<Element>()
    val evilIndices = mutableListOf<Int32>()

    // Go 0..<count and gather the indices that we want to remove, place them in `evilIndices` in ascending order

    this.forEachIndexed { index, element ->
        if (tracker.contains(element)) {
            evilIndices.add(index)
        }
        else {
            tracker.add(element)
        }
    }

    // Now that we have the indices we know we want to remove, and we know they're in ascending order, go through them
    // backwards and remove them. This prevents the actual indices from shifting in relation to our evil indices.

    evilIndices.forEachReversed { evilIndex ->
        removeAt(evilIndex)
    }

    return this
}
