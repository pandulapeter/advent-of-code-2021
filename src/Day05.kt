import kotlin.math.max
import kotlin.math.min

fun main() {
    val inputLines = readInput("Day05")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 05 - Part One\n")
        val points = inputLines.map { it.toLine().points }.flatten()
        val groupedPoints = points.groupingBy { it }.eachCount()
        val solution = groupedPoints.keys.count { (groupedPoints[it] ?: 0) >= 2}
        println("There are $solution points where at least 2 lines overlap.")

    } catch (_: Exception) {
        println("Invalid input.")
    }
}

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val start: Point,
    val end: Point
) {
    val points = (if (start.x == end.x) {
        (min(start.y, end.y)..max(start.y, end.y)).map { y ->
            Point(
                x = start.x,
                y = y
            )
        }
    } else if (start.y == end.y) {
        (min(start.x, end.x)..max(start.x, end.x)).map { x ->
            Point(
                x = x,
                y = start.y
            )
        }
    } else emptyList()).also {
        println("New line: ${it}")
    }
}

private fun String.toLine() = split(" -> ").let { points ->
    Line(
        start = points.first().toPoint(),
        end = points.last().toPoint()
    )
}

private fun String.toPoint() = split(",").let { coordinates ->
    Point(
        x = coordinates.first().toInt(),
        y = coordinates.last().toInt()
    )
}