package jp.nemi.hardcore.object.blocks.vanilla;


import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.object.blocks.WallStickBlock;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class HCWallTorchBlock extends HCTorchBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public HCWallTorchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT) == 1 || (state.getValue(LIT) == 0 && world.getRandom().nextInt(2) == 1)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + 0.7D;
            double d2 = (double) pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void lit(World world, BlockPos pos, BlockState state) {
        world.setBlock(pos, HCBlocks.WALL_STICK.get().defaultBlockState().setValue(LIT, 1).setValue(LIGHTING_TIME, getDefaultLightingTime()).setValue(FACING, state.getValue(FACING)), 2);

        if (isBurnout) world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void few(World world, BlockPos pos, BlockState state, int newLightingTime) {
        if (isBurnout) {
            world.setBlock(pos, HCBlocks.WALL_STICK.get().defaultBlockState().setValue(LIT, 0).setValue(LIGHTING_TIME, newLightingTime).setValue(FACING, state.getValue(FACING)), 2);
            world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    @Override
    public void burnout(World world, BlockPos pos, BlockState state) {
        if (isBurnout) {
            if (HCConfigCommon.isRemoveTorch.get().booleanValue()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.GUNPOWDER)));
            }
            else {
                world.setBlock(pos, HCBlocks.WALL_STICK.get().defaultBlockState().setValue(WallStickBlock.FACING, state.getValue(FACING)), 2);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return WallTorchBlock.getShape(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, IWorld world, BlockPos pos, BlockPos otherPos) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos onPos = pos.relative(direction.getOpposite());
        BlockState onState = world.getBlockState(onPos);
        return onState.isFaceSturdy(world, onPos, direction);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
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