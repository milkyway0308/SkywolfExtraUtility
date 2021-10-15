package skywolf46.extrautility.bungee.interceptor

import net.bytebuddy.implementation.bind.annotation.RuntimeType
import net.bytebuddy.implementation.bind.annotation.This
import net.md_5.bungee.api.plugin.Event
import skywolf46.extrautility.bungee.abstraction.AbstractPriorityListener
import skywolf46.extrautility.util.EventUtil

object BungeeListenerInterceptor {
    @JvmStatic
    @RuntimeType
    fun interceptEvent(event: Event, @This handler: AbstractPriorityListener) {
        EventUtil.triggerEvent(event, priority = handler.priority)
    }
}