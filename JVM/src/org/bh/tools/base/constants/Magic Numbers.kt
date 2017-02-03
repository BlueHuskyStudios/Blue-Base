@file:Suppress("unused")

/* Magic Numbers, made for Blue Base, is copyright Blue Husky Software ©2016 BH-1-PS.
*
* For when real values aren't good enough.
* Created by Kyli Rouge on 2016-10-24.
*/

/**
 * Indicates that something could not be found. If this needs to be communicated without optionals, use [NotFoundNumber]
 */
val NotFound: Int? = null

/**
 * Indicates that something could not be found, when a number must represent this.
 * Use of this is discouraged. If at all possible, use [NotFound].
 */
val NotFoundNumber = -1
