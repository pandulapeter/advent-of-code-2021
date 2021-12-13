fun main() {
    val inputLines = readInput("Day13")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 13 - Part One$C_RESET\n")
        val separator = inputLines.indexOf("")
        val dots = inputLines.take(separator).map { line ->
            line.split(",").let {
                Dot(
                    x = it.first().toInt(),
                    y = it.last().toInt()
                )
            }
        }
        val instructions = inputLines.drop(separator + 1).map { line ->
            line.filter { it.isDigit() }.toInt().let { value ->
                if (line.contains("x")) {
                    Instruction.HorizontalFold(value)
                } else {
                    Instruction.VerticalFold(value)
                }
            }
        }
        println("There are ${dots.size} dots and ${instructions.size} instructions.\n")
        dots.draw(instructions)
        println("After folding once:\n")
        dots.forEach { it.fold(instructions.take(1)) }
        var foldedDots = dots.distinct()
        foldedDots.draw(instructions)
        println("There are $C_YELLOW${foldedDots.size}$C_RESET dots after folding once.")
        println("\n${C_GREEN}Day 13 - Part Two$C_RESET\n")
        println("After performing all remaining folds:\n")
        dots.forEach { it.fold(instructions) }
        foldedDots = dots.distinct()
        foldedDots.draw(instructions)
        println("There are $C_YELLOW${foldedDots.size}$C_RESET dots after performing all instructions.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private data class Dot(
    var x: Int,
    var y: Int
) {

    fun fold(instructions: List<Instruction>) = instructions.forEach { instruction ->
        when (instruction) {
            is Instruction.HorizontalFold -> foldHorizontally(instruction.x)
            is Instruction.VerticalFold -> foldVertically(instruction.y)
        }
    }

    private fun foldHorizontally(foldX: Int) {
        x = if (x < foldX) x else foldX - (x - foldX)
    }

    private fun foldVertically(foldY: Int) {
        y = if (y < foldY) y else foldY - (y - foldY)
    }
}

private sealed class Instruction {

    data class HorizontalFold(val x: Int) : Instruction()

    data class VerticalFold(val y: Int) : Instruction()
}

private fun List<Dot>.draw(instructions: List<Instruction>) {
    val horizontalFolds = instructions.filterIsInstance<Instruction.HorizontalFold>()
    val verticalFolds = instructions.filterIsInstance<Instruction.VerticalFold>()
    for (y in 0 until (maxByOrNull { it.y }?.y ?: 0) + 1) {
        for (x in 0 until (maxByOrNull { it.x }?.x ?: 0) + 1) {
            print(
                when {
                    horizontalFolds.any { it.x == x } -> "$C_RED|$C_RESET"
                    verticalFolds.any { it.y == y } -> "$C_RED-$C_RESET"
                    any { it.x == x && it.y == y } -> "$C_BLUE#$C_RESET"
                    else -> "."
                }
            )
        }
        println()
    }
    println()
}