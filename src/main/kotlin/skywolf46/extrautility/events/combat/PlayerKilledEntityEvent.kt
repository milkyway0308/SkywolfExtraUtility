package skywolf46.extrautility.events.combat

import lombok.Getter
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerKilledEntityEvent(who: Player?, @field:Getter private val entity: Entity) : PlayerEvent(who) {
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
