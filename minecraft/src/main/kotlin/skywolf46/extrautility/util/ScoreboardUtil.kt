package skywolf46.extrautility.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import skywolf46.extrautility.data.ArgumentStorage

fun Player.getAttachedScoreboard(): ScoreboardUtil.ScoreboardDisplay {
    return this["Scoreboard"] ?: kotlin.run {
        scoreboard = Bukkit.getScoreboardManager().newScoreboard
        this["Scoreboard"] = ScoreboardUtil.ScoreboardDisplay(this, scoreboard)
        this["Scoreboard"]!!
    }
}

fun Player.removeAttachedScoreboard() {
    player.removeValue("Scoreboard")
}

object ScoreboardUtil {
    var interceptor: Player.(String) -> String = {
        it
    }
    val scoreboardThread = ThreadingUtil.newOrderedThread()

    class ScoreboardDisplay(val player: Player, val scoreboard: Scoreboard) {
        private var lastUpdate = mutableListOf<ScoreboardData>()
        var scoreboardTitle: String = "Scoreboard"
            set(value) {
                if (field == value)
                    return
                field = value
                scoreboard.getObjective(DisplaySlot.SIDEBAR)?.displayName = interceptor(player, value)
            }
        var instance: ScoreboardInstance? = null
            set(value) {
                field = value
                value?.apply {
                    doUpdate(player, scoreboard)
                } ?: kotlin.run {
                    clearSlot()
                }
            }


        private fun clearSlot() {
            scoreboard.getObjective(DisplaySlot.SIDEBAR)?.unregister()
            lastUpdate = mutableListOf()
        }


        private fun acquireSlot(board: Scoreboard): Objective {
            return board.getObjective(DisplaySlot.SIDEBAR) ?: kotlin.run {
                board.registerNewObjective("boarder_01", "dummy").apply {
                    displaySlot = DisplaySlot.SIDEBAR
                    displayName = interceptor(player, scoreboardTitle)
                }
                board.getObjective(DisplaySlot.SIDEBAR)!!
            }
        }

        private fun doUpdate(player: Player, scoreboard: Scoreboard) {
            schedule {
                val currentText = instance?.parse(player) ?: emptyList()
                // Modify
                if (currentText.size != lastUpdate.size && currentText.isEmpty()) {
                    // Reset all previous score
                    clearSlot()
                    return@schedule
                }
                modifyAll(scoreboard, currentText)
//                if (lastUpdate.size != currentText.size) {
//                    modifyAll(scoreboard, currentText)
//                    return@schedule
//                }
//                modifyDifference(scoreboard, currentText)
            }
        }

        private fun modifyAll(scoreboard: Scoreboard, texts: List<ScoreboardData>) {
            for (x in lastUpdate) {
                scoreboard.resetScores(x.name)
                scoreboard.getTeam(x.name)?.unregister()
            }
            lastUpdate.clear()
            for (count in texts.indices) {
                try {
                    val next = texts[count]
                    lastUpdate.add(next)
                    next.applyTitle(texts.size - count)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }

        private fun modifyDifference(scoreboard: Scoreboard, texts: List<ScoreboardData>) {
            for (count in texts.indices) {
                val next = texts[count]
                acquireSlot(scoreboard).apply {
                    // TODO change to replacer
                    compareAndReplace(texts.size - count, count, next.rawString)
                }
            }
        }

        private fun compareAndReplace(score: Int, index: Int, text: String) {
            val before = lastUpdate[index]
            val next = ScoreboardData(text)
            if (next.name != before.name) {
                // Remove before
                before.removeTitle()
                // Exchange
                next.applyTitle(score)
                // Replace
                lastUpdate[index] = next
            } else {
                if (comparePrefix(before, next) || compareSuffix(before, next)) {
                    next.updateTitle()
                    lastUpdate[index] = next
                }
            }
        }


        private fun comparePrefix(before: ScoreboardData, after: ScoreboardData): Boolean {
            return before.prefix != after.prefix
        }

        private fun compareSuffix(before: ScoreboardData, after: ScoreboardData): Boolean {
            return before.suffix != after.suffix
        }

        private fun ScoreboardData.applyTitle(score: Int) {
            acquireSlot(scoreboard).getScore(name).apply {
                this.score = score
                val team = scoreboard.getTeam(name) ?: scoreboard.registerNewTeam(name).apply {
                    prefix = this@applyTitle.prefix ?: ""
                    suffix = this@applyTitle.suffix ?: ""
                }
                team.addEntry(name)
            }
        }

        private fun ScoreboardData.updateTitle() {
            scoreboard.getScores(name).apply {
                (scoreboard.getTeam(name) ?: scoreboard.registerNewTeam(name)).apply {
                    prefix = this@updateTitle.prefix ?: ""
                    suffix = this@updateTitle.suffix ?: ""
                }
            }
        }

        private fun ScoreboardData.removeTitle() {
            scoreboard.resetScores(name)
        }

        private fun ScoreboardData.refreshPrefix() {
            scoreboard.getTeam(name).prefix = prefix
        }


        private fun ScoreboardData.refreshSuffix() {
            scoreboard.getTeam(name).suffix = suffix
        }
    }

    class ScoreboardInstance(private val currentTexts: List<String>) {
        fun parse(player: Player): List<ScoreboardData> {
            val storage = ArgumentStorage()
            storage.addArgument(player)
            return currentTexts.map { x -> ScoreboardData(interceptor(player, x)) }
        }
    }

    class ScoreboardData(val rawString: String, val prefix: String?, val name: String, val suffix: String?) {
        companion object {
            operator fun invoke(str: String): ScoreboardData {
                with(str.splitUntilImpossible(16, 32, 48)) {
                    return when (size) {
                        1 -> {
                            ScoreboardData(str, null, this[0], null)
                        }
                        2 -> {
                            ScoreboardData(str, this[0], this[1], null)
                        }
                        else -> {
                            ScoreboardData(str, this[0], this[1], this[2])
                        }
                    }.run {
                        // TODO
                        // Reformat
//                        if (prefix == null)
                        return@run this
//                        else
//                            return@run ScoreboardData(str, prefix,
//                                if (prefix.contains("§")) prefix.substring(prefix.indexOf()))

                    }
                }
            }
        }
    }
}