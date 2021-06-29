package skywolf46.extrautility.areas.impl

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import skywolf46.extrautility.abstraction.IArea
import skywolf46.extrautility.abstraction.IAreaSnapshot
import skywolf46.extrautility.areas.ArrayLocation
import skywolf46.extrautility.enums.Direction
import skywolf46.extrautility.util.inRange
import java.util.*
import java.util.stream.Collectors

class RectangleArea(val world: World, val locs: Array<ArrayLocation>) : IArea {
    companion object {
        val instance: RectangleArea = RectangleArea(arrayOf(Location(Bukkit.getWorlds()[0], 0.0, 0.0,0.0),Location(Bukkit.getWorlds()[0], 0.0, 0.0,0.0)))
    }

    constructor(loc: Array<Location>) : this(loc[0].world, Arrays.stream(loc).map { x -> ArrayLocation(x) }
        .collect(Collectors.toList()).toTypedArray())


    init {
        println("Test")
        if (locs[0].x > locs[1].x) locs[0].swapX(locs[1])
        if (locs[0].y > locs[1].y) locs[0].swapY(locs[1])
        if (locs[0].z > locs[1].z) locs[0].swapZ(locs[1])
    }

    override fun isInArea(x: Location): Boolean {
        return x.x.inRange(locs[0].x, locs[1].x)
                && x.y.inRange(locs[0].y, locs[1].y)
                && x.z.inRange(locs[0].z, locs[1].z)
    }

    override fun point(): Int = 2

    override fun create(x: Array<Location>): IArea? {
        if (x.size != point())
            return null
        return RectangleArea(x)
    }

    override fun world(): World = world

    override fun locations(): Array<Location> = locs.map { x -> x.location(world) }.toTypedArray()

    override fun snapshot(): IAreaSnapshot {
        return RectangleAreaSnapshot(this)
    }

    fun sizeOf(direction: Direction): Double = when (direction) {
        Direction.X -> locs[1].x - locs[0].x
        Direction.Y -> locs[1].y - locs[0].y
        Direction.Z -> locs[1].z - locs[0].z
    }

    fun parseBlocks(unit: World.(Vector) -> Unit) {
        for (y in locs[0].y.toInt()..locs[1].y.toInt()) {
            for (x in locs[0].x.toInt()..locs[1].x.toInt()) {
                for (z in locs[0].z.toInt()..locs[1].z.toInt()) {
                    unit(world, Vector(x, y, z))
                }
            }
        }
    }

    fun slice(direction: Direction, amount: Int): RectangleArea {
        return RectangleArea(
            world, arrayOf(
                locs[0].clone().apply {
                    when (direction) {
                        Direction.X -> this.x = (this.x + amount).coerceAtMost(locs[1].x)
                        Direction.Y -> this.y = (this.y + amount).coerceAtMost(locs[1].y)
                        Direction.Z -> this.z = (this.z + amount).coerceAtMost(locs[1].z)
                    }
                }, locs[1]
            )
        )
    }

}