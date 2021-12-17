import kotlin.math.max

fun main() {
    val inputLines = readInput("Day17")
    println("Read ${inputLines.size} lines")
    try {
        val target = inputLines.first().replace("target area: ", "").split(", ").let { parts ->
            Target(
                xRange = parts.first().range(),
                yRange = parts.last().range()
            )
        }
        println("\n${C_GREEN}Day 17 - Part One$C_RESET\n")
        val validShots = target.findValidPaths(
            xVelocityRange = -INTERVAL_SIZE..INTERVAL_SIZE,
            yVelocityRange = -INTERVAL_SIZE..INTERVAL_SIZE
        )
        val bestShot = validShots.maxByOrNull { shot -> shot.path.maxOf { it.y } }
        if (bestShot == null) {
            println("\n${C_YELLOW}There is no shot that reaches the target.$C_RESET")
        } else {
            println()
            target.draw(bestShot.path)
            print("\nThe best shot has the starting velocity $C_CYAN(${bestShot.xVelocity},${bestShot.yVelocity})$C_RESET ")
            println("and reaches the maximum height of $C_YELLOW${bestShot.path.maxOf { it.y }}$C_RESET.")
        }
        println("\n${C_GREEN}Day 17 - Part Two$C_RESET\n")
        println("There are $C_YELLOW${validShots.size}$C_RESET valid shots in total.")
    } catch (_: Exception) {
        println("Invalid input.")
    }
}

const val INTERVAL_SIZE = 200

private fun String.range() = drop(2).split("..").let { range ->
    range.first().toInt()..range.last().toInt()
}

private data class Target(
    val xRange: IntRange,
    val yRange: IntRange
) {
    val xMax = xRange.last
    val yMin = yRange.first
}

private data class PathSegment(
    val x: Int,
    val y: Int
)

private data class Shot(
    val xVelocity: Int,
    val yVelocity: Int,
    val path: List<PathSegment>
)

private fun Target.findValidPaths(
    xVelocityRange: IntRange,
    yVelocityRange: IntRange
) = mutableListOf<Shot>().apply {
    xVelocityRange.forEach { xVelocity ->
        yVelocityRange.forEach { yVelocity ->
            simulatePath(xVelocity, yVelocity)?.let(::add)
        }
    }
}.toList()

private fun Target.simulatePath(xVelocity: Int, yVelocity: Int): Shot? {
    print("Testing shot $C_CYAN ($xVelocity,$yVelocity)$C_RESET: ")
    val path = mutableListOf<PathSegment>()
    var hasReachedTarget = false
    var x = 0
    var drag = 0
    var y = 0
    var gravity = 0
    while (!hasReachedTarget && y > yMin && x < xMax) {
        x += max(0, xVelocity - drag++)
        y += yVelocity - gravity++
        path.add(PathSegment(x, y))
        hasReachedTarget = x in xRange && y in yRange
    }
    return if (hasReachedTarget) {
        println("reached the target with the maximum height of $C_PURPLE${path.maxOf { it.y }}$C_RESET.")
        Shot(
            xVelocity = xVelocity,
            yVelocity = yVelocity,
            path = path
        )
    } else {
        println("did not reach the target.")
        null
    }
}

private fun Target.draw(path: List<PathSegment>) {
    var y = path.maxByOrNull { it.y }?.y ?: 0
    while (y > yMin) {
        var x = 0
        while (x < xMax) {
            if (x == 0 && y == 0) {
                print("${C_CYAN}S$C_RESET")
            } else {
                if (path.contains(PathSegment(x, y))) {
                    print("${C_YELLOW}#$C_RESET")
                } else {
                    if (x in xRange && y in yRange) print("${C_RED}T$C_RESET") else print(".")
                }
            }
            x++
        }
        println()
        y--
    }
}