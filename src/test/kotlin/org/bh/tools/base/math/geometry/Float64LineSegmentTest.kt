@file:Suppress("unused")

package BlueBase

import BlueBase.FractionPoint.Companion.zero
import BlueBase.IntersectionDescription.*
import org.junit.jupiter.api.*


private data class Intersection(
        val name: String,
        val line1: FractionLineSegment, val line2: FractionLineSegment,
        val expectedPoint: FractionPoint?,
        val expectedType: IntersectionDescription<*, *>
) {
    constructor(name: String,
                line1Start: FractionPoint, line1End: FractionPoint,   line2Start: FractionPoint, line2End: FractionPoint,
                expectedIntersectionPoint: FractionPoint?,
                expectedIntersectionDescriptionType: IntersectionDescription<*, *>):
            this(name, FractionLineSegment(line1Start, line1End), FractionLineSegment(line2Start, line2End), expectedIntersectionPoint, expectedIntersectionDescriptionType)
}


@Suppress("NOTHING_TO_INLINE")
private inline fun p(x: Fraction, y: Fraction) = FractionPoint(x, y)


@Suppress("NOTHING_TO_INLINE")
private inline fun p(x: Integer, y: Integer) = FractionPoint(x, y)

@Suppress("NOTHING_TO_INLINE")
private inline infix fun FractionPoint.lineTo(other: FractionPoint) = FractionLineSegment(this, other)




/**
 * @author Kyli Rouge
 * @since 1/23/2017.
 */
class Float64LineSegmentTest {
    private val edgeTouchingEdgeIntersections: List<Intersection> = listOf(
            Intersection("Edge crosses edge perpendicularly at (0, 0) A", p(-1, 0)   lineTo p(1, 0),       p(0, 1)    lineTo p(0, -1),    expectedPoint = zero,                                 expectedType = edgesCross(zero)),
            Intersection("Edge crosses edge perpendicularly at (0, 0) B", p(-1, 1)   lineTo p(1, -1),      p(1, 1)    lineTo p(-1, -1),   expectedPoint = zero,                                 expectedType = edgesCross(zero)),
            Intersection("Edge crosses edge perpendicularly at (0, 0) C", p(-1, 2)   lineTo p(1, -2),      p(1, 2)    lineTo p(-1, -2),   expectedPoint = zero,                                 expectedType = edgesCross(zero)),
            Intersection("Edge crosses edge at (4, 5)",                   p(4, 1)    lineTo p(4, 8),       p(7, 2)    lineTo p(1, 8),     expectedPoint = p(4, 5),                              expectedType = edgesCross(p(4, 5))),
            Intersection("Edge crosses edge at (660, 139)",               p(631, 87) lineTo p(851, 481),   p(729, 48) lineTo p(651, 150), expectedPoint = p(2923451.0/4431.0, 613820.0/4431.0), expectedType = edgesCross(p(2923451.0/4431.0, 613820.0/4431.0)))
    )

