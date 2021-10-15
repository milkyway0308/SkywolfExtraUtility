package skywolf46.extrautility.impl

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import skywolf46.extrautility.SkywolfExtraUtility
import skywolf46.extrautility.abstraction.IEventProducer
import skywolf46.extrautility.util.EventUtil
import skywolf46.extrautility.util.triggerEvent

class BukkitEventProvider : IEventProducer<Event> {
    override fun produce(data: Class<Event>, sector: String, priority: Int) {
        Bukkit.getPluginManager().registerEvent(
            data,
            EmptyListener,
            if (priority >= EventPriority.values().size) EventPriority.MONITOR else (if (priority < 0) EventPriority.LOWEST else EventPriority.values()[priority]),
            EventProvider(priority),
            SkywolfExtraUtility.inst
        )
    }

    object EmptyListener : Listener

    class EventProvider(private val priority: Int) : EventExecutor {
        override fun execute(p0: Listener?, p1: Event) {
            p1.triggerEvent(priority)
        }
    }
}