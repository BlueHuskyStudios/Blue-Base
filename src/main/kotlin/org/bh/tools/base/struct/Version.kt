@file:Suppress("unused", "EnumEntryName", "MemberVisibilityCanBePrivate")

package BlueBase

import BlueBase.VersionChannel.*

typealias VersionStage = Integer

/**
 * Version, made for BHToolbox, is copyright Blue Husky Software ©2014 BH-1-PS
 *
 * @author Kyli and Ben of Blue Husky Software
 *
 * @version 3.0.0
 *     - 3.1.0 (2016-10-21) - Ben Leggiero rewrote some things to be more Kotlin and pruned dead code
 *     - 3.0.0 (2016-10-21) - Kyli Rouge updated to Kotlin
 *     - 2.1.0 (2016-10-09) - Ben Leggiero updated to work without version 1, cleaned up warnings
 *     - 2.0.0 (2016-04-24) - Kyli Rouge updated to modern BH standards
 *     - 1.1.1 (2014-11-29) - Kyli Rouge Changed version pattern from `\d(\.\d)*` to `\d+(\.\d+)*`
 *     - 1.1.0 (2014-11-29) - Kyli Rouge added support for channels
 *
 * @since 2014-09-22
 */
data class Version
(
        /** The stages in this version. For instance, `1.0.2` would be `listOf(1,0,2)`. */
        val stages: List<VersionStage>,

        /** The channel in which this version lives. For instance, `2.0 β` would have the channel [β][VersionChannel.β]. */
        val channel: VersionChannel = stable
) : Comparable64<Version> {

    private val cachedStringValue by lazy { stages.toString(glue = ".") + channel.unicode.toString() }

    /**
     * Creates a version with the given channel and stages. For instance, if it's version 1.2.3 β, you would call
     * `Version(1,2,3, β)`
     * @param channel The channel of the version
     * @param stages  The stages (number) of the version
     */
    constructor(vararg stages: VersionStage, channel: VersionChannel = stable)
            : this(stages = stages.asList(), channel = channel)


    /**
     * @return a string representation of the version, like `"1.2.3β"`
     */
    override fun toString(): String = cachedStringValue


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
    override fun compareTo(other: Version): Int32 = compareTo64(other).int32Value

    override fun compareTo64(otherComparable: Version): Int64 {
        return when {
            equals(otherComparable) -> 0
            channel != otherComparable.channel -> (channel.ordinal - otherComparable.channel.ordinal).toLong()
            else -> {
                val l = min(stages.size, otherComparable.stages.size)
                (0 until l)
                        .firstOrNull { stages[it] != otherComparable.stages[it] }
                        ?.let { stages[it] - otherComparable.stages[it] }
                        ?: (stages.size - otherComparable.stages.size).int64Value
            }
        }
    }
}

/** Creates a new version with the given stages and the default channel */
fun v(vararg versionStage: VersionStage): Version = Version(stages = *versionStage)

/** Creates a new version with the given stages and channel */
fun v(vararg versionStage: VersionStage, channel: VersionChannel): Version = Version(stages = *versionStage, channel = channel)



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