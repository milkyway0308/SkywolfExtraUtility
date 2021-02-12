package skywolf46.extrautility.events.abstraction;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractPlayerItemEvent extends PlayerEvent implements Cancellable {
    private boolean cancelled;

    public AbstractPlayerItemEvent(Player who) {
        super(who);
    }

    public ItemStack getItemInHand() {
        return getPlayer().getEquipment().getItemInMainHand();
    }

    public void setItemInHand(ItemStack ita) {
        getPlayer().getEquipment().setItemInMainHand(ita);
    }

    public boolean isItemValid() {
        return getItemInHand() != null && getItemInHand().getType() != Material.AIR;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public AbstractPlayerItemEvent setCancel(boolean cancel) {
        setCancelled(cancel);
        return this;
    }
}
