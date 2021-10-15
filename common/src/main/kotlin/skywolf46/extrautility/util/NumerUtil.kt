package skywolf46.extrautility.util

import kotlin.math.max
import kotlin.math.min

fun Number.inRange(x1: Number, x2: Number): Boolean =
    min(x1.toDouble(), x2.toDouble()) <= this.toDouble() && max(x1.toDouble(), x2.toDouble()) >= this.toDouble()
