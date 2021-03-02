package skywolf46.extrautility.util

import org.bukkit.Material
import skywolf46.extrautility.util.MaterialUtil.ItemTypeData




class MaterialUtil {
    fun parseStringName(type: String): ItemTypeData? {
        var type = type
        var durability: Short = 0
        if (type.contains(":")) {
            val split = type.split(":").toTypedArray()
            type = split[0]
            durability = split[1].toShort()
        }
        val data = Material.matchMaterial(type) ?: return null
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