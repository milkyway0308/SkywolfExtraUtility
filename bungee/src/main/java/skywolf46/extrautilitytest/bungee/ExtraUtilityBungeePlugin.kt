package skywolf46.extrautilitytest.bungee

import net.md_5.bungee.api.plugin.Plugin
import skywolf46.extrautility.ExtraUtilityCore

class ExtraUtilityBungeePlugin : Plugin() {
    override fun onEnable() {
        ExtraUtilityCore.scanHandlers()
    }
}