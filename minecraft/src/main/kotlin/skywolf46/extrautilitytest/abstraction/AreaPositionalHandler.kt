package skywolf46.extrautilitytest.abstraction

import org.bukkit.Location
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import skywolf46.extrautilitytest.areas.impl.RectangleArea
import skywolf46.messagereplacer.MessageReplacer
import skywolf46.placeholders.util.MessageParameters

class AreaPositionalHandler(ar: IArea) {
    val loc = Array<Location?>(ar.point()) { null }
    var pointer = 0
    val area = ar

    fun toArea(): IArea? = area.create(loc.requireNoNulls())

    fun isCompleted(): Boolean = pointer == loc.size
    fun accept(e: PlayerInteractEvent, success: Enum<*>, delete: Enum<*>, deleteFail: Enum<*>, fail: Enum<*>) {
        when (e.action) {
            Action.LEFT_CLICK_BLOCK -> {
                if (isCompleted()) {
                    MessageReplacer.get(fail.javaClass)[fail.name]
                        .sendParameterTo(
                            e.player,
                            MessageParameters()
                                .add("currentArea", pointer.toString())
                                .add("maxArea", loc.size.toString())
                        )
                    return
                }
                loc[pointer++] = e.clickedBlock.location
                MessageReplacer.get(success.javaClass)[success.name]
                    .sendParameterTo(
                        e.player,
                        MessageParameters()
                            .add("currentArea", pointer.toString())
                            .add("maxArea", loc.size.toString())
                    )
                return
            }
            Action.RIGHT_CLICK_BLOCK -> {
                if (pointer == 0) {
                    MessageReplacer.get(deleteFail.javaClass)[deleteFail.name]
                        .sendParameterTo(
                            e.player,
                            MessageParameters()
                                .add("currentArea", pointer.toString())
                                .add("maxArea", loc.size.toString())
                        )
                    return
                }
                loc[--pointer] = null
                MessageReplacer.get(delete.javaClass)[delete.name]
                    .sendParameterTo(
                        e.player,
                        MessageParameters()
                            .add("currentArea", pointer.toString())
                            .add("maxArea", loc.size.toString())
                    )
                return
            }
            else -> return
        }
    }

    companion object {
        fun rectangle(): AreaPositionalHandler = AreaPositionalHandler(RectangleArea.instance)
    }
}
