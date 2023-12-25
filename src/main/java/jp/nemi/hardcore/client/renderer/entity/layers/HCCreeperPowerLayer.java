package jp.nemi.hardcore.client.renderer.entity.layers;

import jp.nemi.hardcore.client.model.HCCreeperModel;
import jp.nemi.hardcore.object.entities.vanilla.HCCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class HCCreeperPowerLayer extends EnergySwirlLayer<HCCreeper, HCCreeperModel<HCCreeper>> {
        private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
        private final HCCreeperModel<HCCreeper> model;

        public HCCreeperPowerLayer(RenderLayerParent<HCCreeper, HCCreeperModel<HCCreeper>> layerParent, EntityModelSet modelSet) {
                super(layerParent);
                this.model = new HCCreeperModel<>(modelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
        }

        protected float xOffset(float value) {
                return value * 0.01F;
        }

        protected ResourceLocation getTextureLocation() {
                return POWER_LOCATION;
        }

        protected EntityModel<HCCreeper> model() {
                return this.model;
        }
}
