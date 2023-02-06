package jp.nemi.hardcore.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerLastPosCapability {
    @CapabilityInject(Data.class)
    public static Capability<Data> PLAYER_LAST_POS;

    public static class Storage implements Capability.IStorage<Data> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<PlayerLastPosCapability.Data> capability, PlayerLastPosCapability.Data instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();
            compound.putDouble("lastX", instance.getLastX());
            compound.putDouble("lastY", instance.getLastY());
            compound.putDouble("lastZ", instance.getLastZ());
            compound.putBoolean("isLastOnGround", instance.isLastOnGround());
            return (INBT) compound;
        }

        @Override
        public void readNBT(Capability<PlayerLastPosCapability.Data> capability, PlayerLastPosCapability.Data instance, Direction side, INBT nbt) {
            instance.setLastX(((CompoundNBT) nbt).getDouble("lastX"));
            instance.setLastY(((CompoundNBT) nbt).getDouble("lastY"));
            instance.setLastZ(((CompoundNBT) nbt).getDouble("lastZ"));
            instance.setIsLastOnGround(((CompoundNBT) nbt).getBoolean("isLastOnGround"));
        }
    }

    public static class Data {
        private double lastX;
        private double lastY;
        private double lastZ;
        private boolean isLastOnGround;

        public double getLastX() {
            return this.lastX;
        }

        public double getLastY() {
            return this.lastY;
        }

        public double getLastZ() {
            return this.lastZ;
        }

        public boolean isLastOnGround() {
            return this.isLastOnGround;
        }

        public void setLastX(double lastX) {
            this.lastX = lastX;
        }

        public void setLastY(double lastY) {
            this.lastY = lastY;
        }

        public void setLastZ(double lastZ) {
            this.lastZ = lastZ;
        }

        public void setIsLastOnGround(boolean isLastOnGround) {
            this.isLastOnGround = isLastOnGround;
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        private PlayerLastPosCapability.Data playerLastPosition = new PlayerLastPosCapability.Data();
        private Capability.IStorage<PlayerLastPosCapability.Data> storage = PlayerLastPosCapability.PLAYER_LAST_POS.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap.equals(PlayerLastPosCapability.PLAYER_LAST_POS))
                return LazyOptional.of(() -> this.playerLastPosition).cast();
            return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return this.storage.writeNBT(PlayerLastPosCapability.PLAYER_LAST_POS, this.playerLastPosition, null);
        }

        public void deserializeNBT(INBT nbt) {
            this.storage.readNBT(PlayerLastPosCapability.PLAYER_LAST_POS, this.playerLastPosition, null, nbt);
        }
    }
}
