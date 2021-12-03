fun main() {
    val inputLines = readInput("Day03")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 03 - Part One\n")
        var binaryGammaRate = ""
        var binaryEpsilonRate = ""
        val halfDataSetSize = inputLines.size / 2
        for (index in 0 until inputLines.first().length) {
            (inputLines.count { it[index] == '1' } > halfDataSetSize).let {
                binaryGammaRate += if (it) '1' else '0'
                binaryEpsilonRate += if (it) '0' else '1'
            }
        }
        val gammaRate = binaryGammaRate.toInt(2)
        val epsilonRate = binaryEpsilonRate.toInt(2)
        println("Gamma rate: $binaryGammaRate ($gammaRate)")
        println("Epsilon rate: $binaryEpsilonRate ($epsilonRate)")
        println("Power consumption: ${gammaRate * epsilonRate}")
        println("\nDay 03 - Part Two\n")
        var potentialOxygenGeneratorRatings = inputLines.toList()
        var index = 0
        while (potentialOxygenGeneratorRatings.size != 1) {
            (potentialOxygenGeneratorRatings.count { it[index] == '1' } >= potentialOxygenGeneratorRatings.count { it[index] == '0' }).let { isOneTheMostCommonBit ->
                potentialOxygenGeneratorRatings = potentialOxygenGeneratorRatings.filter {
                    it[index] == if (isOneTheMostCommonBit) '1' else '0'
                }
            }
            index++
        }
        val binaryOxygenGeneratorRating = potentialOxygenGeneratorRatings.first()
        val oxygenGeneratorRating = binaryOxygenGeneratorRating.toInt(2)
        println("Oxygen generator rating: $binaryOxygenGeneratorRating ($oxygenGeneratorRating)")
        var potentialCO2ScrubberRatings = inputLines.toList()
        index = 0
        while (potentialCO2ScrubberRatings.size != 1) {
            (potentialCO2ScrubberRatings.count { it[index] == '1' } < potentialCO2ScrubberRatings.count { it[index] == '0' }).let { isOneTheMostCommonBit ->
                potentialCO2ScrubberRatings = potentialCO2ScrubberRatings.filter {
                    it[index] == if (isOneTheMostCommonBit) '1' else '0'
                }
            }
            index++
        }
        val binaryCO2ScrubberRating = potentialCO2ScrubberRatings.first()
        val cO2ScrubberRating = binaryCO2ScrubberRating.toInt(2)
        println("CO2 scrubber rating: $binaryCO2ScrubberRating ($cO2ScrubberRating)")
        println("Life support rating: ${oxygenGeneratorRating * cO2ScrubberRating}")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}