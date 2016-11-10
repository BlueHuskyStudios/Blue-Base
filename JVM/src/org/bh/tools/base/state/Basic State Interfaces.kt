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
 * Represents a change in a state, large or small.
 *
 * @param StateType The type of state that has changed
 */
interface StateChange<StateType: State>
