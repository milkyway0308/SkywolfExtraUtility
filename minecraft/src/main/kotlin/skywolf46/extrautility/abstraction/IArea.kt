package skywolf46.extrautility.abstraction

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

interface IArea {
    fun isInArea(x: Location): Boolean

    fun point(): Int

    fun create(x: Array<Location>): IArea?

    fun world(): World

    fun locations(): Array<Location>

    fun snapshot(): IAreaSnapshot

    fun parseBlocks(unit: World.(Vector) -> Unit)
}