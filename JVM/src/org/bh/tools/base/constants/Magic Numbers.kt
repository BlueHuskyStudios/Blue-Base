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

/**
 * The result of a comparison
 */
enum class ComparisonResult(
        /**
         * The value of this result as an [Int]
         */
        val intValue: Int
) {
    /**
     * Indicates that the left item is less/lower than the right item (the value ascends from left to right)
     */
    ascending(-1),
    /**
     * Indicates that the left item is the same as the right
     */
    same(0),
    /**
     * Indicates that the left item is greater/higher than the right item (the value descends from left to right)
     */
    descending(1);

    /**
     * This enum as a native type
     */
    public typealias NativeType = Int
    /**
     * The value of this result as a native type
     */
    public val nativeValue: Int get() = intValue

    companion object {
        /**
         * Indicates that the **left** item is greater/higher than the right
         */
        public val left: ComparisonResult get() = descending
        /**
         * Indicates that **neither** item is greater/higher than the other
         */
        public val equal: ComparisonResult get() = same
        /**
         * Indicates that the **right** item is greater/higher than the left
         */
        public val right: ComparisonResult get() = ascending
    }
}