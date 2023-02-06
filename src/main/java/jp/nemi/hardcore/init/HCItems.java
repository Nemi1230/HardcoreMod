package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.object.items.HCArmorMaterial;
import jp.nemi.hardcore.object.items.HCItemTier;
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
public class HCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HCCore.MOD_ID);

    public static RegistryObject<Item> N_WOODEN_SWORD;
    public static RegistryObject<Item> N_WOODEN_SHOVEL;
    public static RegistryObject<Item> N_WOODEN_PICKAXE;
    public static RegistryObject<Item> N_WOODEN_AXE;
    public static RegistryObject<Item> N_WOODEN_HOE;
    public static RegistryObject<Item> N_STONE_SWORD;
    public static RegistryObject<Item> N_STONE_SHOVEL;
    public static RegistryObject<Item> N_STONE_PICKAXE;
    public static RegistryObject<Item> N_STONE_AXE;
    public static RegistryObject<Item> N_STONE_HOE;
    public static RegistryObject<Item> N_IRON_SWORD;
    public static RegistryObject<Item> N_IRON_SHOVEL;
    public static RegistryObject<Item> N_IRON_PICKAXE;
    public static RegistryObject<Item> N_IRON_AXE;
    public static RegistryObject<Item> N_IRON_HOE;
    public static RegistryObject<Item> N_DIAMOND_SWORD;
    public static RegistryObject<Item> N_DIAMOND_SHOVEL;
    public static RegistryObject<Item> N_DIAMOND_PICKAXE;
    public static RegistryObject<Item> N_DIAMOND_AXE;
    public static RegistryObject<Item> N_DIAMOND_HOE;
    public static RegistryObject<Item> N_GOLDEN_SWORD;
    public static RegistryObject<Item> N_GOLDEN_SHOVEL;
    public static RegistryObject<Item> N_GOLDEN_PICKAXE;
    public static RegistryObject<Item> N_GOLDEN_AXE;
    public static RegistryObject<Item> N_GOLDEN_HOE;
    public static RegistryObject<Item> N_NETHERITE_SWORD;
    public static RegistryObject<Item> N_NETHERITE_SHOVEL;
    public static RegistryObject<Item> N_NETHERITE_PICKAXE;
    public static RegistryObject<Item> N_NETHERITE_AXE;
    public static RegistryObject<Item> N_NETHERITE_HOE;
    public static RegistryObject<Item> N_LEATHER_HELMET;
    public static RegistryObject<Item> N_LEATHER_CHESTPLATE;
    public static RegistryObject<Item> N_LEATHER_LEGGINGS;
    public static RegistryObject<Item> N_LEATHER_BOOTS;
    public static RegistryObject<Item> N_CHAINMAIL_HELMET;
    public static RegistryObject<Item> N_CHAINMAIL_CHESTPLATE;
    public static RegistryObject<Item> N_CHAINMAIL_LEGGINGS;
    public static RegistryObject<Item> N_CHAINMAIL_BOOTS;
    public static RegistryObject<Item> N_IRON_HELMET;
    public static RegistryObject<Item> N_IRON_CHESTPLATE;
    public static RegistryObject<Item> N_IRON_LEGGINGS;
    public static RegistryObject<Item> N_IRON_BOOTS;
    public static RegistryObject<Item> N_DIAMOND_HELMET;
    public static RegistryObject<Item> N_DIAMOND_CHESTPLATE;
    public static RegistryObject<Item> N_DIAMOND_LEGGINGS;
    public static RegistryObject<Item> N_DIAMOND_BOOTS;
    public static RegistryObject<Item> N_GOLDEN_HELMET;
    public static RegistryObject<Item> N_GOLDEN_CHESTPLATE;
    public static RegistryObject<Item> N_GOLDEN_LEGGINGS;
    public static RegistryObject<Item> N_GOLDEN_BOOTS;
    public static RegistryObject<Item> N_NETHERITE_HELMET;
    public static RegistryObject<Item> N_NETHERITE_CHESTPLATE;
    public static RegistryObject<Item> N_NETHERITE_LEGGINGS;
    public static RegistryObject<Item> N_NETHERITE_BOOTS;

    public static final ItemGroup TAB_MAIN = new ItemGroup("hardcore_main") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.WITHER_SKELETON_SKULL.getItem());
        }
    };
    public static final ItemGroup TAB_TOOLS = new ItemGroup("hardcore_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.STONE_SWORD.getItem());
        }
    };

    public static void register(IEventBus eventBus) {
        if (HCConfigCommon.isArmorWeakening.get()) {
            N_WOODEN_SWORD = ITEMS.register("durable_wooden_sword", () -> new SwordItem(HCItemTier.N_WOOD, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_WOODEN_SHOVEL = ITEMS.register("durable_wooden_shovel", () -> new ShovelItem(HCItemTier.N_WOOD, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_WOODEN_PICKAXE = ITEMS.register("durable_wooden_pickaxe", () -> new PickaxeItem(HCItemTier.N_WOOD, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_WOODEN_AXE = ITEMS.register("durable_wooden_axe", () -> new AxeItem(HCItemTier.N_WOOD, 6, -3.2F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_WOODEN_HOE = ITEMS.register("durable_wooden_hoe", () -> new HoeItem(HCItemTier.N_WOOD, 0, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_STONE_SWORD = ITEMS.register("durable_stone_sword", () -> new SwordItem(HCItemTier.N_STONE, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_STONE_SHOVEL = ITEMS.register("durable_stone_shovel", () -> new ShovelItem(HCItemTier.N_STONE, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_STONE_PICKAXE = ITEMS.register("durable_stone_pickaxe", () -> new PickaxeItem(HCItemTier.N_STONE, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_STONE_AXE = ITEMS.register("durable_stone_axe", () -> new AxeItem(HCItemTier.N_STONE, 7.0F, -3.2F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_STONE_HOE = ITEMS.register("durable_stone_hoe", () -> new HoeItem(HCItemTier.N_STONE, -1, -2.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_IRON_SWORD = ITEMS.register("durable_iron_sword", () -> new SwordItem(HCItemTier.N_IRON, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_IRON_SHOVEL = ITEMS.register("durable_iron_shovel", () -> new ShovelItem(HCItemTier.N_IRON, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_IRON_PICKAXE = ITEMS.register("durable_iron_pickaxe", () -> new PickaxeItem(HCItemTier.N_IRON, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_IRON_AXE = ITEMS.register("durable_iron_axe", () -> new AxeItem(HCItemTier.N_IRON, 5.0F, -3.1F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_IRON_HOE = ITEMS.register("durable_iron_hoe", () -> new HoeItem(HCItemTier.N_IRON, -2, -1.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_DIAMOND_SWORD = ITEMS.register("durable_diamond_sword", () -> new SwordItem(HCItemTier.N_DIAMOND, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_DIAMOND_SHOVEL = ITEMS.register("durable_diamond_shovel", () -> new ShovelItem(HCItemTier.N_DIAMOND, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_DIAMOND_PICKAXE = ITEMS.register("durable_diamond_pickaxe", () -> new PickaxeItem(HCItemTier.N_DIAMOND, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_DIAMOND_AXE = ITEMS.register("durable_diamond_axe", () -> new AxeItem(HCItemTier.N_DIAMOND, 5.0F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_DIAMOND_HOE = ITEMS.register("durable_diamond_hoe", () -> new HoeItem(HCItemTier.N_DIAMOND, -3, 0.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_GOLDEN_SWORD = ITEMS.register("durable_golden_sword", () -> new SwordItem(HCItemTier.N_GOLD, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_GOLDEN_SHOVEL = ITEMS.register("durable_golden_shovel", () -> new ShovelItem(HCItemTier.N_GOLD, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_GOLDEN_PICKAXE = ITEMS.register("durable_golden_pickaxe", () -> new PickaxeItem(HCItemTier.N_GOLD, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_GOLDEN_AXE = ITEMS.register("durable_golden_axe", () -> new AxeItem(HCItemTier.N_GOLD, 6.0F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_GOLDEN_HOE = ITEMS.register("durable_golden_hoe", () -> new HoeItem(HCItemTier.N_GOLD, 0, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_NETHERITE_SWORD = ITEMS.register("durable_netherite_sword", () -> new SwordItem(HCItemTier.N_NETHERITE, 3, -2.4F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_NETHERITE_SHOVEL = ITEMS.register("durable_netherite_shovel", () -> new ShovelItem(HCItemTier.N_NETHERITE, 1.5F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_NETHERITE_PICKAXE = ITEMS.register("durable_netherite_pickaxe", () -> new PickaxeItem(HCItemTier.N_NETHERITE, 1, -2.8F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_NETHERITE_AXE = ITEMS.register("durable_netherite_axe", () -> new AxeItem(HCItemTier.N_NETHERITE, 5.0F, -3.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_NETHERITE_HOE = ITEMS.register("durable_netherite_hoe", () -> new HoeItem(HCItemTier.N_NETHERITE, -4, 0.0F, (new Item.Properties().tab(HCItems.TAB_TOOLS))));
            N_LEATHER_HELMET = ITEMS.register("durable_leather_helmet", () -> new DyeableArmorItem(HCArmorMaterial.N_LEATHER, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_LEATHER_CHESTPLATE = ITEMS.register("durable_leather_chestplate", () -> new DyeableArmorItem(HCArmorMaterial.N_LEATHER, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_LEATHER_LEGGINGS = ITEMS.register("durable_leather_leggings", () -> new DyeableArmorItem(HCArmorMaterial.N_LEATHER, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_LEATHER_BOOTS = ITEMS.register("durable_leather_boots", () -> new DyeableArmorItem(HCArmorMaterial.N_LEATHER, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_CHAINMAIL_HELMET = ITEMS.register("durable_chainmail_helmet", () -> new ArmorItem(HCArmorMaterial.N_CHAIN, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_CHAINMAIL_CHESTPLATE = ITEMS.register("durable_chainmail_chestplate", () -> new ArmorItem(HCArmorMaterial.N_CHAIN, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_CHAINMAIL_LEGGINGS = ITEMS.register("durable_chainmail_leggings", () -> new ArmorItem(HCArmorMaterial.N_CHAIN, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_CHAINMAIL_BOOTS = ITEMS.register("durable_chainmail_boots", () -> new ArmorItem(HCArmorMaterial.N_CHAIN, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_IRON_HELMET = ITEMS.register("durable_iron_helmet", () -> new ArmorItem(HCArmorMaterial.N_IRON, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_IRON_CHESTPLATE = ITEMS.register("durable_iron_chestplate", () -> new ArmorItem(HCArmorMaterial.N_IRON, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_IRON_LEGGINGS = ITEMS.register("durable_iron_leggings", () -> new ArmorItem(HCArmorMaterial.N_IRON, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_IRON_BOOTS = ITEMS.register("durable_iron_boots", () -> new ArmorItem(HCArmorMaterial.N_IRON, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_DIAMOND_HELMET = ITEMS.register("durable_diamond_helmet", () -> new ArmorItem(HCArmorMaterial.N_DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_DIAMOND_CHESTPLATE = ITEMS.register("durable_diamond_chestplate", () -> new ArmorItem(HCArmorMaterial.N_DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_DIAMOND_LEGGINGS = ITEMS.register("durable_diamond_leggings", () -> new ArmorItem(HCArmorMaterial.N_DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_DIAMOND_BOOTS = ITEMS.register("durable_diamond_boots", () -> new ArmorItem(HCArmorMaterial.N_DIAMOND, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_GOLDEN_HELMET = ITEMS.register("durable_golden_helmet", () -> new ArmorItem(HCArmorMaterial.N_GOLD, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_GOLDEN_CHESTPLATE = ITEMS.register("durable_golden_chestplate", () -> new ArmorItem(HCArmorMaterial.N_GOLD, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_GOLDEN_LEGGINGS = ITEMS.register("durable_golden_leggings", () -> new ArmorItem(HCArmorMaterial.N_GOLD, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_GOLDEN_BOOTS = ITEMS.register("durable_golden_boots", () -> new ArmorItem(HCArmorMaterial.N_GOLD, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_NETHERITE_HELMET = ITEMS.register("durable_netherite_helmet", () -> new ArmorItem(HCArmorMaterial.N_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_NETHERITE_CHESTPLATE = ITEMS.register("durable_netherite_chestplate", () -> new ArmorItem(HCArmorMaterial.N_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_NETHERITE_LEGGINGS = ITEMS.register("durable_netherite_leggings", () -> new ArmorItem(HCArmorMaterial.N_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().tab(HCItems.TAB_TOOLS)));
            N_NETHERITE_BOOTS = ITEMS.register("durable_netherite_boots", () -> new ArmorItem(HCArmorMaterial.N_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().tab(HCItems.TAB_TOOLS)));
        }

        ITEMS.register(eventBus);
    }
}
