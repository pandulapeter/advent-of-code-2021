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
    } catch (_: Exception) {
        println("Invalid input.")
    }
}