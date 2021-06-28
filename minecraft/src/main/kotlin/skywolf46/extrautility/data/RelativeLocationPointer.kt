package skywolf46.extrautility.data

import org.bukkit.util.Vector

data class RelativeLocationPointer(val x: Int, val y: Int, val z: Int) {
    companion object {
        operator fun invoke(x: Int, y: Int, z: Int): RelativeLocationPointer {
            return RelativeLocationPointer(x, y, z)
        }
        operator fun invoke(vector: Vector) : RelativeLocationPointer {
            return invoke(vector.blockX, vector.blockY, vector.blockZ)
        }
    }

    fun toVector(): Vector {
        return Vector(x, y, z)
    }
}