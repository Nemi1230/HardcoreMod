package jp.nemi.hardcore.object.blocks;

import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.object.blocks.vanilla.HCTorchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class StickBlock extends Block {
    protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public StickBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        ItemStack itemStack = player.getItemInHand(handIn);

        if (itemStack.getItem() == Items.FLINT_AND_STEEL || itemStack.getItem() == Items.FIRE_CHARGE) {
            if (!worldIn.isClientSide) {
                worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                worldIn.setBlock(pos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(HCTorchBlock.LIT, 1).setValue(HCTorchBlock.LIGHTING_TIME, HCTorchBlock.getDefaultLightingTime()), 2);
                worldIn.getBlockTicks().scheduleTick(pos, HCBlocks.HC_TORCH.get(), HCTorchBlock.TICK_INTERVAL);

                if (!player.isCreative()) {
                    itemStack.hurtAndBreak(1, player, (p_220282_1_) -> {
                        p_220282_1_.broadcastBreakEvent(handIn);
                    });
                }
            }

            return ActionResultType.SUCCESS;
        }
        else {
            return super.use(state, worldIn, pos, player, handIn, result);
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return AABB;
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        return p_196271_2_ == Direction.DOWN && !canSurvive(p_196271_1_, p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return canSupportCenter(p_196260_2_, p_196260_3_.below(), Direction.UP);
    }
}