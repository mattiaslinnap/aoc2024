package day04

import helpers.*

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day04"

fun matrixWordMatches(m: Array<String>, word: String, start: Pos, direction: Pos): Boolean {
    val size = Pos(m[0].length, m.size)
    var pos = start
    for (char in word) {
        if (pos.nonNegative() && pos.less(size)) {
            if (m[pos.y][pos.x] == char) {
                pos += direction
            } else {
                return false
            }
        } else {
            return false
        }
    }
    return true
}

fun countMatrixWords(m: Array<String>, word: String, direction: Pos): Int {
    val size = Pos(m[0].length, m.size)
    var found = 0
    for (y in 0 ..< size.y) {
        for (x in 0..< size.x) {
            if (matrixWordMatches(m, word, Pos(x, y), direction)) {
                found += 1
            }
        }
    }
    return found
}


fun silver(fname: String): Int {
    val input = readCharMatrix(fname)
    val silverWord = "XMAS"
    val silverDirections = PosDirections8
    return silverDirections.sumOf { countMatrixWords(input, silverWord, it) }
}

val patterns = arrayOf(
    arrayOf("M.S",
            ".A.",
            "M.S"),
    arrayOf("M.M",
            ".A.",
            "S.S"),
    arrayOf("S.M",
            ".A.",
            "S.M"),
    arrayOf("S.S",
            ".A.",
            "M.M"),
)

fun gold(fname: String): Int {
    val input = readCharMatrix(fname)
    val size = Pos(input[0].length, input.size)

    var count = 0
    patterns.forEach { pattern ->
        size.allCoordinatesLessThan().forEach { start ->
            if (containSubMatrixAt(input, pattern, start)) {
                count += 1
            }
        }
    }
    return count
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day4Test {
    @Test
    fun testExampleSilver() {
        assertEquals(18, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(9, gold("$DAY/example1.txt"))
    }

    @Test
    fun testMatrixWordMatches() {
        val m = arrayOf("XMAS", "M...", "A...", "S...")
        assertEquals(true, matrixWordMatches(m, "XMAS", Pos(0,0), PosE))
        assertEquals(true, matrixWordMatches(m, "XMAS", Pos(0,0), PosS))
//        assertEquals(1, countWords("XMAS", listOf("XMAS")))
//        assertEquals(1, countWords("XXMAS", listOf("XMAS")))
//        assertEquals(1, countWords("XMASX", listOf("XMAS")))
//        assertEquals(2, countWords("XMASXMAS", listOf("XMAS")))
    }
}