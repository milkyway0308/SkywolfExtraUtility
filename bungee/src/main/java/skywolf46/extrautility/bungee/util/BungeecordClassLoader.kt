package skywolf46.extrautility.bungee.util

import net.md_5.bungee.BungeeCord
import net.md_5.bungee.api.plugin.Plugin
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.extrautility.util.ClassUtil

object BungeecordClassLoader {
    fun loadAllClass(): List<Class<*>> {

        return ClassUtil.scanClassExactly(ExtraUtilityCore.getIgnoredList(), false, *mutableListOf<ClassLoader>().apply {
            for (x in BungeeCord.getInstance().pluginManager.plugins) {
                if (x.javaClass.getAnnotation(AllowScanning::class.java) != null) {
                    add((x as Plugin).javaClass.classLoader as ClassLoader)
                }
            }
        }.toTypedArray())
    }
}