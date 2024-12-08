package helpers

fun <T> allPairs(lst: Iterable<T>): Sequence<Pair<T, T>> {
    val seen = mutableListOf<T>()
    return sequence {
        lst.forEach { elem ->
            seen.forEach { prev ->
                yield(Pair(prev, elem))
            }
            seen.add(elem)
        }
    }
}