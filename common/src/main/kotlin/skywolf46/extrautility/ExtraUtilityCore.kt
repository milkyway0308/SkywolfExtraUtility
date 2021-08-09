package skywolf46.extrautility

import skywolf46.extrautility.annotations.handler.ExtraEventHandler
import skywolf46.extrautility.annotations.handler.ExtraJavaEventHandler
import skywolf46.extrautility.data.EventStorage
import skywolf46.extrautility.util.ClassUtil
import skywolf46.extrautility.util.MethodUtil
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

object ExtraUtilityCore {
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
            "nonapi.io",
            "org.bukkit",
            "org.apache",
            "com.fasterxml",
            "com.mysql",
            "org.json",
            "org.jetbrains",
            "org.intellij",
            "org.fusesource",
            "org.spigotmc",
            "org.bukkit",
            "net.minecraft.server",
            "gnu.trove",
            "com.google",
            "org.yaml",
            "javax.",
            "org.sqlite",
            "it.unimi.dsi",
            "com.ibm",
            "net.minecraftforge",
            "scala",
            "org.codehaus",
            "akka.",
            "LMZA.",
            "ibxm.",
            "com.typesafe",
            "catserver.",
            "jline.",
            "joptsimple.",
            "mcp.",
            "nl.komponents",
            "org.objectweb",
            "org.hamcrest",
            "oshi.",
            "guava10.",
            "foxz.",
            "cpw.mods.",
            "com.avaje",
            "cern.colt",

        )
    }

    fun getIgnoredList() = ArrayList(ignored)

    fun addIgnored(vararg packageName: String) {
        ignored += packageName
    }


    fun scanHandlers(defClass: List<Class<*>>? = null) {
        MethodUtil.filter( *(defClass?.toTypedArray() ?: ClassUtil.scanClass(ignored).toTypedArray()))
            .filter(false, ExtraEventHandler::class.java, ExtraEventHandler::class.java)
            .filter({
                System.err.println("Warning: Method ${method.name} in ${method.declaringClass.name} requires instance. Event handling rejected.")
            }, MethodUtil.ReflectionMethodFilter.INSTANCE_NOT_REQUIRED)
            .unlockAll()
            .methods.forEach { methodWrapper ->
                if (methodWrapper.method.parameters.size != 1) {
                    System.err.println("Cannot parse method ${methodWrapper.method.name} in class ${methodWrapper.method.declaringClass.name} : ExtraEventHandler requires one parameter")
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