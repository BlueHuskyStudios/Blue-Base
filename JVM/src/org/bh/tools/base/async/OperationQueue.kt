package org.bh.tools.base.async

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.collections.pumpAll
import org.bh.tools.base.util.TimeInterval
import org.bh.tools.base.async.Operation.*
import java.util.*

/**
 * A queue of operations
 *
 * @author Ben Leggiero
 * @since 2017-07-02
 */
class OperationQueue

/**
 * Creates a new operation queue
 *
 * @param name        The name of the queue, used in the underlying thread
 * @param threadGroup _optional_ - The thread group in which to place this operation queue's underlying thread
 */
constructor(
        /**
         * The name of the queue, used in the underlying thread
         */
        val name: String,

        /**
         * _optional_ - The thread group in which to place this operation queue's underlying thread
         */
        threadGroup: ThreadGroup? = null
) {

    private val compare: (Operation, Operation) -> Int32 = { lhs, rhs ->
        /*return*/ lhs.priority.compareTo(rhs.priority)
    }

    private val backingQueue = PriorityQueue<Operation>(compare)

    private val thread = Thread({ threadMain() }, "OperationQueue $name")

    var threadPriority = ThreadPriority.normal


    init {
        thread.start()
    }


    fun enqueue(operation: Operation) {
        backingQueue.add(operation)
    }


    /**
     * Acts as the underlying thread's main method
     */
    private fun threadMain() {
        while (true) {
            if (backingQueue.isEmpty()) {
                try {
                    sleep(delayBetweenQueuePolling)
                }
                catch (problem: InterruptedException) {
                    println("Interrupted by ${problem.javaClass.simpleName} while sleeping $delayBetweenQueuePolling ms. Ignoring it.")
                }
                catch (problem: Throwable) {
                    println("Caught a ${problem.javaClass.simpleName} while sleeping $delayBetweenQueuePolling ms. Attempting to ignore it.")
                }
                continue
            }
            else {
                backingQueue.pumpAll {
                    Thread.currentThread().priority = it.priority.threadPriorityValue.jvmValue

                    try {
                        if (it.state is State.completed) {
                            it.completionBlock?.invoke(CompletionStatus.cancelled)
                            return@pumpAll
                        }
                        else {
                            it.state = State.executing
                        }

                        if (it.state is State.completed) {
                            it.completionBlock?.invoke(CompletionStatus.cancelled)
                            return@pumpAll
                        }
                        else {
                            it.main()
                        }

                        it.state = State.completed(status = CompletionStatus.successful)
                        it.completionBlock?.invoke(CompletionStatus.successful)
                    }
                    catch (_: InterruptedException) {
                        it.state = State.completed(status = CompletionStatus.cancelled)
                        it.completionBlock?.invoke(CompletionStatus.cancelled)
                    }
                    catch (uncaught: Throwable) {
                        System.err.println("Unexpected error when executing Operation ${it.name}: $uncaught")
                        it.state = State.completed(status = CompletionStatus.failed(problem = uncaught))
                        it.completionBlock?.invoke(CompletionStatus.failed(problem = uncaught))
                    }
                }
            }

            Thread.currentThread().priority = threadPriority.jvmValue
        }
    }


    companion object {
        val main by lazy { OperationQueue(name = "main") }

        private val delayBetweenQueuePolling: TimeInterval = 0.25
    }
}