package org.bh.tools.base.strings

import org.bh.tools.base.strings.Regex.Companion.Options
import java.util.regex.Pattern

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A shorter, more natural way to interact with regular expressions
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
class Regex(private val basis: Pattern) {
    constructor(basis: String, options: Set<Options> = emptySet()) : this(Pattern.compile(basis, options.intValue))

    companion object {
        enum class Options(val intValue: Int) {
            caseInsensitive(Pattern.CASE_INSENSITIVE),
            multiline(Pattern.MULTILINE),
            dotMatchesAnyCharacter(Pattern.DOTALL),
            unicodeCaseInsensitive(Pattern.UNICODE_CASE),
            canonicalMatch(Pattern.CANON_EQ),
            unixLineEndings(Pattern.UNIX_LINES),
            literal(Pattern.LITERAL),
            unicodeCharacterClass(Pattern.UNICODE_CHARACTER_CLASS),
            allowComments(Pattern.COMMENTS);
        }
    }

    /**
     * Determines whether the given string matches this regex
     */
    fun isMatch(possibleMatch: String): Boolean = basis.matcher(possibleMatch).matches()

    /**
     * Finds and returns any and all matching groups within the given string
     */
    fun groupsWithin(possiblyMatchingString: String): Array<String> {
        val matcher = basis.matcher(possiblyMatchingString)
        return (0 until matcher.groupCount()).map { matcher.group(it) }.toTypedArray()
    }
}

val Set<Regex.Companion.Options>.intValue: Int get() = this.map { it.intValue }.reduce(Int::or)

val String.regexValue: Regex get() = Regex(this)
fun String.regexValue(options: Set<Options>) = Regex(this, options)
/** Determines whether this string matches the given regex */
fun String.matchs(regex: Regex): Boolean = regex.isMatch(this)

/*
public class Regex {
  private let basis: NSRegularExpression
  public init(basis: NSRegularExpression)
  public init(basis: String, options: NSRegularExpression.Options = [])

  public func matches(_: String) -> Bool
  public func matches(in: String) -> [NSTextCheckingResult]
  public func groups(in: String) -> [String]
}

public extension String {
  public val regexValue: Regex
  public func regexValue(options: NSRegularExpression.Options) -> Regex
}
 */