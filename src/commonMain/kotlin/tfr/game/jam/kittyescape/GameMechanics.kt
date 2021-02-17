package tfr.game.jam.kittyescape

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.model.World
import tfr.game.jam.kittyescape.renderer.WorldComponent

/**
 * Actions and checks for the provided [GridLayer]
 */
class GameMechanics(val bus: EventBus, val world: World, val worldComponent: WorldComponent) {

    companion object {
        val log = Logger("WorldComponent")

        suspend operator fun invoke(injector: AsyncInjector): GameMechanics {
            injector.mapSingleton {
                GameMechanics(get(), get(), get())
            }
            return injector.get()
        }
    }

    private val zoomStep = 0.1
    private val moveStep = 128.0

    init {
        bus.register<NewPinEvent> { addPint(it) }
    }

    private fun addPint(newPinEvent: NewPinEvent) {
        world.addPint(newPinEvent.point)
    }

    fun zoomIn() {
        worldComponent.scale += zoomStep
    }

    fun zoomOut() {
        worldComponent.scale -= zoomStep
    }

    fun moveMapLeft() {
        worldComponent.x += moveStep
    }

    fun moveMapRight() {
        worldComponent.x -= moveStep
    }

    fun moveMapUp() {
        worldComponent.y += moveStep
    }

    fun moveMapDown() {
        worldComponent.y -= moveStep
    }

}