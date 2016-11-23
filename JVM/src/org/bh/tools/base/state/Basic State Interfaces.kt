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
 */
interface State

/**
 * Represents a state, large or small, which can be changed.
 *
 * @param SameType   Just put the same name of the class here, like `class Foo: State<Foo>`. This is a band-aid for the
 *                   JVM not allowing for interface class type inference.
 *                   See also: http://stackoverflow .com/questions/40645590/kotlin-function-returns-implementation-type
 * @param ChangeType The type of change that can be applied to this state
 */
interface ChangeableState<SameType: ChangeableState<SameType, ChangeType>, ChangeType: StateChange<*, SameType>>
    : State {

    /**
     * This state, as if it were a complete change.
     */
    val changeValue: ChangeType

    /**
     * Without mutating, creates and returns a new state based on the given change
     *
     * @param change The change to the state
     */
    fun applyingChange(change: ChangeType): SameType
}



/**
 * Represents a change in a state, large or small.
 *
 * @param SameType  Just put the same name of the class here, like `class Foo: StateChange<Foo, Bar>`. This is a
 *                  band-aid for the JVM not allowing for interface class type inference.
 *                  See also: http://stackoverflow .com/questions/40645590/kotlin-function-returns-implementation-type
 * @param StateType The type of state that has changed
 */
interface StateChange<SameType: StateChange<SameType, StateType>, StateType: State> {

    /**
     * Without mutating, creates and returns a new state based on the given change
     *
     * @param change The change to the state
     */
    fun applyingChange(change: SameType): SameType
}
