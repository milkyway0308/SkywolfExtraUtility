package skywolf46.extrautility.forge

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.forge.util.ForgeClassLoader
import skywolf46.extrautility.util.ClassUtil

object ForgeKotlin {
    @JvmStatic
    fun init(event: FMLPostInitializationEvent) {
        println("ExtraUtility-Forge | Init..")
        ClassUtil.registerUpdater {
            ForgeClassLoader.loadAllClass()
        }

        println("ExtraUtility-Forge | Processing annotation..")
        ExtraUtilityCore.processAnnotations()

    }
}