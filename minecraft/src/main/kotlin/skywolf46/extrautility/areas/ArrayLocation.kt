package skywolf46.extrautility.areas

import org.bukkit.Location
import org.bukkit.World

class ArrayLocation(x: Location) {
    val xyz: Array<Double> = arrayOf(x.x, x.y, x.z)

    fun location(wx: World) = Location(wx, xyz[0], xyz[1], xyz[2])

    fun x() = xyz[0]

    fun y() = xyz[1]

    fun z() = xyz[2]

    fun x(arx: ArrayLocation) {
        val cache = x()
        xyz[0] = arx.x()
        arx.xyz[0] = cache
    }


    fun y(arx: ArrayLocation) {
        val cache = y()
        xyz[1] = arx.y()
        arx.xyz[1] = cache
    }


    fun z(arx: ArrayLocation) {
        val cache = z()
        xyz[2] = arx.z()
        arx.xyz[2] = cache
    }

}