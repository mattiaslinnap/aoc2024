package helpers

fun <T> printTable(data: List<T>) {
    if (data.isNotEmpty()) {
        data.forEach { row ->
            val printable = when (row) {
                is String -> row
                is List<*> -> row.joinToString("")
                else -> "$row"
            }
            println(printable)
        }
    } else {
        println("No table data.")
    }
}

fun <T> printTable(data: Array<T>) {
    if (data.isNotEmpty()) {
        data.forEach { row ->
            println("$row")
        }
    } else {
        println("No table data.")
    }
}