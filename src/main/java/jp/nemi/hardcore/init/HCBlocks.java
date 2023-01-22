package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.blocks.StickBlock;
import jp.nemi.hardcore.object.blocks.WallStickBlock;
import jp.nemi.hardcore.object.blocks.vanilla.CustomTorchBlock;
import jp.nemi.hardcore.object.blocks.vanilla.CustomWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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

    public static final RegistryObject<Block> STICK = registerBlock("stick", () -> new StickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WALL_STICK = registerBlock("wall_stick", () -> new WallStickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD).dropsLike(HCBlocks.STICK.get())));
    public static final RegistryObject<Block> CUSTOM_TORCH = registerBlock("torch", () -> new CustomTorchBlock(AbstractBlock.Properties.copy(Blocks.TORCH).lightLevel(CustomTorchBlock.getLightValueFromState()), ParticleTypes.FLAME));
    public static final RegistryObject<Block> CUSTOM_WALL_TORCH = registerBlock("wall_torch", () -> new CustomWallTorchBlock(AbstractBlock.Properties.copy(Blocks.TORCH).lightLevel(CustomTorchBlock.getLightValueFromState()), ParticleTypes.FLAME));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, ItemGroup group) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, group);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, ItemGroup group) {
        HCItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(group)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}