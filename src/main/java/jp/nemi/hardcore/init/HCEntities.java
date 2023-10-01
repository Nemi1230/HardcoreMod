package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.entities.vanilla.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

//Unused
public class HCEntities {
    //Vanilla
    //public static final EntityType<HCEndermanEntity> ENDERMAN = registerEntity(EntityType.Builder.of(HCEndermanEntity::new, EntityClassification.CREATURE).sized(0.6F, 2.9F).clientTrackingRange(8), "enderman", false);

    private static final EntityType registerEntity(EntityType.Builder builder, String entityName, boolean isVanillaEntity) {
        ResourceLocation nameLoc = isVanillaEntity ?  new ResourceLocation("minecraft", entityName) : new ResourceLocation(HCCore.MOD_ID, entityName);
        return (EntityType) builder.build(entityName).setRegistryName(nameLoc);
    }
}