package org.bh.tools.base.collections

import org.bh.tools.base.state.State
import org.bh.tools.base.state.StateChange
import org.bh.tools.base.state.StateStore
import java.util.*

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A stack of changes
 *
 * @author Kyli Rouge
 * @since 2016-11-16
 */
open class DeltaStack
        <ContentType : State<ContentType>, DeltaType : StateChange<ContentType>>
        (baseState: ContentType)
        : Stack<DeltaType>() {
    init {
        push(baseState.changeValue) // Currently broken in Kotlin 1.1
    }
}
