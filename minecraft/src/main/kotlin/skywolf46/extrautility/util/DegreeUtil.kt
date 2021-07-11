package skywolf46.extrautility.util

import org.bukkit.Location
import org.bukkit.util.Vector
import skywolf46.extrautility.data.DegreeData
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

const val DOUBLE_PI = PI * 2
fun Location.asFacingDegree(to: Location): DegreeData {
    val direction = to.toVector().subtract(toVector()).normalize()
    with(direction) {
        if (x == 0.0 && z == 0.0) {
            return DegreeData(0.0, if (y > 0.0) -90.0 else 90.0)
        }
        val theta = atan2(-x, z)
        val yaw = Math.toDegrees((theta + DOUBLE_PI) % DOUBLE_PI)
        val pitch = Math.toDegrees(
            atan(-y / (x * x + z * z))
        )
        return DegreeData(yaw, pitch)
    }
}