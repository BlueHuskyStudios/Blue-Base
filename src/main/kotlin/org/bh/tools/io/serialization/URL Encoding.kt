package org.bh.tools.io.serialization

/*
 * @author Ben Leggiero
 * @since 2018-03-09
 */


external fun encodeURIComponent(raw: String): String
external fun decodeURIComponent(raw: String): String


val String.urlEncoded get() = encodeURIComponent(this)
val String.urlDecoded get() = decodeURIComponent(this)
