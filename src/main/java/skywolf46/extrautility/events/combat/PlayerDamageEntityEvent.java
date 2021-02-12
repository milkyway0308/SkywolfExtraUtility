package skywolf46.extrautility.events.combat;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDamageEntityEvent extends PlayerEvent implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    @Getter
    private Entity victim;
    @Getter
    @Setter
    private double damage;
    @Getter
    @Setter
    private boolean cancelled;

    public PlayerDamageEntityEvent(Player who, Entity victim, double dmg, boolean isCancelled) {
        super(who);
        this.victim = victim;
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
