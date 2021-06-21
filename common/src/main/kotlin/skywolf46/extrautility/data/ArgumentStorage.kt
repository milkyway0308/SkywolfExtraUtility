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

    open operator fun <T : Any> get(str: String): T? = (argumentFixed[str] ?: kotlin.run {
        for (x in proxies)
            x.get<T>(str)?.apply {
                return@run this
            }
        return@run null
    }) as T?

    open operator fun <T : Any> get(cls: Class<T>): List<T> = (arguments[cls] ?: kotlin.run {
        for (x in proxies)
            x[cls].apply {
                return@run this
            }
        return@run null
    }) as List<T>? ?: emptyList()

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