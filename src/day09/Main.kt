package day09

import helpers.readNonEmptyLines
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

val DAY = "day09"

data class FileBlock(val len: Int, val id: Int)

fun readInputBlocks(fname: String): List<FileBlock?> {
    val lines = readNonEmptyLines(fname)
    require(lines.size == 1)
    val line = lines[0]
    println("Num files/spaces: ${line.length}")
    println("Total blocks: ${line.sumOf { it.toString().toInt() }}")

    val blocks: MutableList<FileBlock?> = mutableListOf()
    line.forEachIndexed { index, lenChar ->
        val len = lenChar.toString().toInt()
        val isFile = index % 2 == 0
        if (isFile) {
            (0..<len).forEach { blocks.add(FileBlock(len, index / 2)) }
        } else {
            (0..<len).forEach { blocks.add(null) }
        }
    }
    return blocks
}

fun debugBlocks(bs: List<Int>): String {
    val maxFileId = bs.max()
    return if (maxFileId <= 9) {
        bs.joinToString("") { if (it < 0) "." else it.toString() }
    } else {
        // join with spaces for more readability of multi-digit IDs
        bs.joinToString(" ") { if (it < 0) "." else it.toString() }
    }
}

fun moveBlocks(bs: MutableList<FileBlock?>) {
    var leftPtr = 0
    var rightPtr = bs.size - 1

    while (true) {
        if (leftPtr >= rightPtr)
            break
        if (bs[leftPtr] != null) {
            leftPtr += 1
        } else if (bs[rightPtr] == null) {
            rightPtr -= 1
        } else {
            bs[leftPtr] = bs[rightPtr]
            bs[rightPtr] = null
        }
    }
}

fun checksumBlocks(bs: List<FileBlock?>): Long {
    return bs.indices.sumOf {
        it.toLong() * (bs[it]?.id?.toLong() ?: 0)
    }
}

fun silver(fname: String): Long {
    val blocks = readInputBlocks(fname)
//    println(debugBlocks(blockFileIds))
    val moved = blocks.toMutableList()
    moveBlocks(moved)
//    println(debugBlocks(moved))
    val sum = checksumBlocks(moved)
    return sum
}

data class File(val id: Int, val len: Int, var start: Int) : Comparable<File> {
    override fun compareTo(other: File): Int = compareValuesBy(this, other, { it.start })
}

data class Space(val len: Int, val start: Int) : Comparable<Space> {
    override fun compareTo(other: Space): Int = compareValuesBy(this, other, { it.start })
}

fun readInputFilesAndSpaces(fname: String): Pair<MutableList<File>, MutableList<Space>> {
    val lines = readNonEmptyLines(fname)
    require(lines.size == 1)
    val line = lines[0]
    println("Num files/spaces: ${line.length}")
    println("Total blocks: ${line.sumOf { it.toString().toInt() }}")

    val files: MutableList<File> = mutableListOf()
    val spaces: MutableList<Space> = mutableListOf()
    var start = 0
    line.forEachIndexed { index, lenChar ->
        val len = lenChar.toString().toInt()
        val isFile = index % 2 == 0
        if (isFile) {
            files.add(File(index / 2, len, start))
        } else {
            spaces.add(Space(len, start))
        }
        start += len
    }
    return Pair(files, spaces)
}

fun debugFiles(files: List<File>): String {
    var start = 0
    val chars: MutableList<String> = mutableListOf()
    files.sorted().forEachIndexed { idx, file ->
        (start..<file.start).forEach { chars.add(".") }
        (0..<file.len).forEach { chars.add(file.id.toString()) }
        start = file.start + file.len
    }
    if (files.size <= 9) {
        return chars.joinToString("")
    } else {
        // join with spaces for more readability of multi-digit IDs
        return chars.joinToString(" ")
    }
}

fun debugSpaces(spaces: List<PriorityQueue<Space>>): String {
    val allSpaces = spaces.flatten().sorted()
    var start = 0
    val chars: MutableList<String> = mutableListOf()
    allSpaces.forEach { s ->
        (start..<s.start).forEach { chars.add("-") }
        (0..<s.len).forEach { chars.add(".") }
        start = s.start + s.len
    }
    return chars.joinToString("")
}

fun checksumFiles(files: List<File>): ULong {
    var sum = 0UL
    files.forEach { file ->
        (file.start..<file.start + file.len).forEach { blockIdx ->
            sum += blockIdx.toULong() * file.id.toULong()
        }
    }
    return sum
}

fun gold(fname: String): ULong {
    val (files, spaces) = readInputFilesAndSpaces(fname)
//    println(debugFiles(files))

    val spacesBySize: MutableList<PriorityQueue<Space>> = mutableListOf()
    spacesBySize.add(PriorityQueue()) // length 0 has no spaces
    (1..9).forEach { size ->
        spacesBySize.add(PriorityQueue(spaces.filter { it.len == size }))
    }
//    println(debugSpaces(spacesBySize))
//    (1..9).forEach {
//        val space = spacesBySize[it]
//        println("Size ${it}: ${space.joinToString()}")
//    }
//    println()
    files.reverse()
    files.forEach { file ->
        var firstSpace: Space? = null
        for (spaceSize in file.len..9) {
            val emptySpace = spacesBySize[spaceSize].peek()
            if (emptySpace != null && emptySpace.start < file.start) {
                if (firstSpace == null || emptySpace.start < firstSpace.start) {
                    firstSpace = emptySpace
                }
            }
        }
        if (firstSpace != null) {
            spacesBySize[firstSpace.len].poll() // Remove existing
            file.start = firstSpace.start
            val remainingLen = firstSpace.len - file.len
            val remainingStart = firstSpace.start + file.len
            if (remainingLen > 0)
                spacesBySize[remainingLen].add(Space(remainingLen, remainingStart))
//            println("Used space ${firstSpace} for file ${file}")
        }
//        println(debugFiles(files))
//        println(debugSpaces(spacesBySize))
//        (1..9).forEach {
//            val space = spacesBySize[it]
//            println("Size ${it}: ${space.joinToString()}")
//        }
//        println()
    }
//    println(debugFiles(files))
//    println(debugSpaces(spacesBySize))
    return checksumFiles(files)
}


fun main() {
    println(gold("$DAY/input.txt"))
}

class Day9Test {
    @Test
    fun testExampleSilver() {
        assertEquals(1928L, silver("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold1() {
        assertEquals(2858UL, gold("$DAY/example1.txt"))
    }

    @Test
    fun testExampleGold2() {
        assertEquals(4UL, gold("$DAY/example2.txt"))
    }

    @Test
    fun testFoo() {
        assertEquals(1, 1)
    }
}