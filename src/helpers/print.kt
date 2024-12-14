package helpers

fun <T> printTable(data: List<T>) {
    if (data.isNotEmpty()) {
        data.forEach { row ->
            val printable = when (row) {
                is String -> row
                is List<*> -> row.map { it.toString() }.joinToString("")
                else -> "$row"
            }
            println(printable)
        }
    } else {
        println("No table data.")
    }
}

fun printTableNonZero(data: List<List<Int>>) {
    if (data.isNotEmpty()) {
        data.forEach { row ->
            println(row.joinToString("") { if (it > 0) it.toString() else "." })
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