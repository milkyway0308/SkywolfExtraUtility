package skywolf46.extrautility.events.interaction;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent;

public class PlayerLeftClickAtBlockEvent extends AbstractPlayerItemEvent {
    private static HandlerList handlerList = new HandlerList();
    private Block targetBlock;

    public PlayerLeftClickAtBlockEvent(Player who, Block bx) {
        super(who);
        this.targetBlock = bx;
    }

    public Block getTargetBlock() {
        return targetBlock;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
