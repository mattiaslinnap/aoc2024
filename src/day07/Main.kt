package day07

import helpers.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day07"

data class Line(val expected: Long, val elem: List<Long>)

fun readInput(fname: String): List<Line> {
    val lines = readNonEmptyLines(fname).map { line ->
        val parts = line.split(": ")
        require(parts.size == 2)
        val elems = parts[1].split(" ").map { it.toLong() }
        Line(parts[0].toLong(), elems)
    }
    println("Max expected: " + lines.maxOf { it.expected })
    println("Min element: " + lines.minOf { it.elem.min() })
    println("Max element: " + lines.maxOf { it.elem.max() })
    println("Max num elements: " + lines.maxOf { it.elem.size })
    return lines
}

fun canMatchExpectedSilver(expected: Long, elem: List<Long>, before: Long, index: Int): Boolean {
    if (index == elem.size) {
        return expected == before
    }
    if (index == 0) {
        return canMatchExpectedSilver(expected, elem, elem[0], 1)
    }
    return canMatchExpectedSilver(expected, elem, before + elem[index], index + 1) ||
            canMatchExpectedSilver(expected, elem, before * elem[index], index + 1)
}

fun silver(fname: String): Long {
    val lines = readInput(fname)
    return lines.sumOf {
        if (canMatchExpectedSilver(it.expected, it.elem, 0, 0)) {
            it.expected
        } else {
            0
        }
    }
}

fun canMatchExpectedGold(expected: Long, elem: List<Long>, before: Long, index: Int): Boolean {
    if (index == elem.size) {
        return expected == before
    }
    if (index == 0) {
        return canMatchExpectedGold(expected, elem, elem[0], 1)
    }
    return canMatchExpectedGold(expected, elem, before + elem[index], index + 1) ||
            canMatchExpectedGold(expected, elem, before * elem[index], index + 1) ||
            canMatchExpectedGold(expected, elem, (before.toString() + elem[index].toString()).toLong(), index + 1)
}

fun gold(fname: String): Long {
    val lines = readInput(fname)
    return lines.sumOf {
        if (canMatchExpectedGold(it.expected, it.elem, 0, 0)) {
            println(it)
            it.expected
        } else {
            0
        }
    }
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day7Test {
    @Test
    fun testExampleSilver() {
        assertEquals(3749L, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(11387L, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}