fun main() {
    val inputLines = readInput("Day11")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 11 - Part One$C_RESET\n")
        val energyLevels = inputLines.map { row -> row.map { it.digitToInt() }.toMutableList() }
        var step = 0
        var flashCount = 0
        while (step < 100) {
            energyLevels.draw(step)
            flashCount += energyLevels.process()
            step++
        }
        energyLevels.draw(step)
        println("There have been $C_YELLOW$flashCount$C_RESET flashes in total.")
        println("\n${C_GREEN}Day 11 - Part Two$C_RESET\n")
        val energyLevelsCopy = inputLines.map { row -> row.map { it.digitToInt() }.toMutableList() }
        var stepsToSynchronize = 0
        do {
            energyLevelsCopy.draw(stepsToSynchronize)
            stepsToSynchronize++
            energyLevelsCopy.process()
        } while (energyLevelsCopy.any { row -> row.any { energyLevel -> energyLevel != 0 } })
        energyLevelsCopy.draw(stepsToSynchronize)
        println("It takes $C_YELLOW$stepsToSynchronize$C_RESET steps for the energy levels to synchronize.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<MutableList<Int>>.process(): Int {
    forEachIndexed { y, row ->
        row.forEachIndexed { x, _ ->
            row[x] += 1
        }
    }
    var shouldRepeat: Boolean
    val flashes = mutableListOf<Pair<Int, Int>>()
    do {
        shouldRepeat = false
        forEachIndexed { y, row ->
            row.forEachIndexed { x, energyLevel ->
                if (energyLevel > 9 && !flashes.contains(x to y)) {
                    shouldRepeat = true
                    flashes.add(x to y)
                    if (y > 0) {
                        if (x > 0) this[y - 1][x - 1] += 1
                        this[y - 1][x] += 1
                        if (x < first().lastIndex) this[y - 1][x + 1] += 1
                    }
                    if (x > 0) this[y][x - 1] += 1
                    if (x < first().lastIndex) this[y][x + 1] += 1
                    if (y < lastIndex) {
                        if (x > 0) this[y + 1][x - 1] += 1
                        this[y + 1][x] += 1
                        if (x < first().lastIndex) this[y + 1][x + 1] += 1
                    }
                }
            }
        }
    } while (shouldRepeat)
    forEach { row ->
        row.forEachIndexed { x, energyLevel ->
            if (energyLevel > 9) {
                row[x] = 0
            }
        }
    }
    return flashes.size
}


private fun List<MutableList<Int>>.draw(stepCount: Int) {
    println("Step $stepCount:")
    forEach { row ->
        row.forEach { energyLevel ->
            print("${if (energyLevel == 0) C_YELLOW else C_WHITE}$energyLevel${C_RESET}")
        }
        println()
    }
    println()
}