package BlueBase


import org.junit.jupiter.api.*


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
        val expectedDifference: List<DifferingCharacter>
)
private val _differingCharactersTests: List<_DifferingCharactersTest> = listOf(
        _DifferingCharactersTest("123", "123", listOf()),
        _DifferingCharactersTest("123", "321", listOf(DifferingCharacter(0, '1', '3'), DifferingCharacter(2, '3', '1'))),
        _DifferingCharactersTest("1", "1+2", listOf(DifferingCharacter(1, null, '+'), DifferingCharacter(2, null, '2'))),
        _DifferingCharactersTest("B&A", "B", listOf(DifferingCharacter(1, '&', null), DifferingCharacter(2, 'A', null)))
)
