package jp.nemi.hardcore.init;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.config.HCConfigClient;
import jp.nemi.hardcore.config.HCConfigCommon;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class HCConfigs {
    public static void init() {
        Path parentPath = FMLPaths.CONFIGDIR.get();
        Path hcConfigPath = Paths.get(parentPath.toAbsolutePath().toString(), new String[]{"hardcore"});

        try {
            Files.createDirectory(hcConfigPath, (FileAttribute<?>[]) new FileAttribute[0]);
        } catch (IOException e) {
            HCCore.LOGGER.error("Failed to create hardcore config directory", e);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HCConfigCommon.SPEC, "hardcore/hardcore.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HCConfigClient.SPEC, "hardcore/hardcore-client.toml");
    }
}
