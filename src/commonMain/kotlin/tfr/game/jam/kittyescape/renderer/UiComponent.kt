package tfr.game.jam.kittyescape.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.degrees
import tfr.game.jam.kittyescape.*
import tfr.game.jam.kittyescape.InputEvent.Action
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.World

class UiComponent(val world: World, val res: Resources, val rootView: Stage, val bus: EventBus) {

    companion object {
        val log = Logger("UiComponent")

        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton {
                UiComponent(get(), get(), get(), get())
            }
            injector.get<UiComponent>()
        }
    }

    val playerControls = mutableListOf<PlayerControl>()

    init {
        rootView.apply {
            addRoomCounter()
            alignRightToRightOf(rootView)
            image(res.uiTimer) {
                position(31, 7)
            }
        }
        addPlayerControls()

        addMoveMap()

        addZoomMap()

        addRightMenuButtons()
    }

    private fun addRightMenuButtons() = rootView.apply {
        val settingsButton = image(res.buttonSettings) {
            alignRightToRightOf(rootView, 8.0)
            alignTopToTopOf(rootView, 120.0)
            onClick {
                bus.send(OpenSettingsEvent)
            }
        }
        image(res.buttonInfo) {
            alignRightToRightOf(settingsButton)
            alignTopToTopOf(settingsButton, 90.0)
            onClick {
                bus.send(OpenFaqEvent)
            }
        }
    }

    private fun addRoomCounter() = rootView.apply {
        text("1/12", font = res.fontBubble, textSize = 38.0) {
            position(80.0, 16.0)
        }
    }

    private fun addPlayerControls() = rootView.apply {
        for (playerNumber in 1..4) {
            val playerControl = PlayerControl(playerNumber, playerControls, rootView, res, bus)
            addChild(playerControl)
            playerControls.add(playerControl)
        }
    }

    class PlayerControl(
        val playerNumber: Int, val others: MutableList<PlayerControl>, val rootView: View, res: Resources, bus: EventBus
    ) : Container() {

        val checkPlayer: CheckBox

        init {
            alignLeftToLeftOf(rootView, 48.0)
            alignBottomToBottomOf(rootView, 48.0)
            val distance = (playerNumber - 1) * 65.0

            checkPlayer = CheckBox(res.uiCheckDisabled, res.uiCheckEnabled, {
                bus.send(InputEvent(Action.SelectHero, playerNumber))
            }) {
                anchor(0.5, 0.0)
                x = distance
            }
            addChild(checkPlayer)

        }

    }


    class CheckBox(
        val base: Bitmap, val checked: Bitmap, action: (CheckBox) -> Unit, adjust: Image.() -> Unit = {}
    ) : Container() {
        var checkedState = false
        var enabled = true

        val checkedImage: Image

        fun select() {
            checkedState = true
            checkedImage.alpha = 1.0
        }

        fun uncheck() {
            checkedState = false
            checkedImage.alpha = 0.0
        }

        init {
            image(base) {
                adjust.invoke(this)
            }
            checkedImage = image(checked) {
                adjust.invoke(this)

                alpha = 0.0

                onClick {
                    if (enabled) {

                        action.invoke(this@CheckBox)
                        select()
                    }
                }

                onOver {
                    if (enabled) {
                        alpha = 1.0
                    }
                }
                onOut {
                    if (!checkedState && enabled) {
                        alpha = 0.0
                    }
                }
            }

        }

    }

    private fun addZoomMap() = rootView.apply {
        val zoomOut = image(res.uiMapZoomOut) {
            anchor(0.5, 1.0)
            alignBottomToBottomOf(rootView, 150.0)
            alignRightToRightOf(rootView, 60.0)
            onClick { sendUiEvent(Action.MapZoomOut) }
        }

        val zoomIn = image(res.uiMapZoomIn) {
            anchor(0.5, 1.0)
            alignLeftToLeftOf(zoomOut)
            alignBottomToTopOf(zoomOut, 10.0)
            onClick { sendUiEvent(Action.MapZoomIn) }
        }
    }


    private fun addMoveMap() = rootView.apply {
        val distance = 0.5
        val moveMap = image(res.uiMapMoveDown) {
            anchor(0.5, distance)
            alignRightToRightOf(rootView, 64.0)
            alignBottomToBottomOf(rootView, 64.0)
            onClick { sendUiEvent(Action.MapMoveDown) }
            anchor(0.5, -distance)
        }
        image(res.uiMapMoveDown) {
            position(moveMap.pos)
            anchor(0.5, -distance)
            rotation(90.degrees)
            onClick { sendUiEvent(Action.MapMoveLeft) }
        }
        image(res.uiMapMoveDown) {
            position(moveMap.pos)
            anchor(0.5, -distance)
            rotation(180.degrees)
            onClick { sendUiEvent(Action.MapMoveUp) }
        }
        image(res.uiMapMoveDown) {
            position(moveMap.pos)
            anchor(0.5, -distance)
            rotation(270.degrees)
            onClick { sendUiEvent(Action.MapMoveRight) }
        }
    }


    fun sendUiEvent(action: Action) {
        log.info { "New UI input Event $action" }
        bus.send(InputEvent(action, world.selectedPlayer))
    }


}