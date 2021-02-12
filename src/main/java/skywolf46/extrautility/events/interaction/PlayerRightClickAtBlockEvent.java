package skywolf46.extrautility.events.interaction;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import skywolf46.extrautility.events.abstraction.AbstractPlayerItemEvent;

public class PlayerRightClickAtBlockEvent extends AbstractPlayerItemEvent {
    private static HandlerList handlerList = new HandlerList();
    private boolean isOffHanded;
    private Block targetBlock;

    public PlayerRightClickAtBlockEvent(Player who, Block bx, boolean isOffHanded) {
        super(who);
        this.targetBlock = bx;
        this.isOffHanded = isOffHanded;
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

    @Override
    public ItemStack getItemInHand() {
        return isOffHanded ? getPlayer().getEquipment().getItemInOffHand() : getPlayer().getEquipment().getItemInMainHand();
    }

    @Override
    public void setItemInHand(ItemStack ita) {
        if (isOffHanded)
            getPlayer().getEquipment().setItemInOffHand(ita);
        else
            getPlayer().getEquipment().setItemInMainHand(ita);
    }
}
