package tfr.game.jam.kittyescape

import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.lib.EventBus

class LevelCheck(private val bus: EventBus) {

    companion object {
        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { LevelCheck(get()) }
        }
    }

    private var totalScore = 0
    private var moves = 0


    init {
        bus.register<NewScoreEvent> { onScore(it) }
    }


    fun failed(): Boolean {
        return false
    }


    fun onScore(score: NewScoreEvent) {
    }


    fun reset() {
        moves = 0
        totalScore = 0
    }

}