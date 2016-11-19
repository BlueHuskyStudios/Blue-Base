@file:Suppress("unused")

package org.bh.tools.base.state

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Basic stuff for managing state
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */

/**
 * Represents a state, large or small.
 *
 * @param SameType Just put the same name of the class here, like `class Foo: State<Foo>`. This is a band-aid for the
 *                 JVM not allowing for interface class reflection.
 *                 See also: http://stackoverflow .com/questions/40645590/kotlin-function-returns-implementation-type
 */
interface State<SameType: State<SameType>> {
    /**
     * This state, as if it were a complete change.
     */
    val changeValue: StateChange<SameType>
}


/**
 * Represents a change in a state, large or small.
 *
 * @param StateType The type of state that has changed
 */
interface StateChange<StateType: State<StateType>>
