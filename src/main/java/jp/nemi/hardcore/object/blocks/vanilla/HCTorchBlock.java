package jp.nemi.hardcore.object.blocks.vanilla;

import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.ToIntFunction;

public class HCTorchBlock extends TorchBlock {
    public static final int TICK_INTERVAL = 1200;
    protected static final boolean isBurnout = HCConfigCommon.torchLightingTime.get().intValue() > -1;
    public static final IntegerProperty LIT = IntegerProperty.create("lit", 0, 1);
    public static final IntegerProperty LIGHTING_TIME = IntegerProperty.create("lighting_time", 0, true ? HCConfigCommon.torchLightingTime.get().intValue() : Integer.MAX_VALUE);

    public HCTorchBlock(Properties properties) {
        super(properties, ParticleTypes.FLAME);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, 1).setValue(LIGHTING_TIME, getDefaultLightingTime()));
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT) == 1 || (state.getValue(LIT) == 0 && world.getRandom().nextInt(2) == 1)) {
            super.animateTick(state, world, pos, rand);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (world.isClientSide()) return ActionResultType.SUCCESS;

        if (player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL || player.getItemInHand(hand).getItem() == Items.FIRE_CHARGE) {
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);

            if (!player.isCreative()) {
                ItemStack stack = player.getItemInHand(hand);
                stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
            }
            if (world.isRainingAt(pos))
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
            else
                lit(world, pos, state);
        }

        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isClientSide() && isBurnout) {
            if (world.isRainingAt(pos)) {
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
                burnout(world, pos, state);
                return;
            }

            int newLightingTime = state.getValue(LIGHTING_TIME) - 1;

            if (newLightingTime < 1) {
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
                burnout(world, pos, state);
                world.updateNeighborsAt(pos, this);
            }
            else if (state.getValue(LIT) == 1 && (newLightingTime <= HCConfigCommon.torchLightingTime.get().intValue() / 10 || newLightingTime < 0)) {
                few(world, pos, state, newLightingTime);
                world.updateNeighborsAt(pos, this);
            }
            else {
                world.setBlock(pos, state.setValue(LIGHTING_TIME, newLightingTime), 2);
                world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
            }
        }
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(world, pos, state, entity, stack);
        world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock())
            defaultBlockState().updateNeighbourShapes(world, pos, 3);
        super.onPlace(state, world, pos, newState, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, LIGHTING_TIME);
    }

    public static int getDefaultLightingTime() {
        return isBurnout ? HCConfigCommon.torchLightingTime.get().intValue() : 0;
    }

    public void lit(World world, BlockPos pos, BlockState state) {
        world.setBlock(pos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(LIT, 1).setValue(LIGHTING_TIME, getDefaultLightingTime()), 2);

        if (isBurnout)
            world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
    }

    public void few(World world, BlockPos pos, BlockState state, int newLightingTime) {
        if (isBurnout) {
            world.setBlock(pos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(LIT, 0).setValue(LIGHTING_TIME, newLightingTime), 2);
            world.getBlockTicks().scheduleTick(pos, this, TICK_INTERVAL);
        }
    }

    public void burnout(World world, BlockPos pos, BlockState state) {
        if (isBurnout) {
            if (HCConfigCommon.isRemoveTorch.get().booleanValue()) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.GUNPOWDER, 2)));
            }
            else {
                world.setBlock(pos, HCBlocks.STICK.get().defaultBlockState(), 2);
            }
        }
    }

    public static ToIntFunction<BlockState> getLightValueFromState() {
        return (state) -> {
            if (state.getValue(HCTorchBlock.LIT) == 1) return 14;
            else if (state.getValue(HCTorchBlock.LIT) == 0) return 10;

            return 0;
        };
    }
}