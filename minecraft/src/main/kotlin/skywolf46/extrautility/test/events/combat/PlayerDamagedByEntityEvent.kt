package skywolf46.extrautility.test.events.combat

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerDamagedByEntityEvent(
    who: Player?,
    val damager: Entity,
    var damage: Double,
    private var c: Boolean,
) :
    PlayerEvent(who), Cancellable {

    private var cancelled: Boolean = false

    init {
        cancelled = c
    }

    override fun setCancelled(p0: Boolean) {
        cancelled = p0
    }

    override fun isCancelled(): Boolean = cancelled

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
