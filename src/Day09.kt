fun main() {
    val inputLines = readInput("Day09")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 09 - Part One\n")
        val heightmap = inputLines.map { row -> row.map { it.digitToInt() } }
        val lowPoints = mutableListOf<Int>()
        heightmap.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                val isLowPoint = heightmap.isLowPoint(x, y)
                val color = if (isLowPoint) C_RED else C_WHITE
                if (isLowPoint) {
                    lowPoints.add(cell)
                }
                print("$color$cell$C_RESET")
            }
            println()
        }
        println("\nThe sum of all risk levels is $C_YELLOW${lowPoints.sumOf { it + 1 }}$C_RESET.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<List<Int>>.isLowPoint(x: Int, y: Int): Boolean {
    val neighbourCoordinates = mutableListOf<Pair<Int, Int>>()
    if (x > 0) {
        neighbourCoordinates.add(x - 1 to y)
    }
    if (x < this[y].lastIndex) {
        neighbourCoordinates.add(x + 1 to y)
    }
    if (y > 0) {
        neighbourCoordinates.add(x to y - 1)
    }
    if (y < lastIndex) {
        neighbourCoordinates.add(x to y + 1)
    }
    return neighbourCoordinates.all { neighbour ->
        this[neighbour.second][neighbour.first] > this[y][x]
    }
}