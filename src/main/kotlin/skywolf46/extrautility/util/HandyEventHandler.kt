package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.ArrayList
import java.util.function.Consumer


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

    private fun registerHandler(mtd: Method) {
        val inv = HandyEventInvoker(mtd)
        if (inv.baseEvent == null) return
        handyEvent.computeIfAbsent(
            inv.baseEvent
        ) { a: Class<out Event>? -> HandyEventListener() }.register(inv)
    }

    private inner class HandyEventListener : Listener {
        private val listeners = HashMap<EventPriority, MutableList<HandyEventInvoker>>()
        private val registered: MutableList<EventPriority> = ArrayList()
        fun register(invoker: HandyEventInvoker) {
            if (!registered.contains(invoker.priority)) {
                registered.add(invoker.priority)
                Bukkit.getPluginManager().registerEvent(invoker.baseEvent, this, invoker.priority,
                    { listener, event -> listenEvent(invoker.priority, event) }, pl
                )
            }
            listeners.computeIfAbsent(
                invoker.priority
            ) { a: EventPriority? -> ArrayList() }.add(invoker)
        }

        fun listenEvent(pr: EventPriority, ev: Event?) {
            if (listeners.containsKey(pr)) listeners[pr]
                ?.forEach(Consumer { inv: HandyEventInvoker -> inv.onEvent(ev) })
        }
    }

    private class HandyEventInvoker(mtd: Method) {
        val baseEvent: Class<out Event>?
        private val methodWorker: Method
        val priority: EventPriority

        fun onEvent(ex: Event?) {
            val params = arrayOfNulls<Any>(methodWorker.parameters.size)
            params[0] = ex
            try {
                methodWorker.invoke(null, *params)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        }

        init {
            val handler = mtd.getAnnotation(EventHandler::class.java)
            priority = handler.priority
            baseEvent =
                if (mtd.parameters.size > 0) if (Event::class.java.isAssignableFrom(mtd.parameters[0].type)) mtd.parameters[0].type as Class<out Event?> else null else null
            methodWorker = mtd
        }
    }
}