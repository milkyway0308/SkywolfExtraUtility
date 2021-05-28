package skywolf46.extrautility.annotations.handler

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExtraJavaEventHandler(val baseEvent: String = "kotlin.Unit", val priority: Int = 0)