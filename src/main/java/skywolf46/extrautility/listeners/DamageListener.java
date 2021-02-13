package skywolf46.extrautility.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import skywolf46.extrautility.events.combat.PlayerDamageEntityEvent;
import skywolf46.extrautility.events.combat.PlayerDamagedByEntityEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            PlayerDamagedByEntityEvent pdbee = new PlayerDamagedByEntityEvent((Player) e.getEntity(), e.getDamager(), e.getDamage(), e.isCancelled());
            Bukkit.getPluginManager().callEvent(pdbee);
            e.setCancelled(pdbee.isCancelled());
            e.setDamage(pdbee.getDamage());
//            System.out.println("Player Damaged Event!");
        }

        if (e.getDamager() instanceof Player) {
            PlayerDamageEntityEvent pdee = new PlayerDamageEntityEvent((Player) e.getDamager(), e.getEntity(), e.getDamage(), e.isCancelled());
            Bukkit.getPluginManager().callEvent(pdee);
            e.setCancelled(pdee.isCancelled());
            e.setDamage(pdee.getDamage());
//            System.out.println("Player Damage Event!" + pdee.isCancelled());
        }
    }
}
