package org.bh.tools.base.basics

/**
 * @author Ben Leggiero
 * @since 2017-09-16
 */

public interface Cloneable<out Self: Cloneable<Self>> {
    fun clone(): Self
}
