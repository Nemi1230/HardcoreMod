package jp.nemi.hardcore.capability;

import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCDamageSource;
import jp.nemi.hardcore.init.HCEffects;
import jp.nemi.hardcore.network.PlayerThirstLevelPacket;
import jp.nemi.hardcore.network.SimpleNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ThirstLevelCapability {
    @CapabilityInject(Data.class)
    public static Capability<Data> PLAYER_THIRST_LEVEL;

    public static boolean canPlayerAddWaterExhaustionLevel(PlayerEntity player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator() && player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL) != null && player.level.getDifficulty() != Difficulty.PEACEFUL && HCConfigCommon.isThirstEnabled.get().booleanValue();
    }

    public static class Storage implements Capability.IStorage<Data> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<Data> capability, Data instance, Direction side) {
            CompoundNBT compound = new CompoundNBT();

            if (HCConfigCommon.isThirstEnabled.get().booleanValue()) {
                compound.putInt("thirstLevel", instance.getThirstLevel());
                compound.putInt("thirstSaturationLevel", instance.getThirstSaturationLevel());
                compound.putFloat("thirstExhaustionLevel", instance.getThirstExhaustionLevel());
            }
            else {
                compound.putInt("thirstLevel", 20);
                compound.putInt("thirstSaturationLevel", 2);
                compound.putFloat("thirstExhaustionLevel", 0.0F);
            }

            return compound;
        }

        @Override
        public void readNBT(Capability<Data> capability, Data instance, Direction side, INBT nbt) {
            if (!(nbt instanceof CompoundNBT)) throw new IllegalArgumentException("Thirst data must be a CompoundNBT!");
            CompoundNBT compound = (CompoundNBT) nbt;
            if (compound.contains("thirstLevel", 90)) {
                if (HCConfigCommon.isThirstEnabled.get().booleanValue()) {
                    instance.setThirstLevel(compound.getInt("thirstLevel"));
                    instance.setThirstSaturationLevel(compound.getInt("thirstSaturationLevel"));
                    instance.setThirstExhaustionLevel(compound.getFloat("thirstExhaustionLevel"));
                }
                    else {
                    instance.setThirstLevel(20);
                    instance.setThirstSaturationLevel(2);
                    instance.setThirstExhaustionLevel(0.0F);
                }
            }
        }
    }

    public static class Data {
        private int thirstLevel = 20;
        private int thirstSaturationLevel;
        private float thirstExhaustionLevel;

        public void addThirstLevel(PlayerEntity player, int value) {
            this.thirstLevel = Math.min(this.thirstLevel + value, 20);
            update(player);
        }

        public void addThirstSaturationLevel(PlayerEntity player, int value) {
            this.thirstSaturationLevel = Math.min(this.thirstSaturationLevel + value, 20);
            update(player);
        }

        protected void addThirstExhaustion(float value) {
            reduceLevel((int)((this.thirstExhaustionLevel + value) / 4.0F));
            this.thirstExhaustionLevel = (this.thirstExhaustionLevel + value) % 4.0F;
        }

        public void addThirstExhaustion(PlayerEntity player, float value) {
            float finalValue = (float)(value * HCConfigCommon.thirstReduceRate.get().doubleValue());
            EffectInstance effect = player.getEffect(HCEffects.THIRST.get());
            if (effect != null) {
                addThirstExhaustion(finalValue * (3 + effect.getAmplifier()) / 2.0F);
            } else {
                addThirstExhaustion(finalValue);
            }
            update(player);
        }

        public void update(PlayerEntity player) {
            if (!player.getCommandSenderWorld().isClientSide())
                SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new PlayerThirstLevelPacket(getThirstLevel(), getThirstSaturationLevel(), getThirstExhaustionLevel()));
        }

        public void setThirstLevel(int thirstLevel) {
            this.thirstLevel = thirstLevel;
        }

        public void setThirstExhaustionLevel(float thirstExhaustionLevel) {
            this.thirstExhaustionLevel = thirstExhaustionLevel;
        }

        public int getThirstLevel() {
            return this.thirstLevel;
        }

        public void setThirstSaturationLevel(int thirstSaturationLevel) {
            this.thirstSaturationLevel = thirstSaturationLevel;
        }

        public int getThirstSaturationLevel() {
            return this.thirstSaturationLevel;
        }

        public float getThirstExhaustionLevel() {
            return this.thirstExhaustionLevel;
        }

        public void reduceLevel(int reduceValue) {
            if (this.thirstSaturationLevel - reduceValue >= 0) {
                this.thirstSaturationLevel -= reduceValue;
            }
            else if (this.thirstLevel - reduceValue - this.thirstSaturationLevel >= 0) {
                this.thirstLevel -= reduceValue;
                this.thirstSaturationLevel = 0;
            }
            else {
                this.thirstLevel = 0;
                this.thirstSaturationLevel = 0;
            }
        }

        public void restoreThirst(PlayerEntity player, int thirstLevel, int thirstSaturationLevel) {
            addThirstLevel(player, thirstLevel);
            addThirstSaturationLevel(player, thirstSaturationLevel);
        }

        public void restoreThirst(int restoreValue) {
            this.thirstLevel = Math.min(this.thirstLevel + restoreValue, 20);

            if (this.thirstLevel == 20)
                this.thirstSaturationLevel = Math.min(this.thirstLevel + restoreValue, 20);
        }

        public void award(PlayerEntity player) {
            if (player.level.getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION) && getThirstLevel() > 17 && player.getFoodData().getFoodLevel() > 10 && player.getHealth() < player.getMaxHealth()) {
                player.heal(1.0F);
                switch (player.getCommandSenderWorld().getDifficulty()) {
                    case EASY:
                        addThirstExhaustion(5.0F);
                        player.causeFoodExhaustion(0.6F);
                        break;
                    case NORMAL:
                        addThirstExhaustion(6.0F);
                        player.causeFoodExhaustion(0.8F);
                        break;
                    case HARD:
                        addThirstExhaustion(7.0F);
                        player.causeFoodExhaustion(1.0F);
                        break;
                }
            }
        }

        public void punishment(PlayerEntity player) {
            int weaknessAmp = ((Integer)HCConfigCommon.weaknessEffectAmplifier.get()).intValue();
            int slownessAmp = ((Integer)HCConfigCommon.slownessEffectAmplifier.get()).intValue();

            if (getThirstLevel() <= 6)
                switch (player.getCommandSenderWorld().getDifficulty()) {
                    case PEACEFUL:
                        break;
                    case EASY:
                        if (weaknessAmp > -1 && (player.getEffect(Effects.WEAKNESS) == null || player.getEffect(Effects.WEAKNESS).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.WEAKNESS, 400, weaknessAmp, false, false));
                        if (slownessAmp > -1 && (player.getEffect(Effects.MOVEMENT_SLOWDOWN) == null || player.getEffect(Effects.MOVEMENT_SLOWDOWN).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400, slownessAmp, false, false));
                        break;
                    case NORMAL:
                        if (weaknessAmp > -1 && (player.getEffect(Effects.WEAKNESS) == null || player.getEffect(Effects.WEAKNESS).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.WEAKNESS, 400, weaknessAmp + 1, false, false));
                        if (slownessAmp > -1 && (player.getEffect(Effects.MOVEMENT_SLOWDOWN) == null || player.getEffect(Effects.MOVEMENT_SLOWDOWN).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400, slownessAmp, false, false));
                        break;
                    case HARD:
                        if (weaknessAmp > -1 && (player.getEffect(Effects.WEAKNESS) == null || player.getEffect(Effects.WEAKNESS).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.WEAKNESS, 400, weaknessAmp + 2, false, false));
                        if (slownessAmp > -1 && (player.getEffect(Effects.MOVEMENT_SLOWDOWN) == null || player.getEffect(Effects.MOVEMENT_SLOWDOWN).getDuration() <= 100))
                            player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400, slownessAmp + 1, false, false));
                        break;
                }

            int i = 0;

            if (player.getCommandSenderWorld().getDifficulty() != Difficulty.HARD)
                i = 1;
            if (getThirstLevel() == 0 && player.getHealth() > i)
                if (!player.getCommandSenderWorld().isClientSide())
                    player.hurt(HCDamageSource.THIRST, 1.0F);
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        private ThirstLevelCapability.Data playerWaterLevel = new ThirstLevelCapability.Data();
        private Capability.IStorage<ThirstLevelCapability.Data> storage = ThirstLevelCapability.PLAYER_THIRST_LEVEL.getStorage();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap.equals(ThirstLevelCapability.PLAYER_THIRST_LEVEL))
                return LazyOptional.of(() -> this.playerWaterLevel).cast();
            return LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return this.storage.writeNBT(ThirstLevelCapability.PLAYER_THIRST_LEVEL, this.playerWaterLevel, null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            this.storage.readNBT(ThirstLevelCapability.PLAYER_THIRST_LEVEL, this.playerWaterLevel, null, nbt);
        }
    }
}
