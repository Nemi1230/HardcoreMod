package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.blocks.StickBlock;
import jp.nemi.hardcore.object.blocks.WallStickBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HCBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HCCore.MOD_ID);

        public static RegistryObject<Block> STICK;
        public static RegistryObject<Block> WALL_STICK;

        public static void register(IEventBus modBus) {
                STICK = registerBlock("stick", () -> new StickBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.WOOD)));
                WALL_STICK = registerBlock("wall_stick", () -> new WallStickBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.WOOD)));

                BLOCKS.register(modBus);
        }

        private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
                RegistryObject<T> toReturn = BLOCKS.register(name, block);
                return toReturn;
        }

        private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, Item.Properties properties) {
                RegistryObject<T> toReturn = BLOCKS.register(name, block);
                registerBlockItem(name, toReturn, properties);
                return toReturn;
        }

        private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Item.Properties properties) {
                HCItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        }
}
