package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HCItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HCCore.MOD_ID);

        public static RegistryObject<Item> TORCH;

        public static void register(IEventBus modBus) {
                TORCH = ITEMS.register("torch", () -> new StandingAndWallBlockItem(HCBlocks.HC_TORCH.get(), HCBlocks.HC_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));

                ITEMS.register(modBus);
        }
}
