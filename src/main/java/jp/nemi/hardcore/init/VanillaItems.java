package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.items.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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

    //region Tools
    public static final RegistryObject<Item> O_WOODEN_SWORD = ITEMS.register("wooden_sword", () -> new SwordItem(HCItemTier.O_WOOD, 3, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_WOODEN_SHOVEL = ITEMS.register("wooden_shovel", () -> new ShovelItem(HCItemTier.O_WOOD, 1.5F, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_WOODEN_PICKAXE = ITEMS.register("wooden_pickaxe", () -> new PickaxeItem(HCItemTier.O_WOOD, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_WOODEN_AXE = ITEMS.register("wooden_axe", () -> new AxeItem(HCItemTier.O_WOOD, 6.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_WOODEN_HOE = ITEMS.register("wooden_hoe", () -> new HoeItem(HCItemTier.O_WOOD, 0, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_STONE_SWORD = ITEMS.register("stone_sword", () -> new SwordItem(HCItemTier.O_STONE, 3, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_STONE_SHOVEL = ITEMS.register("stone_shovel", () -> new ShovelItem(HCItemTier.O_STONE, 1.5F, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_STONE_PICKAXE = ITEMS.register("stone_pickaxe", () -> new PickaxeItem(HCItemTier.O_STONE, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_STONE_AXE = ITEMS.register("stone_axe", () -> new AxeItem(HCItemTier.O_STONE, 7.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_STONE_HOE = ITEMS.register("stone_hoe", () -> new HoeItem(HCItemTier.O_STONE, -1, -2.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_IRON_SWORD = ITEMS.register("iron_sword", () -> new SwordItem(HCItemTier.O_IRON, 2, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_IRON_SHOVEL = ITEMS.register("iron_shovel", () -> new ShovelItem(HCItemTier.O_IRON, 1, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_IRON_PICKAXE = ITEMS.register("iron_pickaxe", () -> new PickaxeItem(HCItemTier.O_IRON, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_IRON_AXE = ITEMS.register("iron_axe", () -> new AxeItem(HCItemTier.O_IRON, 6.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_IRON_HOE = ITEMS.register("iron_hoe", () -> new HoeItem(HCItemTier.O_IRON, 0, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_DIAMOND_SWORD = ITEMS.register("diamond_sword", () -> new SwordItem(HCItemTier.O_DIAMOND, 3, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_DIAMOND_SHOVEL = ITEMS.register("diamond_shovel", () -> new ShovelItem(HCItemTier.O_DIAMOND, 1.5F, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_DIAMOND_PICKAXE = ITEMS.register("diamond_pickaxe", () -> new PickaxeItem(HCItemTier.O_DIAMOND, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_DIAMOND_AXE = ITEMS.register("diamond_axe", () -> new AxeItem(HCItemTier.O_DIAMOND, 7.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_DIAMOND_HOE = ITEMS.register("diamond_hoe", () -> new HoeItem(HCItemTier.O_DIAMOND, -1, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_GOLDEN_SWORD = ITEMS.register("golden_sword", () -> new SwordItem(HCItemTier.O_GOLD, 2, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_GOLDEN_SHOVEL = ITEMS.register("golden_shovel", () -> new ShovelItem(HCItemTier.O_GOLD, 1, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_GOLDEN_PICKAXE = ITEMS.register("golden_pickaxe", () -> new PickaxeItem(HCItemTier.O_GOLD, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_GOLDEN_AXE = ITEMS.register("golden_axe", () -> new AxeItem(HCItemTier.O_GOLD, 6.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_GOLDEN_HOE = ITEMS.register("golden_hoe", () -> new HoeItem(HCItemTier.O_GOLD, 0, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_NETHERITE_SWORD = ITEMS.register("netherite_sword", () -> new SwordItem(HCItemTier.O_NETHERITE, 3, -2.4F, (new Item.Properties()).tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_NETHERITE_SHOVEL = ITEMS.register("netherite_shovel", () -> new ShovelItem(HCItemTier.O_NETHERITE, 1.5F, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_NETHERITE_PICKAXE = ITEMS.register("netherite_pickaxe", () -> new PickaxeItem(HCItemTier.O_NETHERITE, 1, -2.8F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_NETHERITE_AXE = ITEMS.register("netherite_axe", () -> new AxeItem(HCItemTier.O_NETHERITE, 7.0F, -3.2F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> O_NETHERITE_HOE = ITEMS.register("netherite_hoe", () -> new HoeItem(HCItemTier.O_NETHERITE, -1, -3.0F, (new Item.Properties()).tab(ItemGroup.TAB_TOOLS)));
    //endregion

    //region Armors
    public static final RegistryObject<Item> O_LEATHER_HELMET = ITEMS.register("leather_helmet", () -> new DyeableArmorItem(HCArmorMaterial.O_LEATHER, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_LEATHER_CHESTPLATE = ITEMS.register("leather_chestplate", () -> new DyeableArmorItem(HCArmorMaterial.O_LEATHER, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_LEATHER_LEGGINGS = ITEMS.register("leather_leggings", () -> new DyeableArmorItem(HCArmorMaterial.O_LEATHER, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_LEATHER_BOOTS = ITEMS.register("leather_boots", () -> new DyeableArmorItem(HCArmorMaterial.O_LEATHER, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_CHAINMAIL_HELMET = ITEMS.register("chainmail_helmet", () -> new ArmorItem(HCArmorMaterial.O_CHAIN, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_CHAINMAIL_CHESTPLATE = ITEMS.register("chainmail_chestplate", () -> new ArmorItem(HCArmorMaterial.O_CHAIN, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_CHAINMAIL_LEGGINGS = ITEMS.register("chainmail_leggings", () -> new ArmorItem(HCArmorMaterial.O_CHAIN, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_CHAINMAIL_BOOTS = ITEMS.register("chainmail_boots", () -> new ArmorItem(HCArmorMaterial.O_CHAIN, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_IRON_HELMET = ITEMS.register("iron_helmet", () -> new ArmorItem(HCArmorMaterial.O_IRON, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_IRON_CHESTPLATE = ITEMS.register("iron_chestplate", () -> new ArmorItem(HCArmorMaterial.O_IRON, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_IRON_LEGGINGS = ITEMS.register("iron_leggings", () -> new ArmorItem(HCArmorMaterial.O_IRON, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_IRON_BOOTS = ITEMS.register("iron_boots", () -> new ArmorItem(HCArmorMaterial.O_IRON, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_DIAMOND_HELMET = ITEMS.register("diamond_helmet", () -> new ArmorItem(HCArmorMaterial.O_DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_DIAMOND_CHESTPLATE = ITEMS.register("diamond_chestplate", () -> new ArmorItem(HCArmorMaterial.O_DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_DIAMOND_LEGGINGS = ITEMS.register("diamond_leggings", () -> new ArmorItem(HCArmorMaterial.O_DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_DIAMOND_BOOTS = ITEMS.register("diamond_boots", () -> new ArmorItem(HCArmorMaterial.O_DIAMOND, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_GOLDEN_HELMET = ITEMS.register("golden_helmet", () -> new ArmorItem(HCArmorMaterial.O_GOLD, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_GOLDEN_CHESTPLATE = ITEMS.register("golden_chestplate", () -> new ArmorItem(HCArmorMaterial.O_GOLD, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_GOLDEN_LEGGINGS = ITEMS.register("golden_leggings", () -> new ArmorItem(HCArmorMaterial.O_GOLD, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_GOLDEN_BOOTS = ITEMS.register("golden_boots", () -> new ArmorItem(HCArmorMaterial.O_GOLD, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_NETHERITE_HELMET = ITEMS.register("netherite_helmet", () -> new ArmorItem(HCArmorMaterial.O_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_NETHERITE_CHESTPLATE = ITEMS.register("netherite_chestplate", () -> new ArmorItem(HCArmorMaterial.O_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_NETHERITE_LEGGINGS = ITEMS.register("netherite_leggings", () -> new ArmorItem(HCArmorMaterial.O_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> O_NETHERITE_BOOTS = ITEMS.register("netherite_boots", () -> new ArmorItem(HCArmorMaterial.O_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    //endregion

    public static final RegistryObject<Item> STICK = ITEMS.register("stick", () -> new WallOrFloorItem(HCBlocks.STICK.get(), HCBlocks.WALL_STICK.get(), (new Item.Properties().tab(ItemGroup.TAB_MATERIALS))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
