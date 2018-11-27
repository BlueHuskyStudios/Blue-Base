package org.bh.tools.io.serialization

/**
 * @author Ben Leggiero
 * @since 2018-03-09
 */


fun Any.jsonString() = JSON.stringify(this)
