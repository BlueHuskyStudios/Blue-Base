package org.bh.tools.base.strings


import org.bh.tools.base.func.Tuple2
import org.bh.tools.base.func.tuple
import org.bh.tools.base.util.assertListEquals
import org.junit.Test

import org.junit.Assert.*


/**
 * @author Kyli Rouge
 * *
 * @since 2017-03-25 025.
 */
class String_UtilsKtTest {

    @Test
    fun toAbbreviation() {
    }


    @Test
    fun times() {
    }


    @Test
    fun times1() {
    }


    @Test
    fun differingCharacters() {
        _differingCharactersTests.forEach { (firstString, secondString, expectedDifference) ->
            val actualDifference = firstString.differingCharacters(secondString)
            assertListEquals("\"$firstString\" ~ \"$secondString\"", expectedDifference, actualDifference)
        }
    }
}


private data class _DifferingCharactersTest(
        val firstString: String,
        val secondString: String,
        val expectedDifference: List<Tuple2<Char?, Char?>>
)
private val _differingCharactersTests: List<_DifferingCharactersTest> = listOf(
        _DifferingCharactersTest("123", "123", listOf()),
        _DifferingCharactersTest("123", "321", listOf(tuple('1', '3'), tuple('3', '1'))),
        _DifferingCharactersTest("1", "1+2", listOf(tuple(null, '+'), tuple(null, '2'))),
        _DifferingCharactersTest("B&A", "B", listOf(tuple('&', null), tuple('A', null)))
)
