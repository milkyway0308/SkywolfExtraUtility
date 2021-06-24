package skywolf46.extrautility.data

import skywolf46.extrautility.util.ClassUtil.iterateParentClasses
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
open class ArgumentStorage {
    protected var arguments = mutableMapOf<Class<*>, MutableList<Any>>()
    protected var argumentFixed = mutableMapOf<String, Any>()
    protected var proxies = mutableListOf<ArgumentStorage>()

    open fun shallowCopy(shallowCopyProxy: Boolean): ArgumentStorage {
        val args = ArgumentStorage()
        args.arguments = arguments
        args.argumentFixed = argumentFixed
        if (shallowCopyProxy)
            args.proxies = proxies
        else
            args.proxies = ArrayList(proxies)
        return args
    }

    open fun deepCopy(): ArgumentStorage {
        val args = ArgumentStorage()
        args.arguments = HashMap(arguments)
        args.argumentFixed = HashMap(argumentFixed)
        args.proxies = ArrayList(proxies)
        return args
    }

    fun addProxy(proxy: ArgumentStorage) {
        proxies.add(proxy)
    }

    fun removeProxy(proxy: ArgumentStorage) {
        proxies.remove(proxy)
    }

    open operator fun <T : Any> get(str: String): T? {
        for (x in proxies.size - 1..0) {
            proxies[x].get<T>(str)?.run {
                return this
            }
        }
        return argumentFixed[str] as T?
    }

    open operator fun <T : Any> get(cls: Class<T>): List<T> {
        val next = mutableListOf<Any>()
        for (x in proxies.size - 1..0) {
            next.addAll(proxies[x].get(cls))
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

    fun add(cls: Class<*>, args: Any) {
        arguments.computeIfAbsent(cls) { mutableListOf() }.add(args)
    }

    fun setArgument(key: String, any: Any) {
        argumentFixed[key] = any
    }

    open fun addArgument(any: Any) {
        any.javaClass.iterateParentClasses {
            arguments.computeIfAbsent(this) { mutableListOf() }.add(any)
        }
    }

}