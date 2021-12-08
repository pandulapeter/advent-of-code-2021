fun main() {
    val inputLines = readInput("Day08")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 08 - Part One\n")
        val displays = inputLines.map { inputLine ->
            inputLine.split(" | ").let { parts ->
                Display(
                    tenSignalPatterns = parts.first().split(" "),
                    fourOutputDigits = parts.last().split(" ")
                )
            }
        }
        displays.forEach {
            it.process()
        }
        val acceptedOutputLengths = listOf(Numbers.ONE, Numbers.FOUR, Numbers.SEVEN, Numbers.EIGHT).map { it.segments.size }
        val solution = displays.flatMap { it.fourOutputDigits }.filter { it.length in acceptedOutputLengths }.size
        println("In total the numbers 1, 4, 7 and 8 appear $solution times in the output values.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private enum class Segments {
    A, B, C, D, E, F, G
}

private enum class Numbers(
    val value: Int,
    val segments: List<Segments>
) {
    ZERO(0, listOf(Segments.A, Segments.B, Segments.C, Segments.E, Segments.F, Segments.G)), // 6 segments
    ONE(1, listOf(Segments.C, Segments.F)), // 2 segments -- UNIQUE
    TWO(2, listOf(Segments.A, Segments.C, Segments.D, Segments.E, Segments.G)), // 5 segments
    THREE(3, listOf(Segments.A, Segments.C, Segments.D, Segments.F, Segments.G)), // 5 segments
    FOUR(4, listOf(Segments.B, Segments.C, Segments.D, Segments.F)), // 4 segments -- UNIQUE
    FIVE(5, listOf(Segments.A, Segments.B, Segments.D, Segments.F, Segments.G)), // 5 segments
    SIX(6, listOf(Segments.A, Segments.B, Segments.D, Segments.E, Segments.F, Segments.G)), // 6 segments
    SEVEN(7, listOf(Segments.A, Segments.C, Segments.F)), // 3 segments -- UNIQUE
    EIGHT(8, listOf(Segments.A, Segments.B, Segments.C, Segments.D, Segments.E, Segments.F, Segments.G)), // 7 segments -- UNIQUE
    NINE(9, listOf(Segments.A, Segments.B, Segments.C, Segments.D, Segments.F, Segments.G)), // 6 segments
}

private data class Display(
    val tenSignalPatterns: List<String>,
    val fourOutputDigits: List<String>
)

private fun Display.process() {
    println("Processing: $tenSignalPatterns | $fourOutputDigits")
    tenSignalPatterns.forEach { signalPattern ->
        println(" - '$signalPattern' could mean ${Numbers.values().filter { it.segments.size == signalPattern.length }}")
    }
}