fun main() {
    fun firstUniqueCharSequence(chars: CharArray, sequenceLength: Int): Int {
        val lastNchars = chars.take(sequenceLength).toMutableList()
        chars.foldIndexed(lastNchars) { index, acc, c ->
            acc.removeFirst()
            acc.add(c)
            if (acc.toSet().size == sequenceLength) {
                return index + 1
            }
            acc
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val chars = input[0].toCharArray()
        return firstUniqueCharSequence(chars, 4)
    }

    fun part2(input: List<String>): Int {
        val chars = input[0].toCharArray()
        return firstUniqueCharSequence(chars, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput) == 11)
    println(part1(input))

    check(part2(testInput) == 26)
    println(part2(input))
}
