package jp.nemi.hardcore.event;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCItems;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = HCCore.MOD_ID)
    public static class PlayerEvent {
        @SubscribeEvent
        public static void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) {
            if (event.getPlayer() != null && HCConfigCommon.isDisableSleep.get()) {
                ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

                if ((!player.isSleeping() && player.isAlive()) || event.getEntityLiving().getCommandSenderWorld().dimension() != World.OVERWORLD) {
                    event.getPlayer().displayClientMessage(new TranslationTextComponent("hardcore.disabled_bed"), true);
                    event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);

                    if(HCConfigCommon.isSetSpawnPoint.get() && !player.level.isClientSide()) player.setRespawnPosition(player.level.dimension(), event.getPos(), player.yRot, false, true);
                    return;
                }
                return;
            }
        }
    }

    @Mod.EventBusSubscriber(modid = HCCore.MOD_ID)
    public static class EntityEvent {
        @SubscribeEvent
        public static void onLivingSpawnEvent(LivingSpawnEvent event) {
            if (!(event.getEntity() instanceof MobEntity)) return;

            Entity entity = event.getEntity();
            if (entity instanceof ZombieEntity) {
                ZombieEntity zombie = (ZombieEntity)entity;
                zombie.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET)); //test
            }
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void setupItemColors(final ColorHandlerEvent.Item event) {
            ItemColors colors = event.getItemColors();
            colors.register((stack, color) ->
                            color > 0 ? -1 :((IDyeableArmorItem) stack.getItem()).getColor(stack),
                    HCItems.N_LEATHER_HELMET.get(), HCItems.N_LEATHER_CHESTPLATE.get(), HCItems.N_LEATHER_LEGGINGS.get(), HCItems.N_LEATHER_BOOTS.get());
        }
    }
}
