package org.bh.tools.base.collections

/**
 * HistoryArray, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS <hr/>
 *
 * An array with extended capabilities. Designed to be subclassed!
 *
 * @author Kyli
 * @since 2016-09-15
 */
class HistoryArray<ElementType>(var selectionMode: HistoryArraySelectionMode): BHMutableArray<ElementType>() {

}

enum class HistoryArraySelectionMode {
    SelectNewlyPushed,
    PersistSelection
}