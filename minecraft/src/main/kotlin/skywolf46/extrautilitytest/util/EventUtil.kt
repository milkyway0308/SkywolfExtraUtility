package skywolf46.extrautilitytest.util

import org.bukkit.Bukkit
import org.bukkit.event.Event

fun <T : Event> T.callEvent(): T {
    Bukkit.getPluginManager().callEvent(this)
    return this
}