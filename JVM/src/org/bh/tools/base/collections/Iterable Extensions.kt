package org.bh.tools.base.collections

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */





/**
 * @return the first element, or `null` if there are no elements.
 */
inline val <ElementType> Iterable<ElementType>.firstOrNull: ElementType? get() = firstOrNull()

data class Triad<out Type>(val a: Type, val b: Type, val c: Type)

val <Type> Triad<Type>.left get() = a
val <Type> Triad<Type>.center get() = b
val <Type> Triad<Type>.right get() = c

typealias TriadComparator<Type> = (triad: Triad<Type>) -> Boolean

/**
 * Iterates through this by contiguous groups of three (triads), and asks the given triad comparator if that's the
 * group it's looking for. If that comparator ever returns `true`, that triad is immediately returned and the
 * iteration ceases.
 *
 * Iteration proceeds one at a time, not by groups of three. For example, in the array `[1, 2, 3, 4, 5]`, this function
 * will iterate over `[1, 2, 3]`, `[2, 3, 4]`, and `[3, 4, 5]`.
 *
 * `null` is returned if the comparator never returns `true`, or if there are fewer than three items in the iterable.
 *
 * @param triadComparator The comparator that will compare against contiguous triads in this iterable
 *
 * @return The first triad for which the comparator returned `true`, or `null`.
 */
inline fun <ElementType> Iterable<ElementType>.firstOrNullComparingTriads(crossinline triadComparator: TriadComparator<ElementType>): Triad<ElementType>? {
    val iterator = iterator()

    if (!iterator.hasNext()) return null
    var previousCenter: ElementType = iterator.next()

    if (!iterator.hasNext()) return null
    var previousRight: ElementType = iterator.next()

    while (iterator.hasNext()) {
        val left = previousCenter
        val center = previousRight
        val right = iterator.next()

        val triad = Triad(left, center, right)
        if (triadComparator(triad)) {
            return triad
        } else {
            previousCenter = center
            previousRight = right
        }
    }

    return null
}
