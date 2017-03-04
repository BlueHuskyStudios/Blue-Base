@file:Suppress("unused")

package org.bh.tools.base.disambiguation

import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.disambiguation.OSArchitecture.*
import org.bh.tools.base.disambiguation.OSSupport.*
import org.bh.tools.base.func.tuple
import org.bh.tools.base.struct.Version
import org.bh.tools.base.struct.v
import kotlin.reflect.KClass

/**
 * For easy OS detection.
 *
 * Recommended usage: `[OS.current][OS.Companion.current]`
 *
 * See https://www.java.com/en/download/help/sysreq.xml for compatibility specifics
 *
 * @author Ben Leggiero
 * @since 2017-02-26
 */
sealed class OS(
        val rawName: String,
        val rawVersion: String?,
        val architecture: OSArchitecture
) {
    /**
     * The Windows family of operating systems
     */
    class windows(val subtype: WindowsSubtype,
                  rawName: String,
                  rawVersion: String,
                  architecture: OSArchitecture)
        : OS(rawName = rawName,
            rawVersion = rawVersion,
            architecture = architecture)

    class macOS(val subtype: MacOSSubtype,
                rawName: String,
                rawVersion: String,
                architecture: OSArchitecture)
        : OS(rawName = rawName,
            rawVersion = rawVersion,
            architecture = architecture)

    class linux(val subtype: LinuxSubtype,
                rawName: String,
                rawVersion: String,
                architecture: OSArchitecture)
        : OS(rawName = rawName,
            rawVersion = rawVersion,
            architecture = architecture)

    class android(val subtype: AndroidSubtype,
                  rawName: String,
                  rawVersion: String,
                  architecture: OSArchitecture)
        : OS(rawName = rawName,
            rawVersion = rawVersion,
            architecture = architecture)

    class unknown(rawName: String,
                  rawVersion: String?,
                  architecture: OSArchitecture)
        : OS(rawName = rawName,
            rawVersion = rawVersion,
            architecture = architecture)


    /**
     * Indicates whether this is likely a desktop OS. This may still be wrong if the OS is being run in an odd way
     * (e.g. specialized Android with a large screen, mouse, and keyboard) or not correctly reporting.
     *
     * When it's too ambiguous (e.g. `unknown` or `other`), `false` is reported.
     */
    val isDesktop: Boolean by lazy {
        when (this) {
            is macOS -> false
            is windows,
            is linux -> when (architecture) {
                is i386,
                is i686,
                is x86,
                is x86_64,
                is amd64 -> true

                is arm6,
                is arm7,
                is arm64 -> false

                is powerPC,
                is ppc,
                is sparc -> true

                is other,
                is OSArchitecture.unknown -> false
            }
            is android -> false
            is unknown -> false
        }
    }



    companion object {
        /** A placeholder in case iOS ever supports JVM apps */
        @Deprecated("iOS does not support JVM apps, and Blue Base does not yet support Kotlin Native",
                ReplaceWith("unknown(\"iOS\", version, )"), DeprecationLevel.ERROR)
        @JvmStatic
        val iOS = OS.unknown("iOS", "all", OSArchitecture.arm64("arm64"))


        /** Finds and returns the current OS, as parsed from `System.getProperty("os.name")` to [OS] */
        @JvmStatic
        val current by lazy {
            val rawName = System.getProperty("os.name")
            val rawVersion = System.getProperty("os.version")
            val rawArchitecture = System.getProperty("os.arch")
            return@lazy fromRaw(rawName = rawName, rawVersion = rawVersion, rawArchitecture = rawArchitecture)
        }


        /**
         * Chooses an [OS] that fits the given [rawName]
         *
         * @param rawName The name of the OS, which will be parsed into an [OS]
         *
         * @return an [OS] that fits the given [rawName], or [unknown][OS.unknown] if it can't be parsed to a known one
         */
        @JvmStatic
        fun fromRaw(rawName: String, rawVersion: String? = null, rawArchitecture: String? = null): OS =
                WindowsSubtype.fromRawName(rawName)?.let { subtype ->
                    windows(subtype = subtype,
                            rawName = rawName,
                            rawVersion = rawVersion ?: subtype.newestVersion.toString(),
                            architecture = OSArchitecture.fromRaw(rawArchitecture))
                } ?: MacOSSubtype.fromRawName(rawName)?.let { subtype ->
                    macOS(subtype = subtype,
                            rawName = rawName,
                            rawVersion = rawVersion ?: subtype.newestVersion.toString(),
                            architecture = OSArchitecture.fromRaw(rawArchitecture))
                } ?: LinuxSubtype.fromRawName(rawName)?.let { subtype ->
                    linux(subtype = subtype,
                            rawName = rawName,
                            rawVersion = rawVersion ?: subtype.newestVersion.toString(),
                            architecture = OSArchitecture.fromRaw(rawArchitecture))
                } ?: AndroidSubtype.fromRawName(rawName)?.let { subtype ->
                    android(subtype = subtype,
                            rawName = rawName,
                            rawVersion = rawVersion ?: subtype.newestVersion.toString(),
                            architecture = OSArchitecture.fromRaw(rawArchitecture))
                } ?: unknown(rawName = rawName,
                        rawVersion = rawVersion,
                        architecture = OSArchitecture.fromRaw(rawArchitecture))
    }
}



