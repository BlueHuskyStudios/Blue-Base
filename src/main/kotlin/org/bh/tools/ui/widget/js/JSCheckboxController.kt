package org.bh.tools.ui.widget.js

import org.bh.tools.base.struct.*
import org.bh.tools.base.struct.Ternary.*
import org.bh.tools.ui.behavior.*
import org.bh.tools.ui.widget.*
import org.w3c.dom.*


typealias HTMLCheckboxElement = HTMLInputElement



/**
 * A controller that connects the [TernaryCheckbox] model and the [HTMLCheckboxElement] view
 *
 * @author Ben Leggiero
 * @since 2018-01-22
 */
class JSCheckboxController(val controlledElement: HTMLCheckboxElement): TernaryCheckbox {

    override val id: String
        get() = controlledElement.id

    override var representedObject: TernaryCheckboxData
        get() = TernaryCheckboxData(text = controlledElement.textContent, state = controlledElement.ternaryState)
        set(newValue) {
            val oldValue by lazy { TernaryCheckboxData(text = controlledElement.textContent, state = controlledElement.ternaryState) }

            controlledElement.textContent = newValue.text
            controlledElement.ternaryState = newValue.state

            onStateChangeObservers.forEach { it(oldValue, newValue) }
        }

    private var onStateChangeObservers = mutableSetOf<OnRepresentedObjectChange<TernaryCheckboxData>>()


    init {
        controlledElement.onchange = {
            onStateChangeObservers.forEach { listener -> listener(null, representedObject) }
        }
    }

    override fun onRepresentedObjectDidChange(onRepresentedObjectChange: OnRepresentedObjectChange<TernaryCheckboxData>) {
        onStateChangeObservers.add(onRepresentedObjectChange)
    }
}



/**
 * Reads the state of this checkbox view and returns it as a clean [Ternary] value
 */
var HTMLCheckboxElement.ternaryState: Ternary
    get() = when (this.indeterminate) {
        true -> Ternary.indeterminate
        false -> Ternary(fromBoolean = this.checked)
    }
    set(value) {
        when (value) {
            `true` -> {
                this.checked = true
                this.indeterminate = false
            }
            `false` -> {
                this.checked = false
                this.indeterminate = false
            }
            Ternary.indeterminate -> {
                this.indeterminate = true
            }
        }
    }



/**
 * Like [JSCheckboxController], but for a [TernaryCheckboxTree]
 */
class JSTernaryCheckboxTreeController(val rootCheckbox: JSCheckboxController, children: List<JSCheckboxController>)
    : TernaryCheckboxTree(children = children, id = rootCheckbox.id, text = rootCheckbox.text) {

    constructor(rootCheckbox: HTMLCheckboxElement, children: List<HTMLCheckboxElement>): this(rootCheckbox = JSCheckboxController(rootCheckbox),
                                                                                              children = children.map { JSCheckboxController(it) })

    init {
        rootCheckbox.state = this.state

        rootCheckbox.onRepresentedObjectDidChange { _, newValue ->
            this.representedObject = newValue
        }
        this.onRepresentedObjectDidChange { _, newValue ->
            rootCheckbox.representedObject = newValue
        }
    }


    companion object
}
