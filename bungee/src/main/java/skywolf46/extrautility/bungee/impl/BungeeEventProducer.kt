package skywolf46.extrautility.bungee.impl

import net.bytebuddy.ByteBuddy
import net.bytebuddy.asm.MemberAttributeExtension
import net.bytebuddy.description.annotation.AnnotationDescription
import net.bytebuddy.description.modifier.Visibility
import net.bytebuddy.implementation.MethodDelegation
import net.bytebuddy.matcher.ElementMatchers
import net.md_5.bungee.BungeeCord
import net.md_5.bungee.api.plugin.Event
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import skywolf46.extrautility.abstraction.IEventProducer
import skywolf46.extrautility.bungee.ExtraUtilityBungeePlugin
import skywolf46.extrautility.bungee.abstraction.AbstractPriorityListener
import skywolf46.extrautility.bungee.interceptor.BungeeListenerInterceptor

private const val LISTENER_METHOD_NAME = "listenEvent"

object BungeeEventProducer : IEventProducer<Event> {
    private val buddy = ByteBuddy()
    private var counter = 0
    override fun produce(data: Class<Event>, sector: String, priority: Int) {
        // Scaling priority to byte.
        val realPriority = priority.coerceAtLeast(Byte.MIN_VALUE.toInt()).coerceAtMost(Byte.MAX_VALUE.toInt())
        BungeeCord.getInstance().pluginManager.registerListener(
            ExtraUtilityBungeePlugin.inst,
            generateListener(data).getConstructor(Int::class.java).newInstance(realPriority) as Listener
        )
    }

    private fun generateListener(cls: Class<Event>): Class<*> {
        return buddy.subclass(AbstractPriorityListener::class.java)
            .name("ExUtilListener$${counter}")
            .run {
                defineMethod(
                    LISTENER_METHOD_NAME,
                    Void::class.java,
                    Visibility.PUBLIC,
                ).withParameters(cls)
                    .intercept(MethodDelegation.to(BungeeListenerInterceptor::class.java))
                    .visit(
                        MemberAttributeExtension.ForMethod()
                            .annotateMethod(
                                AnnotationDescription.Builder.ofType(EventHandler::class.java)
                                    .build()
                            )
                            .on(ElementMatchers.named(LISTENER_METHOD_NAME))
                    ).make()
                    .load(ExtraUtilityBungeePlugin::class.java.classLoader).loaded
            }
    }
}