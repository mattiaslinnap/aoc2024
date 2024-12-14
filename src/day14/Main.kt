package day14

import helpers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

val DAY = "day14"

data class Robot(var p: Pos, val v: Pos)

fun readInput(fname: String): List<Robot> {
    val regex = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
    return readNonEmptyLines(fname).map { line ->
        val match = regex.matchEntire(line)
        require(match != null)
        val (px, py, vx, vy) = match.destructured
        Robot(Pos(px.toInt(), py.toInt()), Pos(vx.toInt(), vy.toInt()))
    }
}

fun silver(fname: String): Int {
    val input = readInput(fname).toMutableList()
    val size = Pos(101, 103)
    for (i in 0..<100) {
        input.forEach {
            it.p = (it.p + it.v).wrappedInSize(size)
        }
    }
    val qCount = mutableListOf(0, 0, 0, 0)
    input.forEach {
        if (it.p.x < size.x / 2 && it.p.y < size.y / 2)
            qCount[0] += 1
        if (it.p.x > size.x / 2 && it.p.y < size.y / 2)
            qCount[1] += 1
        if (it.p.x < size.x / 2 && it.p.y > size.y / 2)
            qCount[2] += 1
        if (it.p.x > size.x / 2 && it.p.y > size.y / 2)
            qCount[3] += 1
    }
    //printMap(input, size)
    return qCount[0] * qCount[1] * qCount[2] * qCount[3]
}

fun gold(fname: String): Int {
    val input = readInput(fname).toMutableList()
    val size = Pos(101, 103)
    for (i in 1..<1000000000) {
        input.forEach {
            it.p = (it.p + it.v).wrappedInSize(size)
        }
        if ((i - 70) % 101 == 0 && (i - 19) % 103 == 0) {
            println("Iteration $i v")
            printMap(input, size)
            println("Iteration $i ^")
            println()
            Thread.sleep(1000)
        }
    }
    return 0
}

fun printMap(rs: List<Robot>, size: Pos) {
    val arr = mutableListOf<MutableList<Int>>()
    for (y in 0..<size.y) {
        val line = mutableListOf<Int>()
        for (x in 0..<size.x)
            line.add(0)
        arr.add(line)
    }
    rs.forEach { arr[it.p.y][it.p.x] += 1 }
    printTableNonZero(arr)
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day14Test {
    @Test
    fun testExampleSilver() {
        assertEquals(1, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(1, gold("$DAY/example1.txt"))
    }

    @Test
    fun testWrap() {
        assertEquals(Pos(0, 0), Pos(0, 0).wrappedInSize(Pos(3, 4)))
        assertEquals(Pos(0, 0), Pos(3, 4).wrappedInSize(Pos(3, 4)))
        assertEquals(Pos(2, 2), Pos(5, 6).wrappedInSize(Pos(3, 4)))
        assertEquals(Pos(2, 3), Pos(-1, -1).wrappedInSize(Pos(3, 4)))
    }
}