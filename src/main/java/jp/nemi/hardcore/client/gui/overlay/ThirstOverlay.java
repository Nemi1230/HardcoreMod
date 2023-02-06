package jp.nemi.hardcore.client.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.capability.ThirstLevelCapability;
import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Random;

@EventBusSubscriber(value = {Dist.CLIENT}, modid = HCCore.MOD_ID)
public class ThirstOverlay extends AbstractGui {
    public static final ResourceLocation ICON = new ResourceLocation(HCCore.MOD_ID, "textures/gui/icons.png");
    protected static final int WIDTH = 9;
    protected static final int HEIGHT = 9;
    protected static int tick = 0;
    protected Minecraft mc;
    protected final Random rand = new Random();

    public ThirstOverlay(Minecraft mc) {
        this.mc = mc;
    }

    public void render(MatrixStack ms, int screenWidth, int screenHeight, ThirstLevelCapability.Data capData, double toughness) {
        Minecraft.getInstance().getFrameTime();
        ms.pushPose();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        this.mc.getTextureManager().bind(ICON);

        EffectInstance effect = mc.player.getEffect(HCEffects.THIRST.get());
        int thirstLevel = capData.getThirstLevel();
        int thirstSaturationLevel = capData.getThirstSaturationLevel();
        float thirstExhaustionLevel = capData.getThirstExhaustionLevel();
        int left = screenWidth / 2 + 91;
        int top = screenHeight - 50;
        int icon = 0;
        int background = 0;

        if (effect != null) {
            icon += 36;
            background += 12;
        }

        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;

            if (thirstSaturationLevel <= 0.0F && tick % (thirstLevel * 3 + 1) == 0)
                y = top + (rand.nextInt(3) - 1);

            this.blit(ms, x, y, background * 9, 0, WIDTH, HEIGHT);

            if (idx < thirstLevel)
                this.blit(ms, x, y, icon + 36, 0, WIDTH, HEIGHT);
            if (idx == thirstLevel)
                this.blit(ms, x, y, icon + 45, 0, WIDTH, HEIGHT);
            if (HCConfigCommon.isShowThirstSaturationLevel.get().booleanValue()) { //隠し水分量表示
                if (idx < thirstSaturationLevel)
                    this.blit(ms, x - 1, y -1, icon, 9, WIDTH + 2, HEIGHT + 2);
                if (idx == thirstSaturationLevel)
                    this.blit(ms, x -1, y - 1, icon + 11, 9, WIDTH + 2, HEIGHT + 2);
            }
        }

        if (HCConfigCommon.isDebugMode.get().booleanValue()) { //デバックモード(PlayerThirstLevel Cap表示)
            this.drawString(ms, mc.font, "Thirst Level: " + thirstLevel, left - 125, top - 30, 16777215);
            this.drawString(ms, mc.font, "Thirst Saturation Level: " + thirstSaturationLevel, left - 125, top - 20, 16777215);
            this.drawString(ms, mc.font, "Thirst Exhaustion Level: " + thirstExhaustionLevel, left - 125, top - 10, 16777215);
        }

        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
        ms.popPose();
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        tick++;
        tick %= 1200;
    }
}
