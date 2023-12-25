package jp.nemi.hardcore.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import jp.nemi.hardcore.client.model.HCCreeperModel;
import jp.nemi.hardcore.client.renderer.entity.layers.HCCreeperPowerLayer;
import jp.nemi.hardcore.object.entities.vanilla.HCCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HCCreeperRenderer extends MobRenderer<HCCreeper, HCCreeperModel<HCCreeper>> {
        public static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper.png");

        public HCCreeperRenderer(EntityRendererProvider.Context p_173958_) {
                super(p_173958_, new HCCreeperModel<>(p_173958_.bakeLayer(ModelLayers.CREEPER)), 0.5F);
                this.addLayer(new HCCreeperPowerLayer(this, p_173958_.getModelSet()));
        }

        protected void scale(HCCreeper p_114046_, PoseStack p_114047_, float p_114048_) {
                float f = p_114046_.getSwelling(p_114048_);
                float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
                f = Mth.clamp(f, 0.0F, 1.0F);
                f *= f;
                f *= f;
                float f2 = (1.0F + f * 0.4F) * f1;
                float f3 = (1.0F + f * 0.1F) / f1;
                p_114047_.scale(f2, f3, f2);
        }

        protected float getWhiteOverlayProgress(HCCreeper p_114043_, float p_114044_) {
                float f = p_114043_.getSwelling(p_114044_);
                return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
        }

        public ResourceLocation getTextureLocation(HCCreeper p_114041_) {
                return CREEPER_LOCATION;
        }
}
