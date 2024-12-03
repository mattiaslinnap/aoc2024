package day03

import helpers.printTable
import helpers.readIntegerMatrix

import helpers.readText
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.sign

val DAY = "day03"

data class Op(val op: String, val a: Int, val b: Int)

fun silver(fname: String): Int {
    val input = readText(fname)
    val muls = findOps(input)
    val total = muls.map { it.a * it.b }.sum()
    return total
}

fun findOps(line: String): List<Op> {
    val digitPattern = "([1-9][0-9]{0,3})"
    val mulPattern = "(mul)\\($digitPattern,$digitPattern\\)"
    val doPattern = "(do)\\(\\)"
    val dontPattern = "(don\'t)\\(\\)"
    val pattern = Regex("(do|don't|mul)\\((?:$digitPattern,$digitPattern)?\\)")

    return pattern.findAll(line).map {
        val op = it.groupValues[1].replace("'", "")
        if (op == "mul") {
            Op(op, it.groupValues[2].toInt(), it.groupValues[3].toInt())
        } else {
            Op(op, 0, 0)
        }
    }.toList()
}


fun gold(fname: String): Int {
    val input = readText(fname)
    val ops = findOps(input)
    var enabled = true
    var sum = 0
    ops.forEach {
        if (it.op == "mul" && enabled) {
            sum += it.a * it.b
        } else if (it.op == "do") {
            enabled = true
        } else if (it.op == "dont") {
            enabled = false
        }
    }
    return sum
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day3Test {
    @Test
    fun testExampleSilver() {
        assertEquals(161, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold() {
        assertEquals(48, gold("$DAY/example2.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}