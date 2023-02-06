package jp.nemi.hardcore.object.effects;

import jp.nemi.hardcore.capability.ThirstLevelCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;

public class ThirstHydrationEffect extends InstantEffect {
    public ThirstHydrationEffect(EffectType effectType, int color) {
        super(effectType, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity && !entity.level.isClientSide()) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> {
                data.restoreThirst(amplifier + 1);
            });
        }
    }
}
