package jp.nemi.hardcore.client.gui.overlay;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.capability.ThirstLevelCapability;
import jp.nemi.hardcore.config.HCConfigCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = {Dist.CLIENT}, modid = HCCore.MOD_ID)
public class OverlayHandler {
    private static final ThirstOverlay THIRST_OVERLAY = new ThirstOverlay(Minecraft.getInstance());

    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = event.getWindow().getGuiScaledWidth();
        int screenHeight = event.getWindow().getGuiScaledHeight();
        ClientPlayerEntity player = Minecraft.getInstance().player;
        RayTraceResult pos = mc.hitResult;

        if (event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
            if (player != null && !player.isCreative() && !player.isSpectator() && !Minecraft.getInstance().options.hideGui) {
                if (!player.isSpectator() && HCConfigCommon.isThirstEnabled.get().booleanValue())
                    player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> THIRST_OVERLAY.render(event.getMatrixStack(), screenWidth, screenHeight, t, player.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue()));
            }
        }
    }
}
