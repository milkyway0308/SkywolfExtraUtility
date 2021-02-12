package skywolf46.extrautility.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent;
import skywolf46.extrautility.events.interaction.PlayerLeftClickAtBlockEvent;
import skywolf46.extrautility.events.interaction.PlayerLeftClickEvent;
import skywolf46.extrautility.events.interaction.PlayerRightClickAtBlockEvent;

public class InteractionListener implements Listener {
    @EventHandler
    public void onInteraction(PlayerInteractEvent e) {
        AbstractPlayerItemEvent cc;
        switch (e.getAction()) {
            case LEFT_CLICK_BLOCK:
                Bukkit.getPluginManager().callEvent(cc = new PlayerLeftClickAtBlockEvent(e.getPlayer(), e.getClickedBlock()).setCancel(e.isCancelled()));
                e.setCancelled(cc.isCancelled());
            case LEFT_CLICK_AIR:
                Bukkit.getPluginManager().callEvent(cc = new PlayerLeftClickEvent(e.getPlayer()).setCancel(e.isCancelled()));
                e.setCancelled(cc.isCancelled());
                break;
            case RIGHT_CLICK_BLOCK:
                Bukkit.getPluginManager().callEvent(cc = new PlayerRightClickAtBlockEvent(e.getPlayer(), e.getClickedBlock(), e.getHand() == EquipmentSlot.OFF_HAND).setCancel(e.isCancelled()));
                e.setCancelled(cc.isCancelled());
            case RIGHT_CLICK_AIR:
                Bukkit.getPluginManager().callEvent(cc = new PlayerLeftClickEvent(e.getPlayer()).setCancel(e.isCancelled()));
                e.setCancelled(cc.isCancelled());
                break;
        }
    }
}
