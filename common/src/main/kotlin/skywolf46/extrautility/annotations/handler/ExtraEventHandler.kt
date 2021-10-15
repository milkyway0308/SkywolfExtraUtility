package skywolf46.extrautility.annotations.handler

import kotlin.reflect.KClass

@Deprecated(
    "Deprecated from 1.63.0",
    ReplaceWith("@GlobalEventHandler()", "skywolf46.extrautility.annotations.handler.GlobalEventHandler"),
    level = DeprecationLevel.WARNING
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExtraEventHandler(val baseEvent: KClass<*> = Unit::class, val priority: Int = 0)