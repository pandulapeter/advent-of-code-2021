import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val inputLines = readInput("Day05")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 05 - Part One\n")
        println("There are ${inputLines.solve(false)} points where at least 2 lines overlap.")
        println("\nDay 05 - Part Two\n")
        println("There are ${inputLines.solve(true)} points where at least 2 lines overlap.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private data class Point(
    val x: Int,
    val y: Int
)

private data class Line(
    val start: Point,
    val end: Point,
    val shouldConsiderDiagonals: Boolean
) {
    val points = (if (start.x == end.x) {
        print("Vertical")
        (min(start.y, end.y)..max(start.y, end.y)).map { y ->
            Point(
                x = start.x,
                y = y
            )
        }
    } else if (start.y == end.y) {
        print("Horizontal")
        (min(start.x, end.x)..max(start.x, end.x)).map { x ->
            Point(
                x = x,
                y = start.y
            )
        }
    } else if (shouldConsiderDiagonals) {
        if (abs(start.x - end.x) == abs(start.y - end.y)) {
            print("Diagonal")
            (0..max(abs(start.x - end.x), abs(start.y - end.y))).map { iteration ->
                Point(
                    x = if (start.x > end.x) start.x - iteration else start.x + iteration,
                    y = if (start.y > end.y) start.y - iteration else start.y + iteration
                )
            }
        } else {
            print("Invalid")
            emptyList()
        }
    } else {
        print("Invalid")
        emptyList()
    }).also { println(" line from $start to $end: $it") }
}

private fun List<String>.solve(shouldConsiderDiagonals: Boolean) = map { it.toLine(shouldConsiderDiagonals).points }
    .flatten()
    .groupingBy { it }
    .eachCount()
    .let { groupedPoints -> groupedPoints.keys.count { (groupedPoints[it] ?: 0) >= 2 } }

private fun String.toLine(shouldConsiderDiagonals: Boolean) = split(" -> ").let { points ->
    Line(
        start = points.first().toPoint(),
        end = points.last().toPoint(),
        shouldConsiderDiagonals = shouldConsiderDiagonals
    )
}

private fun String.toPoint() = split(",").let { coordinates ->
    Point(
        x = coordinates.first().toInt(),
        y = coordinates.last().toInt()
    )
}