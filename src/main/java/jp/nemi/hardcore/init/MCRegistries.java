package jp.nemi.hardcore.init;

import jp.nemi.hardcore.object.entities.vanilla.HCCreeper;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCRegistries {
        private static final String MC = "minecraft";
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MC);
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MC);
        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MC);

        //Items
        public static RegistryObject<Item> STICK;

        //Entities
        public static RegistryObject<EntityType<HCCreeper>> HC_CREEPER;

        public static void register(IEventBus modBus) {
                STICK = ITEMS.register("stick", () -> new StandingAndWallBlockItem(HCBlocks.STICK.get(), HCBlocks.WALL_STICK.get(), (new Item.Properties()), Direction.DOWN));

                //Entities
                HC_CREEPER = ENTITIES.register("creeper", () -> EntityType.Builder.<HCCreeper>of(HCCreeper::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8).build(new ResourceLocation(MC, "creeper").toString()));

                ITEMS.register(modBus);
                BLOCKS.register(modBus);
                ENTITIES.register(modBus);
        }
}