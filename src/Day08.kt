enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

fun main() {

    fun part1(input: List<String>): Int {
        val trees = input.map { line -> line.toCharArray().map { it.toString().toInt() } }
        val height = trees.size
        val width = trees[0].size

        val numVisibleTrees = trees.mapIndexed { row, line ->
            line.mapIndexed { col, tree ->
                if ((0 until row).map { trees[it][col] }.none { otherTree -> otherTree >= tree }
                    || (row + 1 until height).map { trees[it][col] }.none { otherTree -> otherTree >= tree }
                    || (0 until col).map { trees[row][it] }.none { otherTree -> otherTree >= tree }
                    || (col + 1 until width).map { trees[row][it] }.none { otherTree -> otherTree >= tree }) {
                    1
                } else {
                    0
                }
            }
        }
            .flatten()
            .sum()

        return numVisibleTrees
    }

    fun treesSeen(trees: List<List<Int>>, tree: Int, row: Int, col: Int, gridHeight: Int, gridWidth: Int, direction: Direction): Int {
        var seen = 0
        when (direction) {
            Direction.NORTH -> {
                for (r in row - 1 downTo 0) {
                    seen++
                    if (trees[r][col] >= tree) break
                }
            }

            Direction.SOUTH -> {
                for (r in row + 1 until gridHeight) {
                    seen++
                    if (trees[r][col] >= tree) break
                }
            }

            Direction.EAST -> {
                for (c in col + 1 until gridWidth) {
                    seen++
                    if (trees[row][c] >= tree) break
                }
            }

            Direction.WEST -> {
                for (c in col - 1 downTo 0) {
                    seen++
                    if (trees[row][c] >= tree) break
                }
            }
        }

        return seen
    }

    fun scenicScore(trees: List<List<Int>>, tree: Int, row: Int, col: Int, gridHeight: Int, gridWidth: Int): Int {
        val treesSeenWest = treesSeen(trees, tree, row, col, gridHeight, gridWidth, Direction.WEST)
        val treesSeenEast = treesSeen(trees, tree, row, col, gridHeight, gridWidth, Direction.EAST)
        val treesSeenNorth = treesSeen(trees, tree, row, col, gridHeight, gridWidth, Direction.NORTH)
        val treesSeenSouth = treesSeen(trees, tree, row, col, gridHeight, gridWidth, Direction.SOUTH)

        return treesSeenSouth * treesSeenNorth * treesSeenEast * treesSeenWest
    }

    fun part2(input: List<String>): Int {
        val trees = input.map { line -> line.toCharArray().map { it.toString().toInt() } }
        val height = trees.size
        val width = trees[0].size

        val bestScenicScore = trees.mapIndexed { row, line ->
            line.mapIndexed { col, tree ->
                scenicScore(trees, tree, row, col, height, width)
                    .also {
                        if (col in 1 until width - 1 && row in 1 until height - 1) {
                            println("($col, $row) is $tree tall and has score $it")
                        }
                    }
            }
        }
            .flatten()
            .maxByOrNull { it } ?: 0

        return bestScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")


    check(part1(testInput) == 21)
    println(part1(input))

    check(part2(testInput) == 8)
    println(part2(input))
}
