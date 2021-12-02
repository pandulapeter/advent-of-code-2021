import java.lang.Integer.max

fun main() {
    val inputLines = readInput("Day02")
    println("Read ${inputLines.size} lines")
    try {
        val commands = inputLines.map { it.toCommand() }
        println("\nDay 02 - Part One\n")
        var horizontalPosition = 0
        var depth = 0
        commands.forEach { command ->
            print("Moving ")
            when (command) {
                is Command.Backward -> {
                    print("backwards")
                    horizontalPosition -= command.value
                }
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
        println("The sum of the final horizontal position and depth is ${horizontalPosition * depth}.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun String.toCommand() = split(" ").let { parts ->
    val value = parts[1].toInt()
    when (val command = parts[0]) {
        "backward" -> Command.Backward(value)
        "down" -> Command.Down(value)
        "forward" -> Command.Forward(value)
        "up" -> Command.Up(value)
        else -> throw IllegalArgumentException("Unknown keyword: $command")
    }
}

private sealed class Command {
    abstract val value: Int

    data class Backward(override val value: Int) : Command()
    data class Down(override val value: Int) : Command()
    data class Forward(override val value: Int) : Command()
    data class Up(override val value: Int) : Command()
}
