import java.util.*

fun main() {
    val inputLines = readInput("Day15")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 15 - Part One$C_RESET\n")
        val riskLevels = inputLines.map { line -> line.map { it.digitToInt() } }
        println("The lowest risk path has a total risk of ${C_YELLOW}${riskLevels.lowestTotalRiskPath()}${C_RESET}.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

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