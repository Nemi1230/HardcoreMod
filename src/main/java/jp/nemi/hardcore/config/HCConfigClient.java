package jp.nemi.hardcore.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HCConfigClient {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.comment("Client configuration settings").push("Client Configs");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
