package skywolf46.extrautility.util

import skywolf46.extrautility.annotations.parameter.Named
import skywolf46.extrautility.data.ArgumentStorage
import java.lang.reflect.Method

class MethodInvoker(val method: Method, private val instance: Any?) {
    private val indexed = mutableListOf<Class<*>?>()
    private val variableIndexed = mutableMapOf<Int, Pair<String, Class<*>>>()

    constructor(invoker: MethodWrapper) : this(invoker.method, invoker.instance)

    init {
        for (i in 0 until method.parameterCount) {
            val param = method.parameters[i]
            val namedParameter = param.getAnnotation(Named::class.java)
            if (namedParameter != null) {
                variableIndexed[i] = namedParameter.name to param.type
                indexed.add(null)
            } else {
                indexed.add(param.type)
            }
        }
    }

    operator fun invoke(storage: ArgumentStorage) : Any?{
        val arrNullable = Array<Any?>(indexed.size) { null }
        for (x in 0 until indexed.size) {
            arrNullable[x] = variableIndexed[x]?.run { storage[this.first] } ?: storage[indexed[x]!!]?.get(0)
        }
        return method.invoke(instance, *arrNullable)
    }
}