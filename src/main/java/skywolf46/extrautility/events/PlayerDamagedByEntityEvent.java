package skywolf46.extrautility.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDamagedByEntityEvent extends PlayerEvent implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    @Getter
    private Entity damager;
    @Getter
    @Setter
    private double damage;
    @Getter
    @Setter
    private boolean cancelled;

    public PlayerDamagedByEntityEvent(Player who, Entity damager, double dmg, boolean isCancelled) {
        super(who);
        this.damager = damager;
        this.damage = dmg;
        this.cancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
