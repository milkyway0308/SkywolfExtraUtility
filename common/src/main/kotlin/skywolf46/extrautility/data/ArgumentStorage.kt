package skywolf46.extrautility.data

import skywolf46.extrautility.util.ClassUtil.iterateParentClasses
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
open class ArgumentStorage {
    protected var arguments = mutableMapOf<Class<*>, MutableList<Any>>()
    protected var argumentFixed = mutableMapOf<String, Any>()
    protected var proxies = mutableListOf<ArgumentStorage>()

    fun keySet() = ArrayList(argumentFixed.keys)

    open fun shallowCopy(shallowCopyProxy: Boolean): ArgumentStorage {
        val args = newInstance()
        args.arguments = arguments
        args.argumentFixed = argumentFixed
        if (shallowCopyProxy)
            args.proxies = proxies
        else
            args.proxies = ArrayList(proxies)
        return args
    }

    open fun deepCopy(): ArgumentStorage {
        val args = newInstance()
        args.arguments = HashMap(arguments)
        args.argumentFixed = HashMap(argumentFixed)
        args.proxies = ArrayList(proxies)
        return args
    }

    open fun newInstance() = ArgumentStorage()

    open fun addProxy(proxy: ArgumentStorage) {
        proxies.add(proxy)
    }

    open fun removeProxy(proxy: ArgumentStorage) {
        proxies.remove(proxy)
    }

    open operator fun <T : Any> get(str: String): T? {
        for (x in proxies.size downTo 1) {
            proxies[x - 1].get<T>(str)?.run {
                return this
            }
        }
        return argumentFixed[str] as T?
    }

    open operator fun <T : Any> get(cls: Class<T>): List<T> {
        val next = mutableListOf<Any>()
        for (x in proxies.size downTo 1) {
            next.addAll(proxies[x - 1].get(cls))
        }
        arguments[cls]?.apply {
            next.addAll(this)
        }
        return next as List<T>
    }

    open operator fun <T : Any> get(cls: KClass<T>): List<T> = get(cls.java)

    open operator fun set(key: String, any: Any) {
        setArgument(key, any)
    }

    open operator fun plusAssign(any: Any) {
        addArgument(any)
    }

    open fun add(cls: Class<*>, args: Any) {
        arguments.computeIfAbsent(cls) { mutableListOf() }.add(args)
    }

    open fun setArgument(key: String, any: Any) {
        argumentFixed[key] = any
    }

    open fun addArgument(any: Any) {
        val proceed = mutableListOf<Class<*>>()
        any.javaClass.iterateParentClasses {
            if (this in proceed)
                return@iterateParentClasses
            proceed += this
            arguments.computeIfAbsent(this) { mutableListOf() }.apply {
                add(any)
            }
        }
    }

}