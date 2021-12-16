fun main() {
    val inputLines = readInput("Day16")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 16 - Part One$C_RESET\n")
        inputLines.forEach { hexadecimal ->
            versionNumberSum = 0
            println("Hexadecimal input: $C_CYAN$hexadecimal$C_RESET")
            hexadecimal.hexToBinary().processPacket()
            println("The sum of all version numbers is $C_YELLOW$versionNumberSum$C_RESET.\n")
        }
        println("\n${C_GREEN}Day 16 - Part Two$C_RESET\n")
        inputLines.forEach { hexadecimal ->
            println("Hexadecimal input: $C_CYAN$hexadecimal$C_RESET")
            println("The decrypted value is $C_YELLOW${hexadecimal.hexToBinary().processPacket().literalValue}$C_RESET.\n")
        }
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private const val VERSION_LENGTH = 3
private const val TYPE_ID_LENGTH = 3
private const val LENGTH_TYPE_ID_LENGTH = 1
private const val TOTAL_LENGTH_IN_BITS = 15
private const val NUMBER_OF_SUB_PACKETS = 11

private var versionNumberSum = 0L

private fun String.processPacket(): IterationResult {
    if (isNotBlank() && !all { it == '0' }) {
        print(" - Packet: ")
        forEachIndexed { index, character ->
            print(
                when {
                    index < VERSION_LENGTH -> C_RED
                    index < VERSION_LENGTH + TYPE_ID_LENGTH -> C_PURPLE
                    else -> C_WHITE
                }
            )
            print("$character$C_RESET")
        }
        val versionValue = take(VERSION_LENGTH).toLong(2)
        versionNumberSum += versionValue
        print(" (version $C_RED${versionValue}$C_RESET, ")
        val typeIdValue = drop(VERSION_LENGTH).take(TYPE_ID_LENGTH).toLong(2)
        print("type $C_PURPLE")
        when (typeIdValue) {
            4L -> {
                val numbers = drop(VERSION_LENGTH + TYPE_ID_LENGTH)
                var shouldReadNextNumber = true
                var index = 0
                var fullNumber = ""
                while (shouldReadNextNumber) {
                    shouldReadNextNumber = numbers[index] != '0'
                    val number = numbers.substring(index + 1, index + 5)
                    fullNumber += number
                    index += 5
                }
                val fullNumberValue = fullNumber.toLong(2)
                println("literal$C_RESET): $C_YELLOW${fullNumberValue}$C_RESET")
                return IterationResult(
                    bitsProcessedInThisIteration = VERSION_LENGTH + TYPE_ID_LENGTH + index,
                    literalValue = fullNumberValue
                )
            }
            else -> {
                val lengthTypeId = drop(VERSION_LENGTH + TYPE_ID_LENGTH).take(LENGTH_TYPE_ID_LENGTH).toLong(2)
                val remainingBinary = drop(VERSION_LENGTH + TYPE_ID_LENGTH + LENGTH_TYPE_ID_LENGTH)
                when (lengthTypeId) {
                    0L -> {
                        val totalLengthInBits = remainingBinary.take(TOTAL_LENGTH_IN_BITS).toLong(2).toInt()
                        println("operator 0$C_RESET with $C_BLUE$totalLengthInBits$C_RESET bits)")
                        val subPacketsAndRemainder = remainingBinary.drop(TOTAL_LENGTH_IN_BITS)
                        var bitCounter = 0
                        var remaining = subPacketsAndRemainder.take(totalLengthInBits)
                        val values = mutableListOf<Long>()
                        while (bitCounter != totalLengthInBits) {
                            val result = remaining.processPacket()
                            values.add(result.literalValue)
                            remaining = remaining.drop(result.bitsProcessedInThisIteration)
                            bitCounter += result.bitsProcessedInThisIteration
                        }
                        return IterationResult(
                            bitsProcessedInThisIteration = VERSION_LENGTH + TYPE_ID_LENGTH + LENGTH_TYPE_ID_LENGTH + TOTAL_LENGTH_IN_BITS + totalLengthInBits,
                            literalValue = values.processByTypeId(typeIdValue)
                        )
                    }
                    1L -> {
                        val totalSubPacketCount = remainingBinary.take(NUMBER_OF_SUB_PACKETS).toLong(2)
                        println("operator 1$C_RESET with $C_BLUE$totalSubPacketCount$C_RESET sub-packets)")
                        var remaining = remainingBinary.drop(NUMBER_OF_SUB_PACKETS)
                        var accumulator = 0
                        var subPacketCounter = 0L
                        val values = mutableListOf<Long>()
                        while (subPacketCounter != totalSubPacketCount) {
                            val result = remaining.processPacket()
                            values.add(result.literalValue)
                            remaining = remaining.drop(result.bitsProcessedInThisIteration)
                            accumulator += result.bitsProcessedInThisIteration
                            subPacketCounter++
                        }
                        return IterationResult(
                            bitsProcessedInThisIteration = VERSION_LENGTH + TYPE_ID_LENGTH + LENGTH_TYPE_ID_LENGTH + NUMBER_OF_SUB_PACKETS + accumulator,
                            literalValue = values.processByTypeId(typeIdValue)
                        )
                    }
                    else -> {
                        println("invalid operator)")
                        return IterationResult(
                            bitsProcessedInThisIteration = length,
                            literalValue = 0
                        )
                    }
                }
            }
        }
    } else {
        return IterationResult(
            bitsProcessedInThisIteration = length,
            literalValue = 0
        )
    }
}

private fun List<Long>.processByTypeId(typeIdValue: Long) = when (typeIdValue) {
    0L -> sum()
    1L -> {
        var product = 1L
        forEach { product *= it }
        product
    }
    2L -> minOf { it }
    3L -> maxOf { it }
    5L -> if (first() > last()) 1 else 0
    6L -> if (first() < last()) 1 else 0
    7L -> if (first() == last()) 1 else 0
    else -> throw IllegalArgumentException("Invalid type ID :$typeIdValue.")
}

private fun String.hexToBinary() = map { it.hexToBinary() }.joinToString("")

private fun Char.hexToBinary() = String.format("%4s", Integer.toBinaryString(toString().toInt(16))).replace(' ', '0')

private data class IterationResult(
    val bitsProcessedInThisIteration: Int,
    val literalValue: Long
)
