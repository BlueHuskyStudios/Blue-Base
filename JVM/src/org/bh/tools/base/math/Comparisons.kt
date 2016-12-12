package org.bh.tools.base.math

/* Comparisons, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
 *
 * For comparing stuffs.
 * Created by Kyli Rouge on 2016-10-28.
 */

open class ComparableComparator<T: Comparable<T>>: Comparator<T> {
    override fun compare(lhs: T, rhs: T): Int {
        return lhs.compareTo(rhs)
    }
}
