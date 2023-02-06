package jp.nemi.hardcore.init;

import jp.nemi.hardcore.capability.PlayerLastPosCapability;
import jp.nemi.hardcore.capability.ThirstLevelCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class HCCapabilities {
    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(ThirstLevelCapability.Data.class, (Capability.IStorage) new ThirstLevelCapability.Storage(), ThirstLevelCapability.Data::new);
        CapabilityManager.INSTANCE.register(PlayerLastPosCapability.Data.class, (Capability.IStorage) new PlayerLastPosCapability.Storage(), PlayerLastPosCapability.Data::new);
    }
}
