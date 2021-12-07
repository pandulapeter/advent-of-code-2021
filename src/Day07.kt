import kotlin.math.abs

fun main() {
    val inputLines = readInput("Day07")
    println("Read ${inputLines.size} lines")
    try {
        val crabPositions = inputLines.first().split(",").map { it.toInt() }
        println("\nDay 07 - Part One\n")
        crabPositions.solve(true)
        println("\nDay 07 - Part Two\n")
        crabPositions.solve(false)
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<Int>.solve(isPartOne: Boolean) =
    (minOf { it }..maxOf { it }).map { calculateFuelTo(isPartOne, it) }.let { costMap ->
        println("The minimum fuel cost is ${costMap.minByOrNull { it }}.")
    }

private fun List<Int>.calculateFuelTo(isPartOne: Boolean, target: Int) = sumOf { it.calculateFuelTo(isPartOne, target) }

private fun Int.calculateFuelTo(isPartOne: Boolean, target: Int) = abs(this - target).let {
    if (isPartOne) it else (it * (it + 1)) / 2
}
