package skywolf46.extrautility.util

import org.bukkit.entity.Entity
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.extrautility.util.ifFalse
import skywolf46.extrautility.util.ifTrue
import skywolf46.extrautility.inst


fun Entity.hasValue(str: String): Boolean = hasMetadata(str)

fun Entity.setValue(str: String, data: Any) = setMetadata(str, FixedMetadataValue(inst, data))

fun Entity.removeValue(str: String): Boolean = hasMetadata(str).ifTrue { removeMetadata(str, inst) }


@Suppress("UNCHECKED_CAST")
fun <T> Entity.getValueOrNull(str: String): T? =
    if (hasValue(str)) getMetadata(str)[0].value() as T else null

@Suppress("UNCHECKED_CAST")
fun <T> Entity.getValue(str: String, default: T): T =
    if (hasValue(str)) getMetadata(str)[0].value() as T else default

fun <T> Entity.getOrSetValue(str: String, provider: () -> T): T =
    hasValue(str)
        .ifFalse {
            setValue(str, provider() as Any)
        }.let {
            return getValueOrNull(str)!!
        }

fun Entity.intValue(str: String, default: Int): Int = getValue(str, default)

fun Entity.booleanValue(str: String, default: Boolean): Boolean = getValue(str, default)

fun Entity.longValue(str: String, default: Long): Long = getValue(str, default)

fun Entity.doubleValue(str: String, default: Double): Double = getValue(str, default)

fun Entity.stringValue(str: String, default: String): String = getValue(str, default)

fun <K, V> Entity.mapValue(str: String): Map<K, V> = getOrSetValue(str) { HashMap() }

fun <K> Entity.listValue(str: String): List<K> = getOrSetValue(str) { ArrayList() }

operator fun <T : Any> Entity.get(str: String) = getValueOrNull<T>(str)

operator fun <T : Any> Entity.set(str: String, data: T) = setValue(str, data)

operator fun Entity.minusAssign(str: String) {
    removeValue(str)
}
