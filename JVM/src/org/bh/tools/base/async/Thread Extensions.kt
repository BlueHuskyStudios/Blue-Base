package org.bh.tools.base.async

import org.bh.tools.base.math.*
import org.bh.tools.base.util.TimeConversion.millisecondsPerTimeInterval
import org.bh.tools.base.util.TimeInterval

/*
 * To make Threads easier
 *
 * @author Ben Leggiero
 * @since 2017-07-03
 */



@Suppress("NOTHING_TO_INLINE")
inline fun sleep(wait: TimeInterval) = Thread.sleep((wait * millisecondsPerTimeInterval).int64Value)
