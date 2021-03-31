package skywolf46.extrautility.events.abstraction

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack


abstract class AbstractPlayerItemEvent(val ev: PlayerInteractEvent ,who: Player?) : PlayerEvent(who), Cancellable {
    open var itemInHand: ItemStack?
        get() = getPlayer().equipment.itemInMainHand
        set(ita) {
            getPlayer().equipment.itemInMainHand = ita
        }
    val isItemValid: Boolean
        get() = itemInHand != null && itemInHand!!.type != Material.AIR

    override fun isCancelled(): Boolean {
        return ev.isCancelled
    }

    override fun setCancelled(cancelled: Boolean) {
        ev.isCancelled = cancelled
    }

    fun setCancel(cancel: Boolean): AbstractPlayerItemEvent {
        isCancelled = cancel
        return this
    }
}
