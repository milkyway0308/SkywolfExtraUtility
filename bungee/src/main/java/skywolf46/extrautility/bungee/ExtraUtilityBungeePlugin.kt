package skywolf46.extrautility.bungee

import net.md_5.bungee.api.plugin.Plugin
import skywolf46.extrautility.ExtraUtilityCore

class ExtraUtilityBungeePlugin : Plugin() {
    override fun onEnable() {
        ExtraUtilityCore.scanHandlers()
    }
}