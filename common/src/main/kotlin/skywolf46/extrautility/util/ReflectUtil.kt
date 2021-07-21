package skywolf46.extrautility.util

fun <T : Any?> Any.extractField(fieldName: String): Any? {
    try {
        javaClass.getDeclaredField(fieldName).apply {
            val orig = isAccessible
            isAccessible = true
            return get(this@extractField)?.apply {
                isAccessible = orig
            }
        }
    } catch (e: Exception) {
        return null
    }
}

fun Any.setField(fieldName: String, data: Any?) {
    try {
        javaClass.getDeclaredField(fieldName).apply {
            val orig = isAccessible
            isAccessible = true
            return set(this@setField, data).apply {
                isAccessible = orig
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}