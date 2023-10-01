package jp.nemi.hardcore.config;

import jp.nemi.hardcore.HCCore;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class HCSaveData extends WorldSavedData {
    public static final String NAME = HCCore.MOD_ID + "_savedata";
    public boolean isAlreadyLoggInWorld = false;

    public static HCSaveData instance;

    public HCSaveData(String identifier) {
        super(identifier);
        this.setDirty();

        instance = this;
    }

    public HCSaveData() {
        this(NAME);
    }

    @Override
    public void load(CompoundNBT nbt) {
        this.isAlreadyLoggInWorld = nbt.getBoolean("isAlreadyLoggInWorld");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putBoolean("isAlreadyLoggInWorld", this.isAlreadyLoggInWorld);

        return nbt;
    }
}
