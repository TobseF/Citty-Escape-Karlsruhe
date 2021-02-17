package tfr.game.jam.kittyescape

import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.lib.EventBus

class Scoring(val bus: EventBus) {

    companion object {
        suspend operator fun invoke(injector: AsyncInjector): Scoring {
            injector.run {
                return Scoring(get())
            }
        }
    }


    init {
    }


}