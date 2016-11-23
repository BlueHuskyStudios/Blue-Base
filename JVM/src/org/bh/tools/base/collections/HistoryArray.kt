package org.bh.tools.base.collections

/**
 * HistoryArray, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * An array with extended capabilities. Designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
open class HistoryArray<ElementType>(var selectionMode: HistoryArraySelectionMode): BHMutableArray<ElementType>() {

}

/**
 * How selections work in [HistoryArray]
 */
enum class HistoryArraySelectionMode {
    /**
     * Select items as they are pushed onto the array
     */
    SelectNewlyPushed,

    /**
     * Attempt to keep the same selection even as the array contents changes
     */
    PersistSelection
}