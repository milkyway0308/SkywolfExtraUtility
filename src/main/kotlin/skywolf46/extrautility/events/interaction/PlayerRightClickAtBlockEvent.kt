package skywolf46.extrautility.events.interaction

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent


class PlayerRightClickAtBlockEvent(who: Player?, val targetBlock: Block, private val isOffHanded: Boolean) :
    AbstractPlayerItemEvent(who) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override var itemInHand: ItemStack?
        get() = if (isOffHanded) getPlayer().equipment.itemInOffHand else getPlayer().equipment.itemInMainHand
        set(ita) {
            if (isOffHanded) getPlayer().equipment.itemInOffHand = ita else getPlayer().equipment.itemInMainHand = ita
        }

    companion object {
        val handlerList = HandlerList()
    }
}
