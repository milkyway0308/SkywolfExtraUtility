package skywolf46.extrautility.events.interaction

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent


class PlayerLeftClickEvent(ev: PlayerInteractEvent, who: Player?, val isBlockInteraction: Boolean) :
    AbstractPlayerItemEvent(ev, who) {
    override fun getHandlers(): HandlerList {
        return _handle
    }

    companion object {

        internal val _handle = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return _handle
        }
    }
}
