@file:Suppress("LocalVariableName")

package BlueBase


import org.junit.jupiter.api.*


/**
 * @author Kyli Rouge
 * @since 2017-10-15 015.
 */
internal class BasicMathFunctionsTest {

//    @Test
//    fun isEven() {
//    }
//
//
//    @Test
//    fun isOdd() {
//    }
//
//
//    @Test
//    fun pow() {
//    }
//
//
//    @Test
//    fun toThePowerOf() {
//    }
//
//
//    @Test
//    fun abs() {
//    }
//
//
//    @Test
//    fun getAbsoluteValue() {
//    }


    private val inverseSequareRootTests: List<FractionFunctionTestCase> = listOf(
        FractionFunctionTestCase(input = 125348.0, expectedOutput = 0.00282449815593580),
        FractionFunctionTestCase(input = 152.2756, expectedOutput = 0.08103727714748784),
        FractionFunctionTestCase(input = 2.0, expectedOutput = 0.70711356243812756),

        FractionFunctionTestCase(input = 303.74, expectedOutput = 0.0573785),
        FractionFunctionTestCase(input = 394.973, expectedOutput = 0.0503172),
        FractionFunctionTestCase(input = 934.62, expectedOutput = 0.0327101),

        FractionFunctionTestCase(input = 0.67726, expectedOutput = 1.21513),
        FractionFunctionTestCase(input = 0.6994, expectedOutput = 1.19574),
        FractionFunctionTestCase(input = 0.98364, expectedOutput = 1.00828),


        FractionFunctionTestCase(input = 0.00657, expectedOutput = 12.33722016996),
        FractionFunctionTestCase(input = 0.0091190, expectedOutput = 10.471921841553163),
        FractionFunctionTestCase(input = 0.0023800, expectedOutput = 20.49800154227)
    )

    /**
     * Tests [org.bh.tools.base.math.inverseSquareRoot] and its various implementations
     */
    @Test
    fun inverseSquareRoot() {

        val results: MutableSet<FractionFunctionTestResult> = mutableSetOf()

        fun saveResult(result: FractionFunctionTestResult) {
            results.add(result)
        }


        val measurementMode = TimeTrialMeasurementMode.total
        val warmupTrials = 50L
        val trialsPerTest = 1000L


        val inverseSquareRoot_accuracy1_result = measureTimeInterval(mode = measurementMode,
                                                                     warmupTrials = warmupTrials,
                                                                     trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_functional_accuracy1,
                                      specialTolerance = 0.05,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (functional-style) accuracy 1: $inverseSquareRoot_accuracy1_result")

        val inverseSquareRoot_accuracy2_result = measureTimeInterval(mode = measurementMode,
                                                                     warmupTrials = warmupTrials,
                                                                     trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_functional_accuracy2,
                                      specialTolerance = defaultCalculationTolerance,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (functional-style) accuracy 2: $inverseSquareRoot_accuracy2_result")

        val inverseSquareRoot_accuracy3_result = measureTimeInterval(mode = measurementMode,
                                                                     warmupTrials = warmupTrials,
                                                                     trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_functional_accuracy3,
                                      specialTolerance = defaultCalculationTolerance,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (functional-style) accuracy 3: $inverseSquareRoot_accuracy3_result")

        val inverseSquareRoot_accuracy4_result = measureTimeInterval(mode = measurementMode,
                                                                     warmupTrials = warmupTrials,
                                                                     trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_functional_accuracy4,
                                      specialTolerance = 0.000007,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (functional-style) accuracy 4: $inverseSquareRoot_accuracy4_result")


        val inverseSquareRoot_allInOne_accuracy1_result = measureTimeInterval(mode = measurementMode,
                                                                              warmupTrials = warmupTrials,
                                                                              trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_allInOne_accuracy1,
                                      specialTolerance = 0.1,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (traditional-style) accuracy 1: $inverseSquareRoot_allInOne_accuracy1_result")

        val inverseSquareRoot_allInOne_accuracy2_result = measureTimeInterval(mode = measurementMode,
                                                                              warmupTrials = warmupTrials,
                                                                              trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_allInOne_accuracy2,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (traditional-style) accuracy 2: $inverseSquareRoot_allInOne_accuracy2_result")

        val inverseSquareRoot_allInOne_accuracy3_result = measureTimeInterval(mode = measurementMode,
                                                                              warmupTrials = warmupTrials,
                                                                              trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_allInOne_accuracy3,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (traditional-style) accuracy 3: $inverseSquareRoot_allInOne_accuracy3_result")

        val inverseSquareRoot_allInOne_accuracy4_result = measureTimeInterval(mode = measurementMode,
                                                                              warmupTrials = warmupTrials,
                                                                              trials = trialsPerTest) {
            allInverseSquareRootTests(functionToTest = ::inverseSquareRoot_allInOne_accuracy4,
                                      resultAccumulator = ::saveResult)
        }

        println("Inverse Square Root (traditional-style) accuracy 4: $inverseSquareRoot_allInOne_accuracy4_result")



        if (!results.all(FractionFunctionTestResult::success)) {
            assertionFailure("Final results: ${results.humanReadableString()}")
        }
    }


    private fun allInverseSquareRootTests(functionToTest: (Fraction) -> Fraction,
                                          specialTolerance: Fraction = defaultFractionCalculationTolerance,
                                          resultAccumulator: (FractionFunctionTestResult) -> Unit) {
        inverseSequareRootTests.forEach { testCase ->
            resultAccumulator(testCase.test(specialTolerance = specialTolerance, block = functionToTest))
        }
    }
}
