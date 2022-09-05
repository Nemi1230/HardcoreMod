package jp.nemi.hardcore.object.blocks.vanilla;

import jp.nemi.hardcore.config.HCConfigGeneral;
import jp.nemi.hardcore.init.HCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.ToIntFunction;

public class CustomTorchBlock extends TorchBlock {
    protected static final int lightingTime = HCConfigGeneral.torchLightingTime.get();
    protected static final boolean isBurnout = lightingTime > 0;
    public static final IntegerProperty LIGHTINGTIME = IntegerProperty.create("lighting_time", 0, isBurnout ? lightingTime : 1);
    public static final IntegerProperty LITSTATE = IntegerProperty.create("lit_state", 0, 1);

    public CustomTorchBlock(Properties properties, IParticleData particle) {
        super(properties, particle);
        this.registerDefaultState(defaultBlockState().setValue(LIGHTINGTIME, Integer.valueOf(0)).setValue(LITSTATE, Integer.valueOf(0)));
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LITSTATE).intValue() == 1 || (state.getValue(LITSTATE).intValue() == 0 && world.getRandom().nextInt(2) == 1)) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.7D;
            double d2 = (double)pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            world.addParticle(this.flameParticle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isClientSide() && player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL && state.getValue(LITSTATE) == Integer.valueOf(0)) {
            playLightingSound(world, pos);
            if (!player.isCreative()) {
                ItemStack itemStack = player.getItemInHand(hand);
                itemStack.hurtAndBreak(1, (LivingEntity)player, player1 -> player1.broadcastBreakEvent(hand));
            }
            if (world.isRainingAt(pos))
                playExtinguishSound(world, pos);
            else lit(world, pos, state);

            return ActionResultType.SUCCESS;
        }

        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isClientSide() && isBurnout) {
            if (world.isRainingAt(pos)) {
                playExtinguishSound(world, pos);
                unlit(world, pos, state);
                return;
            }

            int currentLightingTime = (state.getValue(LIGHTINGTIME)).intValue() - 1;
            if (currentLightingTime <= 0) {
                playExtinguishSound(world, pos);
                unlit(world, pos, state);
                world.updateNeighborsAt(pos, this);
            }
            else if ((state.getValue(LITSTATE)).intValue() == 1 && (currentLightingTime <= lightingTime / 10 || currentLightingTime <= 1)) {
                few(world, pos, state, currentLightingTime);
                world.updateNeighborsAt(pos, this);
            }
            else {
                world.setBlockAndUpdate(pos, state.setValue(LIGHTINGTIME, Integer.valueOf(currentLightingTime)));
                world.getBlockTicks().scheduleTick(pos, this, 1200);
            }
        }
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, entity, itemStack);
        world.getBlockTicks().scheduleTick(pos, this, 1200);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state1, boolean isMoving) {
        if (!isMoving && state.getBlock() != state1.getBlock())
            defaultBlockState().updateNeighbourShapes((IWorld)world, pos, 3);
        super.onRemove(state, world, pos, state1, isMoving);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{ (Property)LIGHTINGTIME });
        builder.add(new Property[]{ (Property)LITSTATE });
    }

    public void lit(World world, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, HCBlocks.CUSTOM_TORCH.get().defaultBlockState().setValue(LIGHTINGTIME, Integer.valueOf(getInitialLightingTime())).setValue(LITSTATE, Integer.valueOf(1)));
        if (isBurnout)
            world.getBlockTicks().scheduleTick(pos, this, 1200);
    }

    public void few(World world, BlockPos pos, BlockState state, int currentLightingTime) {
        if (isBurnout) {
            world.setBlockAndUpdate(pos, HCBlocks.CUSTOM_TORCH.get().defaultBlockState().setValue(LIGHTINGTIME, Integer.valueOf(currentLightingTime)).setValue(LITSTATE, Integer.valueOf(0)));
            world.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    public void unlit(World world, BlockPos pos, BlockState state) {
        if (isBurnout) {
            if (HCConfigGeneral.isRemoveTorch.get()) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
            world.setBlockAndUpdate(pos, HCBlocks.STICK.get().defaultBlockState());
            world.getBlockTicks().scheduleTick(pos, this, 1200);
        }
    }

    public void playExtinguishSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public void playLightingSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public static IntegerProperty getLightingTime() {
        return LIGHTINGTIME;
    }

    public static IntegerProperty getLitState() {
        return LITSTATE;
    }

    public static int getInitialLightingTime() {
        return isBurnout ? lightingTime : 0;
    }

    //Light Level
    public static ToIntFunction<BlockState> getLightValueFromState() {
        /*return (state) -> {
            if (state.getValue(CustomTorchBlock.LITSTATE) == CustomTorchBlock.LIT)
                return 14;
            else if (state.getValue(CustomTorchBlock.LITSTATE) == CustomTorchBlock.LIT)
                return 10;
            return 0;
        };*/
        return (state) -> 14;
    }
}
