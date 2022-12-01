fun main() {
    fun createElves(input: List<String>): MutableList<List<Int>> {
        val elvesStrings = mutableListOf<List<Int>>()

        var thisElfStrings = mutableListOf<Int>()
        for (s in input) {
            if (s == "") {
                elvesStrings.add(thisElfStrings)
                thisElfStrings = mutableListOf()
            } else {
                thisElfStrings.add(s.toInt())
            }
        }
        elvesStrings.add(thisElfStrings)
        return elvesStrings
    }

    fun part1(input: List<String>): Int {
        val elvesStrings = createElves(input)
        return elvesStrings.maxOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        val elvesStrings = createElves(input)

        return elvesStrings.sortedByDescending { it.sum() }
            .take(3)
            .sumOf { it.sum() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")

    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
