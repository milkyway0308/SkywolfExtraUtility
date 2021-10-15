package skywolf46.extrautility

import skywolf46.extrautility.annotations.handler.ExtraEventHandler
import skywolf46.extrautility.annotations.handler.ExtraJavaEventHandler
import skywolf46.extrautility.annotations.handler.GlobalEventHandler
import skywolf46.extrautility.data.EventStorage
import skywolf46.extrautility.util.EventUtil
import skywolf46.extrautility.util.MethodUtil
import skywolf46.extrautility.util.MethodWrapper
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
            "net.bytebuddy.",
            "se.llbit.nbt",
            "net.md_5.bungee",
            "com.mojang.brigadier"

            )
    }

    fun getIgnoredList() = ArrayList(ignored)

    fun addIgnored(vararg packageName: String) {
        ignored += packageName
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


    fun processAnnotations() {
        scanLegacyEventHandlers()
        scanEventHandlers()
    }


    private fun scanLegacyEventHandlers() {
        MethodUtil.getCache()
            .filter(false, ExtraEventHandler::class.java, ExtraJavaEventHandler::class.java)
            .filter({
                System.err.println("Warning: Method ${method.name} in ${method.declaringClass.name} requires instance. Event handling rejected.")
            }, MethodUtil.ReflectionMethodFilter.INSTANCE_NOT_REQUIRED)
            .unlockAll()
            .methods.forEach { bindEventHandler(it) }
    }

    private fun scanEventHandlers() {
        MethodUtil.getCache()
            .filter(GlobalEventHandler::class.java)
            .filter(MethodUtil.ReflectionMethodFilter.INSTANCE_NOT_REQUIRED)
            .unlockAll()
            .methods.forEach {
                EventUtil.registerViaAnnotation(it)
            }
    }

    private fun bindEventHandler(wrapper: MethodWrapper) {
        if (wrapper.method.parameters.size != 1) {
            System.err.println("Cannot parse method ${wrapper.method.name} in class ${wrapper.method.declaringClass.name} : ExtraEventHandler requires one parameter")
            return
        }
        var name = ""
        var priority = 0
        wrapper.method.getDeclaredAnnotation(ExtraEventHandler::class.java)?.let { handler ->
            name = handler.baseEvent.jvmName
            priority = handler.priority
        } ?: wrapper.method.getDeclaredAnnotation(ExtraJavaEventHandler::class.java).let { handler ->
            name = handler.baseEvent
            priority = handler.priority
        }
        getEventStorage(name).insertEventHandler(
            wrapper.method.parameters[0].type, priority, wrapper
        )
    }
}