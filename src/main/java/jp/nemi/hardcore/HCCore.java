package jp.nemi.hardcore;

import com.mojang.logging.LogUtils;
import jp.nemi.hardcore.config.HCConfig;
import jp.nemi.hardcore.event.HCEvents;
import jp.nemi.hardcore.init.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;


@Mod(HCCore.MOD_ID)
public class HCCore {
        public static final String MOD_ID = "hardcore";
        public static final Logger LOGGER =  LogUtils.getLogger();

        public HCCore() {
                IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
                modBus.addListener(this::commonSetup);
                modBus.addListener(HCEvents.ClientModEvent::clientSetup);

                HCBlocks.register(modBus);
                HCItems.register(modBus);
                HCCreativeModeTabs.register(modBus);
                MCRegistries.register(modBus);
                HCConfig.loadConfig(HCConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("hardcore/hardcore.toml"));

                MinecraftForge.EVENT_BUS.register(this);
        }

        private void commonSetup(final FMLCommonSetupEvent event) {

        }

        @SubscribeEvent
        public void onServerStarting(ServerStartingEvent event) {

        }

        @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class ClientModEvents {
                @SubscribeEvent
                public static void onClientEvent(final FMLClientSetupEvent event) {

                }
        }
}