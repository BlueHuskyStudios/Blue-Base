@file:Suppress("unused")

package org.bh.tools.base.func

/*
 * To make functional programming easier
 *
 * @author Kyli Rouge
 * @since 2017-02-04
 */



/**
 * Represents an ad-hoc struct of one value
 *
 * There is no meaning attached to value in this class; it can be used for any purpose.
 * Single exhibits value semantics, i.e. singles are equal if both components are equal.
 *
 * @param First The type of the first (and only) value.
 * @property first The first (and only) value.
 * @constructor Creates a new instance of Single.
 */
data class Single<out First>(
        /** The first (and only) value */
        val first: First
) {

    /**
     * Returns string representation of the [Single] including its [first] value.
     */
    override fun toString(): String = "($first)"
}


/**
 * Converts this pair into a list.
 */
fun <Element> Single<Element>.toList(): List<Element> = listOf(first)


/** Creates a tuple of one value */
fun <First> tuple(first: First): Tuple1<First> = Tuple1(first)
/** Creates a tuple of two values */
fun <First, Second> tuple(first: First, second: Second): Tuple2<First, Second> = Tuple2(first, second)
/** Creates a tuple of three values */
fun <First, Second, Third> tuple(first: First, second: Second, third: Third): Tuple3<First, Second, Third> = Tuple3(first, second, third)


/** A tuple containing one value */
typealias Tuple1<First> = Single<First>
/** A tuple containing two values */
typealias Tuple2<First, Second> = Pair<First, Second>
/** A tuple containing three values */
typealias Tuple3<First, Second, Third> = Triple<First, Second, Third>


interface TupleConvertible<out TupleType> {
    /**
     * Converts this into a tuple. The default implementation simply calls [tupleValue].
     */
    fun toTuple() = tupleValue

    /**
     * Converts this into a tuple. It's recommended that this is done `by lazy`.
     */
    val tupleValue: TupleType
}



inline fun <Input, Output> Input.transform(transformer: (Input)->Output): Output = let(transformer)
