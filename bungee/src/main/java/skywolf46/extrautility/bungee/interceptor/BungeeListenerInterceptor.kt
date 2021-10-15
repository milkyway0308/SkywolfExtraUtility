package skywolf46.extrautility.bungee.interceptor

import net.bytebuddy.asm.Advice
import net.bytebuddy.implementation.bind.annotation.RuntimeType
import net.bytebuddy.implementation.bind.annotation.SuperCall
import net.md_5.bungee.api.plugin.Event

object BungeeListenerInterceptor {
    @JvmStatic
    @RuntimeType
    fun interceptEvent(event: Event) {
        println("Woah.")

    }
}