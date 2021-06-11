package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import skywolf46.extrautility.ExtraUtilityCore
import java.net.URLClassLoader

object MinecraftLoader {
    fun loadAllClass(): List<Class<*>> {
        return ClassUtil.scanClass(ExtraUtilityCore.getIgnoredList(), *mutableListOf<ClassLoader>().apply {
            for (x in Bukkit.getPluginManager().plugins) {
                add((x as JavaPlugin).javaClass.classLoader as ClassLoader)
            }
        }.toTypedArray())
    }
}