fun main() {
    val inputLines = readInput("Day04")
    println("Read ${inputLines.size} lines")
    try {
        println("\nDay 04 - Part One\n")
        val drawnNumbers = inputLines.first().split(",").map { it.toInt() }
        var boards: List<List<MutableList<Int?>>> = inputLines.drop(1).chunked(6).map { board ->
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
            winnerIndex = boards.indexOfFirst { board ->
                board.process(drawnNumbers[index])
                board.isWinning
            }
            index++
        }
        if (winnerIndex == -1) {
            println("There is no winning board")
        } else {
            val winningNumber = drawnNumbers[index - 1]
            println("There is a winner after $index drawn numbers (for number $winningNumber).")
            println("The winning score for board $winnerIndex is ${boards[winnerIndex].score(winningNumber)}")
            println("\nDay 04 - Part Two\n")
            boards = boards.filterNot { it.isWinning }
            do {
                boards.forEach { it.process(drawnNumbers[index]) }
                boards = boards.filterNot { it.isWinning }
                index++
            } while (boards.size != 1 && index < drawnNumbers.lastIndex)
            val lastWinningBoard = boards.first()
            while (!lastWinningBoard.isWinning && index < drawnNumbers.lastIndex) {
                lastWinningBoard.process(drawnNumbers[index])
                index++
            }
            if (lastWinningBoard.isWinning) {
                val lastWinningNumber = drawnNumbers[index - 1]
                println("The last winner board happens after $index drawn numbers (for number $lastWinningNumber).")
                println("The winning score for this board is ${lastWinningBoard.score(lastWinningNumber)}")
            } else {
                println("There is a board that never wins with the given drawn numbers.")
            }
        }
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

fun List<MutableList<Int?>>.process(drawnNumber: Int) {
    forEach { line ->
        line.replaceAll { number ->
            if (number == drawnNumber) null else number
        }
    }
}

fun List<MutableList<Int?>>.score(drawnNumber: Int) = flatten().mapNotNull { it }.sum() * drawnNumber

val List<MutableList<Int?>>.isWinning
    get() = any { line -> line.all { number -> number == null } } || (0..lastIndex).any { index -> all { line -> line[index] == null } }