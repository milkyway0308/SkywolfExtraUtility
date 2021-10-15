package skywolf46.extrautility.util

import org.bukkit.Material


class MaterialUtil {
    fun parseStringName(type: String): ItemTypeData? {
        var targetType = type
        var durability: Short = 0
        if (targetType.contains(":")) {
            val split = targetType.split(":").toTypedArray()
            targetType = split[0]
            durability = split[1].toShort()
        }
        val data = Material.matchMaterial(targetType) ?: return null
        return ItemTypeData(data, durability)
    }

    class ItemTypeData(val type: Material, val durability: Short) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ItemTypeData

            if (type != other.type) return false
            if (durability != other.durability) return false

            return true
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + durability
            return result
        }
    }
}