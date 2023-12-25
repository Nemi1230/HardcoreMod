package jp.nemi.hardcore.event;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.client.model.HCCreeperModel;
import jp.nemi.hardcore.client.renderer.entity.HCCreeperRenderer;
import jp.nemi.hardcore.init.MCRegistries;
import jp.nemi.hardcore.object.entities.vanilla.HCCreeper;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class HCEvents {

        @Mod.EventBusSubscriber(modid = HCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class CommonModEvent {
                @SubscribeEvent
                public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
                        event.put(MCRegistries.HC_CREEPER.get(), HCCreeper.createAttributes().build());
                }
        }

        @Mod.EventBusSubscriber(modid = HCCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class ClientModEvent {
                @OnlyIn(Dist.CLIENT)
                public static void clientSetup(final FMLClientSetupEvent event) {
                }

                @SubscribeEvent
                public static void onEntityRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
                        event.registerEntityRenderer(MCRegistries.HC_CREEPER.get(), HCCreeperRenderer::new);
                }

                @SubscribeEvent
                public static void onEntityRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
                        event.registerLayerDefinition(HCCreeperModel.LAYER_LOCATION, () -> HCCreeperModel.createBodyLayer(CubeDeformation.NONE));
                }
        }
}
