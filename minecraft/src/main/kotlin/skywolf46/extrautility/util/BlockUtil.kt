package skywolf46.extrautility.util

import org.bukkit.block.Block
import org.bukkit.metadata.FixedMetadataValue
import skywolf46.extrautility.inst


fun Block.hasValue(str: String): Boolean = hasMetadata(str)

fun Block.setValue(str: String, data: Any) = setMetadata(str, FixedMetadataValue(inst, data))

fun Block.removeValue(str: String): Boolean = hasMetadata(str).ifTrue { removeMetadata(str, inst) }


@Suppress("UNCHECKED_CAST")
fun <T> Block.getValueOrNull(str: String): T? =
    if (hasValue(str)) getMetadata(str)[0].value() as T else null

@Suppress("UNCHECKED_CAST")
fun <T> Block.getValue(str: String, default: T): T =
    if (hasValue(str)) getMetadata(str)[0].value() as T else default

fun <T> Block.getOrSetValue(str: String, provider: () -> T): T =
    hasValue(str)
        .ifFalse {
            setValue(str, provider() as Any)
        }.let {
            return getValueOrNull(str)!!
        }

fun Block.intValue(str: String, default: Int): Int = getValue(str, default)

fun Block.booleanValue(str: String, default: Boolean): Boolean = getValue(str, default)

fun Block.longValue(str: String, default: Long): Long = getValue(str, default)

fun Block.doubleValue(str: String, default: Double): Double = getValue(str, default)

fun Block.stringValue(str: String, default: String): String = getValue(str, default)

fun <K, V> Block.mapValue(str: String): Map<K, V> = getOrSetValue(str) { HashMap() }

fun <K> Block.listValue(str: String): List<K> = getOrSetValue(str) { ArrayList() }

operator fun <T : Any> Block.get(str: String) = getValueOrNull<T>(str)

operator fun <T : Any> Block.set(str: String, data: T) = setValue(str, data)

operator fun Block.minusAssign(str: String) {
    removeValue(str)
}