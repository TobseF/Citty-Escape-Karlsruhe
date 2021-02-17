package tfr.game.jam.kittyescape.renderer.animation

import com.soywiz.korge.view.Stage
import com.soywiz.korinject.AsyncInjector
import kotlinx.coroutines.Job
import tfr.game.jam.kittyescape.audio.SoundMachine
import tfr.game.jam.kittyescape.model.Team
import tfr.game.jam.kittyescape.model.World
import tfr.game.jam.kittyescape.renderer.WorldComponent

class HeroAnimator(
    val stage: Stage, val world: World, val worldComponent: WorldComponent, val soundMachine: SoundMachine
) {

    companion object {

        suspend operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton {
                HeroAnimator(get(), get(), get(), get())
            }
        }
    }

    private val animations: MutableMap<Int, MutableList<Job>> = mutableMapOf()

    init {
        animations[1] = mutableListOf<Job>()
        animations[2] = mutableListOf<Job>()
        animations[3] = mutableListOf<Job>()
        animations[4] = mutableListOf<Job>()
    }


    fun addJob(job: Job, hero: Team.Hero) {
        val mutableList = animations[hero.number]!!
        mutableList.forEach { it.cancel() }
        mutableList.clear()
        mutableList.add(job)
        job.invokeOnCompletion {
            mutableList.clear()
        }
    }


}