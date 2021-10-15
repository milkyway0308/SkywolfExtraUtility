package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import skywolf46.extrautility.SkywolfExtraUtility.Companion.inst

fun Listener.register() {
    Bukkit.getPluginManager().registerEvents(this, inst)
}

