package org.bh.tools.base.state

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A stateful state mutator
 *
 * @param StateType  The type of state that this controls
 * @param ActionType The type of action that can mutate the state that this controls
 *
 * @author Kyli Rouge
 * @since 2016-11-09
 */
interface StateController<out StateType : State, ActionType> {
    /**
     * @return The current state in this controller
     */
    fun currentState(): StateType

    /**
     * A request to mutate the current state by using the given action. This might be ignored; the state might not
     * mutate at all depending on the decisions of this controller.
     *
     * @param action The action that aims to mutate this controller's state
     */
    fun mutate(action: ActionType)
}
