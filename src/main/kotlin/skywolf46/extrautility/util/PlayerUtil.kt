package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.extrautility.cooldown.PlayerCooldownStorage
import skywolf46.extrautility.inst

private val cooldown: PlayerCooldownStorage = PlayerCooldownStorage()

val Player.playerCooldown: PlayerCooldownStorage
    get() = cooldown

fun findPlayerOf(str: String): OfflinePlayer {
    var ofp: OfflinePlayer? = playerOf(str)
    if (ofp == null)
        ofp = Bukkit.getOfflinePlayer(str)
    return ofp!!
}

fun playerOf(str: String): Player? {
    return Bukkit.getPlayerExact(str)
}

fun connectedPlayerOf(str: String): OfflinePlayer? {
    val pl = findPlayerOf(str)
    if (!pl.isOnline and !pl.hasPlayedBefore())
        return null
    return pl
}

fun Player.hasValue(str: String): Boolean = hasMetadata(str)

fun Player.setValue(str: String, data: Any) = setMetadata(str, FixedMetadataValue(inst, data))

fun Player.removeValue(str: String): Boolean = hasMetadata(str).ifTrue { removeMetadata(str, inst) }


@Suppress("UNCHECKED_CAST")
fun <T> Player.getValueOrNull(str: String): T? =
    if (hasValue(str)) getMetadata(str)[0].value() as T else null

@Suppress("UNCHECKED_CAST")
fun <T> Player.getValue(str: String, default: T): T =
    if (hasValue(str)) getMetadata(str)[0].value() as T else default

fun <T> Player.getOrSetValue(str: String, provider: () -> T): T =
    hasValue(str)
        .ifFalse {
            setValue(str, provider() as Any)
        }.let {
            return getValueOrNull(str)!!
        }

fun Player.intValue(str: String, default: Int): Int = getValue(str, default)

fun Player.booleanValue(str: String, default: Boolean): Boolean = getValue(str, default)

fun Player.longValue(str: String, default: Long): Long = getValue(str, default)

fun Player.doubleValue(str: String, default: Double): Double = getValue(str, default)

fun Player.stringValue(str: String, default: String): String = getValue(str, default)

fun <K, V> Player.mapValue(str: String): Map<K, V> = getOrSetValue(str) { HashMap() }

fun <K> Player.listValue(str: String): List<K> = getOrSetValue(str) { ArrayList() }