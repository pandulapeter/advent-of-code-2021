fun main() {
    println("\nDay 01 - Part One\n")
    val inputLines = readInput("Day01")
    println("Read ${inputLines.size} lines")
    try {
        val inputValues = inputLines.map { it.toInt() }
        println("There were a total of ${inputValues.determineIncreaseCount()} incremental measurements.")
        println("\nDay 01 - Part Two\n")
        val sums = mutableListOf<Int>()
        var sum: Int
        var currentValue: Int
        for (index in 0..inputValues.size) {
            if (index + SLIDING_WINDOW_SIZE <= inputValues.size) {
                sum = 0
                for (offset in 0 until SLIDING_WINDOW_SIZE) {
                    currentValue = inputValues[index + offset]
                    print(currentValue)
                    sum += currentValue
                    if (offset == SLIDING_WINDOW_SIZE - 1) {
                        println(" = $sum")
                        sums.add(sum)
                    } else {
                        print(" + ")
                    }
                }
            }
        }
        println("There were a total of ${sums.determineIncreaseCount()} incremental measurements.")
    } catch (_: NumberFormatException) {
        println("Invalid input.")
    }
}

private const val SLIDING_WINDOW_SIZE = 3

private fun List<Int>.determineIncreaseCount(): Int {
    var increaseCount = 0
    var tendency: Tendency
    forEachIndexed { index, value ->
        tendency = determineTendency(
            previousValue = if (index == 0) null else get(index - 1),
            currentValue = value
        )
        println("$value (${tendency.describe()})")
        if (tendency == Tendency.INCREASE) {
            increaseCount++
        }
    }
    return increaseCount
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
    Tendency.NO_CHANGE -> "no change"
    Tendency.DECREASE -> "decreased"
}
