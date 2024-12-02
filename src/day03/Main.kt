package day03

import helpers.printTable
import helpers.readIntegerMatrix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day03"

fun silver(fname: String): Int {
    val input = readIntegerMatrix(fname)
    return 0
}

fun gold(fname: String): Int {
    val input = readIntegerMatrix(fname)
    return 0
}


fun main() {
    println(silver("$DAY/example1.txt"))
}

class Day3Test {
    @Test
    fun testExampleSilver() {
        assertEquals(111, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(222, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}