package org.bh.tools.base.async

import org.bh.tools.base.math.roundedInt32Value
import org.bh.tools.base.util.*
import java.awt.event.ActionListener
import javax.swing.Timer

/*
 * Timers become more timely
 *
 * @author Ben Leggiero
 * @since 2017-03-13
 */


/**
 * Creates a timer executes the given task repeatedly after the given delay in seconds
 *
 * @param delayInSeconds The number of seconds between each repetition of the timer
 * @param action         The action performed on each repetition of the timer
 */
fun Timer(delayInSeconds: TimeInterval, action: ActionListener): Timer {
    return Timer(TimeConversion.convert(TimeAmount(delayInSeconds, TimeUnit.seconds), newUnit = TimeUnit.milliseconds).amount.roundedInt32Value, action)
}


/**
 * Creates a timer executes the given task repeatedly after the given delay in seconds
 *
 * @param delayInSeconds The number of seconds between each repetition of the timer
 * @param action         The action performed on each repetition of the timer
 */
fun Timer(delayInSeconds: TimeInterval, action: () -> Unit): Timer {
    return Timer(delayInSeconds, ActionListener { action() })
}
