@file:Suppress("unused")

package org.bh.tools.base.abstraction

/*
 * In case the default naming is confusing or doesn't quite fit the circumstance, here are some alternate names
 *
 * @author Ben Leggiero
 * @since 2017-02-07
 */



// MARK: - Nothing

/**
 * A semantic name for [Nothing], for use as the return value of a function that is the endpoint of a program
 */
typealias Endpoint = Nothing


/**
 * A semantic name for [Nothing], for use as the return value of a function which intentionally never returns; it
 * either throws or loops until the program forcefully exits.
 */
typealias NeverReturns = Nothing


/**
 * A semantic name for [Nothing], for use as the return value of a function which does nothing but throw immediately.
 * If the function also blocks with a loop, use [NeverReturns] instead.
 */
typealias AlwaysThrows = Nothing



// MARK: - Map

/**
 * The ideal map: Associates keys to values
 */
typealias Dictionary<Key, Value> = HashMap<Key, Value>

typealias AssociativeArray<Key, Value> = Map<Key, Value>
