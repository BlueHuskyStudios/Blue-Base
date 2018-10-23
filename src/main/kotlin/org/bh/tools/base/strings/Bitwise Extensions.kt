package org.bh.tools.base.strings

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * To make bitwise interactions easier
 *
 * @author Kyli Rouge
 * @since 2016-11-20
 */

fun Int.contains(mask: Int): Boolean = 0 != (this and mask)
