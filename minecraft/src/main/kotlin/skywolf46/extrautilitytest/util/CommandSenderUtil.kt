package skywolf46.extrautilitytest.util

import org.bukkit.command.CommandSender

fun CommandSender.sendMessage(vararg messages: String) {
    for (msg in messages)
        sendMessage(msg)
}
