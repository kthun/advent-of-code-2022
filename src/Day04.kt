fun main() {

    fun String.createElfRanges() = split(",")
        .map { it.split("-") }
        .map { it[0].toInt()..it[1].toInt() }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val (elf1, elf2) = line.createElfRanges()
            elf1 in elf2 || elf2 in elf1
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val (elf1, elf2) = line.createElfRanges()
            elf1.first in elf2 || elf1.last in elf2 || elf2.first in elf1 || elf2.last in elf1
        }
    }

    val testInput = readInput("Day04_test")

    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

operator fun IntRange.contains(other: IntRange) = other.first in this && other.last in this
