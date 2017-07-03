package org.bh.tools.base.async

import org.bh.tools.base.async.Operation.CompletionStatus.cancelled
import org.bh.tools.base.async.Operation.State.completed
import org.bh.tools.base.async.Operation.State.ready
import org.bh.tools.base.func.observing


private var operationCount = 0


/**
 * A basic operation to be executed either synchronously or asynchronously, and call a block upon completion.
 *
 * Inspired by [Swift Foundation's `Operation`](https://github.com/apple/swift-corelibs-foundation/blob/master/Foundation/Operation.swift)
 *
 * @author Kyli Rouge
 * @since 2017-01-20
 */
abstract class Operation {

    val name: String = "Blue Base Operation #$operationCount"

//            try {
//                if (_state is completed) {
//                    done(CompletionStatus.cancelled)
//                    return@Thread
//                } else {
//                    _state = executing
//                }
//
//                if (_state is completed) {
//                    done(CompletionStatus.cancelled)
//                    return@Thread
//                } else {
//                    block()
//                }
//
//                if (_state is completed) {
//                    done(CompletionStatus.cancelled)
//                    return@Thread
//                } else {
//                    _state = completed(status = successful)
//                }
//
//                if (_state is completed) {
//                    done(CompletionStatus.cancelled)
//                    return@Thread
//                } else {
//                    done(successful)
//                }
//            } catch (_: InterruptedException) {
//                _state = State.completed(status = cancelled)
//                done(CompletionStatus.cancelled)
//            } catch (uncaught: Throwable) {
//                System.err.println("Unexpected error when executing $name: $uncaught")
//                _state = State.completed(status = failed(problem = uncaught))
//                done(CompletionStatus.failed(problem = uncaught))
//            }
    init {
        operationCount += 1
    }

    protected var _state: State by observing(ready as State,
            willSet = { currentState, newState ->
                prepareForNewState(currentState, newState)
            }, didSet = { _, _ ->
        // notify listeners?
    })

    var state: State
        get() = _state
        internal set(newValue) {
            _state = newValue
        }

    var priority: Priority = Priority.normal

    var completionBlock: OperationCompletionBlock? = null


    /**
     * Called when the state is about to change. You cannot prevent this, but you can react to it.
     */
    abstract fun prepareForNewState(currentState: State, newState: State)


    /**
     * The main method of this operation, called when its primary purpose should be executed **synchronously**
     */
    abstract fun main()


    /**
     * The current state of an operation
     */
    sealed class State {
        /**
         * The operation is ready to start
         */
        object ready : State()

        /**
         * The operation is currently running
         */
        object executing : State()

        /**
         * The operation has finished running
         *
         * @param status
         */
        class completed(val status: CompletionStatus) : State()
    }


    /**
     * The status of the completion of an operation
     */
    sealed class CompletionStatus {
        /**
         * The operation completed normally
         */
        object successful : CompletionStatus()

        /**
         * The operation was cancelled intentionally
         */
        object cancelled : CompletionStatus()

        /**
         * The operation could not complete normally
         *
         * @param problem The problem that caused the operation to fail
         */
        class failed(val problem: Throwable) : CompletionStatus()
    }


    enum class Priority(val threadPriorityValue: ThreadPriority) {
        veryLow  (ThreadPriority.veryLow),
        low      (ThreadPriority.low),
        normal   (ThreadPriority.normal),
        high     (ThreadPriority.high),
        veryHigh (ThreadPriority.veryHigh),
        ;
    }
}



/**
 * A block of code to be executed in an [Operation]
 */
typealias OperationBlock = () -> Unit



/**
 * A block of code to be executed when an [Operation] completes
 *
 * @param status The status of the operation at the time of completion
 */
typealias OperationCompletionBlock = (status: Operation.CompletionStatus) -> Unit




enum class ThreadPriority(val jvmValue: Int) {
    veryLow  (Thread.MIN_PRIORITY),
    low      ((Thread.MIN_PRIORITY + Thread.NORM_PRIORITY) / 2),
    normal   (Thread.NORM_PRIORITY),
    high     ((Thread.MAX_PRIORITY + Thread.NORM_PRIORITY) / 2),
    veryHigh (Thread.MAX_PRIORITY),
    ;
}
