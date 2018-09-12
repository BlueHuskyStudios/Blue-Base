@file:Suppress("unused")

package org.bh.tools.base.disambiguation

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*

/*
 * More clarity for less-clear names
 *
 * @author Ben Leggiero
 * @since 2017-08-17
 */



infix fun Int64.shiftBitsLeft(bitCount: Int8) = this shl bitCount.int32Value
infix fun Int32.shiftBitsLeft(bitCount: Int8) = this shl bitCount.int32Value
infix fun Int16.shiftBitsLeft(bitCount: Int8) = this.int32Value shl bitCount.int32Value
infix fun Int8.shiftBitsLeft(bitCount: Int8) = this.int32Value shl bitCount.int32Value

//infix fun Int64.`<<`(bitCount: Int8) = this shl bitCount.int32Value
//infix fun Int32.`<<`(bitCount: Int8) = this shl bitCount.int32Value
//infix fun Int16.`<<`(bitCount: Int8) = this.int32Value shl bitCount.int32Value
//infix fun Int8.`<<`(bitCount: Int8) = this.int32Value shl bitCount.int32Value


infix fun Int64.shiftBitsRightCopying(bitCount: Int8) = this shr bitCount.int32Value
infix fun Int32.shiftBitsRightCopying(bitCount: Int8) = this shr bitCount.int32Value
infix fun Int16.shiftBitsRightCopying(bitCount: Int8) = this.int32Value shr bitCount.int32Value
infix fun Int8.shiftBitsRightCopying(bitCount: Int8) = this.int32Value shr bitCount.int32Value

//infix fun Int64.`>>`(bitCount: Int8) = this shr bitCount.int32Value
//infix fun Int32.`>>`(bitCount: Int8) = this shr bitCount.int32Value
//infix fun Int16.`>>`(bitCount: Int8) = this.int32Value shr bitCount.int32Value
//infix fun Int8.`>>`(bitCount: Int8) = this.int32Value shr bitCount.int32Value


infix fun Int64.shiftBitsRightWithZeroes(bitCount: Int8) = this ushr bitCount.int32Value
infix fun Int32.shiftBitsRightWithZeroes(bitCount: Int8) = this ushr bitCount.int32Value
infix fun Int16.shiftBitsRightWithZeroes(bitCount: Int8) = this.int32Value ushr bitCount.int32Value
infix fun Int8.shiftBitsRightWithZeroes(bitCount: Int8) = this.int32Value ushr bitCount.int32Value

//infix fun Int64.`>>>`(bitCount: Int8) = this ushr bitCount.int32Value
//infix fun Int32.`>>>`(bitCount: Int8) = this ushr bitCount.int32Value
//infix fun Int16.`>>>`(bitCount: Int8) = this.int32Value ushr bitCount.int32Value
//infix fun Int8.`>>>`(bitCount: Int8) = this.int32Value ushr bitCount.int32Value

