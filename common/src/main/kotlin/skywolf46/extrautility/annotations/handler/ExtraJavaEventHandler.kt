package skywolf46.extrautility.annotations.handler

@Deprecated(
    "Deprecated from 1.63.0",
    ReplaceWith("@GlobalEventHandler()", "skywolf46.extrautility.annotations.handler.GlobalEventHandler"),
    level = DeprecationLevel.WARNING
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExtraJavaEventHandler(val baseEvent: String = "kotlin.Unit", val priority: Int = 0)