package skywolf46.extrautility.util

import skywolf46.extrautility.enums.EnumTimeUnit


fun Int.toTimeString(): String {
    var second = this
    var minutes = second / 60
    second -= minutes * 60
    var hours = minutes / 60
    minutes -= hours * 60
    var days = hours / 24
    hours -= days * 24
    val builder = StringBuilder()
    if (days != 0)
        builder.append("${days}시간 ")
    if (hours != 0)
        builder.append("${hours}시간 ")
    if (minutes != 0)
        builder.append("${minutes}분 ")
    builder.append("${second}초")
    return builder.toString()
}

infix fun Int.from(unitData: EnumTimeUnit) = unitData of this.toLong()
fun EnumTimeUnit.TimeUnitData.toTimeString(
    doSort: Boolean = true,
    skipIfZeroPrefix: Boolean = true,
    skipIfZeroSuffix: Boolean = true,
    ignoreIfZero: Boolean = false,
    vararg acceptUnits: Pair<EnumTimeUnit, String>
) {
    val strLists = mutableListOf<Pair<Int, EnumTimeUnit>>()
    val map = mutableMapOf(*acceptUnits)
    val list = if (doSort) map.keys.sortedBy { it.multiplier } else map.keys.toList()
    val baseSecond = this.of(EnumTimeUnit.millisecond)
    for (x in acceptUnits) {

    }
}