package skywolf46.extrautility.abstraction

/**
 * Event producer interface.
 *
 * This interface is created for event instance what needs pre-registration (like Bukkit Event).
 * After [IEventProducer] registered to [skywolf46.extrautility.util.EventUtil], every event register of [X] will trigger [produce].
 */
interface IEventProducer<X: Any> {
    /**
     * Event produce method.
     *
     * This method will trigger when implementation of [X] is registered.
     * Same class will trigger method only once.
     */
    fun produce(data: Class<X>, sector: String, priority: Int)
}