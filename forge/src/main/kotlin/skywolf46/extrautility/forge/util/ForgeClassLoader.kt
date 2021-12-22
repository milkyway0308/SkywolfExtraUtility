package skywolf46.extrautility.forge.util

import net.minecraftforge.fml.common.Loader
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.extrautility.forge.ForgeMain
import skywolf46.extrautility.util.ClassUtil

object ForgeClassLoader {
    var isRestricted = true
        private set

    fun loadAllClass(): List<Class<*>> {
        return ClassUtil.scanClassExactly(ExtraUtilityCore.getIgnoredList(), true, *mutableListOf<ClassLoader>(ForgeMain::class.java.classLoader).apply {
            for (x in Loader.instance().activeModList) {
                println("ExtraUtility-Forge | Added mod ${x.name}")
                if (isRestricted) {
                    if (x.javaClass.getAnnotation(AllowScanning::class.java) != null) {
                        add(x.mod.javaClass.classLoader as ClassLoader)
                    }
                } else {
                    add(x.mod.javaClass.classLoader as ClassLoader)
                }
            }
        }.toTypedArray())
    }

    fun setRestricted() {
        isRestricted = true
    }
}