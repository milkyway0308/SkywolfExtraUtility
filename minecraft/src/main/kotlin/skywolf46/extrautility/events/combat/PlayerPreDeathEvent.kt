package skywolf46.extrautility.events.combat

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent


class PlayerPreDeathEvent(pl: Player) : PlayerEvent(pl), Cancellable {
    private var cancel = false


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

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(p0: Boolean) {
        cancel = p0
    }

}