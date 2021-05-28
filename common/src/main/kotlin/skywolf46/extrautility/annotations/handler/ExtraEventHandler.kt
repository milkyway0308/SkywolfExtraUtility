package skywolf46.extrautility.annotations.handler

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExtraEventHandler(val baseEvent: KClass<*> = Unit::class, val priority: Int = 0)