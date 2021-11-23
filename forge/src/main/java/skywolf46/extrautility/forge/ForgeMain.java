package skywolf46.extrautility.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import skywolf46.extrautility.annotations.AllowScanning;

@AllowScanning
@Mod(modid = "exutil", name = "exutil", version = "1.64.0")
public class ForgeMain {
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        ForgeKotlin.init(event);
    }
}
