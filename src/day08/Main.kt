package day08

import helpers.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day08"

fun positionsByLetter(arr: List<String>): Map<Char, List<Pos>> {
    val map = mutableMapOf<Char, MutableList<Pos>>()
    arr.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c != '.') {
                if (c !in map) {
                    map[c] = mutableListOf()
                }
                map[c]!!.add(Pos(x, y))
            }
        }
    }
    return map
}

fun node(a: Pos, b: Pos, scale: Int): Pos {
    return a + (b - a) * scale
}

fun silver(fname: String): Int {
    val input = readNonEmptyLines(fname)
    val size = Pos(input[0].length, input.size)
    val charPos = positionsByLetter(input)
    val unique = mutableSetOf<Pos>()
    charPos.values.forEach { positions ->
        allPairs(positions).forEach { (a, b) ->
            val node1 = a * 2 - b
            if (node1.inSize(size)) {
                unique.add(node1)
            }
            val node2 = b * 2 - a
            if (node2.inSize(size)) {
                unique.add(node2)
            }
        }
    }
    return unique.size
}

fun gold(fname: String): Int {
    val input = readNonEmptyLines(fname)
    val size = Pos(input[0].length, input.size)
    val charPos = positionsByLetter(input)
    val unique = mutableSetOf<Pos>()
    charPos.values.forEach { positions ->
        allPairs(positions).forEach { (a, b) ->
            for (scale in -51 .. 51) {
                val node = node(a, b, scale)
                if (node.inSize(size)) {
                    unique.add(node)
                }
            }
        }
    }
    return unique.size
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day7Test {
    @Test
    fun testExampleSilver() {
        assertEquals(14, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(34, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}