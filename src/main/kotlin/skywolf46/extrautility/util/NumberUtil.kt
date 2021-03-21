package skywolf46.extrautility.util

fun String.toDouble(block: Double.() -> Unit): Boolean {
    return try {
        block(toDouble())
        true
    } catch (e: Exception) {
        false
    }
}


fun String.letDouble(def: Double, block: Double.() -> Double): Double {
    return try {
        block(toDouble())
    } catch (e: Exception) {
        def
    }
}

fun String.toInt(block: Int.() -> Unit): Boolean {
    return try {
        block(toInt())
        true
    } catch (e: Exception) {
        false
    }
}

fun String.letInt(def: Int, block: Int.() -> Int): Int {
    return try {
        block(toInt())
    } catch (e: Exception) {
        def
    }
}

fun String.toFloat(block: Float.() -> Unit): Boolean {
    return try {
        block(toFloat())
        true
    } catch (e: Exception) {
        false
    }
}


fun String.letFloat(def: Float, block: Float.() -> Float): Float {
    return try {
        block(toFloat())
    } catch (e: Exception) {
        def
    }
}
