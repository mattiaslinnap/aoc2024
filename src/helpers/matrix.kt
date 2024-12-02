package helpers


/**
 *  Supports uneven columns in rows
 */
fun <T> transpose(matrix: List<List<T>>): List<List<T>> {
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