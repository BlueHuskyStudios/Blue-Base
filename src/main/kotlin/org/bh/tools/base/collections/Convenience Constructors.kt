package org.bh.tools.base.collections


/*
 * @author Ben Leggiero
 * @since 2017-10-23
 */



/**
 * Allows creation of arrays via the bracket syntax: `val x = a[1, 2, 3]`
 */
object a {
    /** Returns an array of the given elements */
    inline operator fun <reified Element> get(vararg elements: Element): Array<Element> = arrayOf(*elements)
}


/**
 * Allows creation of lists via the bracket syntax: `val x = l[1, 2, 3]`
 */
object l {
    /** Returns a list of the given elements */
    inline operator fun <reified Element> get(vararg elements: Element): List<Element> = listOf(*elements)
}


/**
 * Allows creation of sets via the bracket syntax: `val x = s[1, 2, 3]`
 */
object s {
    /** Returns a set of the given elements */
    inline operator fun <reified Element> get(vararg elements: Element): Set<Element> = setOf(*elements)
}


/**
 * Allows creation of sets via the bracket syntax: `val x = s[1, 2, 3]`
 */
object ms {
    /** Returns a set of the given elements */
    inline operator fun <reified Element> get(vararg elements: Element): MutableSet<Element> = mutableSetOf(*elements)
}
