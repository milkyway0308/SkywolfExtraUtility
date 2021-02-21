package skywolf46.extrautility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.extrautility.listeners.DamageListener;
import skywolf46.extrautility.listeners.InteractionListener;

public class ExtraUtilityPlugin extends JavaPlugin {
    private static ExtraUtilityPlugin inst;

    public static ExtraUtilityPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);
    }
}
