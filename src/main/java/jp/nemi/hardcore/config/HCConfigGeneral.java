package jp.nemi.hardcore.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HCConfigGeneral {
    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.IntValue torchLightingTime;
    public static ForgeConfigSpec.BooleanValue isRemoveTorch;

    static {
        BUILDER.push("General Settings");

        torchLightingTime = BUILDER.comment("Sets the time it takes for the torch to burn out (in minutes). A negative value disables torch burnout(default: 60).").defineInRange("torchLightingTime", 60, -1, 1200);
        isRemoveTorch = BUILDER.comment("When the torch burns out, you can choose whether to remove the torch or turn it into a stick(default: true).").define("isRemoveTorch", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}