package org.bh.tools.ui.widget

import org.bh.tools.base.struct.UIView

/**
 * @author Ben Leggiero
 * @since 2018-01-21
 */
interface Widget<RepresentedType>: UIView<RepresentedType> {
    val id: String
}
