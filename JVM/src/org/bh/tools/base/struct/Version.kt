@file:Suppress("unused")

package org.bh.tools.base.struct

//import org.bh.tools.base.math.NumberConversion

import org.bh.tools.base.collections.BHArray
import org.bh.tools.base.math.Comparable64
import org.bh.tools.base.struct.VersionChannel.stable
import java.util.*

typealias VersionStage = Long

/**
 * Version, made for BHToolbox, is copyright Blue Husky Software ©2014 BH-1-PS
 *
 * @author Kyli and Ben of Blue Husky Software
 *
 * @version 3.0.0
 *     - 3.0.0 (2016-10-21) - Kyli Rouge updated to Kotlin
 *     - 2.1.0 (2016-10-09) - Ben Leggiero updated to work without version 1, cleaned up warnings
 *     - 2.0.0 (2016-04-24) - Kyli Rouge updated to modern BH standards
 *     - 1.1.1 (2014-11-29) - Kyli Rouge Changed version pattern from \d(\.\d)* to \d+(\.\d+)*
 *     - 1.1.0 (2014-11-29) - Kyli Rouge added support for channels
 *
 * @since 2014-09-22
 */
data class Version
(val stages: Array<VersionStage>, val channel: VersionChannel = stable)
    : Comparable<Version>, Comparable64<Version> {

    private var cache: String? = null

//    /**
//     * Creates a version with the given stages. For instance, if it's version 1.2.3, you would call
//     * `Version(1,2,3)`
//     * @param initStages The stages (number) of the version
//     */
//    constructor(vararg initStages: VersionStage) : this(initStages, stable)

    /**
     * Creates a version with the given channel and stages. For instance, if it's version 1.2.3 β, you would call
     * `Version(1,2,3, β)`
     * @param initChannel The channel of the version
     * @param initStages  The stages (number) of the version
     */
    constructor(vararg initStages: VersionStage, initChannel: VersionChannel = stable) :
            this(stages = initStages.toTypedArray(), channel = initChannel)

    /**
     * @return a string representation of the version, like: `&quot;1.2.3β&quot;`
     */
    override fun toString(): String {
        if (cache == null) {
            cache = BHArray(stages).toString(glue = ".") + Character.toString(channel.unicode)
        }
        return cache!!
    }

    override fun hashCode(): Int {
        var hash = 3
        hash = 29 * hash + Arrays.deepHashCode(this.stages)
        return hash
    }

    /**
     * Evaluates whether these are equal. Evaluation happens in this order:
     *
     * check with == (yes? true)
     * whether other is null (yes? false)
     * whether they're the same class (no? false)
     * whether their channels are equal (no? false)
     * whether their stages are equal (no? false)
     * if all of the above are not tripped, return true
     *
     * @param other the other object to test
     *
     * @return `true` iff both this and the other object are equal
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other !is Version) {
            return false
        }
        if (this.channel != other.channel) {
            return false
        }
        if (!Arrays.deepEquals(this.stages, other.stages)) {
            return false
        }
        return true
    }


    /**
     *  1. If the given object is this object, `0` is returned
     *  2. Else, if the given object's channel is different from the other Version's, the difference in ordinals is
     * returned
     *  3. Else, if any one of this Version's stages is different from the other Version's corresponding stage, the
     * difference is returned
     *  4. Else, the difference between the number of stages is returned.
     *
     * @param other the other object to compare this one against
     * @return an integer, centered around 0, telling how much more or less this object is than the other.
     */
    override fun compareTo(other: Version): Int {
        return compareTo64(other).toInt()
    }

    override fun compareTo64(otherComparable: Version): Long {
        if (equals(otherComparable)) {
            return 0
        }
        if (channel != otherComparable.channel) {
            return (channel.ordinal - otherComparable.channel.ordinal).toLong()
        }
        var i = 0
        val l = Math.min(stages.size, otherComparable.stages.size)
        while (i < l) {
            if (stages[i] != otherComparable.stages[i]) {
                return stages[i] - otherComparable.stages[i]
            }
            i++
        }
        return (stages.size - otherComparable.stages.size).toLong()
    }
}

/**
 * Represents a channel (stable, beta, alpha, lambda):
 * https://github.com/BlueHuskyStudios/Blue-Husky-s-Stages-of-Product-Creation
 */
enum class VersionChannel

    /**
     * Creates a new Channel with the given translations.

     * @param ascii   The ASCII symbol of the channel.
     * @param unicode The Unicode symbol of the channel. This is preferred.
     * @param html    The HTML escape of the Unicode symbol of the channel.
     */
    constructor(
            /**
             * The ASCII symbol of the channel.
             */
            val ascii: Char,
            /**
             * The Unicode symbol of the channel.
             */
            val unicode: Char,
            /**
             * The HTML escape of the Unicode symbol of the channel.
             */
            val html: String
    ) {

    /**
     * Signifies that this software is in very unstable or incomplete testing, and the next major release should be
     * an unstable alpha test.
     *
     * <DL>
     * <DT>ascii</DT>
     * <DD>{@code l} (108)</DD>
     * <DT>Unicode</DT>
     * <DD>U+03BB Greek Small Letter Lambda</DD>
     * <DT>HTML</DT>
     * <DD>{@code "&lambda;"}</DD>
     * </DL>
     */
    λ('l', 'λ', "&lambda;"),

    /**
     * Signifies that this software is in unstable alpha testing, and the next major release should be a little more
     * stable (beta).
     *
     * <DL>
     * <DT>ASCII</DT>
     * <DD>{@code a} (97)</DD>
     * <DT>Unicode</DT>
     * <DD>U+03B1 Greek Small Letter Alpha</DD>
     * <DT>HTML</DT>
     * <DD>{@code "&alpha;"}</DD>
     * </DL>
     */
    α('a', 'α', "&alpha;"),

    /**
     * Signifies that this software is in beta testing, and the next major release should be stable.
     *
     * <DL>
     * <DT>ASCII</DT>
     * <DD>{@code b} (98)</DD>
     * <DT>Unicode</DT>
     * <DD>U+03B2 Greek Small Letter Beta</DD>
     * <DT>HTML</DT>
     * <DD>{@code "&beta;"}</DD>
     * </DL>
     */
    β('b', 'β', "&beta;"),

    /**
     * Signifies that this software is stable. This is the only channel without a symbol.
     *
     * <DL>
     * <DT>ASCII</DT>
     * <DD>space (20)</DD>
     * <DT>Unicode</DT>
     * <DD>U+200C Zero-Width Non-Joiner</DD>
     * <DT>HTML</DT>
     * <DD>the empty string ({@code ""})</DD>
     * </DL>
     */
    stable(' ', 0x200C.toChar(), "");

    companion object {
        /**
         * A typing-friendly version of [λ]
         * @see [λ]
         */
        val lambda: VersionChannel get() = λ

        /**
         * A typing-friendly version of [α]
         * @see [α]
         */
        val alpha: VersionChannel get() = α

        /**
         * A typing-friendly version of [β]
         * @see [β]
         */
        val beta: VersionChannel get() = β
    }
}