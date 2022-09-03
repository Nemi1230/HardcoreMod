package jp.nemi.hardcore;

import jp.nemi.hardcore.init.HCBlocks;
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

        HCBlocks.BLOCKS.register(modBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void init(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    public static HCCore getInstance() {
        return instance;
    }
}
