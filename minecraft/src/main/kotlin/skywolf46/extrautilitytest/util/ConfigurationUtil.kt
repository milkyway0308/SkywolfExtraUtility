package skywolf46.extrautilitytest.util

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getMap(key: String): Map<String, Any> {
    return (getConfigurationSection(key)?.let {
        val map = mutableMapOf<String, Any>()
        it.getKeys(false).forEach { key ->
            map[key] = it[key]
        }
        return map
    } ?: run {
        return mapOf()
    })
}



