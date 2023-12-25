package jp.nemi.hardcore.object.blocks;

import jp.nemi.hardcore.init.HCBlocks;
import jp.nemi.hardcore.object.blocks.vanilla.HCTorchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StickBlock extends Block {
        protected static final VoxelShape AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

        public StickBlock(Properties properties) {
                super(properties);
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
                                        level.setBlock(blockPos, HCBlocks.HC_TORCH.get().defaultBlockState().setValue(HCTorchBlock.LIT, 1).setValue(HCTorchBlock.LIGHTING_TIME, HCTorchBlock.getDefaultLightingTime()), 2);
                                        level.scheduleTick(blockPos, HCBlocks.HC_TORCH.get(), HCTorchBlock.TICK_INTERVAL);
                                }
                        }

                        return InteractionResult.SUCCESS;
                }
                else {
                        return super.use(state, level, blockPos, player, hand, result);
                }
        }

        @Override
        public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
                return AABB;
        }

        public BlockState updateShape(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
                return p_57504_ == Direction.DOWN && !this.canSurvive(p_57503_, p_57506_, p_57507_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
        }

        public boolean canSurvive(BlockState p_57499_, LevelReader p_57500_, BlockPos p_57501_) {
                return canSupportCenter(p_57500_, p_57501_.below(), Direction.UP);
        }
}
