package tfr.game.jam.kittyescape.model

import tfr.game.jam.kittyescape.InputEvent
import tfr.game.jam.kittyescape.math.PositionGrid.Position
import tfr.game.jam.kittyescape.model.Team.Hero

/**
 * A team out of 4 [Hero]s
 */
data class Team(var heroes: MutableList<Hero> = mutableListOf()) {

    constructor(vararg players: Hero) : this(players.toMutableList())

    operator fun get(playerNumber: Int) = heroes[playerNumber - 1]

    fun isTaken(pos: Position): Boolean {
        return heroes.filter { it.pos() == pos }.any()
    }

    /**
     * One of four brave heroes.
     */
    data class Hero(
        val number: Int, var x: Int = 0, var y: Int = 0
    ) {

        val possibleActions = mutableSetOf<InputEvent.Action>()

        fun pos(x: Int, y: Int) {
            this.x = x
            this.y = y
        }

        fun pos() = Position(x, y)

        fun setPos(pos: Position) {
            x = pos.x
            y = pos.y
        }

    }
}