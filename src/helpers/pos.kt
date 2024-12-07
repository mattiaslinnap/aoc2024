package helpers

data class Pos(val x: Int, val y: Int)

operator fun Pos.plus(other: Pos): Pos { return Pos(x + other.x, y + other.y) }
operator fun Pos.minus(other: Pos): Pos { return Pos(x - other.x, y - other.y) }
//operator fun Pos.plusAssign(other: Pos): Unit {
//    x += other.x;
//    y += other.y;
//}
//operator fun Pos.minusAssign(other: Pos): Unit {
//    x -= other.x;
//    y -= other.y;
//}

fun Pos.nonNegative(): Boolean = x >= 0 && y >= 0
fun Pos.positive(): Boolean = x > 0 && y >= 0
fun Pos.less(other: Pos): Boolean = x < other.x && y < other.y
fun Pos.lessOrEqual(other: Pos): Boolean = x <= other.x && y < other.y

// 0,0 at top left
// X is ->
// Y is v
val PosZ = Pos(0, 0)
val PosN = Pos(0, -1)
val PosNE = Pos(1, -1)
val PosE = Pos(1, 0)
val PosSE = Pos(1, 1)
val PosS = Pos(0, 1)
val PosSW = Pos(-1, 1)
val PosW = Pos(-1, 0)
val PosNW = Pos(-1, -1)

val PosDirections4 = arrayOf(PosN, PosE, PosS, PosW)
val PosDirections4Diagonal = arrayOf(PosNE, PosSE, PosSW, PosNW)
val PosDirections8 = arrayOf(PosN, PosNE, PosE, PosSE, PosS, PosSW, PosW, PosNW)

enum class PosDirection4(val dir: Pos) {
    N(PosN),
    E(PosE),
    S(PosS),
    W(PosW),
}

// Generates all Positions (0, 0) ... (x - 1, y - 1). y is the outer loop
fun Pos.allCoordinatesLessThan(): Sequence<Pos> {
    return sequence {
        for (sy in 0..< y) {
            for (sx in 0 ..< x) {
                yield(Pos(sx, sy))
            }
        }
    }
}