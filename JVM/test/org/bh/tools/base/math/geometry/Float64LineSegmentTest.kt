package org.bh.tools.base.math.geometry

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.math.defaultFractionCalculationTolerance
import org.bh.tools.base.math.geometry.IntersectionDescription.*
import org.bh.tools.base.util.measureTimeInterval
import org.junit.Assert.*
import org.junit.Test
import java.util.logging.Logger


private data class Intersection<IntersectionDescriptionType: IntersectionDescription>
(val name: String,
 val line1: FloatLineSegment, val line2: FloatLineSegment,
 val expectedIntersectionPoint: FloatPoint?,
 val expectedIntersectionDescriptionType: Class<IntersectionDescriptionType>) {

    constructor(name: String,
                line1Start: FloatPoint, line1End: FloatPoint,   line2Start: FloatPoint, line2End: FloatPoint,
                expectedIntersectionPoint: FloatPoint,
                expectedIntersectionDescriptionType: Class<IntersectionDescriptionType>):
            this(name, FloatLineSegment(line1Start, line1End), FloatLineSegment(line2Start, line2End), expectedIntersectionPoint, expectedIntersectionDescriptionType)
}


/**
 * @author Kyli Rouge
 * @since 1/23/2017.
 */
class Float64LineSegmentTest {
    private val edgeTouchingEdgeIntersections: List<Intersection<*>> = listOf(
            Intersection("Edge crosses edge perpendicularly at (0, 0) A", FloatPoint(-1, 0),   FloatPoint(1, 0),       FloatPoint(0, 1),    FloatPoint(0, -1),    expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = edgesCross::class.java),
            Intersection("Edge crosses edge perpendicularly at (0, 0) B", FloatPoint(-1, 1),   FloatPoint(1, -1),      FloatPoint(1, 1),    FloatPoint(-1, -1),   expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = edgesCross::class.java),
            Intersection("Edge crosses edge perpendicularly at (0, 0) C", FloatPoint(-1, 2),   FloatPoint(1, -2),      FloatPoint(1, 2),    FloatPoint(-1, -2),   expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = edgesCross::class.java),
            Intersection("Edge crosses edge at (4, 5)",                   FloatPoint(4, 1),    FloatPoint(4, 8),       FloatPoint(7, 2),    FloatPoint(1, 8),     expectedIntersectionPoint = FloatPoint(4, 5),     expectedIntersectionDescriptionType = edgesCross::class.java),
            Intersection("Edge crosses edge at (658, 139)",               FloatPoint(631, 87), FloatPoint(851, 481),   FloatPoint(729, 48), FloatPoint(651, 150), expectedIntersectionPoint = FloatPoint(2923451.0/4431.0, 613820.0/4431.0), expectedIntersectionDescriptionType = edgesCross::class.java)
    )

    private val vertexTouchingEdgeIntersections: List<Intersection<*>> = listOf(
            Intersection("Vertex touches edge perpendicularly at (0, 0) A", FloatPoint(-1, 0),    FloatPoint(1, 0),       FloatPoint(0, 1),     FloatPoint(0, 0),     expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java),
            Intersection("Vertex touches edge perpendicularly at (0, 0) B", FloatPoint(-1, 1),    FloatPoint(1, -1),      FloatPoint(1, 1),     FloatPoint(0, 0),     expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java),
            Intersection("Vertex touches edge perpendicularly at (0, 0) C", FloatPoint(-1, 2),    FloatPoint(1, -2),      FloatPoint(1, 2),     FloatPoint(0, 0),     expectedIntersectionPoint = FloatPoint(0, 0),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java),
            Intersection("Vertex touches edge at (4, 5)",                   FloatPoint(4, 1),     FloatPoint(4, 8),       FloatPoint(7, 2),     FloatPoint(4, 5),     expectedIntersectionPoint = FloatPoint(4, 5),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java),
            Intersection("Vertex touches edge at (1, 5)",                   FloatPoint(1, 1),     FloatPoint(1, 8),       FloatPoint(4, 2),     FloatPoint(1, 5),     expectedIntersectionPoint = FloatPoint(1, 5),     expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java),
            Intersection("Vertex touches edge at (366, 294)",               FloatPoint(242, 418), FloatPoint(466, 194),   FloatPoint(225, 353), FloatPoint(366, 294), expectedIntersectionPoint = FloatPoint(366, 294), expectedIntersectionDescriptionType = rightVertexTouchesLeftEdge::class.java)
    )

