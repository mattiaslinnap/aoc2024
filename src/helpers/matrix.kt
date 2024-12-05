package helpers


/**
 *  Supports uneven columns in rows
 */
fun <T> transposeJagged(matrix: List<List<T>>): List<List<T>> {
    val maxInputColumns = matrix.maxOfOrNull { it.size }
    if (maxInputColumns == null)
        return emptyList()

    // Columns are now rows
    return List(maxInputColumns) { inputColumnIndex ->
        matrix.mapNotNull { inputRow ->
            // Take inputColumnIndex element from inputRow
            if (inputColumnIndex < inputRow.size)
                inputRow[inputColumnIndex]
            else
                null
        }
    }
}

fun flippedHorizontal(matrix: Array<String>): Array<String> {
    return matrix.map { it.reversed() }.toTypedArray()
}

fun flippedVertical(matrix: Array<String>): Array<String> {
    return matrix.reversed().toTypedArray()
}

fun transpose(matrix: Array<String>): Array<String> {
    val maxInputColumns = matrix.maxOfOrNull { it.length }
    if (maxInputColumns == null)
        return emptyArray()

    // Columns are now rows
    return Array(maxInputColumns) { inputColumnIndex ->
        matrix.mapNotNull { inputRow ->
            // Take inputColumnIndex element from inputRow
            if (inputColumnIndex < inputRow.length)
                inputRow[inputColumnIndex]
            else
                null
        }.joinToString("")
    }
}

fun containSubMatrixAt(big: Array<String>, small: Array<String>, pos: Pos, ignoreSmall: Char = '.'): Boolean {
    val bigSize = Pos(big[0].length, big.size)
    val smallSize = Pos(small[0].length, small.size)

    for (y in 0..< smallSize.y) {
        for (x in 0 ..< smallSize.x) {
            val smallPos = Pos(x, y)
            val smallChar = small[smallPos.y][smallPos.x]

            val bigPos = pos + smallPos
            if (!bigPos.less(bigSize))
                return false
            val bigChar = big[bigPos.y][bigPos.x]
            if (smallChar != ignoreSmall && smallChar != bigChar)
                return false
        }
    }
    return true
}