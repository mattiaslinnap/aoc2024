package day10

import helpers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val DAY = "day10"

fun trailHeads(m: List<List<Int>>, size: Pos, pos: Pos, level: Int): Set<Pos> {
    if (!pos.inSize(size))
        return emptySet()
    if (m[pos.y][pos.x] != level)
        return emptySet()
    if (level == 9)
        return setOf(pos)

    return emptySet<Pos>().union(trailHeads(m, size, pos + PosN, level + 1))
        .union(trailHeads(m, size, pos + PosE, level + 1))
        .union(trailHeads(m, size, pos + PosS, level + 1))
        .union(trailHeads(m, size, pos + PosW, level + 1))

}

fun startLocations(size: Pos): Sequence<Pos> {
    return size.allCoordinatesLessThan().filter {
        (it.x == 0 || it.x == size.x - 1) || (it.y == 0 || it.y == size.y - 1)
    }
}

fun silver(fname: String): Int {
    val input = readIntegerMatrixWithSingleDigits(fname)
    val size = Pos(input[0].size, input.size)
    val sum = startLocations(size).sumOf {
        val score = trailHeads(input, size, it, 0).size
//        println("${it} = ${score}")
        score
    }
    return sum
}

fun gold(fname: String): Int {
    val input = readIntegerMatrixWithSingleDigits(fname)

    return 33
}


fun main() {
    println(silver("$DAY/example1.txt"))
}

class Day10Test {
    @Test
    fun testExampleSilver() {
        assertEquals(36, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(1, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}