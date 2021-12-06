fun main() {
    val inputLines = readInput("Day06")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 06 - Part One\n")
        val lanternfish = inputLines.first().split(",").map { Lanternfish(it.toInt()) }.toMutableList()
        var day = 0
        var index: Int
        var maxIndex: Int
        lanternfish.log(day)
        while (day < DAY_COUNT) {
            day++
            maxIndex = lanternfish.size
            index = 0
            while (index < maxIndex) {
                if (lanternfish[index].process()) {
                    lanternfish.add(Lanternfish())
                }
                index++
            }
            lanternfish.log(day)
        }
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private data class Lanternfish(var counter: Int = FIRST_CYCLE_MAXIMUM) {

    fun process() = (counter-- == 0).also { shouldSpawnNewInstance ->
        if (shouldSpawnNewInstance) {
            counter = CYCLE_MAXIMUM
        }
    }

    companion object {
        private const val CYCLE_MAXIMUM = 6
        private const val FIRST_CYCLE_MAXIMUM = 8
    }
}

private fun List<Lanternfish>.log(day: Int) = println(
    "${if (day == 0) "Initial state" else "Day $day"}: $size lanternfish"
)

private const val DAY_COUNT = 80