    private val vertexTouchingEdgeIntersections: List<Intersection> = listOf(
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) A", p(-1, 0)    lineTo p(1, 0),       p(0, 1)     lineTo zero,        expectedPoint = zero,        expectedType = rightVertexTouchesLeftEdge(zero,        isRightStartVertex = false)),
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) B", p(-1, 1)    lineTo p(1, -1),      p(1, 1)     lineTo zero,        expectedPoint = zero,        expectedType = rightVertexTouchesLeftEdge(zero,        isRightStartVertex = false)),
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) C", p(-1, 2)    lineTo p(1, -2),      p(1, 2)     lineTo zero,        expectedPoint = zero,        expectedType = rightVertexTouchesLeftEdge(zero,        isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (4, 5)",                   p(4, 1)     lineTo p(4, 8),       p(7, 2)     lineTo p(4, 5),     expectedPoint = p(4, 5),     expectedType = rightVertexTouchesLeftEdge(p(4, 5),     isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (1, 5)",                   p(1, 1)     lineTo p(1, 8),       p(4, 2)     lineTo p(1, 5),     expectedPoint = p(1, 5),     expectedType = rightVertexTouchesLeftEdge(p(1, 5),     isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (366, 294)",               p(242, 418) lineTo p(466, 194),   p(225, 353) lineTo p(366, 294), expectedPoint = p(366, 294), expectedType = rightVertexTouchesLeftEdge(p(366, 294), isRightStartVertex = false))
    )

    private val vertexTouchingVertexIntersections: List<Intersection> = listOf(
            Intersection("Vertex touches vertex perpendicularly at (0, 0)", p(-1, 0)   lineTo zero,         p(0, 1)    lineTo zero,        expectedPoint = zero,       expectedType = leftVertexTouchesRightVertex(zero,       isLeftStartVertex = false, isRightStartVertex = false)),
            Intersection("Vertex touches vertex collinearly at (1, 2)",     p(-1, 2)   lineTo p(1, 2),      p(1, 2)    lineTo p(2, 2),     expectedPoint = p(1, 2),    expectedType = leftVertexTouchesRightVertex(p(1, 2),    isLeftStartVertex = false, isRightStartVertex = true)),
            Intersection("Vertex touches vertex at (1, 8)",                 p(1, 1)    lineTo p(1, 8),      p(4, 2)    lineTo p(1, 8),     expectedPoint = p(1, 8),    expectedType = leftVertexTouchesRightVertex(p(1, 8),    isLeftStartVertex = false, isRightStartVertex = false)),
            Intersection("Vertex touches vertex at (289, 29)",              p(159, 87) lineTo p(289, 29),   p(289, 29) lineTo p(267, 203), expectedPoint = p(289, 29), expectedType = leftVertexTouchesRightVertex(p(289, 29), isLeftStartVertex = false, isRightStartVertex = true)),
            Intersection("Vertex touches vertex at (16, 17)",               p(16, 16)  lineTo p(16, 17),    p(16, 17)  lineTo p(17, 17),   expectedPoint = p(16, 17),  expectedType = leftVertexTouchesRightVertex(p(16, 17),  isLeftStartVertex = false, isRightStartVertex = true)),
            Intersection("Vertex touches vertex at (17, 17)",               p(16, 17)  lineTo p(17, 17),    p(17, 17)  lineTo p(18, 17),   expectedPoint = p(17, 17),  expectedType = leftVertexTouchesRightVertex(p(17, 17),  isLeftStartVertex = false, isRightStartVertex = true))
    )

    private val completeOverlapIntersections: List<Intersection> = listOf(
            Intersection("Complete overlap at (0, 0) A",  p(-1, 0)   lineTo zero,         p(-1, 0)   lineTo zero,       expectedPoint = p(-1, 0),   expectedType = completeOverlap(isStartAndEndFlipped = false)),
            Intersection("Complete overlap at (0, 0) B",  p(-1, 2)   lineTo p(1, -2),     p(1, -2)   lineTo p(-1, 2),   expectedPoint = p(-1, 2),   expectedType = completeOverlap(isStartAndEndFlipped = true)),
            Intersection("Complete overlap at (1, 8)",    p(1, 1)    lineTo p(1, 8),      p(1, 1)    lineTo p(1, 8),    expectedPoint = p(1, 1),    expectedType = completeOverlap(isStartAndEndFlipped = false)),
            Intersection("Complete overlap at (289, 29)", p(159, 87) lineTo p(289, 29),   p(289, 29) lineTo p(159, 87), expectedPoint = p(159, 87), expectedType = completeOverlap(isStartAndEndFlipped = true))
    )

    private val noIntersections = listOf(
            Intersection("No intersection A", p(0, 0)   lineTo p(0, -3),    p(2, 3)   lineTo p(2, -3),  expectedPoint = null, expectedType = none),
            Intersection("No intersection B", p(16, 16) lineTo p(16, 17),   p(17, 17) lineTo p(18, 17), expectedPoint = null, expectedType = none)
    )

    private val allIntersections =
            edgeTouchingEdgeIntersections +
            vertexTouchingEdgeIntersections +
            vertexTouchingVertexIntersections +
            completeOverlapIntersections +
            noIntersections


    @Test
    fun describeIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("describe: " + it.name,
                        it.expectedType,
                        it.line1.describeIntersection(it.line2),
                        tolerance = defaultCalculationTolerance)
                assertEquals("describe: " + it.name,
                        it.expectedType.integerValue(),
                        it.line1.integerValue().describeIntersection(it.line2.integerValue()),
                        tolerance = 1.0) // Integer calculations can be offset from accurate fraction calculations by 1
            }
        }

        TestOutputLogger.info { "rawIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }


    @Test
    fun rawIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("rawIntersection: " + it.name,
                        it.expectedPoint,
                        it.line1.rawIntersection(it.line2),
                        defaultCalculationTolerance)
                assertEquals("rawIntersection: " + it.name,
                        it.expectedPoint?.integerValue,
                        it.line1.integerValue.rawIntersection(it.line2.integerValue),
                        defaultCalculationTolerance)
            }
        }

        TestOutputLogger.info { "rawIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }


    @Test
    fun findLineIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("findLineIntersection: " + it.name,
                        it.expectedPoint,
                        FractionLineSegment.findLineIntersection(it.line1, it.line2),
                        defaultCalculationTolerance)
                assertEquals("findLineIntersection: " + it.name,
                        it.expectedPoint?.integerValue,
                        IntegerLineSegment.findLineIntersection(it.line1.integerValue, it.line2.integerValue),
                        defaultCalculationTolerance)
            }
        }

        TestOutputLogger.info { "findLineIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }
}


