package day01

import helpers.readIntegerMatrix
import helpers.transpose
import kotlin.math.abs


fun main() {
    val input = transpose(readIntegerMatrix("day01/input1.txt"))
    val sorted = input.map { it.sorted() }

    var diff = 0
    sorted[0].zip(sorted[1]).forEach { (a, b) ->
        diff += abs(a - b)
    }
    println(diff)

    var sim = 0
    sorted[0].forEach { a ->
        var rightCount = sorted[1].count { it == a }
        sim += a * rightCount
    }
    println(sim)

}
