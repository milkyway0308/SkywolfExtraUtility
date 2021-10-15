package skywolf46.extrautility.annotations

/**
 * Minecraft configuration serializable auto registration.
 *
 * SkywolfExtraUtility will automatically register annotated class.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class MinecraftSerialize()
