package org.bh.tools.base.collections

import org.bh.tools.base.func.observing

/**
 * HistoryList, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * TODO: A list that keeps past and future history. This can be used for back/forward navigation, undo/redo, etc
 *
 * @author Kyli, Ben
 * @since 2016-09-15
 */
@Deprecated("Not yet implemented")
open class HistoryList<ElementType>(initialSelectionMode: HistoryListSelectionMode): ArrayList<ElementType>() {
    var selectionMode: HistoryListSelectionMode by observing(initialSelectionMode, didSet = { _, _->})
}

/**
 * How selections work in [HistoryList]
 */
enum class HistoryListSelectionMode {
    /**
     * Select elements as they are added into the list
     */
    selectNewElements,

    /**
     * Attempt to keep the same selection even as the list's content changes
     */
    persistSelection
}