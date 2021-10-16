package skywolf46.extrautility.util

fun String.toDouble(block: Double.() -> Unit): Boolean {
    return try {
        block(toDouble())
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.toPercentage(def: Double): Double {
    return try {
        (if (endsWith('%')) substring(0 until length) else this).toDouble()
    } catch (e: Throwable) {
        return def
    }.coerceAtLeast(0.0).coerceAtMost(100.0)
}

fun String.letDouble(def: Double, block: Double.() -> Double): Double {
    return try {
        block(toDouble())
    } catch (e: NumberFormatException) {
        def
    }
}

fun String.toInt(block: Int.() -> Unit): Boolean {
    return try {
        block(toInt())
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.letInt(def: Int, block: Int.() -> Int): Int {
    return try {
        block(toInt())
    } catch (e: NumberFormatException) {
        def
    }
}

fun String.toFloat(block: Float.() -> Unit): Boolean {
    return try {
        block(toFloat())
        true
    } catch (e: NumberFormatException) {
        false
    }
}


fun String.letFloat(def: Float, block: Float.() -> Float): Float {
    return try {
        block(toFloat())
    } catch (e: NumberFormatException) {
        def
    }
}
