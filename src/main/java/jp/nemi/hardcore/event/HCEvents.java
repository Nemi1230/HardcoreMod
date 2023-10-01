package jp.nemi.hardcore.event;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.init.HCItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class HCEvents {
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

        @SubscribeEvent
        public static void onEntityJoinWorldEvent(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
            PlayerEntity player = event.getPlayer();

            if (player.level instanceof ServerWorld) {
                ServerWorld world = (ServerWorld) player.level;

                if (!HCCore.getHCSaveData(world).isAlreadyLoggInWorld) {
                    player.sendMessage(new TranslationTextComponent("hardcore.join.message"), player.getUUID());
                    HCCore.getHCSaveData(world).isAlreadyLoggInWorld = true;
                }
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

        @OnlyIn(Dist.CLIENT)
        public static void clientSetup(final FMLClientSetupEvent event) {
            RenderTypeLookup.setRenderLayer(HCBlocks.STICK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(HCBlocks.WALL_STICK.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(HCBlocks.HC_TORCH.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(HCBlocks.HC_WALL_TORCH.get(), RenderType.cutout());
        }
    }
}
