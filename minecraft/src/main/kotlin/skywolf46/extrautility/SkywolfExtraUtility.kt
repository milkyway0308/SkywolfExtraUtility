package skywolf46.extrautility

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.event.Event
import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.abstraction.IThreadSubmitter
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.extrautility.annotations.MinecraftSerialize
import skywolf46.extrautility.impl.BukkitEventProvider
import skywolf46.extrautility.listener.DamageListener
import skywolf46.extrautility.listener.InteractionListener
import skywolf46.extrautility.util.*
import java.io.InputStreamReader
import java.util.jar.JarFile

@AllowScanning
class SkywolfExtraUtility : JavaPlugin() {

    companion object {
        internal lateinit var inst: SkywolfExtraUtility
            private set
    }

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
        log("§e[ExtraUtility] §7Updating class cache")
        ClassUtil.updator = {
            MinecraftLoader.loadAllClass()
        }
        EventUtil.registerProducer(Event::class.java, BukkitEventProvider())
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
        log("§e[ExtraUtility] §7Processing annotations")
        ExtraUtilityCore.processAnnotations()
        log("§e[ExtraUtility] §7Processing annotations - Stage 2")
        processAnnotations()
        Bukkit.getPluginManager().registerEvents(DamageListener(), this)
        try {
            Class.forName("org.bukkit.inventory.EquipmentSlot")
            Bukkit.getPluginManager().registerEvents(InteractionListener(), this)
        } catch (_: ClassNotFoundException) {
            log("§e[ExtraUtility] §cUnsupported version! Disabling event support.")
        }
    }

    private fun processAnnotations() {
        scanAnnotatedConfiguration()
    }


    private fun scanAnnotatedConfiguration() {
        ClassUtil.getCache().filter(MinecraftSerialize::class.java).list.forEach {
            if (ConfigurationSerializable::class.java.isAssignableFrom(it)) {
                ConfigurationSerialization.registerClass(it as Class<out ConfigurationSerializable>)
            } else
                log("§e[ExtraUtility] §cCannot register minecraft serializer class ${it.name} : Class not implements ConfigurationSerializable")
        }
    }


    fun getVersion(): String? = description.version
}