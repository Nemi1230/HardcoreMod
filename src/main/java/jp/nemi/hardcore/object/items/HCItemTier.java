package jp.nemi.hardcore.object.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum HCItemTier implements IItemTier {
    O_WOOD(0, 29, 2.0F, 0.0F, 7, () -> {
        return Ingredient.of(ItemTags.PLANKS);
    }),
    O_STONE(1, 65, 4.9F, 0.5F, 2, () -> {
        return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
    }),
    O_IRON(2, 125, 5.8F, 1.0F, 7, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    O_DIAMOND(3, 780, 7.6F, 1.5F, 5, () -> {
        return Ingredient.of(Items.DIAMOND);
    }),
    O_GOLD(0, 16, 12.0F, 0.0F, 11, () -> {
        return Ingredient.of(Items.GOLD_INGOT);
    }),
    O_NETHERITE(4, 1015, 8.2F, 2.0F, 7, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    N_WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
        return Ingredient.of(ItemTags.PLANKS);
    }),
    N_STONE(1, 131, 4.0F, 1.0F, 5, () -> {
        return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
    }),
    N_IRON(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    N_DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
        return Ingredient.of(Items.DIAMOND);
    }),
    N_GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
        return Ingredient.of(Items.GOLD_INGOT);
    }),
    N_NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    });

    private int level;
    private int maxUses;
    private float speed;
    private float attackDamage;
    private int enchantment;
    private LazyValue<Ingredient> repair;

    private HCItemTier(int level, int maxUses, float speed, float attackDamage, int enchantment, Supplier<Ingredient> repair) {
        this.level = level;
        this.maxUses = maxUses;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.enchantment = enchantment;
        this.repair = new LazyValue<Ingredient>(repair);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantment;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repair.get();
    }
}