/** An operating system's architecture */
sealed class OSArchitecture(
        /** The raw string representation of this architecture, like `x86_64` */
        val raw: String
) {

    /** The i386 architecture */
    class i386(raw: String) : OSArchitecture(raw)

    class i686(raw: String) : OSArchitecture(raw)
    class x86(raw: String) : OSArchitecture(raw)
    class x86_64(raw: String) : OSArchitecture(raw)
    class amd64(raw: String) : OSArchitecture(raw)

    class arm6(raw: String) : OSArchitecture(raw)
    class arm7(raw: String) : OSArchitecture(raw)
    class arm64(raw: String) : OSArchitecture(raw)

    class powerPC(raw: String) : OSArchitecture(raw)
    class ppc(raw: String) : OSArchitecture(raw)
    class sparc(raw: String) : OSArchitecture(raw)

    class other(raw: String) : OSArchitecture(raw)

    object unknown : OSArchitecture("unknown")


    companion object {

        /** The classes for all architectures except [other] */
        @JvmStatic
        val definiteArchitectureKClasses by lazy {
            listOf(i386::class,
                    i686::class,
                    x86::class,
                    x86_64::class,
                    amd64::class,

                    arm6::class,
                    arm7::class,
                    arm64::class,

                    powerPC::class,
                    ppc::class,
                    sparc::class)
        }


        /** The regexes that match all architectures except [other] */
        @JvmStatic
        val definiteArchitectureRegexes = mapOf(
                tuple(i386::class, "^i386$".toRegex()),
                tuple(i686::class, "^i686$".toRegex()),
                tuple(x86::class, "^x86$".toRegex()),
                tuple(x86_64::class, "^x86_64$".toRegex()),
                tuple(amd64::class, "^amd64$".toRegex()),

                tuple(arm6::class, "^arm6$".toRegex()),
                tuple(arm7::class, "^arm7$".toRegex()),
                tuple(arm64::class, "^arm64$".toRegex()),

                tuple(powerPC::class, "^powerpc$".toRegex()),
                tuple(ppc::class, "^ppc$".toRegex()),
                tuple(sparc::class, "^sparc$".toRegex())
        )


        @JvmStatic
        fun fromRaw(raw: String?): OSArchitecture = when {
            raw == null -> unknown

            definiteArchitectureRegexes[i386::class]?.matches(raw) ?: false -> i386(raw)
            definiteArchitectureRegexes[i686::class]?.matches(raw) ?: false -> i686(raw)
            definiteArchitectureRegexes[x86::class]?.matches(raw) ?: false -> x86(raw)
            definiteArchitectureRegexes[x86_64::class]?.matches(raw) ?: false -> x86_64(raw)
            definiteArchitectureRegexes[amd64::class]?.matches(raw) ?: false -> amd64(raw)

            definiteArchitectureRegexes[arm6::class]?.matches(raw) ?: false -> arm6(raw)
            definiteArchitectureRegexes[arm7::class]?.matches(raw) ?: false -> arm7(raw)
            definiteArchitectureRegexes[arm64::class]?.matches(raw) ?: false -> arm64(raw)

            definiteArchitectureRegexes[powerPC::class]?.matches(raw) ?: false -> powerPC(raw)
            definiteArchitectureRegexes[ppc::class]?.matches(raw) ?: false -> ppc(raw)
            definiteArchitectureRegexes[sparc::class]?.matches(raw) ?: false -> sparc(raw)

            else -> other(raw)
        }
    }
}


