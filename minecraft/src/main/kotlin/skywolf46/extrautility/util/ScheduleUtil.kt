package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import skywolf46.extrautilitytest.data.AdvancedSchedulerData
import skywolf46.extrautilitytest.inst
import java.util.concurrent.atomic.AtomicInteger


fun schedule(delay: Long, unit: () -> Unit): Int = Bukkit.getScheduler().scheduleSyncDelayedTask(inst, unit, delay)

fun schedule(unit: () -> Unit): Int = Bukkit.getScheduler().scheduleSyncDelayedTask(inst, unit)

fun scheduleAsync(delay: Long, unit: () -> Unit): BukkitTask =
    Bukkit.getScheduler().runTaskLaterAsynchronously(inst, unit, delay)

fun scheduleRepeat(startDelay: Long, delay: Long, unit: () -> Unit): Int =
    Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, unit, startDelay, delay)

fun scheduleRepeat(delay: Long, unit: () -> Unit): Int =
    Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, unit, delay, delay)

fun cancellableScheduleRepeat(startDelay: Long, delay: Long, unit: AdvancedSchedulerData.() -> Unit): Int {
    val taskNumb = AtomicInteger(0)
    taskNumb.set(
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, {
            unit(AdvancedSchedulerData(taskNumb.get()))
        }, startDelay, delay)
    )
    return taskNumb.get()
}

fun cancellableScheduleRepeat(delay: Long, unit: AdvancedSchedulerData.() -> Unit): Int {
    val taskNumb = AtomicInteger(0)
    taskNumb.set(
        Bukkit.getScheduler().scheduleSyncRepeatingTask(inst, {
            unit(AdvancedSchedulerData(taskNumb.get()))
        }, delay, delay)
    )
    return taskNumb.get()
}

