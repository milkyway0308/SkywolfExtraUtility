package skywolf46.extrautility.util

import java.lang.reflect.Field
import java.lang.reflect.Method

fun findClass(clsName: String): Class<*>? = try {
    Class.forName(clsName)
} catch (e: Throwable) {
    null
}

fun <T : Any?> Any.extractField(fieldName: String): T? {
    try {
        javaClass.getDeclaredField(fieldName).apply {
            val orig = isAccessible
            isAccessible = true
            return get(this@extractField)?.apply {
                isAccessible = orig
            } as T?
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

fun Any.invokeMethod(methodName: String, vararg args: Any): Any? {
    return try {
        javaClass.getMethod(methodName, *args.map { x -> x.javaClass }.toTypedArray()).invoke(this, *args)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// TODO Add version-specific unlock reflection
fun Method.unlock() {
    isAccessible = true
}


// TODO Add version-specific unlock reflection
fun Field.unlock() {
    isAccessible = true
}