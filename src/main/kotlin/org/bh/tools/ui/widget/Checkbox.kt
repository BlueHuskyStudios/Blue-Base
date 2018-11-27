@file:Suppress("unused")

package org.bh.tools.ui.widget

import org.bh.tools.base.struct.Data
import org.bh.tools.base.struct.Ternary



/**
 * Defines how a generic checkbox should behave
 *
 * @author Ben Leggiero
 * @since 2018-01-21
 */
interface Checkbox : CheckboxBase<String, Boolean, CheckboxData, Checkbox>



/**
 * A basic implementation of the data that a checkbox should represent
 */
data class CheckboxData(
        override var text: String?,
        override var state: Boolean
) : CheckboxDataBase<String, Boolean>



/**
 * A checkbox that can have three states: checked, unchecked, and indeterminate
 *
 * @author Ben Leggiero
 * @since 2018-01-21
 */
interface TernaryCheckbox : CheckboxBase<String, Ternary, TernaryCheckboxData, TernaryCheckbox>



data class TernaryCheckboxData(
        /**
         * The text of the label on this checkbox
         */
        override var text: String?,
        override var state: Ternary
) : CheckboxDataBase<String, Ternary>



/**
 * The base definition of a checkbox. This really shouldn't be used; instead, use [Checkbox] or [TernaryCheckbox]
 *
 * @author Ben Leggiero
 * @since 2018-01-21
 */
interface CheckboxBase<Text, State, Represented, Self> : Widget<Represented>
        where Text : CharSequence,
              Represented : CheckboxDataBase<Text, State>,
              Self : CheckboxBase<Text, State, Represented, Self> {
    override var representedObject: Represented


    var state: State
        get() = representedObject.state
        set(newValue) { representedObject.state = newValue }


    var text: Text?
        get() = representedObject.text
        set(value) { representedObject.text = value }
}



/**
 * An easy accessor to the state value within the data value
 *
 * @author Ben Leggiero
 * @since 2018-11-25
 */
var <State, Data> CheckboxBase<*, State, Data, *>.state: State
        where Data : CheckboxDataBase<*, State>
    get() = representedObject.state
    set(newValue) { representedObject.state = newValue }



/**
 * An easy accessor to the state value within the data value
 *
 * @author Ben Leggiero
 * @since 2018-11-25
 */
var <Text, Data> CheckboxBase<Text, *, Data, *>.text: Text?
        where Text : CharSequence,
              Data : CheckboxDataBase<Text, *>
    get() = representedObject.text
    set(newValue) { representedObject.text = newValue }



// MARK: - Data

/**
 * Defines the basics of how the data behind any checkbox should look
 *
 * @author Ben Leggiero
 * @since 2018-11-25
 */
interface CheckboxDataBase<Text, State> : Data
        where Text : CharSequence {
    var text: Text?
    var state: State
}
