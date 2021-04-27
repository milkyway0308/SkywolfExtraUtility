package skywolf46.extrautility.cooldown

import java.util.concurrent.TimeUnit

class Cooldown {
    var data: MutableMap<String, Long> = HashMap()

    fun apply(name: String, cool: Long) {
        data.put(name, System.currentTimeMillis() + cool)
    }

    fun apply(name: String, cool: Double) {
        apply(name, cool * 1000L)
    }

    fun cooldown(name: String, unit: TimeUnit): Long {
        return unit.convert(cooldown(name), TimeUnit.MILLISECONDS)
    }

    fun cooldown(name: String): Long = if (data.getOrDefault(name, 0) < System.currentTimeMillis()) {
        0
    } else {
        data[name]!!.minus(System.currentTimeMillis())
    }
}