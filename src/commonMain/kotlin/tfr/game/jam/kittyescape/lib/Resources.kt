package tfr.game.jam.kittyescape.lib

import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.NinePatchBitmap32
import com.soywiz.korim.bitmap.readNinePatch
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korim.format.readBitmap
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.file.std.resourcesVfs


suspend fun loadImageFile(fileName: String): Bitmap {
    return resourcesVfs["images/$fileName"].readBitmap()
}

//suspend fun loadWorldImage(fileName: String): Bitmap = resourcesVfs["images/world/$fileName"].readBitmap()

suspend fun loadFont(fileName: String): BitmapFont = resourcesVfs["fonts/$fileName"].readBitmapFont()

suspend fun loadNinePatch(fileName: String): NinePatchBitmap32 = resourcesVfs["images/$fileName"].readNinePatch()

class Resources : AsyncDependency {

    suspend fun loadImage(fileName: String): Bitmap = loadImageFile(fileName) //atlas[fileName]

    companion object {
        operator fun invoke(injector: AsyncInjector) {
            injector.mapSingleton { Resources() }
        }
    }

    lateinit var atlas: Atlas

    lateinit var fontBubble: BitmapFont
    lateinit var fontSmall: BitmapFont

    lateinit var imageBackground: Bitmap
    lateinit var messageBox: NinePatchBitmap32


    lateinit var player1: Bitmap
    lateinit var player2: Bitmap
    lateinit var player3: Bitmap
    lateinit var player4: Bitmap
    lateinit var player1_Selected: Bitmap
    lateinit var player2_Selected: Bitmap
    lateinit var player3_Selected: Bitmap
    lateinit var player4_Selected: Bitmap


    lateinit var map: Bitmap
    lateinit var home: Bitmap
    lateinit var flag: Bitmap

    lateinit var uiMapZoomIn: Bitmap
    lateinit var uiMapZoomOut: Bitmap
    lateinit var uiMapMoveDown: Bitmap
    lateinit var uiTimer: Bitmap

    lateinit var uiCheckDisabled: Bitmap
    lateinit var uiCheckEnabled: Bitmap

    lateinit var buttonSettings: Bitmap
    lateinit var buttonInfo: Bitmap


    override suspend fun init() {
        atlas = resourcesVfs["images.atlas.json"].readAtlas()

        buttonSettings = loadImage("settings.png")
        buttonInfo = loadImage("faq.png")

        player1 = loadImage("player_1.png")
        player2 = loadImage("player_2.png")
        player3 = loadImage("player_3.png")
        player4 = loadImage("player_4.png")

        player1_Selected = loadImage("player_1_s.png")
        player2_Selected = loadImage("player_2_s.png")
        player3_Selected = loadImage("player_3_s.png")
        player4_Selected = loadImage("player_4_s.png")


        map = loadImage("map.png")
        home = loadImage("home.png")

        uiMapZoomIn = loadImage("ui_map_zoom_in.png")
        uiMapZoomOut = loadImage("ui_map_zoom_out.png")
        uiMapMoveDown = loadImage("ui_map_move_down.png")
        uiTimer = loadImage("ui_timer.png")
        uiCheckDisabled = loadImage("ui_check.png")
        uiCheckEnabled = loadImage("ui_check_enabled.png")

        fontBubble = loadFont("bubble.fnt")
        fontSmall = fontBubble

        messageBox = loadNinePatch("message_box.9.png")
    }

    fun getPlayer(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> player1
            2 -> player2
            3 -> player3
            4 -> player4
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

    fun getPlayerSelected(playerNumber: Int): Bitmap {
        return when (playerNumber) {
            1 -> player1_Selected
            2 -> player2_Selected
            3 -> player3_Selected
            4 -> player4_Selected
            else -> throw IllegalArgumentException("Out of range: $playerNumber")
        }
    }

}