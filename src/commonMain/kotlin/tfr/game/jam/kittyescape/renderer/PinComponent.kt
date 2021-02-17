package tfr.game.jam.kittyescape.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.anchor
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.Pin

class PinComponent(val pin: Pin, val resources: Resources) : Container() {


    init {
        image(resources.uiCheckDisabled) {
            anchor(0.5, 0.5)
            position(pin.pos.x, pin.pos.y)
        }
    }

}