package skywolf46.extrautility

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.ExtraUtilityCore
import skywolf46.extrautility.abstraction.IThreadSubmitter
import skywolf46.extrautility.areas.impl.RectangleArea
import skywolf46.extrautility.listener.DamageListener
import skywolf46.extrautility.listener.InteractionListener
import skywolf46.extrautility.util.MinecraftLoader
import skywolf46.extrautility.util.ThreadingUtil
import skywolf46.extrautility.util.log
import skywolf46.extrautility.util.schedule
import java.io.InputStreamReader
import java.util.jar.JarFile

internal lateinit var inst: SkywolfExtraUtility
    private set

class SkywolfExtraUtility : JavaPlugin() {

    override fun onEnable() {
        inst = this
        log("§e[ExtraUtility] §7Initializing " + getVersion())
        log("§e[ExtraUtility] §7Updating main thread")
        ThreadingUtil.MAIN_THREAD = object : IThreadSubmitter {
            override fun submit(unit: () -> Unit) {
                schedule(unit)
            }

            override fun stop() {
                // Nothing to do
            }

        }
        try {
            val jar = JarFile(file)
            val yaml =
                YamlConfiguration.loadConfiguration(InputStreamReader(jar.getInputStream(jar.getJarEntry("plugin.yml"))))
            if (yaml.contains("utility-mode") && yaml["utility-mode"].equals("restricted")) {
                log("§e[ExtraUtility] §cRunning plugin as restricted mode.")
                log("§e[ExtraUtility] §cSkywolfExtraUtility will not scan plugin not contains \"@AllowScanning\" on main class.")
                MinecraftLoader.setRestricted()
            }
        } catch (_: Exception) {
            // Ignored
        }
        ExtraUtilityCore.scanHandlers(MinecraftLoader.loadAllClass())
        Bukkit.getPluginManager().registerEvents(DamageListener(), this)
        try {
            Class.forName("org.bukkit.inventory.EquipmentSlot")
            Bukkit.getPluginManager().registerEvents(InteractionListener(), this)
        } catch (e: Exception) {
            log("§e[ExtraUtility] §cUnsupported version! Disabling event support.")
        }
    }


    fun getVersion(): String? = description.version
}