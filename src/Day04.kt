fun main() {
    val inputLines = readInput("Day04")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 04 - Part One\n")
        val drawnNumbers = inputLines.first().split(",").map { it.toInt() }
        val boards: List<List<MutableList<Int?>>> = inputLines.drop(1).chunked(6).map { board ->
            board.drop(1).map { line ->
                line.split("[ \\t]+".toRegex()).mapNotNull { number ->
                    if (number.isEmpty()) null else number.toInt()
                }.toMutableList()
            }
        }
        println("The input contains ${drawnNumbers.size} drawn numbers and ${boards.size} boards.")
        var winnerIndex = -1
        var index = 0
        while (winnerIndex == -1 && index < drawnNumbers.size) {
            winnerIndex = boards.indexOfFirst { board -> board.isWinning(drawnNumbers[index]) }
            index++
        }
        if (winnerIndex == -1) {
            println("There is no winning board")
        } else {
            val winningNumber = drawnNumbers[index - 1]
            val winningBoardSum = boards[winnerIndex].flatten().mapNotNull { it }.sum()
            println("There is a winner after $index drawn numbers.")
            println("The winning score for board $winnerIndex is ${winningBoardSum * winningNumber}")
        }
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

fun List<MutableList<Int?>>.isWinning(drawnNumber: Int): Boolean {
    forEach { line ->
        line.replaceAll { number ->
            if (number == drawnNumber) null else number
        }
    }
    return any { line -> line.all { number -> number == null } } || (0..lastIndex).any { index -> all { line -> line[index] == null } }
}