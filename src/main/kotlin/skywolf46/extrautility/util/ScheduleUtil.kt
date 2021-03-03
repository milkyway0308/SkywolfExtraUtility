package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import skywolf46.extrautility.inst


fun schedule(delay: Long, unit: () -> Unit): Int = Bukkit.getScheduler().scheduleSyncDelayedTask(inst, unit, delay)

fun schedule(unit: () -> Unit): Int = Bukkit.getScheduler().scheduleSyncDelayedTask(inst, unit)

fun scheduleAsync(delay: Long, unit: () -> Unit): BukkitTask =
    Bukkit.getScheduler().runTaskLaterAsynchronously(inst, unit, delay)

fun scheduleRepeat(startDelay: Long, delay: Long, unit: () -> Unit): Int =
    Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, unit, startDelay, delay)

fun scheduleRepeat(delay: Long, unit: () -> Unit): Int =
    Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, unit, delay, delay)