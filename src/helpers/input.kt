package helpers

import kotlin.io.path.Path
import kotlin.io.path.forEachLine
import kotlin.io.path.readLines
import kotlin.io.path.readText


fun readIntegerMatrixWithSpaces(filePath: String): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    Path("src/$filePath").forEachLine { line ->
        if (line.isNotEmpty()) {
            val intList = line.split(" ")
                .mapNotNull { if (it.isNotEmpty()) it.toInt() else null }
            result.add(intList)
        }
    }
    return result
}

fun readIntegerMatrixWithSingleDigits(filePath: String): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    Path("src/$filePath").forEachLine { line ->
        if (line.isNotEmpty()) {
            val intList = line.split("")
                .mapNotNull { if (it.isNotEmpty()) it.toInt() else null }
            result.add(intList)
        }
    }
    return result
}

fun readNonEmptyLines(filePath: String): List<String> {
    return Path("src/$filePath").readLines().map { it.trim() }.filter { it.isNotEmpty() }
}

/**
 * Read lines, split into groups by empty lines.
 *
 * Empty lines in the start and end of the file are ignored. Multiple empty lines are treated as one.
 */
fun readLineGroupsSeparatedByBlankLine(filePath: String): List<List<String>> {
    val groups = mutableListOf<MutableList<String>>()
    var newGroup = true
    Path("src/$filePath").forEachLine { line ->
        val trimmed = line.trim()
        if (trimmed.isEmpty()) {
            newGroup = true
        } else {
            if (newGroup) {
                groups.add(mutableListOf())
                newGroup = false
            }
            groups.last().add(trimmed)
        }
    }
    return groups
}

fun readText(filePath: String): String {
    return Path("src/$filePath").readText()
}

fun readCharMatrix(filePath: String): Array<String> {
    val result = mutableListOf<String>()
    Path("src/$filePath").forEachLine { line ->
        val stripped = line.trim()
        if (stripped.isNotEmpty()) {
            result.add(stripped)
        }
    }
    return result.toTypedArray()
}