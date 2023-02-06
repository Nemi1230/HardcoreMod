package jp.nemi.hardcore.init;

import net.minecraft.util.DamageSource;

public class HCDamageSource {
    public static final DamageSource THIRST = (new DamageSource("thirst")).bypassArmor().setScalesWithDifficulty();
}
