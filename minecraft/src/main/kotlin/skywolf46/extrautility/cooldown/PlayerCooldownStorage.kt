package skywolf46.extrautility.cooldown

import org.bukkit.OfflinePlayer
import skywolf46.extrautility.cooldown.Cooldown
import skywolf46.extrautility.cooldown.CooldownStorage
import java.util.*

class PlayerCooldownStorage : CooldownStorage() {
    fun cooldownOf(str: UUID): Cooldown {
        return super.cooldownOf(str.toString())
    }

    fun cooldownOf(str: OfflinePlayer): Cooldown {
        return cooldownOf(str.uniqueId)
    }


}