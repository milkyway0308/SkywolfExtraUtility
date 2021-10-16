package skywolf46.extrautility.events.interaction

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent


class PlayerLeftClickEvent(ev: PlayerInteractEvent, who: Player?, val isBlockInteraction: Boolean) :
    AbstractPlayerItemEvent(ev, who) {
    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {

        internal val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlerList
        }
    }
}
