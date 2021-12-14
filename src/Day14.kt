fun main() {
    val inputLines = readInput("Day14")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 14 - Part One$C_RESET\n")
        val initialPolymerTemplate = inputLines.first()
        val pairInsertionRules = inputLines.drop(2).map { line ->
            line.split(" -> ").let {
                InsertionRule(
                    template = it.first(),
                    insertion = it.last()
                )
            }
        }
        var polymerTemplate = initialPolymerTemplate
        println(polymerTemplate)
        repeat(10) { iteration ->
            polymerTemplate = polymerTemplate.process(pairInsertionRules)
            println("${iteration + 1}: $polymerTemplate")
        }
        println()
        val counter = polymerTemplate.groupingBy { it }.eachCount().values
        val mostCommonElementCount = counter.maxOrNull() ?: 0
        val leastCommonElementCount = counter.minOrNull() ?: 0
        println("Solution: $C_YELLOW${mostCommonElementCount - leastCommonElementCount}$C_RESET.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private data class InsertionRule(
    val template: String,
    val insertion: String
)

private fun String.process(insertionRules: List<InsertionRule>) = windowed(2).map { pair ->
    (insertionRules.firstOrNull { it.template == pair }?.let { matchingInsertionRule ->
        "${pair.first()}${matchingInsertionRule.insertion}"
    } ?: pair.first())
}.joinToString("").plus(last())