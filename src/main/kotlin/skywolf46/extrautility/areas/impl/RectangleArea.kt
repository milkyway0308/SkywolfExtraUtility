package skywolf46.extrautility.areas.impl

import org.bukkit.Location
import org.bukkit.World
import skywolf46.extrautility.abstraction.IArea
import skywolf46.extrautility.areas.ArrayLocation
import skywolf46.extrautility.util.inRange
import java.util.*
import java.util.stream.Collectors

class RectangleArea(loc: Array<Location>) : IArea {
    companion object {
        val instance: RectangleArea = RectangleArea(arrayOf())
    }

    var locs: Array<ArrayLocation> =
        Arrays.stream(loc).map { x -> ArrayLocation(x) }.collect(Collectors.toList()).toTypedArray()

    val baseWorld: World = loc[0].world

    init {
        if (locs[0].x() > locs[1].x()) locs[0].x(locs[1])
        if (locs[0].y() > locs[1].y()) locs[0].y(locs[1])
        if (locs[0].z() > locs[1].z()) locs[0].z(locs[1])
    }

    override fun isInArea(x: Location): Boolean {
        return x.x.inRange(locs[0].x(), locs[1].x())
                && x.y.inRange(locs[0].y(), locs[1].y())
                && x.z.inRange(locs[0].z(), locs[1].z());
    }

    override fun point(): Int = 2

    override fun create(x: Array<Location>): IArea? {
        if (x.size != point())
            return null
        return RectangleArea(x)
    }

    override fun world(): World = baseWorld

    override fun locations(): Array<Location> = locs.map { x -> x.location(baseWorld) }.toTypedArray()
}