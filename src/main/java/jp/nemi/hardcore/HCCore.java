package jp.nemi.hardcore;

import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.init.HCItems;
import jp.nemi.hardcore.init.VanillaItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HCCore.MOD_ID)
public class HCCore {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hardcore";
    public static HCCore instance;

    public HCCore() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::init);
        modBus.addListener(this::clientSetup);

        HCBlocks.register(modBus);
        HCItems.register(modBus);
        VanillaItems.register(modBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void init(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(HCBlocks.STICK.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(HCBlocks.WALL_STICK.get(), RenderType.cutout());
    }

    public static HCCore getInstance() {
        return instance;
    }
}
