import java.lang.Integer.max

fun main() {
    val inputLines = readInput("Day02")
    println("Read ${inputLines.size} lines")
    try {
        val commands = inputLines.map { it.toCommand() }
        println("\nDay 02 - Part One\n")
        commands.processPartOne()
        println("\nDay 02 - Part Two\n")
        commands.processPartTwo()
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun List<Command>.processPartOne() {
    var horizontalPosition = 0
    var depth = 0
    forEach { command ->
        print("Moving ")
        when (command) {
            is Command.Down -> {
                print("down")
                depth += command.value
            }
            is Command.Forward -> {
                print("forward")
                horizontalPosition += command.value
            }
            is Command.Up -> {
                print("up")
                depth = max(0, depth - command.value)
            }
        }
        println(" ${command.value} (horizontal position: $horizontalPosition, depth: $depth)")
    }
    println("Multiplying the final horizontal position and the final depth results in ${horizontalPosition * depth}.")
}

private fun List<Command>.processPartTwo() {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0
    forEach { command ->
        print("Moving ")
        when (command) {
            is Command.Down -> {
                print("down")
                aim += command.value
            }
            is Command.Forward -> {
                print("forward")
                horizontalPosition += command.value
                depth = max(0, depth + aim * command.value)
            }
            is Command.Up -> {
                print("up")
                aim -= command.value
            }
        }
        println(" ${command.value} (horizontal position: $horizontalPosition, aim: $aim depth: $depth)")
    }
    println("Multiplying the final horizontal position and the final depth results in ${horizontalPosition * depth}.")
}

private fun String.toCommand() = split(" ").let { parts ->
    val value = parts[1].toInt()
    when (val command = parts[0]) {
        "down" -> Command.Down(value)
        "forward" -> Command.Forward(value)
        "up" -> Command.Up(value)
        else -> throw IllegalArgumentException("Unknown keyword: $command")
    }
}

private sealed class Command {
    abstract val value: Int

    data class Down(override val value: Int) : Command()
    data class Forward(override val value: Int) : Command()
    data class Up(override val value: Int) : Command()
}
