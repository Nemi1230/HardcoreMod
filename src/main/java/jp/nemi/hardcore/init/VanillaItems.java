package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.blocks.WallStickBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallOrFloorItem;
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

}
