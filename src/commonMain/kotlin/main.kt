import com.soywiz.klogger.Logger
import com.soywiz.korge.Korge
import com.soywiz.korge.view.View
import com.soywiz.korgw.GameWindow.Quality.QUALITY
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import kotlinx.coroutines.CoroutineScope
import tfr.game.jam.kittyescape.*
import tfr.game.jam.kittyescape.audio.JukeBox
import tfr.game.jam.kittyescape.audio.SoundMachine
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.lib.Resolution
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.Team
import tfr.game.jam.kittyescape.model.World
import tfr.game.jam.kittyescape.model.network.NetworkBridge
import tfr.game.jam.kittyescape.renderer.*
import kotlin.random.Random


/**
 *  Main entry point for the game. To start it, run the gradle tasks:
 * `runJVM` - to run it with JAVA.
 * `runJS` - to run it as HTML Web App.
 * `runAndroidDebug` - to install and start it on an Android device.
 */

const val debug = false

/**
 * Shows all map tiles on start. Useful for map editing.
 */
const val startWithOpenMap = false
const val playBackgroundMusic = false

/**
 * Virtual size which gets projected onto the [windowResolution]
 */
val virtualResolution = Resolution(width = 1280, height = 800)

/**
 * Actual window size
 */
val windowResolution = virtualResolution

val backgroundColor = Colors.LIGHTGRAY //Colors["#2b2b2b"]


suspend fun main() = Korge(
    title = "Isle Maze",
    virtualHeight = virtualResolution.height,
    virtualWidth = virtualResolution.width,
    width = windowResolution.width,
    height = windowResolution.height,
    bgcolor = backgroundColor,
    debug = debug,
    quality = QUALITY
) {

    Logger.defaultLevel = Logger.Level.DEBUG

    val userName = "user" + Random.nextInt(999)

    val player = Team(Team.Hero(0), Team.Hero(1))
    val world = World(player)

    val injector = AsyncInjector().run {
        mapInstance(this@Korge)
        mapInstance(this@Korge as View)
        mapInstance(this@Korge as CoroutineScope)
        mapInstance(EventBus(this@Korge))
        mapInstance(virtualResolution)
        mapInstance(world)
    }
    Resources(injector)
    SoundMachine(injector)

    addChild(WorldComponent(injector))

    GameMechanics(injector)

    JukeBox(injector) { activated = playBackgroundMusic }.play()


    LevelCheck(injector)
    Scoring(injector)

    val gameFlow = GameFlow(injector)

    KeyBindings(injector)

    NetworkBridge(injector).userName = userName

    UiComponent(injector)

    addChild(TimerComponent(injector))
    addComponent(injector.get() as TimerComponent)

    addChild(SettingsComponent(injector))
    addChild(FaqComponent(injector))
    addChild(NetworkSettingsPanelComponent(injector))
}


