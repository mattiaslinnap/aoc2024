package day02

import helpers.readIntegerMatrixWithSpaces
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day02"

fun isSafe(xs: List<Int>): Boolean {
    val diffs = xs.zipWithNext { a, b -> b - a }
    val signs = diffs.map { it.sign }
    val abs = diffs.map { it.absoluteValue }

    if (signs.all { it == -1 } || signs.all { it == 1 }) {
        if (abs.all { it in 1..3 }) {
            return true
        }
    }
    return false
}

fun silver(fname: String): Int {
    val input = readIntegerMatrixWithSpaces(fname)
    return input.count { isSafe(it) }
}

fun gold(fname: String): Int {
    val input = readIntegerMatrixWithSpaces(fname)
    var safe = 0
    input.forEach { row ->
        val safeOriginal = isSafe(row)
        if (safeOriginal) {
            safe++
        } else {
            val safeRemoved = row.indices.any { n ->
                val without = removeNth(row, n)
                isSafe(without)
            }
            if (safeRemoved) {
                safe++
            }
        }
    }

    return safe
}

fun removeNth(xs: List<Int>, n: Int): List<Int> {
    return xs.filterIndexed { index, i -> index != n }
}

fun main() {
    println(gold("$DAY/input.txt"))
}

class Day2Test {
    @Test
    fun testExampleSilver() {
        assertEquals(2, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(4, gold("$DAY/example1.txt"))
    }

    @Test
    fun testRemoveNth() {
        assertEquals(listOf(2, 3), removeNth(listOf(1, 2, 3), 0))
        assertEquals(listOf(1, 3), removeNth(listOf(1, 2, 3), 1))
        assertEquals(listOf(1, 2), removeNth(listOf(1, 2, 3), 2))
    }
}