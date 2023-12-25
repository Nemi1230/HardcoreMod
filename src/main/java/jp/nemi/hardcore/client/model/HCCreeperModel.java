package jp.nemi.hardcore.client.model;

import jp.nemi.hardcore.HCCore;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class HCCreeperModel<T extends Entity> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(HCCore.MOD_ID, "creeper"), "main");


        private final ModelPart root;
        private final ModelPart head;
        private final ModelPart rightHindLeg;
        private final ModelPart leftHindLeg;
        private final ModelPart rightFrontLeg;
        private final ModelPart leftFrontLeg;
        private static final int Y_OFFSET = 6;

        public HCCreeperModel(ModelPart part) {
                this.root = part;
                this.head = part.getChild("head");
                this.leftHindLeg = part.getChild("right_hind_leg");
                this.rightHindLeg = part.getChild("left_hind_leg");
                this.leftFrontLeg = part.getChild("right_front_leg");
                this.rightFrontLeg = part.getChild("left_front_leg");
        }

        public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
                MeshDefinition meshdefinition = new MeshDefinition();
                PartDefinition partdefinition = meshdefinition.getRoot();
                partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation), PartPose.offset(0.0F, 6.0F, 0.0F));
                partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.offset(0.0F, 6.0F, 0.0F));
                CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation);
                partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2.0F, 18.0F, 4.0F));
                partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(2.0F, 18.0F, 4.0F));
                partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2.0F, 18.0F, -4.0F));
                partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(2.0F, 18.0F, -4.0F));
                return LayerDefinition.create(meshdefinition, 64, 32);
        }

        @Override
        public ModelPart root() {
                return this.root;
        }

        @Override
        public void setupAnim(T p_102463_, float p_102464_, float p_102465_, float p_102466_, float p_102467_, float p_102468_) {
                this.head.yRot = p_102467_ * ((float)Math.PI / 180F);
                this.head.xRot = p_102468_ * ((float)Math.PI / 180F);
                this.rightHindLeg.xRot = Mth.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;
                this.leftHindLeg.xRot = Mth.cos(p_102464_ * 0.6662F + (float)Math.PI) * 1.4F * p_102465_;
                this.rightFrontLeg.xRot = Mth.cos(p_102464_ * 0.6662F + (float)Math.PI) * 1.4F * p_102465_;
                this.leftFrontLeg.xRot = Mth.cos(p_102464_ * 0.6662F) * 1.4F * p_102465_;
        }
}
