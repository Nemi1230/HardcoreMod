package jp.nemi.hardcore.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.config.HCConfigCommon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Unused
@Mod.EventBusSubscriber(modid = HCCore.MOD_ID)
public class EntityEvent {
    @SubscribeEvent
    public static void onLivingSpawnEvent(LivingSpawnEvent event) {
        if (!(event.getEntity() instanceof MobEntity) && !HCConfigCommon.isEnhanceEnemyMob.get().booleanValue()) return;

        Entity entity = event.getEntity();

//        if (entity instanceof EndermanEntity) {
//            EndermanEntity enderman = (EndermanEntity) entity;
//            enderman.goalSelector.removeGoal(new WaterAvoidingRandomWalkingGoal(enderman, 1.0D, 0.0F));
//        }
//
//        if (entity instanceof ZombieEntity) {
//            ZombieEntity zombie = (ZombieEntity)entity;
//            zombie.getAttributes().addTransientAttributeModifiers(addAttributeModifier(10.0D, 8.0D, 0.0D, 0.03D, 3.0D, 0.0D, 0.0D, 2.0D, 1.0D, 0.0D, 0.15D, 0.0D));
//            zombie.addEffect(new EffectInstance(Effects.HARM, 2, 100, false, false));
//            equipArmor(zombie, zombie.level, new boolean[] {true, true, true, true});
//        }
//        if (entity instanceof ZombieVillagerEntity) {
//            ZombieVillagerEntity zombie = (ZombieVillagerEntity)entity;
//            zombie.getAttributes().addTransientAttributeModifiers(addAttributeModifier(10.0D, 8.0D, 0.0D, 0.03D, 3.0D, 0.0D, 0.0D, 2.0D, 1.0D, 0.0D, 0.15D, 0.0D));
//            zombie.addEffect(new EffectInstance(Effects.HARM, 2, 100, false, false));
//            equipArmor(zombie, zombie.level, new boolean[] {true, true, true, true});
//        }
    }

    private static Multimap<Attribute, AttributeModifier> addAttributeModifier(double health, double follow, double rKnockback, double mSpeed, double damage, double aKnockBack, double aSpeed, double armor, double armorT, double luck, double spawn, double jump) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.MAX_HEALTH, new AttributeModifier("Increased physical strength",health, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.FOLLOW_RANGE, new AttributeModifier("Increased visibility", follow, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier("Knockback resistance", rKnockback, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("Increased movement speed", mSpeed, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ATTACK_DAMAGE, new AttributeModifier("Increased attack power", damage, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("Knockback given by attack",aKnockBack, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ATTACK_SPEED, new AttributeModifier("Attack speed increase", aSpeed, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ARMOR, new AttributeModifier("armor", armor, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier("Armor strength", armorT, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.LUCK, new AttributeModifier("Luck", luck, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.SPAWN_REINFORCEMENTS_CHANCE, new AttributeModifier("Summon allies", spawn, AttributeModifier.Operation.ADDITION));
        map.put(Attributes.JUMP_STRENGTH, new AttributeModifier("Jumping power", jump, AttributeModifier.Operation.ADDITION));
        return map;
    }

    private static void equipArmor(Entity entity, World world, boolean[] armor) {
        Difficulty difficulty = world.getDifficulty();

        if (armor[0] && difficulty.getId() > 0) entity.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
        if (armor[1] && difficulty.getId() > 1) entity.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
        if (armor[2] && difficulty.getId() > 2) entity.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        if (armor[3] && (difficulty == Difficulty.HARD && world.getLevelData().isHardcore())) entity.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
    }
}