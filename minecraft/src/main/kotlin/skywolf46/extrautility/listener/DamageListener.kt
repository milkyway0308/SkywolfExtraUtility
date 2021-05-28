package skywolf46.extrautility.listener

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import skywolf46.extrautility.events.combat.PlayerDamageEntityEvent
import skywolf46.extrautility.events.combat.PlayerDamagedByEntityEvent
import skywolf46.extrautility.events.combat.PlayerKilledEntityEvent


class DamageListener : Listener {
    @EventHandler
    fun onEvent(e: EntityDamageByEntityEvent) {
        if (e.entity is Player) {
            val pdbee = PlayerDamagedByEntityEvent(e.entity as Player, e.damager, e.damage, e.isCancelled)
            Bukkit.getPluginManager().callEvent(pdbee)
            e.isCancelled = pdbee.isCancelled
            e.damage = pdbee.damage
            //            System.out.println("Player Damaged Event!");
        }
        if (e.damager is Player) {
            val pdee = PlayerDamageEntityEvent(e.damager as Player, e.entity, e.damage, e.isCancelled)
            Bukkit.getPluginManager().callEvent(pdee)
            e.isCancelled = pdee.isCancelled
            e.damage = pdee.damage
            //            System.out.println("Player Damage Event!" + pdee.isCancelled());
        }
    }

    @EventHandler
    fun ev(e: EntityDeathEvent) {
        if (e.entity.killer != null) Bukkit.getPluginManager()
            .callEvent(PlayerKilledEntityEvent(e.entity.killer, e.entity))
    }
}