package tfr.game.jam.kittyescape.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onMouseDrag
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.graphics
import com.soywiz.korge.view.image
import com.soywiz.korim.color.Colors
import com.soywiz.korim.paint.NonePaint
import com.soywiz.korim.vector.ShapeBuilder
import com.soywiz.korim.vector.StrokeInfo
import com.soywiz.korinject.AsyncDependency
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.vector.LineCap
import com.soywiz.korma.geom.vector.LineJoin
import com.soywiz.korma.geom.vector.VectorPath
import com.soywiz.korma.geom.vector.line
import tfr.game.jam.kittyescape.NewPinEvent
import tfr.game.jam.kittyescape.audio.SoundMachine
import tfr.game.jam.kittyescape.lib.EventBus
import tfr.game.jam.kittyescape.lib.Resolution
import tfr.game.jam.kittyescape.lib.Resources
import tfr.game.jam.kittyescape.model.Pin
import tfr.game.jam.kittyescape.model.World

class WorldComponent(
    val injector: AsyncInjector,
    val bus: EventBus,
    val world: World,
    val resolution: Resolution,
    private val resources: Resources,
    override val stage: Stage,
    val soundMachine: SoundMachine
) : Container(), AsyncDependency {

    override suspend fun init() {
        image(resources.map)
        addDragListener()
    }

    private fun addDragListener() {
        var start: Point = pos.copy()
        onMouseDrag { info ->
            if (info.start && !info.end) {
                start = pos.copy()
            } else if (!info.start && !info.end) {
                x = start.x + info.dx
                y = start.y + info.dy
            }
        }
        onClick { info ->
            val pos: Point = info.currentPosLocal
            println(pos)
            bus.send(NewPinEvent(pos))
            addChild(PinComponent(Pin(pos), resources))
            val vectorPath = VectorPath()
            vectorPath.lineTo(
                200.0, 50.0
            )
            val shapeBuilder = ShapeBuilder(500, 500)
            shapeBuilder.line(pos, pos.add(Point(100, 100)))

            graphics {
                val strokeInfo = StrokeInfo(
                    thickness = 6.0,
                    startCap = LineCap.ROUND,
                    endCap = LineCap.ROUND,
                    lineJoin = LineJoin.ROUND
                )
                fillStroke(NonePaint, Colors.BLUE, strokeInfo) {
                    lineTo(pos.x, pos.y)
                    lineTo(pos.x + 100, pos.y + 50)
                    lineTo(pos.x - 50, pos.y + 110)
                }

            }
        }

    }


    companion object {
        val log = Logger("WorldComponent")

        suspend operator fun invoke(injector: AsyncInjector): WorldComponent {
            injector.mapSingleton {
                WorldComponent(get(), get(), get(), get(), get(), get(), get())
            }
            return injector.get()
        }
    }

}