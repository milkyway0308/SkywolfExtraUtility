package skywolf46.extrautility.events.interaction;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent;

public class PlayerLeftClickEvent extends AbstractPlayerItemEvent {
    private static HandlerList handlerList = new HandlerList();


    public PlayerLeftClickEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
