fun main() {
    val inputLines = readInput("Day06")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 06 - Part One\n")
        inputLines.simulate(18)
        println("\nDay 06 - Part Two\n")
        inputLines.simulate(256)
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<String>.simulate(maximumDayCount: Int) {
    val lanternfish = first()
        .split(",")
        .groupingBy { it.toInt() }
        .eachCount()
        .mapValues { it.value.toLong() }
        .toMutableMap()
    lanternfish.log(0)
    var parentCount: Long
    for (day in 1..maximumDayCount) {
        parentCount = lanternfish[0] ?: 0L
        for (index in 0..NEW_CYCLE_LENGTH) {
            lanternfish[index] = when (index) {
                CYCLE_LENGTH -> parentCount + (lanternfish[index + 1] ?: 0L)
                NEW_CYCLE_LENGTH -> parentCount
                else -> lanternfish[index + 1] ?: 0L
            }
        }
        lanternfish.log(day)
    }
}

private fun Map<Int, Long>.log(day: Int) = println(
    "${if (day == 0) "Initial state" else "Day $day"}: ${values.sum()} lanternfish"
)

private const val CYCLE_LENGTH = 6
private const val NEW_CYCLE_LENGTH = 8

