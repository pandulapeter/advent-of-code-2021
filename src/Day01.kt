fun main() {
    println("Day 1")
    val inputLines = readInput("Day01")
    println("Read ${inputLines.size} lines")
    try {
        val inputValues = inputLines.map { it.toInt() }
        var increaseCount = 0
        var tendency: Tendency
        inputValues.forEachIndexed { index, value ->
            tendency = determineTendency(
                previousValue = if (index == 0) null else inputValues[index - 1],
                currentValue = value
            )
            println("$value (${tendency.describe()})")
            if (tendency == Tendency.INCREASE) {
                increaseCount++
            }
        }
        println("There were a total of $increaseCount incremental measurements.")
    } catch (_: NumberFormatException) {
        println("Invalid input.")
    }
}

private fun determineTendency(previousValue: Int?, currentValue: Int) = when {
    previousValue == null -> Tendency.NO_PREVIOUS_MEASUREMENT
    previousValue == currentValue -> Tendency.NO_CHANGE
    previousValue < currentValue -> Tendency.INCREASE
    else -> Tendency.DECREASE
}

private enum class Tendency {
    NO_PREVIOUS_MEASUREMENT,
    INCREASE,
    NO_CHANGE,
    DECREASE
}

private fun Tendency.describe() = when (this) {
    Tendency.NO_PREVIOUS_MEASUREMENT -> "N/A - no previous measurement"
    Tendency.INCREASE -> "increased"
    Tendency.NO_CHANGE -> "unchanged"
    Tendency.DECREASE -> "decreased"
}
