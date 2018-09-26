@file:Suppress("unused")

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*

/* Magic Numbers, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
*
* For when real values aren't good enough.
* Created by Kyli Rouge on 2016-10-24.
*/

/**
 * Indicates that something could not be found. If this needs to be communicated without optionals, use [notFoundNumber]
 */
val notFound: Int? = null

/**
 * Indicates that something could not be found, when a number must represent this.
 * Use of this is discouraged. If at all possible, use [notFound].
 */
val notFoundNumber = Integer.min
