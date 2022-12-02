fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (opponent, self) = line.split(" ")
            val symbolValue = when (self) {
                "X" -> 1
                "Y" -> 2
                "Z" -> 3
                else -> throw IllegalArgumentException("Unknown symbol: $self")
            }
            val winBonus = when (opponent) {
                "A" -> when (self) {
                    "X" -> 3
                    "Y" -> 6
                    "Z" -> 0
                    else -> throw IllegalArgumentException("Unknown self: $self")
                }
                "B" -> when (self) {
                    "X" -> 0
                    "Y" -> 3
                    "Z" -> 6
                    else -> throw IllegalArgumentException("Unknown self: $self")
                }
                "C" -> when (self) {
                    "X" -> 6
                    "Y" -> 0
                    "Z" -> 3
                    else -> throw IllegalArgumentException("Unknown self: $self")
                }
                else -> throw IllegalArgumentException("Unknown opponent: $opponent")
            }
            symbolValue + winBonus
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (opponent, goal) = line.split(" ")
            val (ownSymbol, winBonus) = when (goal) {
                "X" -> when (opponent) {
                    "A" -> Pair("C", 0)
                    "B" -> Pair("A", 0)
                    "C" -> Pair("B", 0)
                    else -> throw IllegalArgumentException("Unknown opponent: $opponent")
                }
                "Y" -> when (opponent) {
                    "A" -> Pair("A", 3)
                    "B" -> Pair("B", 3)
                    "C" -> Pair("C", 3)
                    else -> throw IllegalArgumentException("Unknown opponent: $opponent")
                }
                "Z" -> when (opponent) {
                    "A" -> Pair("B", 6)
                    "B" -> Pair("C", 6)
                    "C" -> Pair("A", 6)
                    else -> throw IllegalArgumentException("Unknown opponent: $opponent")
                }
                else -> throw IllegalArgumentException("Unknown goal: $goal")
            }
            val symbolValue = when (ownSymbol) {
                "A" -> 1
                "B" -> 2
                "C" -> 3
                else -> throw IllegalArgumentException("Unknown symbol: $ownSymbol")
            }
            symbolValue + winBonus
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")

    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
