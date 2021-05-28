package skywolf46.extrautilitytest.util

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import skywolf46.extrautilitytest.inst

fun Listener.register() {
    Bukkit.getPluginManager().registerEvents(this, inst)
}

