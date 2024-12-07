package day06

import helpers.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day06"

data class GuardState(var arr: MutableList<MutableList<Int>>, val size: Pos, var pos: Pos, var dir: PosDirection4)

fun copyState(s: GuardState): GuardState {
    val arr = s.arr.map { line ->
        line.map { it }.toMutableList()
    }.toMutableList()
    return GuardState(arr, s.size, s.pos, s.dir)
}

/**
 * -1 - blocked
 * 0 - unvisited
 * >0 - bitmask 1/2/4/8 visited going N, E, S, W
 */
fun initialState(lines: List<String>): GuardState {
    val size = Pos(lines[0].length, lines.size)
    var pos: Pos? = null
    var dir: PosDirection4? = null
    val arr = lines.mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            when (char) {
                '.' -> 0
                '#' -> -1
                '^' -> {
                    pos = Pos(x, y)
                    dir = PosDirection4.N
                    0
                }
                '>' -> {
                    pos = Pos(x, y)
                    dir = PosDirection4.E
                    0
                }
                'v' -> {
                    pos = Pos(x, y)
                    dir = PosDirection4.S
                    0
                }
                '<' -> {
                    pos = Pos(x, y)
                    dir = PosDirection4.W
                    0
                }
                else -> {
                    // Should never happen
                    require(false)
                    0
                }
            }
        }.toMutableList()
    }.toMutableList()
    requireNotNull(pos)
    return GuardState(arr, size, pos!!, dir!!)
}

fun dirToChar(d: PosDirection4): Char {
    return when(d) {
        PosDirection4.N -> '^'
        PosDirection4.E -> '>'
        PosDirection4.S -> 'v'
        PosDirection4.W -> '<'
    }
}

fun dirToArrayMask(d: PosDirection4): Int {
    return when(d) {
        PosDirection4.N -> 1
        PosDirection4.E -> 2
        PosDirection4.S -> 4
        PosDirection4.W -> 8
    }
}

fun rightTurn(d: PosDirection4): PosDirection4 {
    return when(d) {
        PosDirection4.N -> PosDirection4.E
        PosDirection4.E -> PosDirection4.S
        PosDirection4.S -> PosDirection4.W
        PosDirection4.W -> PosDirection4.N
    }
}

fun printState(s: GuardState) {
    s.arr.forEachIndexed { y, line ->
        println(line.mapIndexed { x, elem ->
            if (s.pos.x == x && s.pos.y == y) {
                dirToChar(s.dir)
            } else {
                when (elem) {
                    -1 -> '#'
                    0 -> '.'
                    in 1..15 -> 'X'
                    else -> '?'
                }
            }
        }.joinToString(""))
    }
    println("Pos: ${s.pos.x}, ${s.pos.y}")
    println("Dir: ${s.dir}")
}

fun updateVisitState(s: GuardState) {
    val before = s.arr[s.pos.y][s.pos.x]
    require(before >= 0)
    s.arr[s.pos.y][s.pos.x] = (before or dirToArrayMask(s.dir))
}

fun alreadyVisited(s: GuardState): Boolean {
    val visits = s.arr[s.pos.y][s.pos.x]
    require(visits >= 0)
    return (visits and dirToArrayMask(s.dir)) > 0
}

/**
 * Returns true if guard leaves area
 */
fun moveGuard(s: GuardState): Boolean {
    val nextPos = s.pos + s.dir.dir
    if (nextPos.nonNegative() && nextPos.less(s.size)) {
        // Next location is inside the area
        if (s.arr[nextPos.y][nextPos.x] == -1) {
            // Obstructed, turn instead
            s.dir = rightTurn(s.dir)
        } else {
            // Continue in straight line
            s.pos = nextPos
        }
        return false
    } else {
        return true
    }
}

fun readInput(fname: String): GuardState {
    val lines = readNonEmptyLines(fname)
    return initialState(lines)
}

/**
 * Returns number of cells visited and true if guard leaves the area
 */
fun runUntilCompletion(s: GuardState): Pair<Int, Boolean> {
    var outside = false
    while (true) {
        updateVisitState(s)
        outside = moveGuard(s)
        if (outside) {
            println("Guard left area")
            break
        }
        if (alreadyVisited(s)) {
            println("Guard looped around")
            break
        }
    }

    val visited = s.arr.sumOf { line ->
        line.count { elem ->
            elem > 0
        }
    }
    return Pair(visited, outside)
}

fun silver(fname: String): Int {
    val state = readInput(fname)
    val (visited, outside) = runUntilCompletion(state)

    printState(state)
    println("visited=$visited, outside=$outside")
    return visited
}

fun gold(fname: String): Int {
    val initial = readInput(fname)
    var goodLocs = 0
    for (loc in initial.size.allCoordinatesLessThan()) {
        if (loc != initial.pos && initial.arr[loc.y][loc.x] == 0) {
            val state = copyState(initial)
            state.arr[loc.y][loc.x] = -1
            val (visited, outside) = runUntilCompletion(state)
            if (!outside) {
                println("Can add # at ${loc.x}, ${loc.y}")
                goodLocs += 1
            }
        }
    }
    return goodLocs
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day6Test {
    @Test
    fun testExampleSilver() {
        assertEquals(41, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(6, gold("$DAY/example1.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}