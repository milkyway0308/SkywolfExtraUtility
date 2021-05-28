package skywolf46.extrautility.util

import java.lang.reflect.Method

data class MethodWrapper(val method: Method, val instance: Any?) {
    fun invoke(vararg data: Any?) {
        method.invoke(instance, *data)
    }

}