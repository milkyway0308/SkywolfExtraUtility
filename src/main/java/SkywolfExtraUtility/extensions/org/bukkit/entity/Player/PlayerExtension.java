package SkywolfExtraUtility.extensions.org.bukkit.entity.Player;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import skywolf46.extrautility.ExtraUtilityPlugin;
import skywolf46.extrautility.cooldown.Cooldown;
import skywolf46.extrautility.cooldown.CooldownData;

@Extension
public class PlayerExtension {
    public static Cooldown getCooldown(@This Player px, String cName) {
        if (!px.hasMetadata("[PE] Cooldown")) {
            px.setMetadata("[PE] Cooldown", new FixedMetadataValue(ExtraUtilityPlugin.inst(), new CooldownData()));
        }
        CooldownData cd = (CooldownData) px.getMetadata("[PE] Cooldown").get(0);
        return cd.get(cName);
    }
}