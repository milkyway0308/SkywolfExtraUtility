package skywolf46.extrautility.cooldown

import java.util.concurrent.TimeUnit

class Cooldown {
    var data: MutableMap<String, Long> = HashMap()

    fun apply(name: String, cool: Long) {
        data[name] = System.currentTimeMillis() + cool
    }

    fun applyUnit(name: String, cool: Long, unit: TimeUnit) {
        apply(name, TimeUnit.MILLISECONDS.convert(cool, unit))
    }


    fun applyUnit(name: String, cool: Int, unit: TimeUnit) {
        applyUnit(name, cool.toLong(), unit)
    }


    fun applyUnit(name: String, cool: Double, unit: TimeUnit) {
        applyUnit(name, cool.toLong(), unit)
    }


    fun applyUnit(name: String, cool: Float, unit: TimeUnit) {
        applyUnit(name, cool.toLong(), unit)
    }


    fun applyUnit(name: String, cool: Short, unit: TimeUnit) {
        applyUnit(name, cool.toLong(), unit)
    }


    fun applyUnit(name: String, cool: Byte, unit: TimeUnit) {
        applyUnit(name, cool.toLong(), unit)
    }

    fun cooldown(name: String, unit: TimeUnit): Long {
        return unit.convert(cooldown(name), TimeUnit.MILLISECONDS)
    }

    fun cooldown(name: String): Long =
        data.getOrDefault(name, 0).run {
            if (this < System.currentTimeMillis())
                0L
            else
                this - System.currentTimeMillis()
        }
}