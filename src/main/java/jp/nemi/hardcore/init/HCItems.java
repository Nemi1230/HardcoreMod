package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HCItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HCCore.MOD_ID);

        public static void register(IEventBus modBus) {
                ITEMS.register(modBus);
        }
}
