package skywolf46.extrautility.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent
import skywolf46.extrautility.events.interaction.PlayerLeftClickAtBlockEvent
import skywolf46.extrautility.events.interaction.PlayerLeftClickEvent
import skywolf46.extrautility.events.interaction.PlayerRightClickAtBlockEvent


class InteractionListener : Listener {
    @EventHandler
    fun onInteraction(e: PlayerInteractEvent) {
        var cc: AbstractPlayerItemEvent
        when (e.action) {
            Action.LEFT_CLICK_BLOCK -> {
                Bukkit.getPluginManager()
                    .callEvent(PlayerLeftClickAtBlockEvent(e.player, e.clickedBlock).setCancel(e.isCancelled).also {
                        cc = it
                    })
                e.isCancelled = cc.isCancelled()
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e.player).setCancel(e.isCancelled).also {
                    cc = it
                })
                e.isCancelled = cc.isCancelled()
            }
            Action.LEFT_CLICK_AIR -> {
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e.player).setCancel(e.isCancelled).also {
                    cc = it
                })
                e.isCancelled = cc.isCancelled()
            }
            Action.RIGHT_CLICK_BLOCK -> {
                Bukkit.getPluginManager().callEvent(
                    PlayerRightClickAtBlockEvent(
                        e.player,
                        e.clickedBlock,
                        e.hand == EquipmentSlot.OFF_HAND
                    ).setCancel(e.isCancelled).also {
                        cc = it
                    })
                e.isCancelled = cc.isCancelled()
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e.player).setCancel(e.isCancelled).also {
                    cc = it
                })
                e.isCancelled = cc.isCancelled()
            }
            Action.RIGHT_CLICK_AIR -> {
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e.player).setCancel(e.isCancelled).also {
                    cc = it
                })
                e.isCancelled = cc.isCancelled()
            }
        }
    }
}
