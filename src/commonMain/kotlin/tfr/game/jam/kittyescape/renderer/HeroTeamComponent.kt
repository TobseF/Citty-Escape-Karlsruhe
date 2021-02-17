package tfr.game.jam.kittyescape.renderer

import com.soywiz.korge.view.Stage
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korinject.AsyncInjector
import tfr.game.jam.kittyescape.audio.SoundMachine
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.Team
import tfr.game.jam.kittyescape.model.World
import tfr.game.jam.kittyescape.renderer.animation.HeroAnimator

/**
 * A team out of 4 [HeroComponent]s
 */
class HeroTeamComponent(
    val injector: AsyncInjector,
    val bus: EventBus,
    val view: Stage,
    val world: World,
    worldComponent: WorldComponent,
    val resources: Resources,
    soundMachine: SoundMachine
) {

    val players = mutableMapOf<Team.Hero, HeroComponent>()
    private val animator = HeroAnimator(view, world, worldComponent, soundMachine)

    companion object {

        suspend operator fun invoke(injector: AsyncInjector): HeroTeamComponent {
            injector.mapSingleton {
                HeroTeamComponent(get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

    init {
        for (heroModel in world.team.heroes) {
            val image: Bitmap = resources.getPlayer(heroModel.number)
            val imageSelected: Bitmap = resources.getPlayerSelected(heroModel.number)
            val player = HeroComponent(bus, heroModel, world, view, animator, image, imageSelected)
            players[heroModel] = player
            worldComponent.addChild(player)
        }
    }


}