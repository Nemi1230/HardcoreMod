package jp.nemi.hardcore.object.entities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class HCVillagerTrades {
        public static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> HC_TRADES = Util.make(Maps.newHashMap(), (trades) -> {
                trades.put(VillagerProfession.FARMER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.WHEAT, 25, 16, 1), new HCVillagerTrades.HCEmeraldForItems(Items.POTATO, 28, 12, 1), new HCVillagerTrades.HCEmeraldForItems(Items.CARROT, 26, 10, 1), new HCVillagerTrades.HCEmeraldForItems(Items.BEETROOT, 20, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BREAD, 2, 8, 6, 1)}, 2, new VillagerTrades.ItemListing[]{new HCEmeraldForItems(Items.PUMPKIN, 10, 6, 5),  new HCVillagerTrades.HCItemsForEmeralds(Items.PUMPKIN_PIE, 2, 5, 6, 3), new HCVillagerTrades.HCItemsForEmeralds(Items.APPLE, 2, 6, 14, 3)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.MELON, 6, 6, 15), new HCVillagerTrades.HCItemsForEmeralds(Items.COOKIE, 8, 24, 6, 6)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Blocks.CAKE, 2, 1, 6, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.NIGHT_VISION, 100, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.JUMP, 100, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.WEAKNESS, 100, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.BLINDNESS, 100, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.POISON, 100, 10), new HCVillagerTrades.HCSuspiciousStewForEmerald(MobEffects.SATURATION, 100, 10)}, 5,  new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.GOLDEN_CARROT, 8, 4, 6, 0), new HCVillagerTrades.HCItemsForEmeralds(Items.GLISTERING_MELON_SLICE, 10, 5, 6, 0)})));
                trades.put(VillagerProfession.FISHERMAN, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.STRING, 25, 10, 1), new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 20, 10, 1), new HCVillagerTrades.HCItemsAndEmeraldsToItems(Items.COD, 12, 2, Items.COOKED_COD, 8, 10, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.COD_BUCKET, 6, 1, 10, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COD, 22, 8, 5), new HCVillagerTrades.HCItemsAndEmeraldsToItems(Items.SALMON, 12, 2, Items.COOKED_SALMON, 8, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Items.CAMPFIRE, 5, 1, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.SALMON, 20, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.PUFFERFISH, 8, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.TROPICAL_FISH, 10, 6, 15)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.FISHING_ROD, 3, 1, 0, 0.23F), new HCVillagerTrades.HCEmeraldsForVillagerTypeItem(1, 6, 0, ImmutableMap.<VillagerType, Item>builder().put(VillagerType.PLAINS, Items.OAK_BOAT).put(VillagerType.TAIGA, Items.SPRUCE_BOAT).put(VillagerType.SNOW, Items.SPRUCE_BOAT).put(VillagerType.DESERT, Items.JUNGLE_BOAT).put(VillagerType.JUNGLE, Items.JUNGLE_BOAT).put(VillagerType.SAVANNA, Items.ACACIA_BOAT).put(VillagerType.SWAMP, Items.DARK_OAK_BOAT).build())})));
                trades.put(VillagerProfession.SHEPHERD, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Blocks.WHITE_WOOL, 25, 8, 1), new HCVillagerTrades.HCEmeraldForItems(Blocks.BROWN_WOOL, 25, 8, 1), new HCVillagerTrades.HCEmeraldForItems(Blocks.BLACK_WOOL, 25, 8, 1), new HCVillagerTrades.HCEmeraldForItems(Blocks.GRAY_WOOL, 25, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SHEARS, 4, 1, 8, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.WHITE_DYE, 20, 8, 5), new HCVillagerTrades.HCEmeraldForItems(Items.GRAY_DYE, 20, 8, 5), new HCVillagerTrades.HCEmeraldForItems(Items.BLACK_DYE, 20, 8, 5), new HCVillagerTrades.HCEmeraldForItems(Items.LIGHT_BLUE_DYE, 20, 8, 5), new HCVillagerTrades.HCEmeraldForItems(Items.LIME_DYE, 20, 8, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.WHITE_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.ORANGE_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.MAGENTA_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_BLUE_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.YELLOW_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIME_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PINK_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GRAY_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_GRAY_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CYAN_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PURPLE_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLUE_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BROWN_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GREEN_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.RED_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLACK_WOOL, 2, 1, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.WHITE_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.ORANGE_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.MAGENTA_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_BLUE_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.YELLOW_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIME_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PINK_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GRAY_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_GRAY_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CYAN_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PURPLE_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLUE_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BROWN_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GREEN_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.RED_CARPET, 2, 4, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLACK_CARPET, 2, 4, 8, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.YELLOW_DYE, 25, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.LIGHT_GRAY_DYE, 25, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.ORANGE_DYE, 25, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.RED_DYE, 25, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.PINK_DYE, 25, 8, 10), new HCVillagerTrades.HCItemsForEmeralds(Blocks.WHITE_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.YELLOW_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.RED_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLACK_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLUE_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BROWN_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CYAN_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GRAY_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GREEN_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_BLUE_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_GRAY_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIME_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.MAGENTA_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.ORANGE_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PINK_BED, 8, 1, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PURPLE_BED, 8, 1, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.BROWN_DYE, 30, 8, 15), new HCVillagerTrades.HCEmeraldForItems(Items.PURPLE_DYE, 30, 8, 15), new HCVillagerTrades.HCEmeraldForItems(Items.BLUE_DYE, 30, 8, 15), new HCVillagerTrades.HCEmeraldForItems(Items.GREEN_DYE, 30, 8, 15), new HCVillagerTrades.HCEmeraldForItems(Items.MAGENTA_DYE, 30, 8, 15), new HCVillagerTrades.HCEmeraldForItems(Items.CYAN_DYE, 30, 8, 15), new HCVillagerTrades.HCItemsForEmeralds(Items.WHITE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BLUE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.PINK_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.GREEN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIME_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.GRAY_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BLACK_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.PURPLE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.MAGENTA_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.CYAN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BROWN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.YELLOW_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.ORANGE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 8, 1, 6, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.PAINTING, 5, 3, 6, 0)})));
                trades.put(VillagerProfession.FLETCHER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.STICK, 64, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ARROW, 2, 8, 1), new HCVillagerTrades.HCItemsAndEmeraldsToItems(Blocks.GRAVEL, 15, 2, Items.FLINT, 10, 8, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FLINT, 45, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Items.BOW, 5, 1, 6, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.STRING, 50, 8, 10), new HCVillagerTrades.HCItemsForEmeralds(Items.CROSSBOW, 10, 1, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FEATHER, 55, 8, 15), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.BOW, 2, 1, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.TRIPWIRE_HOOK, 16, 6, 0), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.CROSSBOW, 3, 1, 7), new HCVillagerTrades.HCTippedArrowForItemsAndEmeralds(Items.ARROW, 5, Items.TIPPED_ARROW, 10, 5, 6, 0)})));
                trades.put(VillagerProfession.LIBRARIAN, toIntMap(ImmutableMap.<Integer, VillagerTrades.ItemListing[]>builder().put(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.PAPER, 50, 8, 1), HCVillagerTrades.createLibrarianTradeItemListing(1, 6), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BOOKSHELF, 20, 1, 6, 1)}).put(2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.BOOK, 4, 12, 10), HCVillagerTrades.createLibrarianTradeItemListing(1, 6), new HCVillagerTrades.HCItemsForEmeralds(Items.LANTERN, 1, 1, 5)}).put(3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.INK_SAC, 10, 6, 10), HCVillagerTrades.createLibrarianTradeItemListing(5, 6), new HCVillagerTrades.HCItemsForEmeralds(Items.GLASS, 2, 4, 6, 5)}).put(4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.WRITABLE_BOOK, 2, 4, 15), HCVillagerTrades.createLibrarianTradeItemListing(7, 6), new HCVillagerTrades.HCItemsForEmeralds(Items.CLOCK, 10, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.COMPASS, 8, 1, 6, 7)}).put(5, new VillagerTrades.ItemListing[]{HCVillagerTrades.createMasterLibrarianTradeItemListing(), new HCVillagerTrades.HCItemsForEmeralds(Items.NAME_TAG, 42, 1, 6, 0)}).build()));
                trades.put(VillagerProfession.CARTOGRAPHER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.PAPER, 40, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.MAP, 15, 1, 8, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.GLASS_PANE, 20, 8, 5), new HCVillagerTrades.HCTreasureMapForEmeralds(20, StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapDecoration.Type.MONUMENT, 6, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COMPASS, 1, 5, 8), new HCVillagerTrades.HCTreasureMapForEmeralds(25, StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapDecoration.Type.MANSION, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.ITEM_FRAME, 15, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.WHITE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BLUE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.PINK_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.GREEN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIME_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.GRAY_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BLACK_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.PURPLE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.MAGENTA_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.CYAN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.BROWN_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.YELLOW_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.ORANGE_BANNER, 8, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 8, 1, 6, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.GLOBE_BANNER_PATTERN, 15, 1, 6, 0)})));
                trades.put(VillagerProfession.ARMORER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 25, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.IRON_LEGGINGS), 20, 1, 8, 1, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.IRON_BOOTS), 10, 1, 8, 1, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.IRON_HELMET), 10, 1, 8, 1, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.IRON_CHESTPLATE), 20, 1, 8, 1, 0.23F)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.IRON_INGOT, 10, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.BELL), 64, 1, 6, 4, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.CHAINMAIL_BOOTS), 10, 1, 6, 2, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.CHAINMAIL_HELMET), 10, 1, 6, 2, 0.23F)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.LAVA_BUCKET, 1, 4, 10), new HCVillagerTrades.HCEmeraldForItems(Items.DIAMOND, 2, 6, 10), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.CHAINMAIL_LEGGINGS), 20, 1, 6, 5, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 20, 1, 6, 5, 0.23F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.SHIELD), 10, 1, 6, 5, 0.23F)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_HELMET, 8, 1, 7, 0.235F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_BOOTS, 8, 1, 7, 0.235F)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_LEGGINGS, 16, 1, 0, 0.24F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_CHESTPLATE, 16, 1, 0, 0.24F)})));
                trades.put(VillagerProfession.CLERIC, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.ROTTEN_FLESH, 64, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.REDSTONE, 5, 1, 8, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.GOLD_INGOT, 10, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(Items.LAPIS_LAZULI, 2, 1, 6, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.RABBIT_FOOT, 5, 6, 10), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GLOWSTONE, 8, 1, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.SCUTE, 10, 6, 15), new HCVillagerTrades.HCEmeraldForItems(Items.GLASS_BOTTLE, 15, 6, 15), new HCVillagerTrades.HCItemsForEmeralds(Items.ENDER_PEARL, 10, 1, 6, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.NETHER_WART, 50, 8, 0), new HCVillagerTrades.HCItemsForEmeralds(Items.EXPERIENCE_BOTTLE, 8, 1, 6, 0)})));
                trades.put(VillagerProfession.WEAPONSMITH, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 25, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.IRON_AXE), 6, 1, 8, 1, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_SWORD, 2, 1, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.IRON_INGOT, 10, 6, 5), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.BELL), 64, 1, 6, 2, 0.23F)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FLINT, 50, 6, 10)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.DIAMOND, 2, 6, 15), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 1, 7, 0.23F)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_SWORD, 8, 1, 0, 0.23F)})));trades.put(VillagerProfession.TOOLSMITH, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 15, 16, 2), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.IRON_INGOT, 4, 12, 10), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FLINT, 30, 12, 20), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_AXE, 1, 3, 10, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_SHOVEL, 2, 3, 10, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_PICKAXE, 3, 3, 10, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2F)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.DIAMOND, 1, 12, 30), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2F)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2F)})));
                trades.put(VillagerProfession.TOOLSMITH, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 15, 16, 2), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.IRON_INGOT, 4, 12, 10), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FLINT, 30, 12, 20), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_AXE, 1, 3, 10, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_SHOVEL, 2, 3, 10, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.IRON_PICKAXE, 3, 3, 10, 0.2F), new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2F)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.DIAMOND, 1, 12, 30), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2F), new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2F)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEnchantedItemForEmeralds(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2F)})));
                trades.put(VillagerProfession.BUTCHER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.CHICKEN, 30, 8, 1), new HCVillagerTrades.HCEmeraldForItems(Items.PORKCHOP, 15, 8, 1), new HCVillagerTrades.HCEmeraldForItems(Items.RABBIT, 10, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.RABBIT_STEW, 2, 1, 8, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.COAL, 30, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.COOKED_PORKCHOP, 2, 5, 8, 2), new HCVillagerTrades.HCItemsForEmeralds(Items.COOKED_CHICKEN, 2, 8, 8, 1)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.MUTTON, 15, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Items.BEEF, 20, 8, 10)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.DRIED_KELP_BLOCK, 20, 6, 15)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.SWEET_BERRIES, 25, 6, 0)})));
                trades.put(VillagerProfession.LEATHERWORKER, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.LEATHER, 12, 8, 1), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_HELMET, 8, 6, 1), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_BOOTS, 8, 6, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.FLINT, 50, 6, 5), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_LEGGINGS, 10, 8, 2), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 10, 8, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.RABBIT_HIDE, 20, 6, 10), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_HELMET, 10, 6, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.SCUTE, 10, 6, 15), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_HORSE_ARMOR, 14, 6, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(new ItemStack(Items.SADDLE), 14, 1, 6, 0, 0.23F), new HCVillagerTrades.HCDyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 12, 6, 0)})));
                trades.put(VillagerProfession.MASON, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.CLAY_BALL, 20, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BRICK, 2, 9, 10, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Blocks.STONE, 42, 8, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CHISELED_STONE_BRICKS, 2, 4, 8, 2)}, 3, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Blocks.GRANITE, 8, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Blocks.ANDESITE, 8, 8, 10), new HCVillagerTrades.HCEmeraldForItems(Blocks.DIORITE, 8, 8, 10), new HCVillagerTrades.HCItemsForEmeralds(Blocks.DRIPSTONE_BLOCK, 2, 4, 8, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.POLISHED_ANDESITE, 2, 4, 8, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.POLISHED_DIORITE, 2, 4, 8, 5), new HCVillagerTrades.HCItemsForEmeralds(Blocks.POLISHED_GRANITE, 2, 4, 8, 5)}, 4, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCEmeraldForItems(Items.QUARTZ, 25, 6, 15), new HCVillagerTrades.HCItemsForEmeralds(Blocks.ORANGE_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.WHITE_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLUE_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_BLUE_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GRAY_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_GRAY_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLACK_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.RED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PINK_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.MAGENTA_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIME_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GREEN_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CYAN_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PURPLE_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.YELLOW_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BROWN_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.ORANGE_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.WHITE_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLUE_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GRAY_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BLACK_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.RED_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PINK_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.MAGENTA_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.LIME_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.GREEN_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.CYAN_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.PURPLE_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.YELLOW_GLAZED_TERRACOTTA, 2, 1, 6, 7), new HCVillagerTrades.HCItemsForEmeralds(Blocks.BROWN_GLAZED_TERRACOTTA, 2, 1, 6, 7)}, 5, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Blocks.QUARTZ_PILLAR, 2, 1, 6, 0), new HCVillagerTrades.HCItemsForEmeralds(Blocks.QUARTZ_BLOCK, 2, 1, 6, 0)})));
        });

        public static final Int2ObjectMap<VillagerTrades.ItemListing[]> HC_WANDERING_TRADER_TRADES = toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.SEA_PICKLE, 2, 1, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.FERN, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SUGAR_CANE, 1, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PUMPKIN, 1, 1, 4, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.KELP, 3, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.CACTUS, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.DANDELION, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.POPPY, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BLUE_ORCHID, 1, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ALLIUM, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.AZURE_BLUET, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_TULIP, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ORANGE_TULIP, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.WHITE_TULIP, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PINK_TULIP, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.OXEYE_DAISY, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.CORNFLOWER, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.WHEAT_SEEDS, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.MELON_SEEDS, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ACACIA_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BIRCH_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.OAK_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.CHERRY_SAPLING, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.MANGROVE_PROPAGULE, 5, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.WHITE_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BLUE_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PINK_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BLACK_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.GREEN_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.MAGENTA_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.YELLOW_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.GRAY_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PURPLE_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.LIME_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ORANGE_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BROWN_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.CYAN_DYE, 1, 3, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.VINE, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_MUSHROOM, 1, 1, 12, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.LILY_PAD, 1, 2, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SMALL_DRIPLEAF, 1, 2, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.POINTED_DRIPSTONE, 1, 2, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.ROOTED_DIRT, 1, 2, 5, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.MOSS_BLOCK, 1, 2, 5, 1)}, 2, new VillagerTrades.ItemListing[]{new HCVillagerTrades.HCItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new HCVillagerTrades.HCItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1)}));

        private static VillagerTrades.ItemListing createLibrarianTradeItemListing(int experience, int maxUses) {
                return new HCTypedWrapper(ImmutableMap.<VillagerType, VillagerTrades.ItemListing>builder().put(VillagerType.DESERT, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.FIRE_PROTECTION, Enchantments.THORNS, Enchantments.INFINITY_ARROWS)).put(VillagerType.JUNGLE, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.FALL_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.POWER_ARROWS)).put(VillagerType.PLAINS, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.PUNCH_ARROWS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS)).put(VillagerType.SAVANNA, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.KNOCKBACK, Enchantments.BINDING_CURSE, Enchantments.SWEEPING_EDGE)).put(VillagerType.SNOW, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.AQUA_AFFINITY, Enchantments.MOB_LOOTING, Enchantments.FROST_WALKER)).put(VillagerType.SWAMP, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.DEPTH_STRIDER, Enchantments.RESPIRATION, Enchantments.VANISHING_CURSE)).put(VillagerType.TAIGA, new HCEnchantBookForEmeralds(experience, maxUses, Enchantments.BLAST_PROTECTION, Enchantments.FIRE_ASPECT, Enchantments.FLAMING_ARROWS)).build());
        }

        private static VillagerTrades.ItemListing createMasterLibrarianTradeItemListing() {
                return new HCTypedWrapper(ImmutableMap.<VillagerType, VillagerTrades.ItemListing>builder().put(VillagerType.DESERT, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 3, 4, Enchantments.BLOCK_EFFICIENCY)).put(VillagerType.JUNGLE, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 3, 3, Enchantments.UNBREAKING)).put(VillagerType.PLAINS, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 3, 4, Enchantments.ALL_DAMAGE_PROTECTION)).put(VillagerType.SAVANNA, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 3, 4, Enchantments.SHARPNESS)).put(VillagerType.SNOW, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 0, Integer.MAX_VALUE, Enchantments.SILK_TOUCH)).put(VillagerType.SWAMP, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 1, 1, 0.35D, Enchantments.MENDING)).put(VillagerType.TAIGA, new HCVillagerTrades.HCEnchantBookForEmeralds(0, 6, 1, 2, Enchantments.BLOCK_FORTUNE)).build());
        }

        private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_35631_) {
                return new Int2ObjectOpenHashMap<>(p_35631_);
        }

        static record HCTypedWrapper(Map<VillagerType, VillagerTrades.ItemListing> trades) implements VillagerTrades.ItemListing {
                public static HCVillagerTrades.HCTypedWrapper oneTradeInBiomes(VillagerTrades.ItemListing tradeListings, VillagerType... villagerTypes) {
                        return new HCVillagerTrades.HCTypedWrapper(Arrays.stream(villagerTypes).collect(Collectors.toMap((p_298558_) ->{
                                return p_298558_;
                        }, (p_298770_) -> {
                                return tradeListings;
                        })));
                }


                @org.jetbrains.annotations.Nullable
                @Override
                public MerchantOffer getOffer(Entity entity, RandomSource rand) {
                        if (entity instanceof  VillagerDataHolder villagerDataHolder) {
                                VillagerType villagerType = villagerDataHolder.getVillagerData().getType();
                                VillagerTrades.ItemListing villagerTrades$itemListing = this.trades.get(villagerType);

                                return villagerTrades$itemListing == null ? null : villagerTrades$itemListing.getOffer(entity, rand);
                        } else {
                                return null;
                        }
                }
        }

        static class HCEnchantBookForEmeralds implements VillagerTrades.ItemListing {
                private final int experience;
                private final int maxUses;
                private final List<Enchantment> possibleEnchantments;
                private final int minLevel;
                private final int maxLevel;

                public HCEnchantBookForEmeralds(int experience, int maxUses) {
                        this(experience, maxUses, BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isTradeable).toArray(Enchantment[]::new));
                }

                public HCEnchantBookForEmeralds(int experience, int maxUses, Enchantment... possibleEnchantments) {
                        this(experience, maxUses, 0, Integer.MAX_VALUE, possibleEnchantments);
                }

                public HCEnchantBookForEmeralds(int experience, int maxUses, int minLevel, int maxLevel, double weight, Enchantment purposeEnchantment) {
                        this.experience = experience;
                        this.maxUses = maxUses;
                        this.minLevel = minLevel;
                        this.maxLevel = maxLevel;

                        Random rand = new Random();
                        if (rand.nextDouble() < weight)
                                this.possibleEnchantments = Arrays.asList(purposeEnchantment);
                        else
                                this.possibleEnchantments = Arrays.asList(Enchantments.BINDING_CURSE, Enchantments.VANISHING_CURSE);
                }

                public HCEnchantBookForEmeralds(int experience, int maxUses, int minLevel, int maxLevel, Enchantment... possibleEnchantments) {
                        this.experience = experience;
                        this.maxUses = maxUses;
                        this.minLevel = minLevel;
                        this.maxLevel = maxLevel;
                        this.possibleEnchantments = Arrays.asList(possibleEnchantments);
                }

                @org.jetbrains.annotations.Nullable
                @Override
                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        Enchantment enchantment = Enchantments.ALL_DAMAGE_PROTECTION;

                        enchantment = this.possibleEnchantments.get(randomSource.nextInt(this.possibleEnchantments.size()));

                        int i = Math.max(enchantment.getMinLevel(), this.minLevel);
                        int j = Math.min(enchantment.getMaxLevel(), this.maxLevel);
                        int k = Mth.nextInt(randomSource, i, j);
                        ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, k));
                        int l = 2 + randomSource.nextInt(5 + k * 10) + 3 * k;

                        if (enchantment.isTreasureOnly()) {
                                l *= 2;
                        }

                        l = (int) Math.ceil(l * 1.5F);

                        if (l > 64) {
                                l = 64;
                        }

                        return new MerchantOffer(new ItemStack(Items.EMERALD, l), new ItemStack(Items.BOOK), itemStack, maxUses, this.experience, 0.23F);
                }
        }

        static class HCDyedArmorForEmeralds implements VillagerTrades.ItemListing {
                private final Item item;
                private final int value;
                private final int maxUses;
                private final int villagerXp;

                public HCDyedArmorForEmeralds(Item item, int value) {
                        this(item, value, 12, 1);
                }

                public HCDyedArmorForEmeralds(Item item, int value, int maxUses, int villagerXp) {
                        this.item = item;
                        this.value = value;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                }

                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        ItemStack itemstack = new ItemStack(Items.EMERALD, this.value);
                        ItemStack itemstack1 = new ItemStack(this.item);

                        if (this.item instanceof DyeableArmorItem) {
                                List<DyeItem> list = Lists.newArrayList();
                                list.add(getRandomDye(randomSource));
                                if (randomSource.nextFloat() > 0.7F) {
                                        list.add(getRandomDye(randomSource));
                                }

                                if (randomSource.nextFloat() > 0.8F) {
                                        list.add(getRandomDye(randomSource));
                                }

                                itemstack1 = DyeableLeatherItem.dyeArmor(itemstack1, list);
                        }

                        return new MerchantOffer(itemstack, itemstack1, this.maxUses, this.villagerXp, 0.23F); //Modified
                }

                private static DyeItem getRandomDye(RandomSource p_219677_) {
                        return DyeItem.byColor(DyeColor.byId(p_219677_.nextInt(16)));
                }
        }

        static class HCEmeraldForItems implements VillagerTrades.ItemListing {
                private final Item item;
                private final int cost;
                private final int maxUses;
                private final int villagerXp;

                public HCEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
                        this.item = item.asItem();
                        this.cost = cost;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                }

                public MerchantOffer getOffer(Entity p_219682_, RandomSource p_219683_) {
                        return new MerchantOffer(new ItemStack(this.item, this.cost), new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.06F); //Modified
                }
        }

        static class HCEmeraldsForVillagerTypeItem implements VillagerTrades.ItemListing {
                private final Map<VillagerType, Item> trades;
                private final int cost;
                private final int maxUses;
                private final int villagerXp;

                public HCEmeraldsForVillagerTypeItem(int cost, int maxUses, int villagerXp, Map<VillagerType, Item> trades) {
                        BuiltInRegistries.VILLAGER_TYPE.stream().filter((p_35680_) -> {
                                return !trades.containsKey(p_35680_);
                        }).findAny().ifPresent((p_258962_) -> {
                                throw new IllegalStateException("Missing trade for villager type: " + BuiltInRegistries.VILLAGER_TYPE.getKey(p_258962_));
                        });
                        this.trades = trades;
                        this.cost = cost;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                }

                @Nullable
                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        if (entity instanceof VillagerDataHolder) {
                                ItemStack itemstack = new ItemStack(this.trades.get(((VillagerDataHolder)entity).getVillagerData().getType()), this.cost);

                                return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.06F); //Modified
                        } else {
                                return null;
                        }
                }
        }

        static class HCEnchantedItemForEmeralds implements VillagerTrades.ItemListing {
                private final ItemStack itemStack;
                private final int baseEmeraldCost;
                private final int maxUses;
                private final int villagerXp;
                private final float priceMultiplier;

                public HCEnchantedItemForEmeralds(Item item, int baseEmeraldCost, int maxUses, int villagerXp) {
                        this(item, baseEmeraldCost, maxUses, villagerXp, 0.06F);
                }

                public HCEnchantedItemForEmeralds(Item item, int baseEmeraldCost, int maxUses, int villagerXp, float priceMultiplier) {
                        this.itemStack = new ItemStack(item);
                        this.baseEmeraldCost = baseEmeraldCost;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                        this.priceMultiplier = priceMultiplier;
                }

                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        int i = 5 + randomSource.nextInt(15); //5 ~ 19
                        int j = Math.min((this.baseEmeraldCost + i) * 2, 64); //Modified
                        ItemStack itemstack = EnchantmentHelper.enchantItem(randomSource, new ItemStack(this.itemStack.getItem()), i, false);
                        ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);

                        return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
                }
        }

        static class HCItemsAndEmeraldsToItems implements VillagerTrades.ItemListing {
                private final ItemStack fromItem;
                private final int fromCount;
                private final int emeraldCost;
                private final ItemStack toItem;
                private final int toCount;
                private final int maxUses;
                private final int villagerXp;
                private final float priceMultiplier;

                public HCItemsAndEmeraldsToItems(ItemLike fromItem, int fromCount, Item toItem, int toCount, int maxUses, int villagerXp) {
                        this(fromItem, fromCount, 1, toItem, toCount, maxUses, villagerXp);
                }

                public HCItemsAndEmeraldsToItems(ItemLike fromItem, int fromCount, int emeraldCost, Item toItem, int toCount, int maxUses, int villagerXp) {
                        this.fromItem = new ItemStack(fromItem);
                        this.fromCount = fromCount;
                        this.emeraldCost = emeraldCost;
                        this.toItem = new ItemStack(toItem);
                        this.toCount = toCount;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                        this.priceMultiplier = 0.06F; //Modified
                }

                @Nullable
                public MerchantOffer getOffer(Entity p_219696_, RandomSource p_219697_) {
                        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
                }
        }

        static class HCItemsForEmeralds implements VillagerTrades.ItemListing {
                private final ItemStack itemStack;
                private final int emeraldCost;
                private final int numberOfItems;
                private final int maxUses;
                private final int villagerXp;
                private final float priceMultiplier;

                public HCItemsForEmeralds(Block block, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
                        this(new ItemStack(block), emeraldCost, numberOfItems, maxUses, villagerXp);
                }

                public HCItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int villagerXp) {
                        this(new ItemStack(item), emeraldCost, numberOfItems, 12, villagerXp);
                }

                public HCItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
                        this(new ItemStack(item), emeraldCost, numberOfItems, maxUses, villagerXp);
                }

                public HCItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
                        this(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp, 0.06F);  //Modified
                }

                public HCItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) {
                        this.itemStack = itemStack;
                        this.emeraldCost = emeraldCost;
                        this.numberOfItems = numberOfItems;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                        this.priceMultiplier = priceMultiplier;
                }

                public MerchantOffer getOffer(Entity p_219699_, RandomSource p_219700_) {
                        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
                }
        }

        static class HCSuspiciousStewForEmerald implements VillagerTrades.ItemListing {
                final MobEffect effect;
                final int duration;
                final int villagerXp;
                private final float priceMultiplier;

                public HCSuspiciousStewForEmerald(MobEffect effect, int duration, int villagerXp) {
                        this.effect = effect;
                        this.duration = duration;
                        this.villagerXp = villagerXp;
                        this.priceMultiplier = 0.06F; //Modified
                }

                @Nullable
                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        ItemStack itemstack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
                        SuspiciousStewItem.saveMobEffect(itemstack, this.effect, this.duration);

                        return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemstack, 12, this.villagerXp, this.priceMultiplier);
                }
        }

        static class HCTippedArrowForItemsAndEmeralds implements VillagerTrades.ItemListing {
                private final ItemStack toItem;
                private final int toCount;
                private final int emeraldCost;
                private final int maxUses;
                private final int villagerXp;
                private final Item fromItem;
                private final int fromCount;
                private final float priceMultiplier;

                public HCTippedArrowForItemsAndEmeralds(Item toItem, int fromCount, Item p_35795_, int toCount, int emeraldCost, int maxUses, int villagerXp) {
                        this.toItem = new ItemStack(toItem);
                        this.emeraldCost = emeraldCost;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                        this.fromItem = toItem;
                        this.fromCount = fromCount;
                        this.toCount = toCount;
                        this.priceMultiplier = 0.06F; //Modified
                }

                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        ItemStack itemstack = new ItemStack(Items.EMERALD, this.emeraldCost);
                        List<Potion> list = BuiltInRegistries.POTION.stream().filter((p_35804_) -> {
                                return !p_35804_.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(p_35804_);
                        }).collect(Collectors.toList());
                        Potion potion = list.get(randomSource.nextInt(list.size()));
                        ItemStack itemstack1 = PotionUtils.setPotion(new ItemStack(this.toItem.getItem(), this.toCount), potion);

                        return new MerchantOffer(itemstack, new ItemStack(this.fromItem, this.fromCount), itemstack1, this.maxUses, this.villagerXp, this.priceMultiplier);
                }
        }

        static class HCTreasureMapForEmeralds implements VillagerTrades.ItemListing {
                private final int emeraldCost;
                private final TagKey<Structure> destination;
                private final String displayName;
                private final MapDecoration.Type destinationType;
                private final int maxUses;
                private final int villagerXp;

                public HCTreasureMapForEmeralds(int emeraldCost, TagKey<Structure> destination, String displayName, MapDecoration.Type destinationType, int maxUses, int villagerXp) {
                        this.emeraldCost = emeraldCost;
                        this.destination = destination;
                        this.displayName = displayName;
                        this.destinationType = destinationType;
                        this.maxUses = maxUses;
                        this.villagerXp = villagerXp;
                }

                @Nullable
                public MerchantOffer getOffer(Entity entity, RandomSource randomSource) {
                        if (!(entity.level() instanceof ServerLevel)) {
                                return null;
                        } else {
                                ServerLevel serverlevel = (ServerLevel)entity.level();
                                BlockPos blockpos = serverlevel.findNearestMapStructure(this.destination, entity.blockPosition(), 100, true);

                                if (blockpos != null) {
                                        ItemStack itemstack = MapItem.create(serverlevel, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                                        MapItem.renderBiomePreviewMap(serverlevel, itemstack);
                                        MapItemSavedData.addTargetDecoration(itemstack, blockpos, "+", this.destinationType);
                                        itemstack.setHoverName(Component.translatable(this.displayName));

                                        return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(Items.COMPASS), itemstack, this.maxUses, this.villagerXp, 0.23F); //Modified
                                } else {
                                        return null;
                                }
                        }
                }
        }
}