@Suppress("NOTHING_TO_INLINE")
@JvmName("assertFractionPointsEqual")
private inline fun assertEquals(message: String, expected: ComputablePoint<Fraction>?, actual: ComputablePoint<Fraction>?, tolerance: Tolerance = defaultCalculationTolerance) {
    val fail = if (actual != null) {
        if (expected != null) {
            !expected.equals(actual, tolerance)
        } else { // if expected == null
            true
        }
    } else { // if actual == null
        expected != null
    }

    if (fail) {
        assertionFailure("Expected $expected, got $actual: $message")
    }
}


@Suppress("NOTHING_TO_INLINE")
@JvmName("assertIntegerPointsEqual")
private inline fun assertEquals(message: String, expected: ComputablePoint<Integer>?, actual: ComputablePoint<Integer>?, tolerance: Tolerance = defaultCalculationTolerance) {
    val fail = if (actual != null) {
        if (expected != null) {
            !expected.equals(actual, tolerance)
        }
        else { // if expected == null
            true
        }
    }
    else { // if actual == null
        expected != null
    }
    if (fail) {
        assertionFailure("Expected $expected, got $actual: $message")
    }
}


@Suppress("NOTHING_TO_INLINE")
private inline fun assertEquals(message: String, expected: IntersectionDescription<*, *>, actual: IntersectionDescription<*, *>, tolerance: Tolerance = defaultCalculationTolerance) {
    val fail: Boolean = if (expected::class != actual::class ) {
        false
    } else {
        when (expected) {
            none -> false
            is leftVertexTouchesRightVertex<*, *> -> !expected.verticesLocation.fractionValue.equals((actual as leftVertexTouchesRightVertex<*, *>).verticesLocation.fractionValue, tolerance = tolerance)
            is leftVertexTouchesRightEdge<*, *> -> !expected.leftVertexLocation.fractionValue.equals((actual as leftVertexTouchesRightEdge<*, *>).leftVertexLocation.fractionValue, tolerance = tolerance)
            is rightVertexTouchesLeftEdge<*, *> -> !expected.rightVertexLocation.fractionValue.equals((actual as rightVertexTouchesLeftEdge<*, *>).rightVertexLocation.fractionValue, tolerance = tolerance)
            is edgesCross<*, *> -> !expected.crossingLocation.fractionValue.equals((actual as edgesCross<*, *>).crossingLocation.fractionValue, tolerance = tolerance)
            is completeOverlap -> !expected.isStartAndEndFlipped == (actual as completeOverlap).isStartAndEndFlipped
        }
    }
    if (fail) {
        assertionFailure("Expected $expected, got $actual: $message")
    }
}
