package skywolf46.extrautility.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent


class PlayerRightClickAtBlockEvent(
    ev: PlayerInteractEvent,
    who: Player?,
    val targetBlock: Block,
    @Suppress("MemberVisibilityCanBePrivate") val isOffHanded: Boolean
) :
    AbstractPlayerItemEvent(ev, who) {

    override var itemInHand: ItemStack?
        get() = if (isOffHanded) getPlayer().equipment.itemInOffHand else getPlayer().equipment.itemInMainHand
        set(ita) {
            if (isOffHanded) getPlayer().equipment.itemInOffHand = ita else getPlayer().equipment.itemInMainHand = ita
        }

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


