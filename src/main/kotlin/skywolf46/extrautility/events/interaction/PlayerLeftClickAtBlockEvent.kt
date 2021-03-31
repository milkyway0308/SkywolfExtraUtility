package skywolf46.extrautility.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent

class PlayerLeftClickAtBlockEvent(ev: PlayerInteractEvent, who: Player?, val targetBlock: Block) :
    AbstractPlayerItemEvent(ev, who) {


    companion object {
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = handlerList
}
