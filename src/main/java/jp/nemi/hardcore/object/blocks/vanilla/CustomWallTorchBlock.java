package jp.nemi.hardcore.object.blocks.vanilla;

import jp.nemi.hardcore.config.HCConfigGeneral;
import jp.nemi.hardcore.init.HCBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
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

public class CustomWallTorchBlock extends CustomTorchBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public CustomWallTorchBlock(Properties properties, IParticleData particle) {
        super(properties, particle);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT).intValue() == 1 || (state.getValue(LIT).intValue() == 0 && world.getRandom().nextInt(2) == 1)) {
            Direction direction = state.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.7D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.22D;
            double d4 = 0.27D;
            Direction direction1 = direction.getOpposite();
            world.addParticle(ParticleTypes.SMOKE, d0 + 0.27D * (double)direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
            world.addParticle(this.flameParticle, d0 + 0.27D * (double)direction1.getStepX(), d1 + 0.22D, d2 + 0.27D * (double)direction1.getStepZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void lit(World world, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, HCBlocks.CUSTOM_TORCH.get().defaultBlockState().setValue(LIGHTING_TIME, Integer.valueOf(getInitialLightingTime())).setValue(LIT, Integer.valueOf(1)));
        if (isBurnout)
            world.getBlockTicks().scheduleTick(pos, this, 1200);
    }

    @Override
    public void few(World world, BlockPos pos, BlockState state, int currentLightingTime) {
        if (isBurnout) {
            world.setBlockAndUpdate(pos, HCBlocks.CUSTOM_WALL_TORCH.get().defaultBlockState().setValue(LIT, Integer.valueOf(1)).setValue(LIGHTING_TIME, Integer.valueOf(currentLightingTime)).setValue(FACING, state.getValue(FACING)));
            world.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    public void unlit(World world, BlockPos pos, BlockState state) {
        if (isBurnout) {
            if (HCConfigGeneral.isRemoveTorch.get()) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
            world.setBlockAndUpdate(pos, HCBlocks.WALL_STICK.get().defaultBlockState().setValue(FACING, state.getValue(FACING)));
            world.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{ FACING });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return WallTorchBlock.getShape(state);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        return Blocks.WALL_TORCH.canSurvive(state, reader, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        return Blocks.WALL_TORCH.updateShape(state, direction, state1, world, pos, pos1);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = Blocks.WALL_TORCH.getStateForPlacement(context);
        return (state == null) ? null : defaultBlockState().setValue((Property)FACING, state.getValue((Property)FACING));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return Blocks.WALL_TORCH.rotate(state, rotation);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return Blocks.WALL_TORCH.mirror(state, mirror);
    }
}