package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Method
import java.lang.reflect.Modifier


@Deprecated("Deprecated from 1.63.0 - Use @GlobalEventHandler instead", level = DeprecationLevel.WARNING)
class HandyEventHandler(private val pl: JavaPlugin) {
    private val handyEvent = HashMap<Class<out Event>?, HandyEventListener>()


    fun registerHandler(cl: Class<*>) {
        for (mtd in cl.methods) {
            if (Modifier.isStatic(mtd.modifiers)) {
                mtd.isAccessible = true
                if (mtd.getAnnotation(EventHandler::class.java) != null) {
                    registerHandler(mtd)
                }
            } else {
                if (mtd.getAnnotation(EventHandler::class.java) != null) {
                    System.err.println("Caution: EventHandler detected on non-static method \"" + mtd.name + "\" in class " + cl.name)
                }
            }
        }
    }


    fun registerHandlerInstance(instance: Any) {
        for (mtd in instance::class.java.methods) {
            if (!Modifier.isStatic(mtd.modifiers)) {
                mtd.isAccessible = true
                if (mtd.getAnnotation(EventHandler::class.java) != null) {
                    registerHandler(mtd, instance)
                }
            } else {
                if (mtd.getAnnotation(EventHandler::class.java) != null) {
                    System.err.println("Caution: EventHandler detected on static method \"" + mtd.name + "\" in instance " + instance::class.java.name)
                }
            }
        }
    }

    private fun registerHandler(mtd: Method, obj: Any? = null) {
        val inv = HandyEventInvoker(mtd, obj)
        if (inv.baseEvent == null) return
        handyEvent.computeIfAbsent(
            inv.baseEvent
        ) { HandyEventListener() }.register(inv)
    }


    private fun registerHandler(instance: Any, mtd: Method) {
        val inv = HandyEventInvoker(mtd, instance)
        if (inv.baseEvent == null) return
        handyEvent.computeIfAbsent(
            inv.baseEvent
        ) { HandyEventListener() }.register(inv)
    }

    private inner class HandyEventListener : Listener {
        private val listeners = HashMap<EventPriority, MutableList<HandyEventInvoker>>()
        private val registered: MutableList<EventPriority> = ArrayList()
        fun register(invoker: HandyEventInvoker) {
            if (!registered.contains(invoker.priority)) {
                registered.add(invoker.priority)
                Bukkit.getPluginManager().registerEvent(
                    invoker.baseEvent, this, invoker.priority,
                    { listener, event ->
                        listenEvent(invoker.priority, event)
                    }, pl
                )
            }
            listeners.computeIfAbsent(
                invoker.priority
            ) {
                object : ArrayList<HandyEventInvoker>() {
                    override fun add(element: HandyEventInvoker): Boolean {
                        return super.add(element)
                    }
                }
            }.add(invoker)
        }

        fun listenEvent(pr: EventPriority, ev: Event) {
            listeners[pr]?.forEach { inv ->
                inv.onEvent(ev)
            }
        }
    }

    private class HandyEventInvoker(mtd: Method, val obj: Any? = null) {
        val baseEvent: Class<out Event>?
        private val methodWorker: Method
        val priority: EventPriority

        fun onEvent(ex: Event) {
            val params = arrayOfNulls<Any>(methodWorker.parameters.size)
            params[0] = ex
            try {
                methodWorker.invoke(obj, *params)
            } catch (e: Exception) {
//                e.printStackTrace()
                // TODO fix it
            }
        }

        init {
            val handler = mtd.getAnnotation(EventHandler::class.java)
            priority = handler.priority
            baseEvent =
                if (mtd.parameters.isNotEmpty() && Event::class.java.isAssignableFrom(mtd.parameters[0].type)) mtd.parameters[0].type as Class<out Event?> else null
            methodWorker = mtd
        }
    }
}