package skywolf46.extrautility.events.abstraction

import org.bukkit.event.Event
import java.util.*

abstract class AbstractUUIDEvent(val uuid: UUID) : Event()