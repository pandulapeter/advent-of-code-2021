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
        println("\nDay 09 - Part Two\n")
        val basins = mutableListOf<List<Pair<Int, Int>>>()
        val heightmapCopy = heightmap.map { it.toMutableList() }
        heightmap.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                val isWall = cell == 9
                heightmapCopy.findBasin(x, y).let { basin ->
                    if (basin.isNotEmpty()) {
                        basins.add(basin.toList())
                    }
                }
                val color = if (isWall) C_PURPLE else C_WHITE
                print("$color$cell$C_RESET")
            }
            println()
        }
        if (basins.isEmpty()) {
            println("There are no basins in the heightmap.")
        } else {
            println("\nThere are ${basins.size} basins.")
            val threeLargestBasinSizes = basins.map { it.size }.sortedDescending().take(3)
            var solution = 1
            threeLargestBasinSizes.forEach { basinSize ->
                solution *= basinSize
            }
            println("The product of the three largest sizes is ${threeLargestBasinSizes.joinToString(" * ")} = $C_YELLOW$solution$C_RESET.")
        }
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<List<Int>>.isLowPoint(x: Int, y: Int) = findNeighbours(x, y).all { neighbour ->
    this[neighbour.second][neighbour.first] > this[y][x]
}

private fun List<MutableList<Int>>.findBasin(x: Int, y: Int): Set<Pair<Int, Int>> {
    val solution = mutableSetOf<Pair<Int, Int>>()
    if (this[y][x] != 9) {
        this[y][x] = 9
        solution.add(x to y)
        val neighbourCoordinates = findNeighbours(x, y)
        neighbourCoordinates.forEach { neighbour ->
            solution.addAll(findBasin(neighbour.first, neighbour.second))
        }
    }
    return solution
}

private fun List<List<Int>>.findNeighbours(x: Int, y: Int) = buildList {
    if (x > 0) {
        add(x - 1 to y)
    }
    if (x < this@findNeighbours[y].lastIndex) {
        add(x + 1 to y)
    }
    if (y > 0) {
        add(x to y - 1)
    }
    if (y < this@findNeighbours.lastIndex) {
        add(x to y + 1)
    }
}