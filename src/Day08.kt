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
        val acceptedOutputs = listOf(Digits.ONE, Digits.FOUR, Digits.SEVEN, Digits.EIGHT)
        val acceptedOutputsLengths = acceptedOutputs.map { it.segments.size }
        val solution = displays.flatMap { it.fourOutputDigits }.filter { it.length in acceptedOutputsLengths }.size
        println("In total the numbers $acceptedOutputs appear $solution times in the output values.")
        println("\nDay 08 - Part Two\n")
        println("The sum of all outputs is ${displays.sumOf { it.decode() }}.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private enum class Segments {
    A, B, C, D, E, F, G
}

private enum class Digits(
    val value: Int,
    val segments: List<Segments>
) {
    ZERO(0, listOf(Segments.A, Segments.B, Segments.C, Segments.E, Segments.F, Segments.G)),
    ONE(1, listOf(Segments.C, Segments.F)),
    TWO(2, listOf(Segments.A, Segments.C, Segments.D, Segments.E, Segments.G)),
    THREE(3, listOf(Segments.A, Segments.C, Segments.D, Segments.F, Segments.G)),
    FOUR(4, listOf(Segments.B, Segments.C, Segments.D, Segments.F)),
    FIVE(5, listOf(Segments.A, Segments.B, Segments.D, Segments.F, Segments.G)),
    SIX(6, listOf(Segments.A, Segments.B, Segments.D, Segments.E, Segments.F, Segments.G)),
    SEVEN(7, listOf(Segments.A, Segments.C, Segments.F)),
    EIGHT(8, listOf(Segments.A, Segments.B, Segments.C, Segments.D, Segments.E, Segments.F, Segments.G)),
    NINE(9, listOf(Segments.A, Segments.B, Segments.C, Segments.D, Segments.F, Segments.G))
}

private data class Display(
    val tenSignalPatterns: List<String>,
    val fourOutputDigits: List<String>
)

private fun Display.decode(): Int {
    println("Processing: $tenSignalPatterns")

    val mapping = Digits.values().associateWith { "" }.toMutableMap()

    // We know a few digits for sure, based on the number of segments
    mapping[Digits.ONE] = tenSignalPatterns.first { it.length == Digits.ONE.segments.size }
    mapping[Digits.FOUR] = tenSignalPatterns.first { it.length == Digits.FOUR.segments.size }
    mapping[Digits.SEVEN] = tenSignalPatterns.first { it.length == Digits.SEVEN.segments.size }
    mapping[Digits.EIGHT] = tenSignalPatterns.first { it.length == Digits.EIGHT.segments.size }

    // 'd' is the segment that is part of 2, 3, 4 and 5, and it's NOT a part of 7
    val twoThreeFive = tenSignalPatterns.filter { it.length == 5 }
    val twoThreeFourFive = twoThreeFive.plus(mapping[Digits.FOUR]!!)
    val d = twoThreeFourFive.first().first { character ->
        twoThreeFourFive.all { it.contains(character) } && !mapping[Digits.SEVEN]!!.contains(character)
    }

    // 0 is the digit that has 6 segments but 'd' is not one of them
    val zeroSixNine = tenSignalPatterns.filter { it.length == 6 }
    mapping[Digits.ZERO] = zeroSixNine.first { !it.contains(d) }
    val sixNine = zeroSixNine.filterNot { it == mapping[Digits.ZERO] }

    // 'c' is the segment that is part of 1 but missing from 6 or 9
    val c = mapping[Digits.ONE]!!.first { character -> sixNine.any { sixOrNine -> sixOrNine.none { it == character } } }

    // 9 is the digit that has is either 6 or 9 and contains the segment 'c'
    mapping[Digits.NINE] = sixNine.first { it.contains(c) }
    mapping[Digits.SIX] = sixNine.first { it != mapping[Digits.NINE] }

    // 3 is the digit that has all the segments from 1
    mapping[Digits.THREE] = twoThreeFive.first { options -> mapping[Digits.ONE]!!.all { options.contains(it) } }
    val twoFive = twoThreeFive.filterNot { it == mapping[Digits.THREE] }

    // 2 is the digit that is either 2 or 5 and contains the segment 'c'
    mapping[Digits.TWO] = twoFive.first { it.contains(c) }
    mapping[Digits.FIVE] = twoFive.first { it != mapping[Digits.TWO] }

    // Log the results and process the output
    mapping.keys.forEach { solution -> println(" - ${solution.value}: ${mapping[solution]}") }
    val output = fourOutputDigits
        .map { digit ->
            mapping.keys.first { mapping[it]!!.all { digit.contains(it) } && mapping[it]!!.length == digit.length }.value
        }
        .joinToString("") { it.toString() }
    println(" - The input $fourOutputDigits corresponds to $output.")
    return output.toInt()
}