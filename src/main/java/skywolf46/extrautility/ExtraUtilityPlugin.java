package skywolf46.extrautility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.extrautility.listeners.DamageListener;

public class ExtraUtilityPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
    }
}
