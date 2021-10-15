package skywolf46.extrautility.annotations.handler

/**
 * Global event handler.
 * When event instance received, ExtraUtilityCore will trigger functions.
 * If [GlobalEventHandler] used in java, method must static.
 * If [GlobalEventHandler] used in kotlin, function must in object or companion class.
 *
 * Caution: Empty string section is <b>Global Event Receiver</b>.
 *          It will accept all event from any sector.
 *
 * Parameters
 *      [handlerSector]
 *      Declare event handler sector.
 *      If sector is empty, all event will be received.
 *
 *      [priority]
 *      Declare event listening priority.
 *      As lower priority is, runs earlier.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class GlobalEventHandler(val priority: Int = 0, val sector: String = "")
