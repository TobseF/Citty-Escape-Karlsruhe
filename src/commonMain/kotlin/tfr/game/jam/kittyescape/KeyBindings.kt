package tfr.game.jam.kittyescape

import com.soywiz.klogger.Logger
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.InputEvent.Action
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.model.World

class KeyBindings(
    private val stage: Stage,
    private val bus: EventBus,
    private val world: World,
    private val gameFlow: GameFlow,
    private val levelCheck: LevelCheck
) : AsyncDependency {


    companion object {
        val log = Logger("KeyBindings")

        suspend operator fun invoke(injector: AsyncInjector): KeyBindings {
            injector.mapSingleton {
                KeyBindings(get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    override suspend fun init() {
        bindKeys()
        bus.register<ResetGameEvent> { reloadLevel() }
    }

    private fun bindKeys() {
        stage.keys { down { onKeyDown(it.key) } }
    }

    private fun resetState() {
        gameFlow.reset()
        levelCheck.reset()
    }

    private fun reloadLevel() {
        log.debug { "Reload level" }
        resetState()
    }

    fun sendPlayerInputEvent(action: Action) {
        bus.send(InputEvent(action, world.selectedPlayer))
    }

    fun Action.isAllowed() = true

    private fun onKeyDown(key: Key) {
        when (key) {
            Key.W -> {
                if (Action.HeroUp.isAllowed()) {
                    sendPlayerInputEvent(Action.HeroUp)
                }
            }
            Key.A -> {
                if (Action.HeroLeft.isAllowed()) {
                    sendPlayerInputEvent(Action.HeroLeft)
                }
            }
            Key.S -> {
                if (Action.HeroDown.isAllowed()) {
                    sendPlayerInputEvent(Action.HeroDown)
                }
            }
            Key.D -> {
                if (Action.HeroRight.isAllowed()) {
                    sendPlayerInputEvent(Action.HeroRight)
                }
            }
            Key.SPACE -> {
            }
            Key.PLUS, Key.KP_ADD -> {
                sendPlayerInputEvent(Action.MapZoomIn)
            }
            Key.MINUS, Key.KP_SUBTRACT -> {
                sendPlayerInputEvent(Action.MapZoomOut)
            }
            Key.P -> {
                log.debug { "Print Field Data" }
            }

            Key.LEFT -> {
                sendPlayerInputEvent(Action.MapMoveLeft)
            }
            Key.RIGHT -> {
                sendPlayerInputEvent(Action.MapMoveRight)
            }
            Key.UP -> {
                sendPlayerInputEvent(Action.MapMoveUp)
            }
            Key.DOWN -> {
                sendPlayerInputEvent(Action.MapMoveDown)
            }

            Key.N1 -> {
                bus.send(InputEvent(Action.SelectHero, heroNumber = 1))
            }
            Key.N2 -> {
                bus.send(InputEvent(Action.SelectHero, heroNumber = 2))
            }
            Key.N3 -> {
                bus.send(InputEvent(Action.SelectHero, heroNumber = 3))
            }
            Key.N4 -> {
                bus.send(InputEvent(Action.SelectHero, heroNumber = 4))
            }

            else -> {
                log.debug { "Pressed unmapped key: $key" }
            }
        }
    }


}