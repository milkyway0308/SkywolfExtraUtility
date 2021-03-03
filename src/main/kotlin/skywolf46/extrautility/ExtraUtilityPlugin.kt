package skywolf46.extrautility

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.listener.DamageListener
import skywolf46.extrautility.listener.InteractionListener
import skywolf46.extrautility.util.log

internal lateinit var inst: ExtraUtilityPlugin
    private set
class ExtraUtilityPlugin : JavaPlugin() {

    override fun onEnable() {
        inst = this
        log("§e[ExtraUtility] §7Initializing " + getVersion())
        Bukkit.getPluginManager().registerEvents(DamageListener(), this)
        Bukkit.getPluginManager().registerEvents(InteractionListener(), this)

    }

    fun getVersion(): String? = description.version
}