package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HCCreativeModeTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HCCore.MOD_ID);

        public static RegistryObject<CreativeModeTab> HARDCORE_MAIN;
        public static RegistryObject<CreativeModeTab> HARDCORE_TOOLS;

        public static void register(IEventBus modBus) {
                HARDCORE_MAIN = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
                        .withTabsBefore(CreativeModeTabs.COMBAT)
                        .icon(() -> Items.WITHER_SKELETON_SKULL.getDefaultInstance())
                        .displayItems((parameters, output) -> {

                        }).build());

                HARDCORE_TOOLS = CREATIVE_MODE_TABS.register("tools_tab", () -> CreativeModeTab.builder()
                        .withTabsBefore(CreativeModeTabs.COMBAT)
                        .icon(() -> Items.STONE_SWORD.getDefaultInstance())
                        .displayItems((parameters, output) -> {

                        }).build());

                CREATIVE_MODE_TABS.register(modBus);
        }

        public void addCreativeTab(BuildCreativeModeTabContentsEvent event) {

        }
}
