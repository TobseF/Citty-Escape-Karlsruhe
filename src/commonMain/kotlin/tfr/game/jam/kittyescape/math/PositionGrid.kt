package tfr.game.jam.kittyescape.math

import com.soywiz.korma.geom.IPoint
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.minus

/**
 * Creates a grid and matches screen coordinates with it.
 */
class PositionGrid(val x: Int = 0, val y: Int = 0, val columns: Int, val rows: Int, val tileSize: Int) {

    /**
     * @param column position of the grid
     * @param row position of the grid
     * @return The [Point] wit screen coordinates
     */
    fun getPosition(column: Int, row: Int): Point {
        return Point(x + (column * tileSize), y + (row * tileSize))
    }

    /**
     * @param column position of the grid
     * @param row position of the grid
     * @return The [Point] wit screen coordinates of the cell center
     */
    fun getCenterPosition(column: Number, row: Number): Point {
        return Point(x + (column.toInt() * tileSize) + (tileSize / 2), y + (row.toInt() * tileSize) + (tileSize / 2))
    }

    /**
     * @param position with column and row
     * @return The [Point] wit screen coordinates of the cell center
     */
    fun getCenterPosition(position: Position): Point {
        return getCenterPosition(position.x, position.y)
    }

    fun getPosition(position: Position): Point = getPosition(position.x, position.y)

    /**
     * @param posX horizontal screen pixel coordinates
     * @param posY vertical screen pixel coordinates
     * @return Tile [Position] in the grid
     */
    fun getField(posX: Int, posY: Int): Position {
        return getField(posX.toDouble(), posY.toDouble())
    }

    /**
     * @param position with screen pixel coordinates
     * @return Tile [Position] in the grid
     */
    fun getField(position: IPoint): Position {
        val relativePosition = position.minus(Point(x, y))
        val column = relativePosition.x / tileSize
        val row = relativePosition.y / tileSize
        return Position(column.toInt(), row.toInt())
    }

    fun isOnGrid(pos: Position): Boolean {
        return (pos.y in 0 until rows) && (pos.x in 0 until columns)
    }

    /**
     * @param posX horizontal screen pixel coordinates
     * @param posY vertical screen pixel coordinates
     * @return Tile [Position] in the grid
     */
    fun getField(posX: Double, posY: Double): Position {
        return getField(Point(posX, posY))
    }

    /**
     * Position in the [PositionGrid]
     */
    data class Position(val x: Int = 0, val y: Int = 0) {

        fun getIndex(): Int {
            return x + (y * x)
        }

        fun horizontal(steps: Int): Position {
            return Position(this.x + steps, this.y)
        }

        fun vertical(steps: Int): Position {
            return Position(this.x, this.y + steps)
        }

        fun left(steps: Int = 1): Position {
            return Position(this.x - steps, this.y)
        }

        fun right(steps: Int = 1): Position {
            return Position(this.x + steps, this.y)
        }

        fun top(steps: Int = 1): Position {
            return Position(this.x, this.y - steps)
        }

        fun bottom(steps: Int = 1): Position {
            return Position(this.x, this.y + steps)
        }

        fun distance(position: Position) = position.toPoint().distanceTo(toPoint())

        fun Position.toPoint() = Point(this.x, this.y)
    }

    fun size() = columns * rows

}