package jp.nemi.hardcore.mixin;

import jp.nemi.hardcore.capability.ThirstLevelCapability;
import jp.nemi.hardcore.config.HCConfigCommon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(at = @At("HEAD"), method = "causeFoodExhaustion(F)V")
    protected void onCauseFoodExhaustion(float exhaustion, CallbackInfo ci) {
        if (HCConfigCommon.isThirstEnabled.get().booleanValue()) {
            this.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.addThirstExhaustion(exhaustion));
        }
    }
}