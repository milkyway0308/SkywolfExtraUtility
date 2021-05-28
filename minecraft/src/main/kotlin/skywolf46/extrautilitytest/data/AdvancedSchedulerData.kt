package skywolf46.extrautilitytest.data

import org.bukkit.Bukkit

data class AdvancedSchedulerData(val taskNumber: Int) {
    fun stop() {
        Bukkit.getScheduler().cancelTask(taskNumber)
    }
}