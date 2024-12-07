package day05

import helpers.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day05"

fun readInput(fname: String): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
    val lines = readLineGroupsSeparatedByBlankLine(fname)
    require(lines.size == 2)

    val after = mutableMapOf<Int, MutableSet<Int>>()
    lines[0].forEach { line ->
        val (a, b) = line.split("|").map { it.toInt() }
        if (a !in after) {
            after[a] = mutableSetOf()
        }
        after[a]!!.add(b)
    }

    val pages = lines[1].map { line ->
        line.split(",").map { it.toInt() }
    }

    return Pair(after, pages)
}

fun middleNumber(xs: List<Int>): Int {
    return xs[xs.size / 2]
}

fun orderingOk(after: Map<Int, Set<Int>>, xs: List<Int>): Boolean {
    for (i in xs.indices) {
        for (j in i + 1 ..< xs.size) {
            val a = xs[i]
            val b = xs[j]
            if (after[b]?.contains(a) == true) {
                return false
            }
        }
    }
    return true
}

fun silver(fname: String): Int {
    val (after, pages) = readInput(fname)
    var middle = 0
    pages.forEach {
        if (orderingOk(after, it)) {
            middle += middleNumber(it)
        }
    }

    return middle
}

fun pageComparator(after: Map<Int, Set<Int>>) = Comparator<Int> { a, b ->
    // -1: a < b
    // 0: any
    // 1: b < a
    when {
        after[a]?.contains(b) == true -> -1
        after[b]?.contains(a) == true -> 1
        else -> 0
    }
}


fun gold(fname: String): Int {
    val (after, pages) = readInput(fname)
    val badPages = pages.filter { !orderingOk(after, it) }
    var middle = 0
    badPages.forEach {
        middle += middleNumber(it.sortedWith(pageComparator(after)))
    }
    return middle
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day5Test {
    @Test
    fun testExampleSilver() {
        assertEquals(143, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(123, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}