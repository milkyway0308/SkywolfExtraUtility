package skywolf46.extrautility.util

import org.bukkit.Location
import skywolf46.extrautility.abstraction.IArea
import skywolf46.extrautility.areas.ArrayLocation
import skywolf46.extrautility.areas.impl.RectangleArea

fun Location.toArea(x: Location): IArea? = RectangleArea.instance.create(arrayOf(this, x))
fun Location.array(): ArrayLocation = ArrayLocation(this)
