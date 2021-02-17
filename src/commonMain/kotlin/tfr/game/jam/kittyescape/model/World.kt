package tfr.game.jam.kittyescape.model

import com.soywiz.korma.geom.Point


class World(val team: Team) {

    val pins = mutableListOf<Pin>()

    fun addPint(point: Point) {
        pins.add(Pin(point))
    }

    var roomName = "A"

    /**
     * Player out of [playersCount] (Not the hero). Starting with 1.
     */
    var selectedPlayer = 1

    /**
     * Total numbers of players
     */
    var playersCount = 1
}