/** The regex that matches all architectures of this class */
val KClass<OSArchitecture>.regex: Regex get() = OSArchitecture.definiteArchitectureRegexes[this]!!



/** The tier by which the OS is supported */
enum class OSSupport {
    /** The OS is not supported at all */
    unsupported,

    /** The OS is supported, but will not be in the future */
    deprecated,

    /** The OS is supported */
    supported,

    /** The support of the OS is unknown */
    unknown
}



const private val windowsDeprecationMessage = "Windows versions prior to Vista are not supported by Java 8"
const private val windowsDeprecationReplacement = "windows_Vista"

/** The sub-type of Windows, like "Windows Vista" or "Windows 10" */
enum class WindowsSubtype(
        /** The tier of support this library offers to this windows subtype */
        val support: OSSupport,
        /** The regex that matches this Windows subtype */
        val regex: Regex,
        /** The latest version that is officially supported, or `null` if that cannot be determined */
        val newestVersion: Version? = null
) {
    /** The Windows 9x family of operating systems */
    @Deprecated(windowsDeprecationMessage, ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_9x(support = unsupported, regex = "^win(dows)?\\s*9[58]$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,10,2222)),

    /** The Windows NT family of operating systems */
    @Deprecated(windowsDeprecationMessage, ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_NT(support = unsupported, regex = "^win(dows)?\\s*NT(\\s*(3.[15]1?|4(.0)?))?$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,0)),

    /** The Windows 2000 operating system */
    @Deprecated(windowsDeprecationMessage, ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_2000(support = unsupported, regex = "^win(dows)?\\s*2000$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(5,0,2195)),

    /** The Windows ME operating system */
    @Deprecated(windowsDeprecationMessage, ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_ME(support = unsupported, regex = "^win(dows)?\\s*(ME|Millennium\\s*Edition)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,90,3000)),

    /** The Windows XP family of operating systems */
    @Deprecated(windowsDeprecationMessage, ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_XP(support = deprecated, regex = "^win(dows)?\\s*XP".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(5,1,2600)),

    /** The Windows Vista family of operating systems */
    windows_Vista(support = supported, regex = "^win(dows)?\\s*Vista\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,0,6002)),

    /** The Windows 7 family of operating systems */
    windows_7(support = supported, regex = "^win(dows)?\\s*7\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,1,7601)),

    /** The Windows 8.x family of operating systems */
    windows_8x(support = supported, regex = "^win(dows)?\\s*8(\\.1)?(\\s*(Pro|Enterprise)(\\s*Edition)?)?$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,3,9600)),

    /** The Windows 8 RT operating system */
    @Deprecated("Windows RT does not run JVM apps", ReplaceWith(windowsDeprecationReplacement), DeprecationLevel.WARNING)
    windows_RT(support = unsupported, regex = "^win(dows)?(\\s*8)?\\s*RT\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,3,9600)),

    /** The Windows 10 family of operating systems */
    windows_10(support = supported, regex = "^win(dows)?\\s*10?\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,0,14393)),


    /** The Windows Server 2008 operating system */
    windowsServer_2008(support = supported, regex = "^win(dows)?\\s*server\\s*2008$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,0,6002)),

    /** The Windows Server 2008 R2 operating system */
    windowsServer_2008_R2(support = supported, regex = "^win(dows)?\\s*server\\s*2008\\s*r2$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,1,7601)),

    /** The Windows Server 2012 operating system */
    windowsServer_2012(support = supported, regex = "^win(dows)?\\s*server\\s*2012$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,3,9600)),

    /** The Windows Server 2012 R2 operating system */
    windowsServer_2012_R2(support = supported, regex = "^win(dows)?\\s*server\\s*2012\\s*r2$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,3,9600)),

    /** The Windows Server 2016 operating system */
    windowsServer_2016(support = supported, regex = "^win(dows)?\\s*server\\s*2016$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,0,14393)),


    /** The OS is likely windows, but the version is unknown. It is likely newer than this library. */
    unknown(support = OSSupport.unknown, regex = "^win(dows)?".toRegex(RegexOption.IGNORE_CASE));


    companion object {
        /**
         * Turns the given raw name into a [WindowsSubtype]
         *
         * @param rawName The name as a string, like `Windows 10`. Case is ignored.
         *
         * @return The Windows subtype that best matches the given raw name, or [unknown] if it's likely an unknown
         *         type of Windows, or `null` if it's likely not a type of Windows at all.
         */
        fun fromRawName(rawName: String): WindowsSubtype? = values().firstOrNull { it.regex.containsMatchIn(rawName) }
    }
}



private const val macOSDeprecationMessage_java7 = "macOS versions prior to Lion (10.7.3) are not supported by Java 7"
private const val macOSDeprecationMessage = "macOS versions prior to Mountain Lion (10.8.3) are not supported by Java 8"
const private val macOSDeprecationReplacement = "mountainLion"

/** The sub-type of macOS, like "Mac OS X Mountain Lion" or "macOS Sierra" */
enum class MacOSSubtype(
        /** The tier of support this library offers to this OS */
        val support: OSSupport,
        /** The regex that matches this macOS subtype */
        val regex: Regex,
        /** The latest version that is officially supported, or `null` if that cannot be determined */
        val newestVersion: Version? = null
) {
    /** The Mac OS X Cheetah family of operating systems (10.0.0 to 10.0.4) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    cheetah(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(cheetah|10\\.0(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,0,4)),

    /** The Mac OS X Puma family of operating systems (10.1.0 to 10.1.5) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    puma(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(puma|10\\.1(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,1,5)),

    /** The Mac OS X Jaguar family of operating systems (10.2.0 to 10.2.8) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    jaguar(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(jaguar|10\\.2(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,2,8)),

    /** The Mac OS X Panther family of operating systems (10.3.0 to 10.3.9) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    panther(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(panther|10\\.3(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,3,9)),

    /** The Mac OS X Tiger family of operating systems (10.4.0 to 10.4.11) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    tiger(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(tiger|10\\.4(\\.\\d{1,2})?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,4,11)),

    /** The Mac OS X Leopard family of operating systems (10.5.0 to 10.5.8) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    leopard(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(leopard|10\\.5(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,5,8)),

    /** The Mac OS X Snow Leopard family of operating systems (10.6.0 to 10.6.8) */
    @Deprecated(macOSDeprecationMessage, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    snowLeopard(support = unsupported, regex = "^(mac)?\\s*os\\s*x\\s*(snow\\s*leopard|10\\.6(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,6,8)),

    /** The Mac OS X Lion family of operating systems (10.7.0 to 10.7.5) */
    @Deprecated(macOSDeprecationMessage_java7, ReplaceWith(macOSDeprecationReplacement), DeprecationLevel.WARNING)
    lion(support = deprecated, regex = "^(mac)?\\s*os\\s*x\\s*(lion|10\\.7(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,7,5)),

    /** The Mac OS X Lion family of operating systems (10.8.0 to 10.8.5) */
    mountainLion(support = supported, regex = "^(mac)?\\s*os\\s*x\\s*(mountain\\s*lion|10\\.8(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,8,5)),

    /** The OS X Mavericks family of operating systems (10.9.0 to 10.9.5) */
    mavericks(support = supported, regex = "^(mac)?\\s*os\\s*x\\s*(mavericks|10\\.9(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,9,5)),

    /** The OS X Yosemite family of operating systems (10.10.0 to 10.10.5) */
    yosemite(support = supported, regex = "^(mac)?\\s*os\\s*x\\s*(yosemite|10\\.10(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,10,5)),

    /** The OS X El Capitan family of operating systems (10.11.0 to 10.11.6) */
    elCapitan(support = supported, regex = "^(mac)?\\s*os\\s*x\\s*(el\\s*cap(itan)?|10\\.11(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,11,6)),

    /** The macOS Sierra family of operating systems (10.12.0 to present) */
    sierra(support = supported, regex = "^(mac)?\\s*os(\\s*x)?\\s*(sierra|10\\.12(\\.\\d)?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,12,3)),


    /** The OS is likely macOS, but the version is unknown. It is likely newer than this library. */
    unknown(support = OSSupport.unknown, regex = "^(mac\\s*os|os\\s*x|mac\\s*os\\s*x)\\s*(\\w+(\\s+\\w+)*|10\\.\\d{1,2}(\\.\\d{1,2})?)?$".toRegex(RegexOption.IGNORE_CASE));


    companion object {
        /**
         * Turns the given raw name into a [MacOSSubtype]
         *
         * @param rawName The name as a string, like `macOS Sierra`. Case is ignored.
         *
         * @return The macOS subtype that best matches the given raw name, or [unknown] if it's likely an unknown
         *         type of macOS, or `null` if it's likely not a type of macOS at all.
         */
        fun fromRawName(rawName: String): MacOSSubtype? = values().firstOrNull {
            it.regex.containsMatchIn(rawName)
        }
    }
}



/** The sub-type of Linux, like "Ubuntu 10.04" or "macOS Sierra" */
enum class LinuxSubtype(
        /** The tier of support this library offers to this OS */
        val support: OSSupport,
        /** The regex that matches this Linux subtype */
        val regex: Regex,
        /** The latest version that is officially supported, or `null` if that cannot be determined */
        val newestVersion: Version? = null
) {
    /** The Oracle Linux 5.5+ family of operating systems */
    oracle_5(support = supported, regex = "^oracle(\\s*linux)?\\s*5\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(5,11)),

    /** The Oracle Linux 6.x family of operating systems */
    oracle_6(support = supported, regex = "^oracle(\\s*linux)?\\s*6\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,8)),

    /** The Oracle Linux 7.x family of operating systems */
    oracle_7(support = supported, regex = "^oracle(\\s*linux)?\\s*7\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(7,3)),

    /** Some unknown version of the Oracle Linux family of operating systems; likely made after this library */
    oracle_unknown(support = OSSupport.unknown, regex = "^oracle(\\s*linux)?\\b".toRegex(RegexOption.IGNORE_CASE)),


    /** The Red Hat Linux 5.5+ family of operating systems */
    redHat_5(support = supported, regex = "^red\\s*hat(\\s*enterprise)?(\\s*linux)?\\s*5\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(5,11)),

    /** The Red Hat Linux 6.x family of operating systems */
    redHat_6(support = supported, regex = "^red\\s*hat(\\s*enterprise)?(\\s*linux)?\\s*6\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,8)),

    /** The Red Hat Linux 7.x family of operating systems */
    redHat_7(support = supported, regex = "^red\\s*hat(\\s*enterprise)?(\\s*linux)?\\s*7\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(7,3)),

    /** Some unknown version of the Red Hat Linux family of operating systems; likely made after this library */
    redHat_unknown(support = OSSupport.unknown, regex = "^red\\s*hat(\\s*enterprise)?(\\s*linux)?\\b".toRegex(RegexOption.IGNORE_CASE)),


    /** The Suse Linux Enterprise Server 10 operating system */
    suse_10(support = supported, regex = "^(open\\s*)?suse(\\s*linux)?(\\s*enterprise)?(\\s*server)?\\s*10\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10)),

    /** The Suse Linux Enterprise Server 11 operating system */
    suse_11(support = supported, regex = "^(open\\s*)?suse(\\s*linux)?(\\s*enterprise)?(\\s*server)?\\s*11\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(11)),

    /** The Suse Linux Enterprise Server 12 operating system */
    suse_12(support = supported, regex = "^(open\\s*)?suse(\\s*linux)?(\\s*enterprise)?(\\s*server)?\\s*12\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(12)),

    /** Some unknown version of the Suse Linux Enterprise Server operating system; likely made after this library */
    suse_unknown(support = OSSupport.unknown, regex = "^(open\\s*)?suse(\\s*linux)?(\\s*enterprise)?(\\s*server)?(\\b\\d+)?".toRegex(RegexOption.IGNORE_CASE)),


    /** The Ubuntu 10.04 Lucid Lynx family of operating systems */
    ubuntu_lucidLynx(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(10\\.0?4|l(ucid|ynx)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,4)),

    /** The Ubuntu 10.10 Maverick Meerkat operating system */
    ubuntu_maverickMeerkat(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(10\\.10|m(averick|eerkat)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(10,10)),

    /** The Ubuntu 11.04 Natty Narwhal operating system */
    ubuntu_nattyNarwhal(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(11\\.0?4|n(atty|arwhal)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(11,4)),

    /** The Ubuntu 11.10 Oneiric Ocelot operating system */
    ubuntu_oneiricOcelot(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(11\\.10|o(neiric|celot)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(11,10)),

    /** The Ubuntu 12.04 Precise Pangolin family of operating systems */
    ubuntu_precisePangolin(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(12\\.0?4|p(recise|angolin)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(12,4)),

    /** The Ubuntu 12.10 Quantal Quetzal operating system */
    ubuntu_quantalQuetzal(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(12\\.10|q(uantal|uetzal)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(12,10)),

    /** The Ubuntu 13.04 Raring Ringtail operating system */
    ubuntu_raringRingtail(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(13\\.0?4|r(aring|ingtail)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(13,4)),

    /** The Ubuntu 13.10 Saucy Salamander operating system */
    ubuntu_saucySalamander(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(13\\.10|s(aucy|alamander)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(13,10)),

    /** The Ubuntu 14.04 Trusty Tahr family of operating systems */
    ubuntu_trustyTahr(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(14\\.0?4|t(rusty|ahr)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(14,4)),

    /** The Ubuntu 14.10 Utopic Unicorn operating system */
    ubuntu_utopicUnicorn(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(14\\.10|u(topic|nicorn)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(14,10)),

    /** The Ubuntu 15.04 Vivid Vervet operating system */
    ubuntu_vividVervet(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(15\\.0?4|v(ivid|ervet)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(15,4)),

    /** The Ubuntu 15.10 Wily Werewolf operating system */
    ubuntu_wilyWerewolf(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(15\\.10|w(ily|erewolf)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(15,10)),

    /** The Ubuntu 16.04 Xenial Xerus family of operating systems */
    ubuntu_xenialXerus(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(16\\.0?4|x(enial|erus)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(16,4)),

    /** The Ubuntu 16.10 Yakkety Yak operating system */
    ubuntu_yakketyYak(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(16\\.10|y(akkety|ak)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(16,10)),

    /** The Ubuntu 17.04 Zesty Zapus operating system */
    ubuntu_zestyZapus(support = supported, regex = "^ubuntu\\s*(linux\\s*)?(17\\.0?4|z(esty|apus)?)\\b".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(17,4)),

    /** Some unknown distribution of the Ubuntu operating system; likely made after this library */
    ubuntu_unknown(support = OSSupport.unknown, regex = "^ubuntu\\s*(linux\\s*)?(\\d+\\.\\d{2})?(\\w+)?\\b".toRegex(RegexOption.IGNORE_CASE)),


    /** The OS is likely Linux, but the version is unknown. */
    unknown(support = OSSupport.unknown, regex = "linux".toRegex(RegexOption.IGNORE_CASE));


    companion object {
        /**
         * Turns the given raw name into a [LinuxSubtype]
         *
         * @param rawName The name as a string, like `Ubuntu Zesty Zapus`. Case is ignored.
         *
         * @return The Linux subtype that best matches the given raw name, or [unknown] if it's likely an unknown
         *         type of Linux, or `null` if it's likely not a type of Linux at all.
         */
        fun fromRawName(rawName: String): LinuxSubtype? = values().firstOrNull { it.regex.containsMatchIn(rawName) }
    }
}



/** The sub-type of Android, like "Cupcake" or "Nougat" */
enum class AndroidSubtype(
        /** The tier of support this library offers to this OS */
        val support: OSSupport,
        /** The regex that matches this Android subtype */
        val regex: Regex,
        /** The latest version that is officially supported, or `null` if that cannot be determined */
        val newestVersion: Version? = null,
        /** The latest API version that is officially supported, or `null` if that cannot be determined */
        val newestAPIVersion: Integer? = null
) {
    /** The Android Alpha operating system (1.0) */
    alpha(support = supported, regex = "^(android\\s*(a(lpha)?(\\s*1(\\.0)?)?|beta\\s*1(\\.0)?)|android\\s*1(\\.0)?(\\s*(a(lpha)?|beta))?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(1,0), newestAPIVersion = 1),

    /** The Android Beta operating system (1.1) */
    beta(support = supported, regex = "^(android\\s*(b(eta)?(\\s*1\\.1)?|alpha\\s*1\\.1)|android\\s*1\\.1(\\s*(b(eta)?|lpha))?)$".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(1,1), newestAPIVersion = 2),

    /** The Android Cupcake operating system (1.5) */
    cupcake(support = supported, regex = "^((android\\s*)?c(upcake)?\\b|android\\s*1\\.5(\\s*c(upcake)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(1,5), newestAPIVersion = 3),

    /** The Android Donut operating system (1.6) */
    donut(support = supported, regex = "^((android\\s*)?d(onut)?\\b|android\\s*1\\.6(\\s*d(onut)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(1,6), newestAPIVersion = 4),

    /** The Android Eclair family of operating systems (2.0 to 2.1) */
    eclair(support = supported, regex = "^((android\\s*)?e(clair)?\\b|android\\s*2(\\.[01])?\\s*(e(clair)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(2,1), newestAPIVersion = 7),

    /** The Android Froyo family of operating systems (2.2 to 2.2.3) */
    froyo(support = supported, regex = "^((android\\s*)?f(royo)?\\b|android\\s*2\\.2(\\.[0-3])?\\s*(f(royo)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(2,2,3), newestAPIVersion = 8),

    /** The Android Gingerbread family of operating systems (2.3 to 2.3.7) */
    gingerbread(support = supported, regex = "^((android\\s*)?g(ingerbread)?\\b|android\\s*2\\.3(\\.[0-7])?\\s*(g(ingerbread)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(2,3,7), newestAPIVersion = 10),

    /** The Android Honeycomb family of operating systems (3.0 to 3.2.6) */
    honeycomb(support = supported, regex = "^((android\\s*)?h(oneycomb)?\\b|android\\s*3(\\.[012](\\.[0-6])?)?\\s*(h(oneycomb)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(3,2,6), newestAPIVersion = 13),

    /** The Ice Cream Sandwich family of operating systems (4.0 to 4.0.4) */
    iceCreamSandwich(support = supported, regex = "^((android\\s*)?i(ce\\s*cream\\s*sandwich)?\\b|android\\s*4(\\.0(\\.[0-4])?)?\\s*(i(ce\\s*cream\\s*sandwich)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,0,4), newestAPIVersion = 15),

    /** The Jelly Bean family of operating systems (4.1 to 4.3.1) */
    jellyBean(support = supported, regex = "^((android\\s*)?j(elly\\s*bean)?\\b|android\\s*4\\.[123](\\.[01])?\\s*(j(elly\\s*bean)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,3,1), newestAPIVersion = 18),

    /** The KitKat family of operating systems (4.4 to 4.4.4) */
    kitKat(support = supported, regex = "^((android\\s*)?k(it\\s*kat)?\\b|android\\s*4\\.4(\\.[0-4])?\\s*(k(it\\s*kat)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(4,4,4), newestAPIVersion = 19),

    /** The Lollipop family of operating systems (5.0 to 5.1.1) */
    lollipop(support = supported, regex = "^((android\\s*)?l(ollipop)?\\b|android\\s*5(\\.[01](\\.[01])?)?\\s*(l(ollipop)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(5,1,1), newestAPIVersion = 22),

    /** The Marshmallow family of operating systems (6.0 to 6.0.1) */
    marshmallow(support = supported, regex = "^((android\\s*)?m(arshmallow)?\\b|android\\s*6(\\.0(\\.[01])?)?\\s*(m(arshmallow)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(6,0,1), newestAPIVersion = 23),

    /** The Nougat family of operating systems (7.0 to 7.1.1) */
    nougat(support = supported, regex = "^((android\\s*)?n(ougat)?\\b|android\\s*7(\\.[01](\\.[01])?)?\\s*(n(ougat)?)?$)".toRegex(RegexOption.IGNORE_CASE), newestVersion = v(7,1,1), newestAPIVersion = 25),


    /** The OS is likely Android, but the version is unknown. */
    unknown(support = OSSupport.unknown, regex = "android".toRegex(RegexOption.IGNORE_CASE));


    companion object {
        /**
         * Turns the given raw name into a [AndroidSubtype]
         *
         * @param rawName The name as a string, like `7.0 Nougat`. Case is ignored.
         *
         * @return The Android subtype that best matches the given raw name, or [unknown] if it's likely an unknown
         *         type of Android, or `null` if it's likely not a type of Android at all.
         */
        fun fromRawName(rawName: String): AndroidSubtype? = values().firstOrNull { it.regex.containsMatchIn(rawName) }
    }
}
