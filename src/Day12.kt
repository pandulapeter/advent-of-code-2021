fun main() {
    val inputLines = readInput("Day12")
    println("Read ${inputLines.size} lines")
    try {
        println("\n${C_GREEN}Day 12 - Part One$C_RESET\n")
        val connections = mutableMapOf<Node, List<Node>>()
        println("${C_RED}Connections:$C_RESET")
        inputLines.forEach { row ->
            row.split("-").let {
                val node1 = it.first().toNode()
                val node2 = it.last().toNode()
                connections[node1] = connections[node1].orEmpty() + node2
                connections[node2] = connections[node2].orEmpty() + node1
            }
        }
        connections.keys.forEach { key ->
            println("[$key] = ${connections[key]}")
        }
        println("\n${C_RED}Paths:$C_RESET")
        val paths = mutableListOf<MutableList<Node>>(mutableListOf(Node.Start))
        connections.findPaths(paths)
        paths.filterNot { it.last() == Node.DeadEnd }.forEach { println(it) }
        println("\nThere are ${C_YELLOW}${paths.filter { it.last() == Node.End }.size}$C_RESET possible paths in total.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

private fun Map<Node, List<Node>>.findPaths(
    paths: MutableList<MutableList<Node>>
) {
    fun currentPath() = paths.last()
    fun lastNode() = currentPath().last()
    while (lastNode() != Node.End && lastNode() != Node.DeadEnd) {
        val potentialNextNodes = this[lastNode()].orEmpty().filter { node ->
            !currentPath().contains(node) || node is Node.Cave.Large
        }
        if (potentialNextNodes.isEmpty()) {
            currentPath().add(Node.DeadEnd)
        } else {
            val copy = currentPath()
            paths.removeAt(paths.lastIndex)
            potentialNextNodes.forEach { node ->
                paths.add((copy + node).toMutableList())
                findPaths(paths)
            }
        }
    }
}

private sealed class Node {

    object Start : Node() {
        override fun toString() = "${C_PURPLE}start$C_RESET"
    }

    object End : Node() {
        override fun toString() = "${C_CYAN}end$C_RESET"
    }

    object DeadEnd : Node() {
        override fun toString() = "${C_RED}deadEnd$C_RESET"
    }

    sealed class Cave : Node() {

        abstract val name: String

        data class Large(override val name: String) : Cave() {
            override fun toString() = "${C_YELLOW}$name$C_RESET"
        }

        data class Small(override val name: String) : Cave() {
            override fun toString() = "${C_BLUE}$name$C_RESET"
        }
    }
}

private fun String.toNode() = when {
    this == "start" -> Node.Start
    this == "end" -> Node.End
    this.all { it.isUpperCase() } -> Node.Cave.Large(this)
    else -> Node.Cave.Small(this)
}