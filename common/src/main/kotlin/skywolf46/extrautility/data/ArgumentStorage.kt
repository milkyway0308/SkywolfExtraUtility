package skywolf46.extrautility.data

import skywolf46.extrautility.util.ClassUtil.iterateParentClasses
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class ArgumentStorage {
    private val arguments = mutableMapOf<Class<*>, MutableList<Any>>()
    private val argumentFixed = mutableMapOf<String, Any>()

    operator fun get(str: String) = argumentFixed[str]
    operator fun get(cls: Class<*>) = arguments[cls]
    operator fun get(cls: KClass<*>) = arguments[cls.java]

    operator fun set(key: String, any: Any) {
        setArgument(key, any)
    }

    operator fun plusAssign(any: Any) {
        addArgument(any)
    }

    fun setArgument(key: String, any: Any) {
        argumentFixed[key] = any
    }

    fun addArgument(any: Any) {
        any.javaClass.iterateParentClasses {
            arguments.computeIfAbsent(this) { mutableListOf() }.add(any)
        }
    }

}