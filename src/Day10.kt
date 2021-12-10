fun main() {
    val inputLines = readInput("Day10")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 10 - Part One\n")
        var score = 0
        inputLines.forEach { line ->
            val lastUnclosedCharacter = mutableListOf<Char>()
            line.forEach { character ->
                when (character) {
                    '(', '[', '{', '<' -> lastUnclosedCharacter.add(0, character.close)
                    ')', ']', '}', '>' -> {
                        if (lastUnclosedCharacter[0] != character) {
                            score += character.score
                        }
                        lastUnclosedCharacter.removeAt(0)
                    }
                    else -> throw IllegalArgumentException("Invalid character: $character.")
                }
            }
        }
        println("The total syntax error score is $score")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private val Char.score
    get() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
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
