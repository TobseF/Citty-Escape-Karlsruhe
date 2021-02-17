package tfr.game.jam.kittyescape

import com.soywiz.korma.geom.Point
import tfr.game.jam.kittyescape.math.PositionGrid.Position

/**
 * Triggered after the deletion of tiles
 */
/**
 * Triggered after a new score value
 */
data class NewScoreEvent(val score: Int, val multiplicator: Int = 1, val pos: Position)

/**
 * Triggered after the user swaps two tiles
 */
object GameOverEvent

object ResetGameEvent

object NextLevelEvent


data class ChangePlayerEvent(val playerId: Int = 0)

object OpenSettingsEvent

object OpenFaqEvent

data class ChangePlayersCountEvent(val playersCount: Int = 0)

data class ChangeRoomEvent(val roomName: String)


data class FoundHomeEvent(val playerNumber: Int = 0)

data class NewPinEvent(val point: Point)


data class InputEvent(
    val action: Action, val heroNumber: Int = 0, val roomId: Int? = null, val isNetworkEvent: Boolean = false
) {

    enum class Action {
        MapMoveUp, MapMoveDown, MapMoveLeft, MapMoveRight, MapZoomIn, MapZoomOut, SelectHero, HeroLeft, HeroRight, HeroUp, HeroDown, Unknown;

        companion object {
            fun parseValue(actioName: String): Action {
                return try {
                    valueOf(actioName)
                } catch (e: Exception) {
                    Unknown
                }
            }
        }
    }

    fun isLocalEvent() = !isNetworkEvent
}