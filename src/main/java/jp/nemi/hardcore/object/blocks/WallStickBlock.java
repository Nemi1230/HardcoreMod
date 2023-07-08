package jp.nemi.hardcore.object.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.object.blocks.vanilla.HCTorchBlock;
import jp.nemi.hardcore.object.blocks.vanilla.HCWallTorchBlock;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        ItemStack itemStack = player.getItemInHand(handIn);

        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            if (!worldIn.isClientSide) {
                worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                worldIn.setBlock(pos, HCBlocks.HC_WALL_TORCH.get().defaultBlockState().setValue(HCTorchBlock.LIT, 1).setValue(HCTorchBlock.LIGHTING_TIME, HCTorchBlock.getDefaultLightingTime()).setValue(HCWallTorchBlock.FACING, worldIn.getBlockState(pos).getValue(WallStickBlock.FACING)), 2);
                worldIn.getBlockTicks().scheduleTick(pos, HCBlocks.HC_WALL_TORCH.get(), HCTorchBlock.TICK_INTERVAL);

                itemStack.hurtAndBreak(1, player, (p_220282_1_) -> {
                    p_220282_1_.broadcastBreakEvent(handIn);
                });
            }

            return ActionResultType.SUCCESS;
        }
        else {
            return super.use(state, worldIn, pos, player, handIn, result);
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
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return blockstate.isFaceSturdy(worldIn, blockpos, direction);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.defaultBlockState();
        IWorldReader worldIn = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(worldIn, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld worldIn, BlockPos pos, BlockPos pos1) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(worldIn, pos) ? Blocks.AIR.defaultBlockState() : state;
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
        builder.add(FACING);
    }
}
