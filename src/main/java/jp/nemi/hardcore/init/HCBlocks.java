package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.blocks.StickBlock;
import jp.nemi.hardcore.object.blocks.WallStickBlock;
import jp.nemi.hardcore.object.blocks.vanilla.HCTorchBlock;
import jp.nemi.hardcore.object.blocks.vanilla.HCWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@SuppressWarnings({"WeakerAccess", "unused"})
@Nonnull
public class HCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HCCore.MOD_ID);

    public static RegistryObject<Block> STICK;
    public static RegistryObject<Block> WALL_STICK;
    public static RegistryObject<Block> HC_TORCH;
    public static RegistryObject<Block> HC_WALL_TORCH;

    public static void register(IEventBus eventBus) {
        STICK = registerBlock("stick", () -> new StickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD)));
        WALL_STICK = registerBlock("wall_stick", () -> new WallStickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD).dropsLike(HCBlocks.STICK.get())));
        HC_TORCH = registerBlock("torch", () -> new HCTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel(HCTorchBlock.getLightValueFromState())));
        HC_WALL_TORCH = registerBlock("wall_torch", () -> new HCWallTorchBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel(HCTorchBlock.getLightValueFromState())));

        BLOCKS.register(eventBus);
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup group) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, new Item.Properties().tab(group));
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Item.Properties properties) {
        HCItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }
}