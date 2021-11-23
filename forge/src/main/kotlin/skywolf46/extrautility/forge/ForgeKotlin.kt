package skywolf46.extrautility.forge

import net.minecraftforge.fml.common.event.FMLInitializationEvent
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.forge.util.ForgeClassLoader
import skywolf46.extrautility.util.ClassUtil
import skywolf46.extrautility.util.triggerEvent

object ForgeKotlin {
    @JvmStatic
    fun init(event: FMLInitializationEvent) {
        println("ExtraUtility-Forge | Init..")
        ClassUtil.updator = {
            ForgeClassLoader.loadAllClass().filter {
                it.name.startsWith("skywolf46.extrautility.forge")
            }
        }

        println(ClassUtil.getCache().list)
        println(ClassUtil.getCache().toMethodFilter().methods)
        println("ExtraUtility-Forge | Processing annotation..")
        ExtraUtilityCore.processAnnotations()

        println("ExtraUtility-Forge | Test..")
        "Test!!".triggerEvent()

    }
}