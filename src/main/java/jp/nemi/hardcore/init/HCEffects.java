package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.object.effects.ThirstEffect;
import jp.nemi.hardcore.object.effects.ThirstHydrationEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HCEffects {
    public static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, HCCore.MOD_ID);

    public static RegistryObject<Effect> THIRST;
    public static RegistryObject<Effect> THIRST_HYDRATION;

    public static void register(IEventBus eventBus) {
        THIRST = EFFECT.register("thirst", () -> new ThirstEffect(EffectType.HARMFUL, 7789388));
        THIRST_HYDRATION = EFFECT.register("hydration", () -> new ThirstHydrationEffect(EffectType.BENEFICIAL, 3644118));

        EFFECT.register(eventBus);
    }
}