package skywolf46.extrautility.areas

import org.bukkit.Location
import org.bukkit.World

class ArrayLocation(val xyz: Array<Double>) : Cloneable {
    constructor(x: Location) : this(arrayOf(x.x, x.y, x.z))

    fun location(wx: World) = Location(wx, xyz[0], xyz[1], xyz[2])

    var x
        get() = xyz[0]
        set(value) {
            xyz[0] = value
        }

    var y
        get() = xyz[1]
        set(value) {
            xyz[1] = value
        }

    var z
        get() = xyz[2]
        set(value) {
            xyz[2] = value
        }

    fun swapX(arx: ArrayLocation) {
        val cache = x
        x = arx.x
        arx.x = cache
    }

    fun swapY(arx: ArrayLocation) {
        val cache = y
        y = arx.y
        arx.y = cache
    }


    fun swapZ(arx: ArrayLocation) {
        val cache = z
        z = arx.z
        arx.z = cache
    }

    public override fun clone(): ArrayLocation {
        return ArrayLocation(xyz)
    }
}