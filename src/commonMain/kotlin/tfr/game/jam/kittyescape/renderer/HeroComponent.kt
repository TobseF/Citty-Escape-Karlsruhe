package tfr.game.jam.kittyescape.renderer

import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import tfr.game.jam.kittyescape.FoundHomeEvent
import tfr.game.jam.kittyescape.InputEvent
import tfr.game.jam.kittyescape.InputEvent.Action
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.model.Team
import tfr.game.jam.kittyescape.model.World
import tfr.game.jam.kittyescape.renderer.animation.HeroAnimator

class HeroComponent(
    val bus: EventBus,
    val hero: Team.Hero,
    val world: World,
    val view: Stage,
    val animator: HeroAnimator,
    bitmap: Bitmap,
    imageSelected: Bitmap
) : Container() {

    var nextPos: Point? = null

    private val defaultImage: Image
    private val selectedImage: Image

    companion object {

        suspend operator fun invoke(injector: AsyncInjector, bitmap: Bitmap, imageSelected: Bitmap): HeroComponent {
            injector.mapSingleton {
                HeroComponent(get(), get(), get(), get(), get(), bitmap, imageSelected)
            }
            return injector.get()
        }
    }

    init {
        val scale = 0.55
        val anchorX = 0.5
        val anchorY = 0.6
        onClick {
            bus.send(InputEvent(Action.SelectHero, heroNumber = hero.number))
        }

        defaultImage = image(bitmap) {
            anchor(anchorX, anchorY)
            scale(scale, scale)
            visible = false
        }
        selectedImage = image(imageSelected) {
            scale(scale, scale)
            anchor(anchorX, anchorY)
        }
        bus.register<FoundHomeEvent> {
            if (hero.number == it.playerNumber) {
                alpha = 0.0
            }
        }
        bus.register<InputEvent> {
            if (it.action == Action.SelectHero) {
                updateSelectionState(it.heroNumber)
            }
        }
        updateSelectionState()
    }

    private fun updateSelectionState(selectedHero: Int = world.selectedPlayer) {
        val selected = selectedHero == hero.number
        selectedImage.visible = selected
        defaultImage.visible = !selected
    }


    override fun toString(): String {
        return "Hero ${hero.number} [${hero.x},${hero.y}] (${x},${y})"
    }
}