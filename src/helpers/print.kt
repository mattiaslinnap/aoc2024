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