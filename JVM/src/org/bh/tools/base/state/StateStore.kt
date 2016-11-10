@file:Suppress("unused")

package org.bh.tools.base.state

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
interface StateStore<StateType: State, in StateChangeType: StateChange<StateType>> {
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
     */
    fun flattenState()
}
