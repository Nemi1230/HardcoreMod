package jp.nemi.hardcore.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import jp.nemi.hardcore.HCCore;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class HCConfig {
        public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        public static final ForgeConfigSpec SPEC;

        //Block
        public static ForgeConfigSpec.IntValue torchLightingTime;
        public static ForgeConfigSpec.BooleanValue isRemoveTorch;

        static {
                BUILDER.push("Hardcore Mod Configs");

                BUILDER.comment("Block Configs");
                torchLightingTime = BUILDER.comment("\n松明が燃え尽きるまでの時間を設定します(単位: 分)。負の値に設定すると、松明の燃え尽きが無効になります。\nSets the time it takes for the torch to burn out (in minutes). A negative value disables torch burnout.\nデフォルト: 60分\nDefault: 60 minutes").defineInRange("torchLightingTime", 60, -1, 1200);
                isRemoveTorch = BUILDER.comment("\ntrueの場合、松明が燃え尽きた時に火薬をドロップします。\nIn true, when the torch burns out, it drops Gunpowder.\nfalseの場合、松明が燃え尽きた時に棒におきかわります。\nIf false, the torch turns into a stick.\nデフォルト: true\nDefault: true").define("isRemoveTorch", true);

                BUILDER.pop();
                SPEC = BUILDER.build();
        }

        public static void loadConfig(ForgeConfigSpec spec, Path path) {
                Path parentPath = FMLPaths.CONFIGDIR.get();
                Path hcConfigPath = Paths.get(parentPath.toAbsolutePath().toString(), new String[]{"hardcore"});

                try {
                        Files.createDirectory(hcConfigPath, (FileAttribute<?>[]) new FileAttribute[0]);
                } catch (IOException e) {
                        HCCore.LOGGER.error("Failed to create hardcore config directory.", e);
                }

                final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                        .sync().autosave().writingMode(WritingMode.REPLACE).build();

                configData.load();
                spec.setConfig(configData);
        }
}
