package skywolf46.extrautility

import skywolf46.extrautility.annotations.handler.ExtraEventHandler
import skywolf46.extrautility.annotations.handler.ExtraJavaEventHandler
import skywolf46.extrautility.data.EventStorage
import skywolf46.extrautility.util.ClassUtil
import skywolf46.extrautility.util.MethodUtil
import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

object ExtraUtilityCore {
    val logger = Logger.getLogger("SkywolfExtraUtility")
    private val eventStorage = mutableMapOf<String, EventStorage>()
    private val ignored = mutableListOf<String>()

    init {
        addIgnored(
            "kotlin",
            "io.gnu",
            "io.netty",
            "java.lang",
            "com.intellij",
            "org.junit",
            "sun.",
            "com.sun",
            "io.github.classgraph",
            "jdk.",
            "junit.",
            "nonapi.io"
        )
    }

    fun addIgnored(vararg packageName: String) {
        ignored += packageName
    }

    fun scanHandlers() {
        MethodUtil.filter(*ClassUtil.scanClass(ignored).toTypedArray())
            .filter(false, ExtraEventHandler::class.java, ExtraEventHandler::class.java)
            .filter({
                logger.warning("Warning: Method ${method.name} in ${method.declaringClass.name} requires instance. Event handling rejected.")
            }, MethodUtil.ReflectionMethodFilter.INSTANCE_NOT_REQUIRED)
            .unlockAll()
            .methods.forEach { methodWrapper ->
                if (methodWrapper.method.parameters.size != 1) {
                    logger.severe("Cannot parse method ${methodWrapper.method.name} in class ${methodWrapper.method.declaringClass.name} : ExtraEventHandler requires one parameter")
                    return@forEach
                }
                var name = ""
                var priority = 0
                methodWrapper.method.getDeclaredAnnotation(ExtraEventHandler::class.java)?.let { handler ->
                    name = handler.baseEvent.jvmName
                    priority = handler.priority
                } ?: methodWrapper.method.getDeclaredAnnotation(ExtraJavaEventHandler::class.java).let { handler ->
                    name = handler.baseEvent
                    priority = handler.priority
                }
                getEventStorage(name).insertEventHandler(
                    methodWrapper.method.parameters[0].type, priority, methodWrapper
                )
            }
    }

    @JvmStatic
    @JvmOverloads
    fun getEventStorage(name: String = "kotlin.Unit"): EventStorage {
        return eventStorage.computeIfAbsent(name) {
            EventStorage()
        }
    }

    fun getEventStorage(cls: KClass<*>): EventStorage {
        return getEventStorage(cls.jvmName)
    }

    fun callEvent(data: Any) {
        callEvent(Unit.javaClass.name, data)
    }

    fun callEvent(cls: String, data: Any) {
        getEventStorage(cls).callEvent(data)
    }

    fun callEvent(cls: KClass<*>, data: Any) {
        getEventStorage(cls).callEvent(data)
    }
}