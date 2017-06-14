package org.bh.tools.base.collections.extensions

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.math.int32Value

/*
 * @author Kyli Rouge
 * @since 2017-06-11
 */

fun <E> HashSet(size: Int32, init: (Int32) -> E): HashSet<E> {
    return HashSet(kotlin.collections.List(size = size, init = init))
}
