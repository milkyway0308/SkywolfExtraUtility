package skywolf46.extrautility.util

import org.bukkit.Bukkit

fun log(x1: String?) = Bukkit.getConsoleSender().sendMessage(x1)
fun broad(x1: String?) = Bukkit.broadcastMessage(x1)