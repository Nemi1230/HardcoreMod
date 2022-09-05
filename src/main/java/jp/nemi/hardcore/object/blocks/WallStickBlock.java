package jp.nemi.hardcore.object.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class WallStickBlock extends StickBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D),
            Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D),
            Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D),
            Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

    public WallStickBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            if (!world.isClientSide) {
                Direction direction = player.getDirection();
                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
                world.playSound((PlayerEntity)null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlock(pos, Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, state.getValue(WallStickBlock.FACING)), 11);

                itemStack.hurtAndBreak(1, player, (p_220282_1_) -> {
                    p_220282_1_.broadcastBreakEvent(hand);
                });
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        }
        else {
            return super.use(state, world, pos, player, hand, result);
        }
    }

    @Override
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        return getShape(state);
    }

    public static VoxelShape getShape(BlockState state) {
        return AABBS.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader iWorldReader, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = iWorldReader.getBlockState(blockpos);
        return blockstate.isFaceSturdy(iWorldReader, blockpos, direction);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.defaultBlockState();
        IWorldReader iworldreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(iworldreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{ FACING });
    }
}
