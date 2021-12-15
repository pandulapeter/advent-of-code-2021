import java.util.*

fun main() {
    val inputLines = readInput("Day15")
    println("Read ${inputLines.size} lines")
    try {
        val riskLevels = inputLines.map { line -> line.map { it.digitToInt() } }
        println("\n${C_GREEN}Day 15 - Part One$C_RESET\n")
        riskLevels.process()
        println("\n${C_GREEN}Day 15 - Part Two$C_RESET\n")
        riskLevels.expand(5).process()
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<List<Int>>.process() =
    println("The lowest risk path has a total risk of ${C_YELLOW}${lowestTotalRiskPath()}${C_RESET}.")

private fun List<List<Int>>.lowestTotalRiskPath(): Int {
    val distances = mapIndexed { y, line ->
        List(line.size) { x -> if (x == 0 && y == 0) 0 else Int.MAX_VALUE }.toMutableList()
    }
    val toVisit = PriorityQueue<Pair<Int, Int>> { (y1, x1), (y2, x2) ->
        distances[y1][x1].compareTo(distances[y2][x2])
    }
    val visited = mutableSetOf(0 to 0)
    toVisit.add(0 to 0)
    while (toVisit.isNotEmpty()) {
        val (y, x) = toVisit.poll()
        visited.add(y to x)
        neighbours(y, x).forEach { (nextY, nextX) ->
            if (!visited.contains(nextY to nextX)) {
                val newDistance = distances[y][x] + this[nextY][nextX]
                if (newDistance < distances[nextY][nextX]) {
                    distances[nextY][nextX] = newDistance
                    toVisit.add(nextY to nextX)
                }
            }
        }
    }
    return distances[distances.lastIndex][distances.last().lastIndex]
}

private fun List<List<Int>>.neighbours(x: Int, y: Int) = buildList {
    if (x > 0) {
        add(x - 1 to y)
    }
    if (x < this@neighbours.first().lastIndex) {
        add(x + 1 to y)
    }
    if (y > 0) {
        add(x to y - 1)
    }
    if (y < this@neighbours.lastIndex) {
        add(x to y + 1)
    }
}

private fun List<List<Int>>.expand(multiplier: Int): List<List<Int>> {
    val expandedRight =
        map { row -> (1 until multiplier).fold(row) { acc, step -> acc + row.increment(step) } }
    return (1 until multiplier).fold(expandedRight) { acc, step -> acc + expandedRight.map { it.increment(step) } }
}

private fun List<Int>.increment(by: Int) = map { level -> (level + by).let { if (it > 9) it - 9 else it } }