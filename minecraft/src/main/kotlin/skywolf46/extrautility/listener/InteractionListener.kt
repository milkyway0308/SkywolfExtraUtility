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
import skywolf46.extrautility.events.interaction.PlayerRightClickEvent
import skywolf46.extrautility.util.callEvent


class InteractionListener : Listener {
    @EventHandler
    fun onInteraction(e: PlayerInteractEvent) {
        var cc: AbstractPlayerItemEvent? = null
        when (e.action) {
            Action.LEFT_CLICK_BLOCK -> {
                PlayerLeftClickAtBlockEvent(e, e.player, e.clickedBlock).callEvent()
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e, e.player,true).also {
                    cc = it
                })
            }
            Action.LEFT_CLICK_AIR -> {
                Bukkit.getPluginManager().callEvent(PlayerLeftClickEvent(e, e.player,false).also {
                    cc = it
                })
            }
            Action.RIGHT_CLICK_BLOCK -> {
                PlayerRightClickAtBlockEvent(
                    e,
                    e.player,
                    e.clickedBlock,
                    e.hand == EquipmentSlot.OFF_HAND
                ).callEvent()
                Bukkit.getPluginManager().callEvent(PlayerRightClickEvent(e, e.player, true,e.hand == EquipmentSlot.OFF_HAND).also {
                    cc = it
                })
            }
            Action.RIGHT_CLICK_AIR -> {
                Bukkit.getPluginManager().callEvent(PlayerRightClickEvent(e, e.player, false,e.hand == EquipmentSlot.OFF_HAND).also {
                    cc = it
                })
            }
        }
        cc?.callEvent()
    }
}
