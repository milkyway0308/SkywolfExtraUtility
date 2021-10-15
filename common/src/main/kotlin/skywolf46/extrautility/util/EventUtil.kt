package skywolf46.extrautility.util

import skywolf46.extrautility.abstraction.IEventProducer
import skywolf46.extrautility.annotations.handler.GlobalEventHandler
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.extrautility.util.ClassUtil.iterateParentClasses
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.full.companionObject

/**
 * Event utility.
 */
object EventUtil {
    private val eventRegistryProvider = mutableMapOf<Class<*>, IEventProducer<*>>()
    private val eventListenerRegistry = mutableMapOf<Class<*>, MutableMap<String, HandlerStorage>>()
    fun <X : Any> registerProducer(clazz: Class<X>, producer: IEventProducer<X>) {
        eventRegistryProvider[clazz] = producer
    }

    fun triggerEvent(any: Any, sector: String = "") {
        eventListenerRegistry[any::class.java]?.get(sector)?.triggerEvent(any)
    }


    fun triggerEvent(any: Any, sector: String = "", priority: Int = 0) {
        eventListenerRegistry[any::class.java]?.get(sector)?.triggerEvent(any, priority)
    }

    fun registerViaAnnotation(wrapper: MethodWrapper): EventRegistrationResult =
        registerViaAnnotation(wrapper.method, wrapper.instance)

    // Companion / Object registration
    fun registerViaAnnotation(method: Method, instance: Any?): EventRegistrationResult {
        println("Processing ${method.name}")
        method.getAnnotation(GlobalEventHandler::class.java)?.let { handler ->
            println("..Registering ${method.name}")
            if (method.parameters.isEmpty()) {
                return EventRegistrationResult.TOO_FEW_ARGUMENTS
            }
            if (method.parameters.size > 1) {
                return EventRegistrationResult.TOO_MANY_ARGUMENTS
            }
            val next = method.parameters[0].type
            // Registration
            next.iterateParentClasses {
                (eventRegistryProvider[this] as IEventProducer<Any>?)?.run {
                    produce(next as Class<Any>, handler.handlerSector, handler.priority)
                }
            }
            method.unlock()
            eventListenerRegistry.computeIfAbsent(next) {
                mutableMapOf()
            }.apply {
                computeIfAbsent("") {
                    HandlerStorage()
                }.registerEvent(handler.priority, MethodInvoker(method, instance))
                if (handler.handlerSector.isNotEmpty())
                    computeIfAbsent(handler.handlerSector) {
                        HandlerStorage()
                    }.registerEvent(handler.priority, MethodInvoker(method, instance))
            }
            return EventRegistrationResult.SUCCESS
        }
        return EventRegistrationResult.NO_ANNOTATION
    }


    fun registerViaClass(cls: Class<Any>) {
        if (cls.kotlin.isCompanion)
            return
        // Basic static check
        for (x in cls.declaredMethods) {
            if (Modifier.isStatic(x.modifiers)) {
                registerViaAnnotation(x, null)
                continue
            }
        }
        val next = cls.kotlin
        // Companion instance register
        next.companionObject?.java?.apply {
            val companion = next.companionObject!!
            for (x in this.declaredMethods) {
                registerViaAnnotation(x, companion)
            }
        }

        // Object instance register
        next.objectInstance?.apply {
            for (x in this.javaClass.declaredMethods) {
                registerViaAnnotation(x, this)
            }
        }

    }

    fun registerViaInstance(instance: Any) {
        val cls = instance.javaClass
        for (x in cls.declaredMethods) {
            if (Modifier.isStatic(x.modifiers))
                continue
            if (x.declaringClass.kotlin.isCompanion)
                continue
            x.getAnnotation(GlobalEventHandler::class.java)?.apply {
                registerViaAnnotation(x, instance)
            }
        }
    }

    enum class EventRegistrationResult(val isSuccess: Boolean) {
        SUCCESS(true),
        TOO_MANY_ARGUMENTS(false),
        TOO_FEW_ARGUMENTS(false),
        NO_ANNOTATION(false)
    }

    private class HandlerStorage {
        val map = sortedMapOf<Int, MutableList<MethodInvoker>>(Comparator.naturalOrder())


        fun registerEvent(priority: Int, invoker: MethodInvoker) {
            map.computeIfAbsent(priority) {
                arrayListOf()
            }.add(invoker)
        }

        fun triggerEvent(any: Any) {
            val storage = ArgumentStorage().apply {
                addArgument(any)
            }
            map.forEach { (_, handlers) ->
                handlers.forEach {
                    try {
                        it.invoke(storage)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }


        fun triggerEvent(any: Any, priority: Int) {
            val storage = ArgumentStorage().apply {
                addArgument(any)
            }
            map[priority]?.forEach {
                try {
                    it.invoke(storage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}

fun Any.triggerEvent() {
    EventUtil.triggerEvent(this)
}


fun Any.triggerEvent(sector: String, priority: Int) {
    EventUtil.triggerEvent(this, sector = sector, priority = priority)
}

fun Any.triggerEvent(priority: Int) {
    EventUtil.triggerEvent(this, priority = priority)
}