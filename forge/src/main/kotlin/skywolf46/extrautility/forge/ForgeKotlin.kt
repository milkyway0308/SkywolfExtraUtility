package skywolf46.extrautility.forge

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.forge.util.ForgeClassLoader
import skywolf46.extrautility.util.ClassUtil
import skywolf46.extrautility.util.triggerEvent

object ForgeKotlin {
    @JvmStatic
    fun init(event: FMLPostInitializationEvent) {
        println("ExtraUtility-Forge | Init..")
        ClassUtil.updator = {
            ForgeClassLoader.loadAllClass()
        }

        println("ExtraUtility-Forge | Processing annotation..")
        ExtraUtilityCore.processAnnotations()

        println("ExtraUtility-Forge | Test..")

    }
}