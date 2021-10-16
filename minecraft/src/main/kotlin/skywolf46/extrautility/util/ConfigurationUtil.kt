package skywolf46.extrautility.util

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getMap(key: String): MutableMap<String, Any> {
    return (getConfigurationSection(key)?.let {
        mutableMapOf<String, Any>().apply {
            it.getKeys(false).forEach { key ->
                this[key] = it[key]
            }
        }
    } ?: mutableMapOf())
}



