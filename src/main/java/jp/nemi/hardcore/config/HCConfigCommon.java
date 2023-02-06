package jp.nemi.hardcore.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class HCConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.IntValue torchLightingTime;
    public static ForgeConfigSpec.BooleanValue isRemoveTorch;

    public static ForgeConfigSpec.BooleanValue isArmorWeakening;

    public static ForgeConfigSpec.BooleanValue isDisableSleep;
    public static ForgeConfigSpec.BooleanValue isSetSpawnPoint;

    public static ForgeConfigSpec.IntValue weaknessEffectAmplifier;
    public static ForgeConfigSpec.IntValue slownessEffectAmplifier;
    public static ForgeConfigSpec.DoubleValue poisonDebuffProbability;
    public static ForgeConfigSpec.DoubleValue thirstDebuffProbability;
    public static ForgeConfigSpec.DoubleValue exhaustionThreshold;
    public static ForgeConfigSpec.DoubleValue thirstReduceRate;
    public static ForgeConfigSpec.BooleanValue isThirstEnabled;
    public static ForgeConfigSpec.BooleanValue isResetThirstLevelInDeath;
    public static ForgeConfigSpec.BooleanValue isShowThirstSaturationLevel;

    public static ForgeConfigSpec.BooleanValue isDebugMode;

    static {
        BUILDER.comment("General configuration settings").push("General Configs");

        torchLightingTime = BUILDER.comment("\n松明が燃え尽きるまでの時間を設定します(単位: 分)。負の値に設定すると、松明の燃え尽きが無効になります。\nSets the time it takes for the torch to burn out (in minutes). A negative value disables torch burnout.\nデフォルト: true\nDefault: true").defineInRange("torchLightingTime", 60, -1, 1200);
        isRemoveTorch = BUILDER.comment("\ntrueの場合、松明が燃え尽きた時に火薬をドロップします。\nIn true, when the torch burns out, it drops Gunpowder.\nfalseの場合、松明が燃え尽きた時に棒におきかわります。\nIf false, the torch turns into a stick.\nデフォルト: true\nDefault: true").define("isRemoveTorch", true);

        isArmorWeakening = BUILDER.comment("\ntrueの場合、バニラ防具の性能を下げ、バニラ防具と同じ性能の「丈夫な防具」を追加します。\nIf true, reduce the performance of vanilla armor and add a \"Durable Armor\" with vanilla performance.\nデフォルト: true\nDefault: true").define("isArmorWeakening", true);

        isDisableSleep = BUILDER.comment("\ntrueの場合、ベッドで夜を過ごすことができなくなります。\nIf true, you will not be able to spend the night in bed.\nデフォルト: true\nDefault: true").define("isDisableSleep", true);
        isSetSpawnPoint = BUILDER.comment("\ntrueの場合、ベッドを使用してリスポーンポイントを設定できます。\nIf true, the bed can be used to set the spawn point.\nデフォルト: true\nDefault: true").define("isSetSpawnPoint", true);

        weaknessEffectAmplifier = BUILDER.comment("\n水分が非常に少ないときにプレーヤーに与える弱体化のレベルを設定します。\nSets the level of weakness that is inflicted on the player when moisture is very low.\nデフォルト: 1\nDefault: 1").defineInRange("weaknessEffectAmplifier", 1, -1, 255);
        slownessEffectAmplifier = BUILDER.comment("\n水分が非常に少ないときにプレーヤーに与える移動速度低下のレベルを設定します。\nSets the level of slowness that is inflicted on the player when moisture is very low.\nデフォルト: 1\nDefault: 1").defineInRange("weaknessEffectAmplifier", 1, -1, 255);
        poisonDebuffProbability = BUILDER.comment("\n未濾過の水を飲んだ時に毒を付与される確率を設定します。\nSets the probability of being given poison status when drinking unfiltered water.\nデフォルト: 0.35\nDefault: 0.35").defineInRange("poisonDebuffProbability", 0.35D, 0.0D, 1.0D);
        thirstDebuffProbability = BUILDER.comment("\n未濾過の水を飲んだ時に喉の渇きを付与される確率を設定します。\nSets the probability of being given thirst status when drinking unfiltered water.\nデフォルト: 0.7\nDefault: 0.7").defineInRange("thirstDebuffProbability", 0.7D, 0.0D, 1.0D);
        exhaustionThreshold = BUILDER.comment("\n疲労による水分低下の閾値を設定します。\nSets the threshold for moisture loss due to fatigue.\nデフォルト: 8.0\nDefault: 8.0").defineInRange("exhaustionThreshold", 8.0D, 0.0D, Double.MAX_VALUE);
        thirstReduceRate = BUILDER.comment("\n水分ゲージの消費率を設定します。\nSets the consumption rate of the thirst gauge.\nデフォルト: 1.0\nDefault: 1.0").defineInRange("thirstReduceRate", 1.0D, 0.0D, 1200.0D);
        isThirstEnabled = BUILDER.comment("\ntrueの場合、喉の渇きの概念が追加されます。\nIf true, the concept of thirst is added.\nデフォルト: true\nDefault: true").define("isThirstEnabled", true);
        isResetThirstLevelInDeath = BUILDER.comment("\ntrueの場合、死亡時に水分ゲージがリセットされます。\nIf true, the thirst gauge is reset upon death.\nデフォルト: true\nDefault: true").define("isResetThirstLevelInDeath", true);
        isShowThirstSaturationLevel = BUILDER.comment("\ntrueの場合、水分ゲージに隠し水分量を表示します。\nIf true, the thirst bar displays the amount of thirst saturation.\nデフォルト: true\nDefault: true").define("isShowThirstSaturationLevel", true);

        isDebugMode = BUILDER.comment("\n開発者用(※false推奨)\nFor developers(*false is recommended)\nデフォルト: true\nDefault: false").define("isDebugMode", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
