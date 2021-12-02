fun main() {
    val inputLines = readInput("Day01")
    println("Read ${inputLines.size} lines")
    try {
        val inputValues = inputLines.map { it.toInt() }
        println("\nDay 01 - Part One\n")
        inputValues.process(1)
        println("\nDay 01 - Part Two\n")
        inputValues.process(3)
    } catch (_: NumberFormatException) {
        println("Invalid input.")
    }
}

private fun List<Int>.process(slidingWindowSize: Int) {
    val sums = mutableListOf<Int>()
    when {
        slidingWindowSize < 1 || slidingWindowSize >= size -> println("Invalid sliding window size ($slidingWindowSize)")
        slidingWindowSize == 1 -> sums.addAll(this)
        else -> {
            var sum: Int
            var currentValue: Int
            for (index in 0..size) {
                if (index + slidingWindowSize <= size) {
                    sum = 0
                    for (offset in 0 until slidingWindowSize) {
                        currentValue = this[index + offset]
                        print(currentValue)
                        sum += currentValue
                        if (offset == slidingWindowSize - 1) {
                            println(" = $sum")
                            sums.add(sum)
                        } else {
                            print(" + ")
                        }
                    }
                }
            }
        }
    }
    println("There were a total of ${sums.determineIncreaseCount()} incremental measurements.")
}

private fun List<Int>.determineIncreaseCount(): Int {
    println("Tendency:")
    var increaseCount = 0
    var tendency: Tendency
    forEachIndexed { index, value ->
        tendency = determineTendency(
            previousValue = if (index == 0) null else this[index - 1],
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
