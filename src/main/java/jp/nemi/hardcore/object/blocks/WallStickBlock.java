package jp.nemi.hardcore.object.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.object.blocks.vanilla.HCTorchBlock;
import jp.nemi.hardcore.object.blocks.vanilla.HCWallTorchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

public class WallStickBlock extends StickBlock {
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
        private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

        public WallStickBlock(Properties properties) {
                super(properties);
                this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
                ItemStack itemStack = player.getItemInHand(hand);

                if (itemStack.getItem() == Items.FLINT_AND_STEEL || itemStack.getItem() == Items.FIRE_CHARGE) {
                        if (!level.isClientSide) {
                                level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

                                if (!player.isCreative()) {
                                        itemStack.hurtAndBreak(1, player, (playerEntity) -> {
                                                playerEntity.broadcastBreakEvent(hand);
                                        });
                                }

                                if (level.isRainingAt(blockPos)) {
                                        level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
                                } else {
                                        level.setBlock(blockPos, HCBlocks.HC_WALL_TORCH.get().defaultBlockState().setValue(HCTorchBlock.LIT, 1).setValue(HCTorchBlock.LIGHTING_TIME, HCTorchBlock.getDefaultLightingTime()).setValue(HCWallTorchBlock.FACING, level.getBlockState(blockPos).getValue(WallStickBlock.FACING)), 2);
                                        level.scheduleTick(blockPos, HCBlocks.HC_WALL_TORCH.get(), HCTorchBlock.TICK_INTERVAL);
                                }
                        }

                        return InteractionResult.SUCCESS;
                }
                else {
                        return super.use(state, level, blockPos, player, hand, result);
                }
        }

        public String getDescriptionId() {
                return this.asItem().getDescriptionId();
        }

        @Override
        public VoxelShape getShape(BlockState p_58152_, BlockGetter p_58153_, BlockPos p_58154_, CollisionContext p_58155_) {
                return getShape(p_58152_);
        }

        public static VoxelShape getShape(BlockState p_58157_) {
                return AABBS.get(p_58157_.getValue(FACING));
        }

        @Override
        public boolean canSurvive(BlockState p_58133_, LevelReader p_58134_, BlockPos p_58135_) {
                Direction direction = p_58133_.getValue(FACING);
                BlockPos blockpos = p_58135_.relative(direction.getOpposite());
                BlockState blockstate = p_58134_.getBlockState(blockpos);
                return blockstate.isFaceSturdy(p_58134_, blockpos, direction);
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext p_58126_) {
                BlockState blockstate = this.defaultBlockState();
                LevelReader levelreader = p_58126_.getLevel();
                BlockPos blockpos = p_58126_.getClickedPos();
                Direction[] adirection = p_58126_.getNearestLookingDirections();

                for(Direction direction : adirection) {
                        if (direction.getAxis().isHorizontal()) {
                                Direction direction1 = direction.getOpposite();
                                blockstate = blockstate.setValue(FACING, direction1);
                                if (blockstate.canSurvive(levelreader, blockpos)) {
                                        return blockstate;
                                }
                        }
                }

                return null;
        }

        @Override
        public BlockState updateShape(BlockState p_58143_, Direction p_58144_, BlockState p_58145_, LevelAccessor p_58146_, BlockPos p_58147_, BlockPos p_58148_) {
                return p_58144_.getOpposite() == p_58143_.getValue(FACING) && !p_58143_.canSurvive(p_58146_, p_58147_) ? Blocks.AIR.defaultBlockState() : p_58143_;
        }

        @Override
        public BlockState rotate(BlockState p_58140_, Rotation p_58141_) {
                return p_58140_.setValue(FACING, p_58141_.rotate(p_58140_.getValue(FACING)));
        }

        @Override
        public BlockState mirror(BlockState p_58137_, Mirror p_58138_) {
                return p_58137_.rotate(p_58138_.getRotation(p_58137_.getValue(FACING)));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_58150_) {
                p_58150_.add(FACING);
        }
}
