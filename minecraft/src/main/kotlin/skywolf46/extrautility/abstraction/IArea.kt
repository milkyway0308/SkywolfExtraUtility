package skywolf46.extrautility.abstraction

import org.bukkit.Location
import org.bukkit.World

interface IArea {
    fun isInArea(x: Location): Boolean

    fun point(): Int

    fun create(x: Array<Location>): IArea?

    fun world(): World

    fun locations(): Array<Location>
}