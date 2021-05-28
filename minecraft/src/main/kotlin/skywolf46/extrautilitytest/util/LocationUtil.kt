package skywolf46.extrautilitytest.util

import org.bukkit.Location
import skywolf46.extrautilitytest.abstraction.IArea
import skywolf46.extrautilitytest.areas.ArrayLocation
import skywolf46.extrautilitytest.areas.impl.RectangleArea

fun Location.toArea(x: Location): IArea? = RectangleArea.instance.create(arrayOf(this, x))
fun Location.array(): ArrayLocation = ArrayLocation(this)
