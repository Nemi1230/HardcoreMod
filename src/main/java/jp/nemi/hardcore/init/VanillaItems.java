package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
@Nonnull
@Mod.EventBusSubscriber(modid = HCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VanillaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> STICK = ITEMS.register("stick", () -> new WallOrFloorItem(HCBlocks.STICK.get(), HCBlocks.WALL_STICK.get(), (new Item.Properties().tab(ItemGroup.TAB_MATERIALS))));
    public static final RegistryObject<Item> TORCH = ITEMS.register("torch", () -> new WallOrFloorItem(HCBlocks.CUSTOM_TORCH.get(), HCBlocks.CUSTOM_WALL_TORCH.get(), (new Item.Properties().tab(ItemGroup.TAB_DECORATIONS))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
