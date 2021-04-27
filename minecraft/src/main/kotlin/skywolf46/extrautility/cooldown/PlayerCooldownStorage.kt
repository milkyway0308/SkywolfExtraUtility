package skywolf46.extrautility.cooldown

import org.bukkit.OfflinePlayer
import java.util.*

class PlayerCooldownStorage : CooldownStorage() {
    override fun cooldownOf(str: String): Cooldown {
        return super.cooldownOf(str)
    }
    fun cooldownOf(str: UUID): Cooldown {
        return super.cooldownOf(str.toString())
    }

    fun cooldownOf(str: OfflinePlayer): Cooldown {
        return cooldownOf(str.uniqueId)
    }


}