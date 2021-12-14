fun main() {
    val inputLines = readInput("Day14")
    println("Read ${inputLines.size} lines")
    try {
        val initialPolymerTemplate = inputLines.first()
        val replacementMap: Map<String, Char> = inputLines.drop(2).associate { line ->
            line.split(" -> ").let { it.first() to it.last().first() }
        }
        println("\n${C_GREEN}Day 14 - Part One$C_RESET\n")
        initialPolymerTemplate.process(replacementMap, 10)
        println("\n${C_GREEN}Day 14 - Part Two$C_RESET\n")
        initialPolymerTemplate.process(replacementMap, 40)
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun String.process(replacementMap: Map<String, Char>, iterationCount: Int) {
    buildMap<Char, Long> {
        increment(first())
        (0 until iterationCount)
            .fold(
                initial = windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }
            ) { current, _ ->
                buildMap {
                    current.forEach { (segment, count) ->
                        replacementMap[segment].let { newCharacter ->
                            increment("${segment.first()}$newCharacter", count)
                            increment("$newCharacter${segment.last()}", count)
                        }
                    }
                }
            }
            .forEach { (segment, count) -> increment(segment.last(), count) }
    }.print(iterationCount)
}

private fun <T> MutableMap<T, Long>.increment(key: T, value: Long = 1) {
    this[key] = (this[key] ?: 0) + value
}

private fun Map<Char, Number>.print(iterationCount: Int) {
    println("Character frequency for $C_BLUE$iterationCount$C_RESET iterations:\n")
    keys.sorted().forEach { println("${it}: ${this[it]}") }
    val mostCommonElementCount = values.maxOf { it.toLong() }
    val leastCommonElementCount = values.minOf { it.toLong() }
    println("\nThe solution is $mostCommonElementCount - $leastCommonElementCount = $C_YELLOW${mostCommonElementCount - leastCommonElementCount}$C_RESET.")
}