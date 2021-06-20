package skywolf46.extrautility.util

class TimeElapse {
    var elapsed = System.currentTimeMillis()

    fun elapsed(): Long {
        val ret = elapsed - System.currentTimeMillis()
        elapsed = System.currentTimeMillis()
        return ret
    }
}