    private val vertexTouchingVertexIntersections: List<Intersection<*>> = listOf(
            Intersection("Vertex touches vertex perpendicularly at (0, 0)", FloatPoint(-1, 0),   FloatPoint(0, 0),      FloatPoint(0, 1),    FloatPoint(0, 0),     expectedIntersectionPoint = FloatPoint(0, 0),    expectedIntersectionDescriptionType = leftVertexTouchesRightVertex::class.java),
            Intersection("Vertex touches vertex at (0, 0)",                 FloatPoint(-1, 2),   FloatPoint(1, -2),     FloatPoint(1, 2),    FloatPoint(0, 0),     expectedIntersectionPoint = FloatPoint(0, 0),    expectedIntersectionDescriptionType = leftVertexTouchesRightVertex::class.java),
            Intersection("Vertex touches vertex at (1, 8)",                 FloatPoint(1, 1),    FloatPoint(1, 8),      FloatPoint(4, 2),    FloatPoint(1, 8),     expectedIntersectionPoint = FloatPoint(1, 8),    expectedIntersectionDescriptionType = leftVertexTouchesRightVertex::class.java),
            Intersection("Vertex touches vertex at (289, 29)",              FloatPoint(159, 87), FloatPoint(289, 29),   FloatPoint(289, 29), FloatPoint(267, 203), expectedIntersectionPoint = FloatPoint(289, 29), expectedIntersectionDescriptionType = leftVertexTouchesRightVertex::class.java)
    )

    private val completeOverlapIntersections: List<Intersection<*>> = listOf(
            Intersection("Complete overlap at (0, 0) A",  FloatPoint(-1, 0),   FloatPoint(0, 0),      FloatPoint(-1, 0),   FloatPoint(0, 0),    expectedIntersectionPoint = FloatPoint(-1, 0),   expectedIntersectionDescriptionType = completeOverlap::class.java),
            Intersection("Complete overlap at (0, 0) B",  FloatPoint(-1, 2),   FloatPoint(1, -2),     FloatPoint(-1, 2),   FloatPoint(1, -2),   expectedIntersectionPoint = FloatPoint(-1, 2),   expectedIntersectionDescriptionType = completeOverlap::class.java),
            Intersection("Complete overlap at (1, 8)",    FloatPoint(1, 1),    FloatPoint(1, 8),      FloatPoint(1, 1),    FloatPoint(1, 8),    expectedIntersectionPoint = FloatPoint(1, 1),    expectedIntersectionDescriptionType = completeOverlap::class.java),
            Intersection("Complete overlap at (289, 29)", FloatPoint(159, 87), FloatPoint(289, 29),   FloatPoint(159, 87), FloatPoint(289, 29), expectedIntersectionPoint = FloatPoint(159, 87), expectedIntersectionDescriptionType = completeOverlap::class.java)
    )

    private val allIntersections = edgeTouchingEdgeIntersections + vertexTouchingEdgeIntersections + vertexTouchingVertexIntersections + completeOverlapIntersections


    @Test
    fun describeIntersection() {
        val totalTimeInSeconds = measureTimeInterval {
            allIntersections.forEach {
                assertEquals("describe: " + it.name,
                        it.expectedIntersectionDescriptionType,
                        it.line1.describeIntersection(it.line2).javaClass)
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
                        Float64LineSegment.findLineIntersection(it.line1, it.line2),
                        defaultFractionCalculationTolerance)
            }
        }

        Logger.getGlobal().info { "findLineIntersection took $totalTimeInSeconds seconds to evaluate ${allIntersections.size} intersections" }
    }
}


fun assertEquals(message: String, expected: ComputablePoint<BHFloat>?, actual: ComputablePoint<BHFloat>?, tolerance: BHFloat) {
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
