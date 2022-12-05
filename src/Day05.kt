import java.util.*

fun createStartingStacks(lineIterator: Iterator<String>): MutableMap<Int, Deque<Char>> {
    val stacks = mutableMapOf<Int, Deque<Char>>()
    var numStacks = 0
    for (line in lineIterator) {
        if (line[1] == '1') {
            numStacks = line.trim().split("\\s+".toRegex()).last().toInt()
            break
        }
        val chunks = line.chunked(4)
        for ((index, chunk) in chunks.withIndex()) {
            val crate = chunk[1]
            if (crate != ' ') {
                stacks.putIfAbsent(index, ArrayDeque())
                stacks[index]?.addFirst(crate)
            }
        }
    }
    for (i in 0 until numStacks) {
        stacks.putIfAbsent(i, ArrayDeque())
    }
    lineIterator.next()

    return stacks
}

data class Instruction(val quantity: Int, val from: Int, val to: Int)

fun createInstructions(lineIterator: Iterator<String>): List<Instruction> {
    val instructions = mutableListOf<Instruction>()
    lineIterator.forEach { line ->
        val splitInstruction = line.split(" ")
        val quantity = splitInstruction[1].toInt()
        val from = splitInstruction[3].toInt()
        val to = splitInstruction[5].toInt()
        instructions.add(Instruction(quantity, from, to))
    }
    return instructions
}


fun main() {

    fun part1(input: List<String>): String {
        val lineIterator = input.iterator()
        val stacks = createStartingStacks(lineIterator)
//        stacks.print()

        val instructions = createInstructions(lineIterator)
        instructions.forEach { instruction ->
            stacks.move(instruction.quantity, instruction.from - 1, instruction.to - 1)
//            stacks.print()
        }

        return (0 until stacks.size).map { stacks[it]?.pollLast() ?: " " }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val lineIterator = input.iterator()
        val stacks = createStartingStacks(lineIterator)
//        stacks.print()

        val instructions = createInstructions(lineIterator)
        instructions.forEach { instruction ->
            stacks.multiMove(instruction.quantity, instruction.from - 1, instruction.to - 1)
//            stacks.print()
        }

        return (0 until stacks.size).map { stacks[it]?.pollLast() ?: " " }.joinToString("")
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")

    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun Map<Int, Deque<Char>>.move(quantity: Int, from: Int, to: Int) {
    val fromStack = this.getOrElse(from) { throw IllegalArgumentException("No stack at $from") }
    val toStack = this.getOrElse(to) { throw IllegalArgumentException("No stack at $to") }
    repeat(quantity) {
        toStack.offerLast(fromStack.pollLast())
    }
}

private fun Map<Int, Deque<Char>>.multiMove(quantity: Int, from: Int, to: Int) {
    val fromStack = this.getOrElse(from) { throw IllegalArgumentException("No stack at $from") }
    val toStack = this.getOrElse(to) { throw IllegalArgumentException("No stack at $to") }
    val movingCrates = (0 until quantity).map { fromStack.pollLast() }.toMutableList()

    for (i in movingCrates.indices.reversed()) {
        toStack.offerLast(movingCrates[i])
    }
}

private fun Map<Int, Deque<Char>>.print() {
    val maxStackSize = this.values.maxOfOrNull { it.size } ?: 0
    for (i in maxStackSize - 1 downTo 0) {
        for (j in 0 until this.size) {
            val stack = this[j] ?: continue
            print("[${stack.elementAtOrNull(i) ?: " "}]" + " ")
        }
        println()
    }
    println()
}
