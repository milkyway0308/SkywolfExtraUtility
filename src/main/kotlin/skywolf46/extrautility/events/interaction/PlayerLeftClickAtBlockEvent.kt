package skywolf46.extrautility.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent

class PlayerLeftClickAtBlockEvent(who: Player?, val targetBlock: Block) : AbstractPlayerItemEvent(who) {


    companion object {
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = handlerList
}
