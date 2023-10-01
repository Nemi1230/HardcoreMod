package jp.nemi.hardcore.object.entities.vanilla;

//Unused

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class HCEndermanEntity {//extends MonsterEntity {
    /*private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", (double)0.15F, AttributeModifier.Operation.ADDITION);
    private static final DataParameter<Optional<BlockState>> DATA_CARRY_STATE = EntityDataManager.defineId(EndermanEntity.class, DataSerializers.BLOCK_STATE);
    private static final DataParameter<Boolean> DATA_CREEPY = EntityDataManager.defineId(EndermanEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_STARED_AT = EntityDataManager.defineId(EndermanEntity.class, DataSerializers.BOOLEAN);
    private static final Predicate<LivingEntity> ENDERMITE_SELECTOR = (p_213626_0_) -> {
        return p_213626_0_ instanceof EndermiteEntity && ((EndermiteEntity)p_213626_0_).isPlayerSpawned();
    };
    private int lastStareSound = Integer.MIN_VALUE;
    private int targetChangeTime;
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    public HCEndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
        super(entityType, world);
        this.maxUpStep = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new HCEndermanEntity.StareGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10, new HCEndermanEntity.PlaceBlockGoal(this));
        this.goalSelector.addGoal(11, new HCEndermanEntity.TakeBlockGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EndermiteEntity.class, 10, true, false, ENDERMITE_SELECTOR));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, (double)0.35F).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CARRY_STATE, Optional.empty());
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
            }
        }

    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (DATA_CREEPY.equals(p_184206_1_) && this.hasBeenStaredAt() && this.level.isClientSide) {
            this.playStareSound();
        }

        super.onSyncedDataUpdated(p_184206_1_);
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        BlockState blockstate = this.getCarriedBlock();
        if (blockstate != null) {
            p_213281_1_.put("carriedBlockState", NBTUtil.writeBlockState(blockstate));
        }

        this.addPersistentAngerSaveData(p_213281_1_);
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        BlockState blockstate = null;
        if (p_70037_1_.contains("carriedBlockState", 10)) {
            blockstate = NBTUtil.readBlockState(p_70037_1_.getCompound("carriedBlockState"));
            if (blockstate.isAir()) {
                blockstate = null;
            }
        }

        this.setCarriedBlock(blockstate);
        if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
            this.readPersistentAngerSaveData((ServerWorld)this.level, p_70037_1_);
    }

    static class FindPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        private final HCEndermanEntity enderman;
        private PlayerEntity pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final EntityPredicate startAggroTargetConditions;
        private final EntityPredicate continueAggroTargetConditions = (new EntityPredicate()).allowUnseeable();

        public FindPlayerGoal(HCEndermanEntity enderman, @Nullable Predicate<LivingEntity> p_i241912_2_) {
            super(enderman, PlayerEntity.class, 10, false, false, p_i241912_2_);
            this.enderman = enderman;
            this.startAggroTargetConditions = (new EntityPredicate()).range(this.getFollowDistance()).selector((p_220790_1_) -> {
                return enderman.isLookingAtMe((PlayerEntity)p_220790_1_);
            });
        }

        public boolean canUse() {
            this.pendingTarget = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = 5;
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
                    return false;
                } else {
                    this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true : super.canContinueToUse();
            }
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTarget((LivingEntity)null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !this.enderman.isPassenger()) {
                    if (this.enderman.isLookingAtMe((PlayerEntity)this.target)) {
                        if (this.target.distanceToSqr(this.enderman) < 16.0D) {
                            this.enderman.teleport();
                        }

                        this.teleportTime = 0;
                    } else if (this.target.distanceToSqr(this.enderman) > 256.0D && this.teleportTime++ >= 30 && this.enderman.teleportTowards(this.target)) {
                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }

        }
    }

    static class PlaceBlockGoal extends Goal {
        private final HCEndermanEntity enderman;

        public PlaceBlockGoal(HCEndermanEntity enderman) {
            this.enderman = enderman;
        }

        public boolean canUse() {
            if (this.enderman.getCarriedBlock() == null) {
                return false;
            } else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.enderman.level, this.enderman)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(2000) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            World world = this.enderman.level;
            int i = MathHelper.floor(this.enderman.getX() - 1.0D + random.nextDouble() * 2.0D);
            int j = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 2.0D);
            int k = MathHelper.floor(this.enderman.getZ() - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = world.getBlockState(blockpos);
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate1 = world.getBlockState(blockpos1);
            BlockState blockstate2 = this.enderman.getCarriedBlock();
            if (blockstate2 != null) {
                blockstate2 = Block.updateFromNeighbourShapes(blockstate2, this.enderman.level, blockpos);
                if (this.canPlaceBlock(world, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(enderman, net.minecraftforge.common.util.BlockSnapshot.create(world.dimension(), world, blockpos1), net.minecraft.util.Direction.UP)) {
                    world.setBlock(blockpos, blockstate2, 3);
                    this.enderman.setCarriedBlock((BlockState)null);
                }

            }
        }

        private boolean canPlaceBlock(World p_220836_1_, BlockPos p_220836_2_, BlockState p_220836_3_, BlockState p_220836_4_, BlockState p_220836_5_, BlockPos p_220836_6_) {
            return p_220836_4_.isAir(p_220836_1_, p_220836_2_) && !p_220836_5_.isAir(p_220836_1_, p_220836_6_) && !p_220836_5_.is(Blocks.BEDROCK) && !p_220836_5_.is(net.minecraftforge.common.Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST) && p_220836_5_.isCollisionShapeFullBlock(p_220836_1_, p_220836_6_) && p_220836_3_.canSurvive(p_220836_1_, p_220836_2_) && p_220836_1_.getEntities(this.enderman, AxisAlignedBB.unitCubeFromLowerCorner(Vector3d.atLowerCornerOf(p_220836_2_))).isEmpty();
        }
    }

    static class StareGoal extends Goal {
        private final HCEndermanEntity enderman;
        private LivingEntity target;

        public StareGoal(HCEndermanEntity enderman) {
            this.enderman = enderman;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            } else {
                double d0 = this.target.distanceToSqr(this.enderman);
                return d0 > 256.0D ? false : this.enderman.isLookingAtMe((PlayerEntity)this.target);
            }
        }

        public void start() {
            this.enderman.getNavigation().stop();
        }

        public void tick() {
            this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    static class TakeBlockGoal extends Goal {
        private final HCEndermanEntity enderman;

        public TakeBlockGoal(HCEndermanEntity enderman) {
            this.enderman = enderman;
        }

        public boolean canUse() {
            if (this.enderman.getCarriedBlock() != null) {
                return false;
            } else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.enderman.level, this.enderman)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(20) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            World world = this.enderman.level;
            int i = MathHelper.floor(this.enderman.getX() - 2.0D + random.nextDouble() * 4.0D);
            int j = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 3.0D);
            int k = MathHelper.floor(this.enderman.getZ() - 2.0D + random.nextDouble() * 4.0D);
            BlockPos blockpos = new BlockPos(i, j, k);
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            Vector3d vector3d = new Vector3d((double)MathHelper.floor(this.enderman.getX()) + 0.5D, (double)j + 0.5D, (double)MathHelper.floor(this.enderman.getZ()) + 0.5D);
            Vector3d vector3d1 = new Vector3d((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
            BlockRayTraceResult blockraytraceresult = world.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, this.enderman));
            boolean flag = blockraytraceresult.getBlockPos().equals(blockpos);
            if (block.is(BlockTags.ENDERMAN_HOLDABLE) && flag) {
                world.removeBlock(blockpos, false);
                this.enderman.setCarriedBlock(blockstate.getBlock().defaultBlockState());
            }

        }
    }*/
}
