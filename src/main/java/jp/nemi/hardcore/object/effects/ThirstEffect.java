package jp.nemi.hardcore.object.effects;

import jp.nemi.hardcore.capability.ThirstLevelCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ThirstEffect extends Effect {
    public ThirstEffect(EffectType effectType, int color) {
        super(effectType, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.addThirstExhaustion(player, 0.005F * (float) (amplifier + 1)));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
