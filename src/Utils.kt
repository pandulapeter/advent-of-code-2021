import java.io.File

const val C_RESET = "\u001B[0m"
const val C_BLACK = "\u001B[30m"
const val C_RED = "\u001B[31m"
const val C_GREEN = "\u001B[32m"
const val C_YELLOW = "\u001B[33m"
const val C_BLUE = "\u001B[34m"
const val C_PURPLE = "\u001B[35m"
const val C_CYAN = "\u001B[36m"
const val C_WHITE = "\u001B[37m"

fun readInput(name: String) = File("src", "input/$name.txt").readLines()