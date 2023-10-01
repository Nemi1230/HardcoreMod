package jp.nemi.hardcore;

import jp.nemi.hardcore.config.HCSaveData;
import jp.nemi.hardcore.event.HCEvents;
import jp.nemi.hardcore.init.*;
import jp.nemi.hardcore.network.SimpleNetworkHandler;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod(HCCore.MOD_ID)
public class HCCore {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "hardcore";
    public static HCCore instance;

    public HCCore() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::init);
        modBus.addListener(HCEvents.ClientModEvent::clientSetup);

        HCBlocks.register(modBus);
        HCItems.register(modBus);
        VanillaItems.register(modBus);
        HCEffects.register(modBus);
        HCConfigs.init();

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void init(final FMLCommonSetupEvent event) {
        HCCapabilities.registerCapability();
        SimpleNetworkHandler.init();
    }

    public static HCCore getInstance() {
        return instance;
    }

    public static HCSaveData getHCSaveData(World world) {
        if (world.isClientSide() || !(world instanceof ServerWorld)) return null;

        ServerWorld serverWorld = (ServerWorld) world;
        DimensionSavedDataManager savedDataManager = serverWorld.getChunkSource().getDataStorage();


        return savedDataManager.computeIfAbsent(HCSaveData::new, HCSaveData.NAME);
    }
}
