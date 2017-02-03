package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.defaultFractionCalculationTolerance
import org.bh.tools.base.math.geometry.FractionPoint.Companion.zero
import org.bh.tools.base.math.geometry.IntersectionDescription.*
import org.bh.tools.base.util.measureTimeInterval
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.logging.Logger


private data class Intersection
(val name: String,
 val line1: FractionLineSegment, val line2: FractionLineSegment,
 val expectedIntersectionPoint: FractionPoint?,
 val expectedIntersectionDescriptionType: IntersectionDescription) {

    constructor(name: String,
                line1Start: FractionPoint, line1End: FractionPoint,   line2Start: FractionPoint, line2End: FractionPoint,
                expectedIntersectionPoint: FractionPoint,
                expectedIntersectionDescriptionType: IntersectionDescription):
            this(name, FractionLineSegment(line1Start, line1End), FractionLineSegment(line2Start, line2End), expectedIntersectionPoint, expectedIntersectionDescriptionType)
}


/**
 * @author Kyli Rouge
 * @since 1/23/2017.
 */
class Float64LineSegmentTest {
    private val edgeTouchingEdgeIntersections: List<Intersection> = listOf(
            Intersection("Edge crosses edge perpendicularly at (0, 0) A", FractionPoint(-1, 0),   FractionPoint(1, 0),       FractionPoint(0, 1),    FractionPoint(0, -1),    expectedIntersectionPoint = zero,                                          expectedIntersectionDescriptionType = edgesCross(zero)),
            Intersection("Edge crosses edge perpendicularly at (0, 0) B", FractionPoint(-1, 1),   FractionPoint(1, -1),      FractionPoint(1, 1),    FractionPoint(-1, -1),   expectedIntersectionPoint = zero,                                          expectedIntersectionDescriptionType = edgesCross(zero)),
            Intersection("Edge crosses edge perpendicularly at (0, 0) C", FractionPoint(-1, 2),   FractionPoint(1, -2),      FractionPoint(1, 2),    FractionPoint(-1, -2),   expectedIntersectionPoint = zero,                                          expectedIntersectionDescriptionType = edgesCross(zero)),
            Intersection("Edge crosses edge at (4, 5)",                   FractionPoint(4, 1),    FractionPoint(4, 8),       FractionPoint(7, 2),    FractionPoint(1, 8),     expectedIntersectionPoint = FractionPoint(4, 5),                              expectedIntersectionDescriptionType = edgesCross(FractionPoint(4, 5))),
            Intersection("Edge crosses edge at (658, 139)",               FractionPoint(631, 87), FractionPoint(851, 481),   FractionPoint(729, 48), FractionPoint(651, 150), expectedIntersectionPoint = FractionPoint(2923451.0/4431.0, 613820.0/4431.0), expectedIntersectionDescriptionType = edgesCross(FractionPoint(2923451.0/4431.0, 613820.0/4431.0)))
    )

