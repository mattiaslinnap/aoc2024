package helpers

import kotlin.io.path.Path
import kotlin.io.path.forEachLine
import kotlin.io.path.readLines
import kotlin.io.path.readText


fun readIntegerMatrix(filePath: String): List<List<Int>> {
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

fun readLines(filePath: String): List<String> {
    return Path("src/$filePath").readLines()
}

fun readText(filePath: String): String {
    return Path("src/$filePath").readText()
}
