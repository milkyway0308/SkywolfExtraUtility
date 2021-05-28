package skywolf46.extrautilitytest.test.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import skywolf46.extrautilitytest.test.events.abstraction.AbstractPlayerItemEvent


class PlayerRightClickAtBlockEvent(
    ev: PlayerInteractEvent,
    who: Player?,
    val targetBlock: Block,
    private val isOffHanded: Boolean
) :
    AbstractPlayerItemEvent(ev, who) {

    override var itemInHand: ItemStack?
        get() = if (isOffHanded) getPlayer().equipment.itemInOffHand else getPlayer().equipment.itemInMainHand
        set(ita) {
            if (isOffHanded) getPlayer().equipment.itemInOffHand = ita else getPlayer().equipment.itemInMainHand = ita
        }

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


