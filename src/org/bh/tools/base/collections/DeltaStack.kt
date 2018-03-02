package org.bh.tools.base.collections

import org.bh.tools.base.state.*



/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A stack of changes
 *
 * @author Kyli Rouge
 * @since 2016-11-16
 *
 * @see StateStore
 */
open class DeltaStack<ContentType, DeltaType>
        (
                /**
                 * The original base state, upon which changes will be made
                 */
                private val _originalState: @ParameterName("originalState") ContentType
        )
        : ResettableStateStore<ContentType, DeltaType>
        where ContentType : ChangeableState<ContentType, DeltaType>,
            DeltaType : StateChange<DeltaType, ContentType> {

    private val _stack = MutableStack<DeltaType>()
    private var _cachedCurrentState: ContentType? = _originalState
    private var _baseState = _originalState

    /** The number of changes atop the base state */
    val size get() = _stack.size

    var delegate: DeltaStackDelegate<ContentType, DeltaType>? = null


    constructor(baseState: ContentType, delegate: DeltaStackDelegate<ContentType, DeltaType>): this(baseState) {
        this.delegate = delegate
    }


    override fun pushState(newState: DeltaType) {
        _stack.push(newState)
        _resetCachedState()

        delegate?.deltaStackDidPushState(this)

        if (delegate?.deltaStackShouldFlattenState(this) ?: false) {
            flattenState()
        }
    }


    override fun popState(): ContentType {
        if (_stack.size > 1) {
            _stack.pop()
            _resetCachedState()
        }

        delegate?.deltaStackDidPopState(this)

        return currentState()
    }


    override fun reset(newState: ContentType?) {
        synchronized(this) {
            _baseState = newState ?: _originalState

            _stack.clear()
            _resetCachedState(newState)
        }
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
            reset(flatState)

            delegate?.deltaStackDidFlattenState(this)

            return flatState
        }
    }
}


/**
 * A delegate whose functions are called when a specific stack's state changes, or to determine how that state changes.
 *
 * @author Ben Leggiero
 * @since 2017-03-10
 *
 * @see DeltaStack
 */
interface DeltaStackDelegate<ContentType, DeltaType>
    where ContentType : ChangeableState<ContentType, DeltaType>,
        DeltaType : StateChange<DeltaType, ContentType> {

    /** Called when the given delta stack has had a new state change pushed atop it */
    fun deltaStackDidPushState(stack: DeltaStack<ContentType, DeltaType>) {}

    /** Called when the given delta stack has had one or more state changes popped off it */
    fun deltaStackDidPopState(stack: DeltaStack<ContentType, DeltaType>) {}

    /** Called when the given delta stack's state has been flattened */
    fun deltaStackDidFlattenState(stack: DeltaStack<ContentType, DeltaType>) {}

    /** Indicates whether the given delta stack should flatten it's state at this time */
    fun deltaStackShouldFlattenState(stack: DeltaStack<ContentType, DeltaType>): Boolean = false
}
