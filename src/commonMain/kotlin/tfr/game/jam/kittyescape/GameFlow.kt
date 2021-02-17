package tfr.game.jam.kittyescape

import com.soywiz.klogger.Logger
import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.InputEvent.Action
import tfr.game.jam.kittyescape.audio.SoundMachine
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.model.World

/**
 * Global game cycle
 */
class GameFlow(
    private val world: World,
    private val bus: EventBus,
    private val mechanics: GameMechanics,
    private val soundMachine: SoundMachine
) {
    fun reset() {
    }

    companion object {
        val log = Logger("GameFlow")

        suspend operator fun invoke(injector: AsyncInjector): GameFlow {
            injector.mapSingleton {
                GameFlow(get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    init {
        bus.register<InputEvent> { handleInput(it) }
    }

    private fun handleInput(inputEvent: InputEvent) {
        val playerId = inputEvent.heroNumber
        executeInput(inputEvent, playerId)
    }

    private fun executeInput(inputEvent: InputEvent, playerId: Int) {
        when (inputEvent.action) {
            Action.MapMoveUp -> mechanics.moveMapUp()
            Action.MapMoveDown -> mechanics.moveMapDown()
            Action.MapMoveLeft -> mechanics.moveMapLeft()
            Action.MapMoveRight -> mechanics.moveMapRight()
            Action.MapZoomIn -> mechanics.zoomIn()
            Action.MapZoomOut -> mechanics.zoomOut()

            Action.Unknown -> {
                log.debug { "What should we do with a drunken sailor?" }
            }
        }
    }
}
