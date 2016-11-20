@file:Suppress("unused")

package org.bh.tools.base.state

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A stateless state mutator
 *
 * @param StateType The type of state that this mutates
 * @param StateChangeType The type of change that might result in an action being applied to a state
 * @param ActionType The type of action that aims to mutate
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */
interface StateMutator
        <StateType: ChangeableState<StateType, StateChangeType>,
            in ActionType,
            StateChangeType: StateChange<StateChangeType, StateType>> {
    /**
     * Taking in a state and an action, produces a new state
     *
     * @param state The state to mutate
     * @param action The action that aims to mutate the state
     *
     * @return The changes that were made to the state
     */
    fun mutate(state: StateType, action: ActionType): StateChangeType
}
