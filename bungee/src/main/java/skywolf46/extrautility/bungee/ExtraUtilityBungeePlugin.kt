package skywolf46.extrautility.bungee

import net.md_5.bungee.api.plugin.Event
import net.md_5.bungee.api.plugin.Plugin
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.extrautility.bungee.impl.BungeeEventProducer
import skywolf46.extrautility.bungee.util.BungeecordClassLoader
import skywolf46.extrautility.util.ClassUtil
import skywolf46.extrautility.util.EventUtil

@AllowScanning
class ExtraUtilityBungeePlugin : Plugin() {
    companion object {
        lateinit var inst: ExtraUtilityBungeePlugin
            private set
    }

    override fun onEnable() {
        inst = this
        EventUtil.registerProducer(Event::class.java, BungeeEventProducer)
        ClassUtil.addUpdater {
            BungeecordClassLoader.loadAllClass()
        }
        ExtraUtilityCore.processAnnotations()

    }
}