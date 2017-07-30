package org.bh.tools.base.async


private var dependentOperationCount = 0



/**
 * @author Kyli Rouge
 * @since 2017-01-20
 */
@Deprecated("Not yet implemented", level = DeprecationLevel.HIDDEN)
class DependentOperation(block: OperationBlock,
                         name: String = "Blue Base Dependent Operation #$dependentOperationCount",
                         var dependencies: List<Operation>) : Operation(block = block, name = name) {
    init {
        dependentOperationCount += 1

        dependencies.forEach {
            it.then {

            }
        }
    }
}


internal open class DependentOperationState: OperationState() {
    class awaitingOtherOperation(val otherOperation: Operation) : DependentOperationState()
}