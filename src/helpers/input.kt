package helpers

import kotlin.io.path.Path
import kotlin.io.path.forEachLine


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
