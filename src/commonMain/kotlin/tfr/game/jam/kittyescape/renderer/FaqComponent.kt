package tfr.game.jam.kittyescape.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.World

class FaqComponent(val world: World, val rootView: Stage, res: Resources, val bus: EventBus) : Container() {

    companion object {
        val log = Logger("FaqComponent")

        suspend operator fun invoke(injector: AsyncInjector): FaqComponent {
            injector.mapSingleton {
                FaqComponent(get(), get(), get(), get())
            }
            return injector.get()
        }
    }


    init {


        visible = false


    }


}