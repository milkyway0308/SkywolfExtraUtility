package skywolf46.extrautility.enums

import skywolf46.extrautility.util.from

@Suppress("EnumEntryName")
enum class EnumTimeUnit(val multiplier: Long) {
    millisecond(1L),
    second(1000L),
    minute(second * 60L),
    hour(minute * 60L),
    day(hour * 24L),
    month(day * 31L),
    year(day * 365L)
    ;

    operator fun times(value: Long) = multiplier * value

    infix fun of(data: Long) = TimeUnitData(this, data)

    class TimeUnitData(val unit: EnumTimeUnit, val data: Long) {
        infix fun to(target: EnumTimeUnit): TimeUnitData {
            // Check distance
            return TimeUnitData(target, of(target))
        }

        infix fun of(target: EnumTimeUnit): Long {
            val toMultiply = unit.multiplier.toDouble() / target.multiplier.toDouble()
            return (data.toDouble() * toMultiply).toLong()
        }
    }

}