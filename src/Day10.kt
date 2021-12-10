fun main() {
    val inputLines = readInput("Day10")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 10 - Part One$C_RESET\n")
        var score = 0
        val incompleteLines = mutableListOf<String>()
        inputLines.forEach { line ->
            val lastUnclosedCharacter = mutableListOf<Char>()
            var isLineCorrupted = false
            line.forEach { character ->
                when (character) {
                    '(', '[', '{', '<' -> lastUnclosedCharacter.add(0, character.close)
                    ')', ']', '}', '>' -> {
                        if (lastUnclosedCharacter[0] != character) {
                            score += character.errorScore
                            isLineCorrupted = true
                        }
                        lastUnclosedCharacter.removeAt(0)
                    }
                    else -> throw IllegalArgumentException("Invalid character: $character.")
                }
            }
            if (!isLineCorrupted) {
                incompleteLines.add(line)
            }
        }
        println("The total syntax error score is $C_YELLOW$score$C_RESET.")
        println("\n${C_GREEN}Day 10 - Part Two$C_RESET\n")
        println("Incomplete lines:")
        val lineScores = mutableListOf<Long>()
        incompleteLines.forEach { line ->
            print(" - $C_PURPLE$line$C_RESET can be completed with $C_PURPLE")
            val unclosedCharacters = mutableListOf<Char>()
            line.forEach { character ->
                when (character) {
                    '(', '[', '{', '<' -> unclosedCharacters.add(0, character)
                    ')', ']', '}', '>' -> unclosedCharacters.removeAt(0)
                    else -> throw IllegalArgumentException("Invalid character: $character.")
                }
            }
            var lineScore = 0L
            unclosedCharacters.forEach { character ->
                val closingCharacter = when (character) {
                    '(' -> ')'
                    '[' -> ']'
                    '{' -> '}'
                    '<' -> '>'
                    else -> throw IllegalArgumentException("Invalid character: $character.")
                }
                lineScore = lineScore * 5 + closingCharacter.completionScore
                print(closingCharacter)
            }
            print("$C_RESET for a score of $C_CYAN$lineScore$C_RESET.")
            lineScores.add(lineScore)
            println()
        }
        println("\nThe middle score is $C_YELLOW${lineScores.sorted()[lineScores.size / 2]}$C_RESET.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private val Char.errorScore
    get() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }

private val Char.completionScore
    get() = when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> 0
    }

private val Char.close
    get() = when (this) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> throw IllegalArgumentException("Invalid character: $this.")
    }
