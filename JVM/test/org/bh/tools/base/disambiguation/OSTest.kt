@file:Suppress("deprecation")

package org.bh.tools.base.disambiguation


import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.reflect.KClass


/**
 * @author Kyli Rouge
 * *
 * @since 2017-02-26 026.
 */
class OSTest {

    private val windowsTests = listOf(
            TestCase(OS.windows::class, WindowsSubtype.windows_9x, rawName = "Windows 95"),
            TestCase(OS.windows::class, WindowsSubtype.windows_9x, rawName = "Win95"),
            TestCase(OS.windows::class, WindowsSubtype.windows_9x, rawName = "Windows 98"),
            TestCase(OS.windows::class, WindowsSubtype.windows_9x, rawName = "Win98"),

            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "Windows NT"),
            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "Windows NT 3.1"),
            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "Windows NT 3.5"),
            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "Windows NT 3.51"),
            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "Windows NT 4.0"),
            TestCase(OS.windows::class, WindowsSubtype.windows_NT, rawName = "winNT4"),

            TestCase(OS.windows::class, WindowsSubtype.windows_2000, rawName = "Windows 2000"),
            TestCase(OS.windows::class, WindowsSubtype.windows_2000, rawName = "win2000"),

            TestCase(OS.windows::class, WindowsSubtype.windows_ME, rawName = "Windows ME"),
            TestCase(OS.windows::class, WindowsSubtype.windows_ME, rawName = "Windows Millennium Edition"),
            TestCase(OS.windows::class, WindowsSubtype.windows_ME, rawName = "winme"),
            TestCase(OS.windows::class, WindowsSubtype.windows_ME, rawName = "winmillenniumedition"),

            TestCase(OS.windows::class, WindowsSubtype.windows_XP, rawName = "Windows XP"),
            TestCase(OS.windows::class, WindowsSubtype.windows_XP, rawName = "WinXP"),
            TestCase(OS.windows::class, WindowsSubtype.windows_XP, rawName = "Windows XP Home Edition"),
            TestCase(OS.windows::class, WindowsSubtype.windows_XP, rawName = "Windows XP Media Center"),

            TestCase(OS.windows::class, WindowsSubtype.windows_Vista, rawName = "Windows Vista"),
            TestCase(OS.windows::class, WindowsSubtype.windows_Vista, rawName = "winvista"),
            TestCase(OS.windows::class, WindowsSubtype.windows_Vista, rawName = "Windows Vista Ultimate"),
            TestCase(OS.windows::class, WindowsSubtype.windows_Vista, rawName = "Windows Vista Home Premium Edition"),

            TestCase(OS.windows::class, WindowsSubtype.windows_7, rawName = "Windows 7"),
            TestCase(OS.windows::class, WindowsSubtype.windows_7, rawName = "win7"),
            TestCase(OS.windows::class, WindowsSubtype.windows_7, rawName = "Windows 7 Ultimate"),
            TestCase(OS.windows::class, WindowsSubtype.windows_7, rawName = "Windows 7 Home Premium Edition"),

            TestCase(OS.windows::class, WindowsSubtype.windows_8x, rawName = "Windows 8"),
            TestCase(OS.windows::class, WindowsSubtype.windows_8x, rawName = "win8"),
            TestCase(OS.windows::class, WindowsSubtype.windows_8x, rawName = "Windows 8 Pro"),
            TestCase(OS.windows::class, WindowsSubtype.windows_8x, rawName = "Windows 8 Enterprise Edition"),

            TestCase(OS.windows::class, WindowsSubtype.windows_RT, rawName = "Windows 8 RT"),
            TestCase(OS.windows::class, WindowsSubtype.windows_RT, rawName = "Windows 8RT"),
            TestCase(OS.windows::class, WindowsSubtype.windows_RT, rawName = "win8rt"),
            TestCase(OS.windows::class, WindowsSubtype.windows_RT, rawName = "winRT"),

            TestCase(OS.windows::class, WindowsSubtype.windows_10, rawName = "Windows 10"),
            TestCase(OS.windows::class, WindowsSubtype.windows_10, rawName = "Windows 10 Pro"),
            TestCase(OS.windows::class, WindowsSubtype.windows_10, rawName = "Windows 10 Mobile"),

            TestCase(OS.windows::class, WindowsSubtype.unknown, rawName = "Windows Fake"),
            TestCase(OS.windows::class, WindowsSubtype.unknown, rawName = "Windows 9"),
            TestCase(OS.windows::class, WindowsSubtype.unknown, rawName = "Windows 100")
    )

    private val macOSTests = listOf(
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "Mac OS X Cheetah"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "Mac OS X 10.0"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "Mac OS X 10.0.0"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "Mac OS X 10.0.4"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "OS X Cheetah"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "osx cheetah"),
            TestCase(OS.macOS::class, MacOSSubtype.cheetah, rawName = "osx10.0"),

            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "Mac OS X Puma"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "Mac OS X 10.1"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "Mac OS X 10.1.0"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "Mac OS X 10.1.5"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "OS X Puma"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "osx puma"),
            TestCase(OS.macOS::class, MacOSSubtype.puma, rawName = "osx10.1"),

            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "Mac OS X Jaguar"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "Mac OS X 10.2"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "Mac OS X 10.2.0"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "Mac OS X 10.2.8"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "OS X Jaguar"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "osx jaguar"),
            TestCase(OS.macOS::class, MacOSSubtype.jaguar, rawName = "osx10.2"),

            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "Mac OS X Panther"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "Mac OS X 10.3"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "Mac OS X 10.3.0"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "Mac OS X 10.3.9"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "OS X Panther"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "osx panther"),
            TestCase(OS.macOS::class, MacOSSubtype.panther, rawName = "osx10.3"),

            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "Mac OS X Tiger"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "Mac OS X 10.4"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "Mac OS X 10.4.0"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "Mac OS X 10.4.11"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "OS X Tiger"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "osx tiger"),
            TestCase(OS.macOS::class, MacOSSubtype.tiger, rawName = "osx10.4"),

            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "Mac OS X Leopard"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "Mac OS X 10.5"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "Mac OS X 10.5.0"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "Mac OS X 10.5.8"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "OS X Leopard"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "osx leopard"),
            TestCase(OS.macOS::class, MacOSSubtype.leopard, rawName = "osx10.5"),

            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "Mac OS X Snow Leopard"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "Mac OS X 10.6"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "Mac OS X 10.6.0"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "Mac OS X 10.6.8"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "OS X SnowLeopard"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "osx snowleopard"),
            TestCase(OS.macOS::class, MacOSSubtype.snowLeopard, rawName = "osx10.6"),

            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "Mac OS X Lion"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "Mac OS X 10.7"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "Mac OS X 10.7.0"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "Mac OS X 10.7.5"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "OS X Lion"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "osx lion"),
            TestCase(OS.macOS::class, MacOSSubtype.lion, rawName = "osx10.7"),

            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "Mac OS X Mountain Lion"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "Mac OS X 10.8"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "Mac OS X 10.8.0"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "Mac OS X 10.8.5"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "OS X MountainLion"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "osx mountainlion"),
            TestCase(OS.macOS::class, MacOSSubtype.mountainLion, rawName = "osx10.8"),

            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "Mac OS X Mavericks"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "Mac OS X 10.9"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "Mac OS X 10.9.0"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "Mac OS X 10.9.5"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "OS X Mavericks"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "osx mavericks"),
            TestCase(OS.macOS::class, MacOSSubtype.mavericks, rawName = "osx10.9"),

            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "Mac OS X yosemite"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "Mac OS X 10.10"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "Mac OS X 10.10.0"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "Mac OS X 10.10.5"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "OS X yosemite"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "osx yosemite"),
            TestCase(OS.macOS::class, MacOSSubtype.yosemite, rawName = "osx10.10"),

            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "Mac OS X El Capitan"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "Mac OS X 10.11"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "Mac OS X 10.11.0"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "Mac OS X 10.11.6"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "OS X El Capitan"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "osx elcapitan"),
            TestCase(OS.macOS::class, MacOSSubtype.elCapitan, rawName = "osx10.11"),

            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "Mac OS X Sierra"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "Mac OS X 10.12"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "Mac OS X 10.12.0"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "Mac OS X 10.12.3"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "OS X Sierra"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "osx sierra"),
            TestCase(OS.macOS::class, MacOSSubtype.sierra, rawName = "osx10.12"),

            TestCase(OS.macOS::class, MacOSSubtype.unknown, rawName = "macOS Fake"),
            TestCase(OS.macOS::class, MacOSSubtype.unknown, rawName = "Mac OS Fake"),
            TestCase(OS.macOS::class, MacOSSubtype.unknown, rawName = "Mac OS X 10.50"),
            TestCase(OS.macOS::class, MacOSSubtype.unknown, rawName = "OS X 10.50"),
            TestCase(OS.macOS::class, MacOSSubtype.unknown, rawName = "osx10.50")
    )

    private val linuxTests = listOf(
            TestCase(OS.linux::class, LinuxSubtype.oracle_5, rawName = "Oracle Linux 5"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_5, rawName = "Oracle Linux 5.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_5, rawName = "Oracle 5"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_5, rawName = "Oracle 5.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_5, rawName = "oracle5"),

            TestCase(OS.linux::class, LinuxSubtype.oracle_6, rawName = "Oracle Linux 6"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_6, rawName = "Oracle Linux 6.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_6, rawName = "Oracle 6"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_6, rawName = "Oracle 6.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_6, rawName = "oracle6"),

            TestCase(OS.linux::class, LinuxSubtype.oracle_7, rawName = "Oracle Linux 7"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_7, rawName = "Oracle Linux 7.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_7, rawName = "Oracle 7"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_7, rawName = "Oracle 7.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_7, rawName = "oracle7"),

            TestCase(OS.linux::class, LinuxSubtype.oracle_unknown, rawName = "Oracle Linux 50"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_unknown, rawName = "Oracle Linux 50.0"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_unknown, rawName = "Oracle 50"),
            TestCase(OS.linux::class, LinuxSubtype.oracle_unknown, rawName = "Oracle 50.0"),


            TestCase(OS.linux::class, LinuxSubtype.redHat_5, rawName = "Red Hat Linux 5"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_5, rawName = "Red Hat Linux 5.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_5, rawName = "Red Hat 5"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_5, rawName = "Red Hat 5.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_5, rawName = "redhat5"),

            TestCase(OS.linux::class, LinuxSubtype.redHat_6, rawName = "Red Hat Linux 6"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_6, rawName = "Red Hat Linux 6.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_6, rawName = "Red Hat 6"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_6, rawName = "Red Hat 6.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_6, rawName = "redhat6"),

            TestCase(OS.linux::class, LinuxSubtype.redHat_7, rawName = "Red Hat Linux 7"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_7, rawName = "Red Hat Linux 7.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_7, rawName = "Red Hat 7"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_7, rawName = "Red Hat 7.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_7, rawName = "redhat7"),

            TestCase(OS.linux::class, LinuxSubtype.redHat_unknown, rawName = "Red Hat Linux 50"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_unknown, rawName = "Red Hat Linux 50.0"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_unknown, rawName = "Red Hat 50"),
            TestCase(OS.linux::class, LinuxSubtype.redHat_unknown, rawName = "Red Hat 50.0"),


            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse Linux 10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse Linux 10.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse 10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse 10.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse Enterprise 10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Suse Enterprise 10.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Open Suse Linux 10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Open Suse Linux 10.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Open Suse 10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "Open Suse 10.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "suse10"),
            TestCase(OS.linux::class, LinuxSubtype.suse_10, rawName = "opensuse10"),

            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse Linux 11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse Linux 11.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse 11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse 11.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse Enterprise 11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Suse Enterprise 11.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Open Suse Linux 11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Open Suse Linux 11.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Open Suse 11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "Open Suse 11.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "suse11"),
            TestCase(OS.linux::class, LinuxSubtype.suse_11, rawName = "opensuse11"),

            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse Linux 12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse Linux 12.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse 12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse 12.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse Enterprise 12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Suse Enterprise 12.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Open Suse Linux 12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Open Suse Linux 12.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Open Suse 12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "Open Suse 12.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "suse12"),
            TestCase(OS.linux::class, LinuxSubtype.suse_12, rawName = "opensuse12"),

            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Suse Linux Fake"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Suse Linux 999.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Suse Fake"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Suse 999.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Open Suse Linux Fake"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Open Suse Linux 999.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Open Suse Fake"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "Open Suse 999.0"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "susefake"),
            TestCase(OS.linux::class, LinuxSubtype.suse_unknown, rawName = "opensuse999"),


            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu 10.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu Linux 10.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu L"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu Linux L"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu Lucid Lynx"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "Ubuntu Linux Lynx"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_lucidLynx, rawName = "ubuntu10.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu 10.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu Linux 10.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu M"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu Linux M"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu Maverick Meerkat"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "Ubuntu Linux Meerkat"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_maverickMeerkat, rawName = "ubuntu10.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu 11.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu Linux 11.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu N"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu Linux N"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu Natty Narwhal"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "Ubuntu Linux Narwhal"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_nattyNarwhal, rawName = "ubuntu11.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu 11.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu Linux 11.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu O"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu Linux O"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu Oneiric Ocelot"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "Ubuntu Linux Ocelot"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_oneiricOcelot, rawName = "ubuntu11.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu 12.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu Linux 12.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu P"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu Linux P"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu Precise Pangolin"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "Ubuntu Linux Pangolin"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_precisePangolin, rawName = "ubuntu12.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu 12.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu Linux 12.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu Q"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu Linux Q"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu Quantal Quetzal"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "Ubuntu Linux Quetzal"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_quantalQuetzal, rawName = "ubuntu12.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu 13.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu Linux 13.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu R"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu Linux R"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu Raring Ringtail"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "Ubuntu Linux Ringtail"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_raringRingtail, rawName = "ubuntu13.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu 13.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu Linux 13.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu S"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu Linux S"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu Saucy Salamander"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "Ubuntu Linux Salamander"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_saucySalamander, rawName = "ubuntu13.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu 14.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu Linux 14.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu T"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu Linux T"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu Trusty Tahr"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "Ubuntu Linux Tahr"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_trustyTahr, rawName = "ubuntu14.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu 14.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu Linux 14.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu U"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu Linux U"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu Utopic Unicorn"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "Ubuntu Linux Unicorn"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_utopicUnicorn, rawName = "ubuntu14.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu 15.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu Linux 15.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu V"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu Linux V"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu Vivid Vervet"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "Ubuntu Linux Vervet"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_vividVervet, rawName = "ubuntu15.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu 15.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu Linux 15.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu W"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu Linux W"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu Wily Werewolf"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "Ubuntu Linux Werewolf"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_wilyWerewolf, rawName = "ubuntu15.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu 16.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu Linux 16.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu X"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu Linux X"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu Xenial Xerus"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "Ubuntu Linux Xerus"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_xenialXerus, rawName = "ubuntu16.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu 16.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu Linux 16.10"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu Y"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu Linux Y"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu Yakkety Yak"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "Ubuntu Linux Yak"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_yakketyYak, rawName = "ubuntu16.10"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu 17.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu Linux 17.04"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu Z"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu Linux Z"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu Zesty Zapus"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "Ubuntu Linux Zapus"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_zestyZapus, rawName = "ubuntu17.04"),

            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu 99.09"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu Linux 99.09"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu ?"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu Linux ?"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu Fake Faucet"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "Ubuntu Linux Faucet"),
            TestCase(OS.linux::class, LinuxSubtype.ubuntu_unknown, rawName = "ubuntu99.09"),


            TestCase(OS.linux::class, LinuxSubtype.unknown, rawName = "Fake Linux"),
            TestCase(OS.linux::class, LinuxSubtype.unknown, rawName = "Fake Linux Enterprise"),
            TestCase(OS.linux::class, LinuxSubtype.unknown, rawName = "Linux Kernel 3")
    )

    private val androidTests = listOf(
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "Android 1.0"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "Android alpha"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "Android 1.0 Alpha"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "Android Alpha 1.0"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "Android Beta 1"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "androidA"),
            TestCase(OS.android::class, AndroidSubtype.alpha, rawName = "android1"),

            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "Android 1.1"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "Android beta"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "Android 1.1 Beta"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "Android Beta 1.1"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "Android Alpha 1.1"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "androidB"),
            TestCase(OS.android::class, AndroidSubtype.beta, rawName = "android1.1"),

            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Android 1.5"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Android 1.5 Cupcake"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Android Cupcake 1.5"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Cupcake 1.5"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Cupcake"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Android Cupcake"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "Android C"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "androidC"),
            TestCase(OS.android::class, AndroidSubtype.cupcake, rawName = "android1.5"),

            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Android 1.6"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Android 1.6 Donut"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Android Donut 1.6"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Donut 1.6"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Donut"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Android Donut"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "Android D"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "androidD"),
            TestCase(OS.android::class, AndroidSubtype.donut, rawName = "android1.6"),

            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Android 2"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Android 2.0 Eclair"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Android Eclair 2.0"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Eclair 2.1"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Eclair"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Android Eclair"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "Android E"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "androidE"),
            TestCase(OS.android::class, AndroidSubtype.eclair, rawName = "android2"),

            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Android 2.2"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Android 2.2.0 Froyo"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Android Froyo 2.2.0"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Froyo 2.2.1"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Froyo"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Android Froyo"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "Android F"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "androidF"),
            TestCase(OS.android::class, AndroidSubtype.froyo, rawName = "android2.2.3"),

            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Android 2.3"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Android 2.3.0 Gingerbread"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Android Gingerbread 2.3.0"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Gingerbread 2.3.1"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Gingerbread"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Android Gingerbread"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "Android G"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "androidG"),
            TestCase(OS.android::class, AndroidSubtype.gingerbread, rawName = "android2.3.7"),

            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Android 3"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Android 3.0 Honeycomb"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Android Honeycomb 3.0"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Honeycomb 3.0.0"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Honeycomb"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Android Honeycomb"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "Android H"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "androidH"),
            TestCase(OS.android::class, AndroidSubtype.honeycomb, rawName = "android3.2.6"),

            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Android 4"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Android 4.0 Ice Cream Sandwich"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Android Ice Cream Sandwich 4.0"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Ice Cream Sandwich 4.0.0"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "IceCreamSandwich"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Android Icecream Sandwich"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "Android I"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "androidI"),
            TestCase(OS.android::class, AndroidSubtype.iceCreamSandwich, rawName = "android4.0.4"),

            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Android 4.1"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Android 4.2 Jelly Bean"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Android Jelly Bean 4.2"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Jelly Bean 4.2.1"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "JellyBean"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Android Jellybean"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "Android J"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "androidJ"),
            TestCase(OS.android::class, AndroidSubtype.jellyBean, rawName = "android4.3.1"),

            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Android 4.4"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Android 4.4.0 KitKat"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Android KitKat 4.4.0"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "KitKat 4.4.1"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Kit Kat"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Android Kit Kat"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "Android K"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "androidK"),
            TestCase(OS.android::class, AndroidSubtype.kitKat, rawName = "android4.4.4"),

            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Android 5"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Android 5.0 Lollipop"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Android Lollipop 5.0"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Lollipop 5.0.1"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Lollipop"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Android Lollipop"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "Android L"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "androidL"),
            TestCase(OS.android::class, AndroidSubtype.lollipop, rawName = "android5.1.1"),

            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Android 6"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Android 6.0 Marshmallow"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Android Marshmallow 6.0"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Marshmallow 6.0.0"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Marshmallow"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Android Marshmallow"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "Android M"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "androidM"),
            TestCase(OS.android::class, AndroidSubtype.marshmallow, rawName = "android6.0.1"),

            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Android 7"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Android 7.0 Nougat"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Android Nougat 7.0"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Nougat 7.0.1"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Nougat"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Android Nougat"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "Android N"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "androidN"),
            TestCase(OS.android::class, AndroidSubtype.nougat, rawName = "android7.1.1"),

            TestCase(OS.android::class, AndroidSubtype.unknown, rawName = "Android 99.6 Tapioca Pasta")
    )

    private val unknownTests = listOf(
            TestCase(OS.unknown::class, null, rawName = "DOS"),
            TestCase(OS.unknown::class, null, rawName = "DSL"),

            TestCase(OS.unknown::class, null, rawName = "FakeOS"),
            TestCase(OS.unknown::class, null, rawName = "Doors"),
            TestCase(OS.unknown::class, null, rawName = "Max OS"),
            TestCase(OS.unknown::class, null, rawName = "unknown")
    )

    private val tests = windowsTests + macOSTests + linuxTests + androidTests + unknownTests



    @Test
    fun fromRawName() {
        tests.forEach { (expectedOSClass, expectedSubtype, testString) ->
            val actual = OS.fromRaw(testString)
            assertEquals("Expected \"$testString\" to be a ${expectedOSClass.simpleName}, but it was a ${actual::class.simpleName}",
                    expectedOSClass, actual::class)
            val actualSubtype = when (actual) {
                is OS.windows -> actual.subtype
                is OS.macOS -> actual.subtype
                is OS.linux -> actual.subtype
                is OS.android -> actual.subtype
                is OS.unknown -> null
            }
            assertEquals("Expected \"$testString\" Subtype to be $expectedSubtype, but it was $actualSubtype",
                    expectedSubtype, actualSubtype)
        }
    }
}

private data class TestCase <OSType: OS, out OSSubtype>(
        val expectedOSClass: KClass<OSType>,
        val expectedSubtype: OSSubtype,
        val rawName: String,
        val rawOSVersion: String? = null,
        val rawOSArchitecture: String? = null
)
