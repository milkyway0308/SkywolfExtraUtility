package skywolf46.extrautility.test.events.combat


import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerKilledEntityEvent(who: Player?, val entity: Entity) : PlayerEvent(who) {
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
