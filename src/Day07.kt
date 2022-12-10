class Folder(
    val name: String,
    val subFolders: MutableMap<String, Folder> = mutableMapOf(),
    var parent: Folder? = null,
    var myFiles: MutableList<MyFile> = mutableListOf()
) {
    fun addSubFolder(folder: Folder) {
        subFolders[folder.name] = folder
        folder.parent = this
        folders[folder.fullPath()] = folder
    }
    fun size(): Int {
        return myFiles.sumOf { it.size } + subFolders.values.sumOf { it.size() }
    }
    fun fullPath(): String {
        return if (parent == null) {
            name
        } else {
            parent!!.fullPath() + "/" + name
        }
    }

    fun toString(indent: Int = 0): String {
        val sb = StringBuilder()
        sb.append(" ".repeat(indent))
        sb.append("- $name (dir, size=${size()})")
        sb.appendLine()
        for (folder in subFolders.values.sortedBy { it.name }) {
            sb.append(folder.toString(indent + 2))
        }
        for (file in myFiles.sortedBy { it.name }) {
            sb.append(" ".repeat(indent + 2))
            sb.append("- ${file.name} (file, size=${file.size})")
            sb.appendLine()
        }
        return sb.toString()
    }

    override fun toString() = toString(0)
}

var folders = mutableMapOf<String, Folder>()

data class MyFile(val name: String, val size: Int)

fun main() {

    fun parseFileStructure(input: List<String>): Folder {
        folders.clear()
        val root = Folder("/")
        folders["/"] = root
        var currentDir = root

        input.forEach { line ->
            when {
                line == "$ ls" -> {} // Skip
                line.startsWith("dir") -> {} // Skip
                line.startsWith("$ cd /") -> currentDir = root
                line.startsWith("$ cd ..") -> currentDir = currentDir.parent!!
                line.startsWith("$ cd") -> {
                    val name = line.substringAfterLast(" ")
                    val folder = Folder(name)
                    currentDir.addSubFolder(folder)
                    currentDir = folder
                }

                else -> {
                    val (fileSizeString, fileName) = line.split(" ")
                    currentDir.myFiles.add(MyFile(fileName, fileSizeString.toInt()))
                }
            }
        }
        return root
    }

    fun part1(input: List<String>): Int {
        val root = parseFileStructure(input)
//        println(root)

        val maxFolderSize = 100_000
        return folders.values
            .filter { it.size() <= maxFolderSize }
            .sumOf { it.size() }
    }

    fun part2(input: List<String>): Int {
        val root = parseFileStructure(input)
//        println(root)

        val diskSpace = 70_000_000
        val freeSpace = diskSpace - root.size()
        val spaceNeeded = 30_000_000 - freeSpace

        return folders.map { it.value.size() }
            .filter { it >= spaceNeeded }
            .minByOrNull { it }
            ?: 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")


    check(part1(testInput) == 95437)
    println(part1(input))

    check(part2(testInput) == 24933642)
    println(part2(input))
}