    private val vertexTouchingEdgeIntersections: List<Intersection> = listOf(
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) A", FractionPoint(-1, 0),    FractionPoint(1, 0),       FractionPoint(0, 1),     zero,                 expectedIntersectionPoint = zero,                 expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(zero,                 isRightStartVertex = false)),
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) B", FractionPoint(-1, 1),    FractionPoint(1, -1),      FractionPoint(1, 1),     zero,                 expectedIntersectionPoint = zero,                 expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(zero,                 isRightStartVertex = false)),
            Intersection("Right vertex touches left edge perpendicularly at (0, 0) C", FractionPoint(-1, 2),    FractionPoint(1, -2),      FractionPoint(1, 2),     zero,                 expectedIntersectionPoint = zero,                 expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(zero,                 isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (4, 5)",                   FractionPoint(4, 1),     FractionPoint(4, 8),       FractionPoint(7, 2),     FractionPoint(4, 5),     expectedIntersectionPoint = FractionPoint(4, 5),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(FractionPoint(4, 5),     isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (1, 5)",                   FractionPoint(1, 1),     FractionPoint(1, 8),       FractionPoint(4, 2),     FractionPoint(1, 5),     expectedIntersectionPoint = FractionPoint(1, 5),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(FractionPoint(1, 5),     isRightStartVertex = false)),
            Intersection("Right vertex touches left edge at (366, 294)",               FractionPoint(242, 418), FractionPoint(466, 194),   FractionPoint(225, 353), FractionPoint(366, 294), expectedIntersectionPoint = FractionPoint(366, 294), expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge(FractionPoint(366, 294), isRightStartVertex = false))
    )

    private val vertexTouchingVertexIntersections: List<Intersection> = listOf(
            Intersection("Vertex touches vertex perpendicularly at (0, 0)", FractionPoint(-1, 0),   FractionPoint.zero,       FractionPoint(0, 1),    zero,                 expectedIntersectionPoint = zero,                expectedIntersectionDescriptionType = leftVertexTouchesRightVertex(zero,                isLeftStartVertex = false, isRightStartVertex = false)),
            Intersection("Vertex touches vertex collinearly at (1, 2)",     FractionPoint(-1, 2),   FractionPoint(1, 2),      FractionPoint(1, 2),    FractionPoint(2, 2),     expectedIntersectionPoint = FractionPoint(1, 2),    expectedIntersectionDescriptionType = leftVertexTouchesRightVertex(FractionPoint(1, 2),    isLeftStartVertex = false, isRightStartVertex = true)),
            Intersection("Vertex touches vertex at (1, 8)",                 FractionPoint(1, 1),    FractionPoint(1, 8),      FractionPoint(4, 2),    FractionPoint(1, 8),     expectedIntersectionPoint = FractionPoint(1, 8),    expectedIntersectionDescriptionType = leftVertexTouchesRightVertex(FractionPoint(1, 8),    isLeftStartVertex = false, isRightStartVertex = false)),
            Intersection("Vertex touches vertex at (289, 29)",              FractionPoint(159, 87), FractionPoint(289, 29),   FractionPoint(289, 29), FractionPoint(267, 203), expectedIntersectionPoint = FractionPoint(289, 29), expectedIntersectionDescriptionType = leftVertexTouchesRightVertex(FractionPoint(289, 29), isLeftStartVertex = false, isRightStartVertex = true))
    )

    private val completeOverlapIntersections: List<Intersection> = listOf(
            Intersection("Complete overlap at (0, 0) A",  FractionPoint(-1, 0),   zero,                  FractionPoint(-1, 0),   zero,                expectedIntersectionPoint = FractionPoint(-1, 0),   expectedIntersectionDescriptionType = completeOverlap(isStartAndEndFlipped = false)),
            Intersection("Complete overlap at (0, 0) B",  FractionPoint(-1, 2),   FractionPoint(1, -2),     FractionPoint(1, -2),   FractionPoint(-1, 2),   expectedIntersectionPoint = FractionPoint(-1, 2),   expectedIntersectionDescriptionType = completeOverlap(isStartAndEndFlipped = true)),
            Intersection("Complete overlap at (1, 8)",    FractionPoint(1, 1),    FractionPoint(1, 8),      FractionPoint(1, 1),    FractionPoint(1, 8),    expectedIntersectionPoint = FractionPoint(1, 1),    expectedIntersectionDescriptionType = completeOverlap(isStartAndEndFlipped = false)),
            Intersection("Complete overlap at (289, 29)", FractionPoint(159, 87), FractionPoint(289, 29),   FractionPoint(289, 29), FractionPoint(159, 87), expectedIntersectionPoint = FractionPoint(159, 87), expectedIntersectionDescriptionType = completeOverlap(isStartAndEndFlipped = true))
    )

    private val allIntersections = edgeTouchingEdgeIntersections + vertexTouchingEdgeIntersections + vertexTouchingVertexIntersections + completeOverlapIntersections


    @Test
    fun describeIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("describe: " + it.name,
                        it.expectedIntersectionDescriptionType,
                        it.line1.describeIntersection(it.line2))
            }
        }

        Logger.getGlobal().info { "rawIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }


    @Test
    fun rawIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("raw: " + it.name,
                        it.expectedIntersectionPoint,
                        it.line1.rawIntersection(it.line2),
                        defaultFractionCalculationTolerance)
            }
        }

        Logger.getGlobal().info { "rawIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }


    @Test
    fun findLineIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("findLineIntersection: " + it.name,
                        it.expectedIntersectionPoint!!,
                        FractionLineSegment.findLineIntersection(it.line1, it.line2),
                        defaultFractionCalculationTolerance)
            }
        }

        Logger.getGlobal().info { "findLineIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }
}


fun assertEquals(message: String, expected: ComputablePoint<Fraction>?, actual: ComputablePoint<Fraction>?, tolerance: Fraction) {
    val fail: Boolean
    if (actual != null) {
        if (expected != null) {
            fail = !expected.equals(actual, tolerance)
        } else { // if expected == null
            fail = true
        }
    } else { // if actual == null
        fail = expected != null
    }
    if (fail) {
        @Suppress("DEPRECATION")
        junit.framework.Assert.failNotEquals(message, expected, actual)
    }
}
