package jp.nemi.hardcore.object.blocks.vanilla;

import jp.nemi.hardcore.config.HCConfig;
import jp.nemi.hardcore.init.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class HCTorchBlock extends TorchBlock {
        public static final int TICK_INTERVAL = 1200;
        protected static final boolean isBurnout = HCConfig.torchLightingTime.get().intValue() > 0;
        public static final IntegerProperty LIT = IntegerProperty.create("lit", 0, 2); //0: Burnout, 1: few, 2:lit
        public static final IntegerProperty LIGHTING_TIME = IntegerProperty.create("lighting_time", 0, isBurnout ? HCConfig.torchLightingTime.get().intValue() : Integer.MAX_VALUE);

        public HCTorchBlock() {
                super(BlockBehaviour.Properties.copy(Blocks.TORCH), ParticleTypes.FLAME);
                this.registerDefaultState(this.stateDefinition.any().setValue(LIT, 0).setValue(LIGHTING_TIME, 0));
        }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
                if (level.isClientSide()) return InteractionResult.SUCCESS;

                ItemStack stack = player.getItemInHand(hand);

                if (stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == Items.FIRE_CHARGE) {
                        level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);

                        if (! player.isCreative()) {
                                stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
                        }
                        if (level.isRainingAt(blockPos)) {
                                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                        } else {
                                lit(level, blockPos, state);
                        }
                }

                return InteractionResult.SUCCESS;
        }

        @Override
        public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource randomSource) {
                if (state.getValue(LIT) == 2 || (state.getValue(LIT) == 1 && level.getRandom().nextInt(2) == 1)) {
                        super.animateTick(state, level, blockPos, randomSource);
                }
        }

        @Override
        public void tick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
                if (!level.isClientSide() && isBurnout) {
                        if (level.isRainingAt(blockPos)) {
                                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                                burnout(level, blockPos, state);

                                return;
                        }

                        int newLightingTime = state.getValue(LIGHTING_TIME) - 1;

                        if (newLightingTime < 1) {
                                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                                burnout(level, blockPos, state);
                                level.updateNeighborsAt(blockPos, this);
                        } else if (state.getValue(LIT) == 2 && (newLightingTime < HCConfig.torchLightingTime.get().intValue() / 10 || newLightingTime < 0)) {
                                few(level, blockPos, state, newLightingTime);
                                level.updateNeighborsAt(blockPos, this);
                        } else {
                                level.setBlock(blockPos, state.setValue(LIGHTING_TIME, newLightingTime), 2);
                                level.scheduleTick(blockPos, this, TICK_INTERVAL);
                        }
                }
        }

        @Override
        public void setPlacedBy(Level level, BlockPos blockPos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
                super.setPlacedBy(level, blockPos, state, entity, itemStack);
                level.scheduleTick(blockPos, this, TICK_INTERVAL);
        }

        @Override
        public void onPlace(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
                if (isMoving && state.getBlock() != newState.getBlock())
                        defaultBlockState().updateNeighbourShapes(level, blockPos, 3);
                super.onPlace(state, level, blockPos, newState, isMoving);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(LIT, LIGHTING_TIME);
        }

        public static int getDefaultLightingTime() {
                return isBurnout ? HCConfig.torchLightingTime.get().intValue() : 0;
        }

        public void lit(Level level, BlockPos blockPos, BlockState state) {
                level.setBlock(blockPos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(LIT, 2).setValue(LIGHTING_TIME, getDefaultLightingTime()), 2);

                if (isBurnout)
                        level.scheduleTick(blockPos, this, TICK_INTERVAL);
        }

        public void few(Level level, BlockPos blockPos, BlockState state, int newLightingTime) {
                if (isBurnout) {
                        level.setBlock(blockPos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(LIT, 1).setValue(LIGHTING_TIME, newLightingTime), 2);
                        level.scheduleTick(blockPos, this, TICK_INTERVAL);
                }
        }

        public void burnout(Level level, BlockPos blockPos, BlockState state) {
                if (isBurnout) {
                        if (HCConfig.isRemoveTorch.get().booleanValue()) {
                                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
                                level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(Items.GUNPOWDER, 2)));
                        } else {
                                level.setBlock(blockPos, HCBlocks.HC_TORCH.get().defaultBlockState(), 2);
                                level.scheduleTick(blockPos, this, TICK_INTERVAL);
                        }
                }
        }

        public static ToIntFunction<BlockState> getLightValueFromState() {
                return (state) -> {
                        if (state.getValue(HCTorchBlock.LIT) == 2) return 14;
                        else if (state.getValue(HCTorchBlock.LIT) == 1) return 10;

                        return 0;
                };
        }
}
