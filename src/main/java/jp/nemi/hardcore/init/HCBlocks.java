package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.blocks.*;
import jp.nemi.hardcore.object.blocks.vanilla.CustomTorchBlock;
import jp.nemi.hardcore.object.blocks.vanilla.CustomWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
@Nonnull
@Mod.EventBusSubscriber(modid = HCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HCCore.MOD_ID);

    public static final RegistryObject<Block> STICK = BLOCKS.register("stick", () -> new StickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WALL_STICK = BLOCKS.register("wall_stick", () -> new WallStickBlock(AbstractBlock.Properties.of(Material.WOOD).noCollission().instabreak().sound(SoundType.WOOD).dropsLike(HCBlocks.STICK.get())));
    public static final RegistryObject<Block> CUSTOM_TORCH = BLOCKS.register("torch", () -> new CustomTorchBlock(AbstractBlock.Properties.copy(Blocks.TORCH).lightLevel(CustomTorchBlock.getLightValueFromState()), ParticleTypes.FLAME));
    public static final RegistryObject<Block> CUSTOM_WALL_TORCH = BLOCKS.register("wall_torch", () -> new CustomWallTorchBlock(AbstractBlock.Properties.copy(Blocks.TORCH).lightLevel(CustomTorchBlock.getLightValueFromState()), ParticleTypes.FLAME));

    public static void registerLog() {
        HCCore.LOGGER.info("Register Blocks -> {\n" + BLOCKS + "\n}");
    }
}