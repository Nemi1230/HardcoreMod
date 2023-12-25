package jp.nemi.hardcore.object.blocks.vanilla;

import jp.nemi.hardcore.config.HCConfig;
import jp.nemi.hardcore.init.HCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HCWallTorchBlock extends HCTorchBlock {
        public static final int TICK_INTERVAL = 1200;
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

        public HCWallTorchBlock() {
                super();
                this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource randomSource) {
                if (state.getValue(LIT) == 2 || (state.getValue(LIT) == 1 && level.getRandom().nextInt(2) == 1)) {
                        Direction direction = state.getValue(FACING);
                        double d0 = (double) blockPos.getX() + 0.5D;
                        double d1 = (double) blockPos.getY() + 0.7D;
                        double d2 = (double) blockPos.getZ() + 0.5D;
                        Direction direction1 = direction.getOpposite();
                        level.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
                        level.addParticle(ParticleTypes.FLAME, d0 + 0.27D * (double) direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double) direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
                }
        }

        @Override
        public void lit(Level level, BlockPos blockPos, BlockState state) {
                level.setBlock(blockPos, HCBlocks.HC_WALL_TORCH.get().defaultBlockState().setValue(LIT, 2).setValue(LIGHTING_TIME, getDefaultLightingTime()).setValue(FACING, state.getValue(FACING)), 2);

                if (isBurnout) {
                        level.scheduleTick(blockPos, this, TICK_INTERVAL);
                }
        }

        @Override
        public void few(Level level, BlockPos blockPos, BlockState state, int newLightingTime) {
                if (isBurnout) {
                        level.setBlock(blockPos, HCBlocks.HC_WALL_TORCH.get().defaultBlockState().setValue(LIT, 1).setValue(LIGHTING_TIME, newLightingTime).setValue(FACING, state.getValue(FACING)), 2);
                        level.scheduleTick(blockPos, this, TICK_INTERVAL);
                }
        }

        @Override
        public void burnout(Level level, BlockPos blockPos, BlockState state) {
                if (isBurnout) {
                        if (HCConfig.isRemoveTorch.get().booleanValue()) {
                                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
                        } else {
                                level.setBlock(blockPos, HCBlocks.HC_WALL_TORCH.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), 2);
                                level.scheduleTick(blockPos, this, TICK_INTERVAL);
                        }
                }
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                super.createBlockStateDefinition(builder);
                builder.add(FACING);
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
                return WallTorchBlock.getShape(state);
        }

        @Override
        public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor levelAccessor, BlockPos pos, BlockPos otherPos) {
                return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(levelAccessor, pos) ? Blocks.AIR.defaultBlockState() : state;
        }

        @Override
        public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
                Direction direction = state.getValue(FACING);
                BlockPos onPos = pos.relative(direction.getOpposite());
                BlockState onState = levelReader.getBlockState(onPos);
                return onState.isFaceSturdy(levelReader, onPos, direction);
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext context) {
                BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
                return blockstate == null ? null : defaultBlockState().setValue(FACING, blockstate.getValue(FACING));
        }

        @Override
        public BlockState rotate(BlockState state, Rotation rot) {
                return Blocks.WALL_TORCH.rotate(state, rot);
        }

        @Override
        public BlockState mirror(BlockState state, Mirror mirror) {
                return Blocks.WALL_TORCH.mirror(state, mirror);
        }
}
