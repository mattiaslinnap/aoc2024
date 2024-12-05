package helpers

fun <T> printTable(data: List<List<T>>) {
    if (data.isNotEmpty()) {
        data.forEach { row ->
            println(row.joinToString("\t"))
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