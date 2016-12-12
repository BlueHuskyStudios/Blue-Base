package org.bh.tools.base.collections

import org.bh.tools.base.state.ChangeableState
import org.bh.tools.base.state.StateChange
import org.bh.tools.base.state.StateStore
import java.util.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A stack of changes
 *
 * @author Kyli Rouge
 * @since 2016-11-16
 */
open class DeltaStack<ContentType, DeltaType>
        (baseState: ContentType)
        : StateStore<ContentType, DeltaType>
        where ContentType : ChangeableState<ContentType, DeltaType>, DeltaType : StateChange<DeltaType, ContentType>{

    private val _stack = Stack<DeltaType>()
    private var _cachedCurrentState: ContentType? = baseState
    var _baseState = baseState

    override fun pushState(newState: DeltaType) {
        _stack.push(newState)
        _resetCachedState()
    }

    override fun popState(): ContentType {
        if (_stack.size > 1) {
            _stack.pop()
            _resetCachedState()
        }
        return currentState()
    }

    private fun _resetCachedState(newState: ContentType? = null) {
        _cachedCurrentState = newState
    }

    private fun _flattenedState(): ContentType {
        val flattenedState = _baseState.applyingChange(_stack.reduce { previous, current ->
            previous.applyingChange(current)
        })
        _resetCachedState(flattenedState)
        return flattenedState
    }

    override fun currentState(): ContentType {
        synchronized(this) {
            var state = _cachedCurrentState
            if (state != null) {
                return state
            } else {
                state = _flattenedState()
                _resetCachedState(state)
                return state
            }
        }
    }

    override fun flattenState(): ContentType {
        synchronized(this) {
            val flatState = currentState()
            _stack.clear()
            _resetCachedState(flatState)
            return flatState
        }
    }
}
