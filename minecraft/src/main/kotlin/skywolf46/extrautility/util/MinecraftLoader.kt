package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.annotations.AllowScanning

object MinecraftLoader {
    var isRestricted = false
        private set

    fun loadAllClass(): List<Class<*>> {
        return ClassUtil.scanClassExactly(ExtraUtilityCore.getIgnoredList(),true, *mutableListOf<ClassLoader>().apply {
            for (x in Bukkit.getPluginManager().plugins) {
                if (isRestricted) {
                    if (x.javaClass.getAnnotation(AllowScanning::class.java) != null) {
                        add((x as JavaPlugin).javaClass.classLoader as ClassLoader)
                    }
                } else {
                    add((x as JavaPlugin).javaClass.classLoader as ClassLoader)
                }
            }
        }.toTypedArray())
    }

    fun setRestricted() {
        isRestricted = true
    }
}