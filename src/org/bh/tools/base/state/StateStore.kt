@file:Suppress("unused")

package org.bh.tools.base.state

import org.bh.tools.base.collections.DeltaStack

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * That which stores a history of states
 *
 * @param StateType The type of state that this stores
 * @param StateChangeType The type of change that can be applied to the stored state
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */
interface StateStore<StateType: ChangeableState<StateType, StateChangeType>, StateChangeType: StateChange<StateChangeType,
        StateType>> {
    /**
     * Pushes a new state into the store using the given changes. Memory-restricted or simplistic stores may
     * immediately flatten after this operation.
     *
     * @param newState The changes to apply which will create a new state
     */
    fun pushState(newState: StateChangeType)

    /**
     * Pops the latest changes off the store and returns the resulting state before the changes. If there are no more
     * changes left and we are at the base state, that is returned.
     *
     * @return The state after the latest change has been popped off it
     */
    fun popState(): StateType

    /**
     * @return The current state with all its changes
     */
    fun currentState(): StateType

    /**
     * Flattens all the changes to the state, so that [currentState] is the base state
     *
     * @return The flattened state with all changes applied
     */
    fun flattenState(): StateType
}


/**
 * A basic implementation of [StateStore], with every function implemented and no fanciness.
 */
open class BasicStateStore<StateType, StateChangeType>
    (baseState: StateType)
    : StateStore<StateType, StateChangeType>
    where StateType: ChangeableState<StateType, StateChangeType>,
        StateChangeType: StateChange<StateChangeType, StateType> {

    internal val _stateStack = DeltaStack(_originalState = baseState)

    override fun pushState(newState: StateChangeType) = _stateStack.pushState(newState)

    override fun popState(): StateType = _stateStack.popState()

    override fun currentState(): StateType = _stateStack.currentState()

    override fun flattenState(): StateType = _stateStack.flattenState()
}


interface ResettableStateStore<StateType, StateChangeType>
    : StateStore<StateType, StateChangeType>
    where StateType: ChangeableState<StateType, StateChangeType>,
        StateChangeType: StateChange<StateChangeType, StateType> {

    /**
     * Resets this state store back to a basic state. This doesn't have to be precisely the same as its actual initial
     * state, but must be some clean slate. From this point on, [popState] will be able to navigate back to this state,
     * but no further.
     *
     * If you want to reset this to some unspecified base state but don't care exactly what, pass `null` or omit the parameter.
     *
     * @param newState The state to reset back to, or `null` if the reset-state should be the default state.
     */
    fun reset(newState: StateType? = null)
}


open class BasicResettableStateStore<StateType, StateChangeType>
    (baseState: StateType)
    : BasicStateStore<StateType, StateChangeType>(baseState),
    ResettableStateStore<StateType, StateChangeType>
    where StateType: ChangeableState<StateType, StateChangeType>,
        StateChangeType: StateChange<StateChangeType, StateType> {

    override fun reset(newState: StateType?) {
        _stateStack.reset(newState)
    }
}
