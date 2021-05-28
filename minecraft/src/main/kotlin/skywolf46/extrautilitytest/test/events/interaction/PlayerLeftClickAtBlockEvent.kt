package skywolf46.extrautilitytest.test.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import skywolf46.extrautilitytest.test.events.abstraction.AbstractPlayerItemEvent

class PlayerLeftClickAtBlockEvent(ev: PlayerInteractEvent, who: Player?, val targetBlock: Block) :
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
