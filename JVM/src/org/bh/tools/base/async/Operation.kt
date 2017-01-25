package org.bh.tools.base.async

import org.bh.tools.base.async.OperationCompletionStatus.successful
import org.bh.tools.base.async.OperationState.*
import org.bh.tools.base.collections.Queue
import org.bh.tools.base.func.observing


private var operationCount = 0


/**
 * A basic operation to be executed either synchronously or asynchronously, and call a block upon completion
 *
 * @author Kyli Rouge
 * @since 2017-01-20
 */
open class Operation(open val block: OperationBlock, open val name: String = "Blue Base Operation #$operationCount") {
    protected open var _state: OperationState by observing(ready as OperationState,
            willSet = { currentState, newState ->
                prepareForNewState(currentState, newState)
            }, didSet = { _, _ ->
                // notify listeners?
            })

    val state: OperationState get() = _state

    protected val thread = Thread({
        try {
            if (_state == OperationState.cancelled) {
                done(OperationCompletionStatus.cancelled)
                return@Thread
            }
            else {
                _state = executing
            }

            if (_state == OperationState.cancelled) {
                done(OperationCompletionStatus.cancelled)
                return@Thread
            }
            else {
                block()
            }

            if (_state == OperationState.cancelled) {
                done(OperationCompletionStatus.cancelled)
                return@Thread
            }
            else {
                _state = finished
            }

            if (_state == OperationState.cancelled) {
                done(OperationCompletionStatus.cancelled)
                return@Thread
            }
            else {
                done(successful)
            }
        } catch (_: InterruptedException) {
            _state = OperationState.cancelled
            done(OperationCompletionStatus.cancelled)
        } catch (uncaught: Throwable) {
            System.err.println("Unexpected error when executing $name: $uncaught")
            _state = OperationState.finished
            done(OperationCompletionStatus.failed(exception = uncaught))
        }
    }, name)

    protected val nextOperationQueue = Queue<OperationCompletionBlock>()


    init {
        operationCount += 1
    }


    protected open fun prepareForNewState(currentState: OperationState, newState: OperationState) {
        // TODO: Anything?
    }


    /**
     * Conditionally runs this operation, unless it's already running or has already run
     */
    fun go() {
        synchronized(this) {
            val state = _state
            when (state) {
                is ready -> _executeBlockUnconditionally()

                is executing,
                is finished,
                is OperationState.cancelled -> return
            }
        }
    }


    private fun _executeBlockUnconditionally() {
        thread.run()
    }


    @Suppress("unused")
    fun cancel() {
        _state = OperationState.cancelled
        thread.interrupt()
    }


    protected fun done(completionStatus: OperationCompletionStatus) {
        nextOperationQueue.pumpAll {
            it(completionStatus)
        }
    }


    fun then(nextOperation: OperationCompletionBlock): Operation {
        nextOperationQueue.pushOntoBack(nextOperation)
        return this
    }
}



open class OperationState {
    object ready : OperationState()
    object executing : OperationState()
    object finished : OperationState()
    object cancelled : OperationState()
}


open class OperationCompletionStatus {
    object successful: OperationCompletionStatus()
    object cancelled: OperationCompletionStatus()
    class failed(val exception: Throwable): OperationCompletionStatus()
}



/**
 * A block of code to be executed in an [Operation]
 */
typealias OperationBlock = () -> Unit

typealias OperationCompletionBlock = (status: OperationCompletionStatus) -> Unit

