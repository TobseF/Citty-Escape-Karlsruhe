package tfr.game.jam.kittyescape.model

import com.soywiz.korma.geom.Point

class Pin(val pos: Point) {

    enum class Type { Railway, Foot, Car, EScooter }
}