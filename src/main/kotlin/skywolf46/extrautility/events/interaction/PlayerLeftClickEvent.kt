package skywolf46.extrautility.events.interaction

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent


class PlayerLeftClickEvent(who: Player?) : AbstractPlayerItemEvent(who) {
    companion object {
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = handlerList
}
