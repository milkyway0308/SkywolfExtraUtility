package skywolf46.extrautility.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import skywolf46.extrautility.annotations.AllowScanning;

import java.util.concurrent.LinkedBlockingQueue;

@AllowScanning
@Mod(modid = "exutil", name = "exutil", version = "1.64.0")
public class ForgeMain {
    @Mod.EventHandler
    public void onInit(FMLPostInitializationEvent event) {
        ForgeKotlin.init(event);
    }
}
