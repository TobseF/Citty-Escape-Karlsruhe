import tfr.game.jam.kittyescape.model.network.Update
import tfr.game.jam.kittyescape.model.network.Update.Player
import tfr.game.jam.kittyescape.model.network.Update.Pos
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateModelTest {
    @Test
    fun test() {
        val update = Update(listOf(Player(0, Pos(1, 2)), Player(1, Pos(2, 3))))
        val updateData = update.write()
        println(updateData)
        val parsed = Update.read(updateData)
        assertEquals(update, parsed)

    }
}