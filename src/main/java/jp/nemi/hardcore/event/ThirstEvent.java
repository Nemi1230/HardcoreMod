package jp.nemi.hardcore.event;

import jp.nemi.hardcore.HCCore;
import jp.nemi.hardcore.capability.PlayerLastPosCapability;
import jp.nemi.hardcore.capability.ThirstLevelCapability;
import jp.nemi.hardcore.config.HCConfigCommon;
import jp.nemi.hardcore.init.HCEffects;
import jp.nemi.hardcore.network.DrinkWaterPacket;
import jp.nemi.hardcore.network.PlayerThirstLevelPacket;
import jp.nemi.hardcore.network.SimpleNetworkHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;

@EventBusSubscriber(modid = HCCore.MOD_ID)
public class ThirstEvent {
    private static int tick = 0;

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation(HCCore.MOD_ID, "player_thirst_level"), new ThirstLevelCapability.Provider());
            event.addCapability(new ResourceLocation(HCCore.MOD_ID, "player_last_position"), new PlayerLastPosCapability.Provider());
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        Entity entity = event.getEntityLiving();

        if (entity instanceof PlayerEntity) {
            if (ThirstLevelCapability.canPlayerAddWaterExhaustionLevel((PlayerEntity) entity)) {
                entity.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> {
                    if (entity.isShiftKeyDown())
                        data.addThirstExhaustion((PlayerEntity) entity, 0.24F);
                    else
                        data.addThirstExhaustion((PlayerEntity) entity, 0.14F);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        boolean flag = false;
        flag = !(event.getPlayer() instanceof FakePlayer) && event.getPlayer() instanceof ServerPlayerEntity;

        if (HCConfigCommon.isResetThirstLevelInDeath.get().booleanValue() && HCConfigCommon.isThirstEnabled.get().booleanValue()) flag = flag && !event.isWasDeath();
        if (flag && event.getPlayer().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL) != null) {
            event.getPlayer().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> event.getOriginal().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> {
                data.setThirstLevel(t.getThirstLevel());
                data.setThirstSaturationLevel(t.getThirstSaturationLevel());
                data.setThirstExhaustionLevel(t.getThirstExhaustionLevel());
            }));

            event.getPlayer().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerThirstLevelPacket(t.getThirstLevel(), t.getThirstSaturationLevel(), t.getThirstExhaustionLevel())));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity && !(event.getPlayer() instanceof FakePlayer))
            event.getPlayer().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PlayerThirstLevelPacket(t.getThirstLevel(), t.getThirstSaturationLevel(), t.getThirstExhaustionLevel())));
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity && !(event.getEntity() instanceof  FakePlayer))
            event.getEntity().getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntity()), new PlayerThirstLevelPacket(t.getThirstLevel(), t.getThirstSaturationLevel(), t.getThirstExhaustionLevel())));
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        tick++;
        tick %= 8000;
        PlayerEntity player = event.player;
        World world = player.getCommandSenderWorld();

        if (player != null && ThirstLevelCapability.canPlayerAddWaterExhaustionLevel(player)) {
            if (tick % 2 == 0) {
                player.getCapability(PlayerLastPosCapability.PLAYER_LAST_POS).ifPresent(data -> {
                    double lastX = data.getLastX();
                    double lastY = data.getLastY();
                    double lastZ = data.getLastZ();
                    boolean isLastOnGround = data.isLastOnGround();

                    if (isLastOnGround && player.isOnGround() || player.isInWater()) {
                        double x = Math.sqrt(Math.pow(lastX - player.getX(), 2) + Math.pow(lastY - player.getY(), 2) + Math.pow(lastZ - player.getZ(), 2));

                        if (x < 5) {
                            player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(t -> {
                                if (player.isSprinting())
                                    t.addThirstExhaustion(player, (float) (x / 15));
                                else
                                    t.addThirstExhaustion(player, (float) (x / 30));
                            });
                        }
                    }

                    if (player.isOnGround() || player.isInWater()) {
                        data.setLastX(player.getX());
                        data.setLastY(player.getY());
                        data.setLastZ(player.getZ());
                        data.setIsLastOnGround(true);
                    }
                    else
                        data.setIsLastOnGround(false);
                });
            }

            if (tick % 10 == 0) {
                Biome biome = world.getBiome(player.blockPosition());

                if (world.getMaxLocalRawBrightness(player.blockPosition()) == 15 && world.getDayTime() < 11000 && world.getDayTime() > 450 && !world.isRainingAt(player.blockPosition())) {
                    if (biome.getBaseTemperature() > 0.3)
                        player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.addThirstExhaustion(player, 0.0075F));
                    if (biome.getBaseTemperature() > 0.9)
                        player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.addThirstExhaustion(player, 0.0055F));
                }
            }

            if (tick % 250 == 0 && player != null && !(player instanceof FakePlayer)) {
                player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> {
                    if (!player.isCreative() && !player.isSpectator()) {
                        data.punishment(player);
                        data.award(player);
                    }
                });
            }

            if (tick % 150 == 0 && player != null && !(player instanceof FakePlayer)) {
                if (world.getDifficulty() == Difficulty.PEACEFUL)
                    player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.restoreThirst(2));
            }

            if (tick % 1500 == 0 && player != null && !(player instanceof FakePlayer) && !world.isClientSide()) {
                player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerThirstLevelPacket(data.getThirstLevel(), data.getThirstSaturationLevel(), data.getThirstExhaustionLevel())));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        PlayerEntity player = event.getPlayer();

        if (ThirstLevelCapability.canPlayerAddWaterExhaustionLevel(player))
            player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> data.addThirstExhaustion(player, 0.005F));
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        BlockState state = event.getWorld().getBlockState(event.getPos());
        ItemStack stack = event.getItemStack();
        PlayerEntity player = event.getPlayer();

        if (stack.isEmpty() && event.getWorld().getFluidState(event.getHitVec().getBlockPos().relative(event.getFace())).getType() == Fluids.WATER && player.isShiftKeyDown())
            drinkWater(player);
    }

    @SubscribeEvent
    public static void onPlayerRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        if (!HCConfigCommon.isThirstEnabled.get()) return;

        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();
        BlockRayTraceResult result = getPlayerPOVHitResult(event.getWorld(), event.getPlayer(), RayTraceContext.FluidMode.SOURCE_ONLY);

        if (player.isShiftKeyDown() && result.getType() == RayTraceResult.Type.BLOCK && world.getFluidState(result.getBlockPos()).getType() == Fluids.WATER) {
            world.playSound(player, player.blockPosition(), SoundEvents.GENERIC_DRINK, SoundCategory.PLAYERS, 0.4F, 1.0F);
            SimpleNetworkHandler.CHANNEL.sendToServer(new DrinkWaterPacket());
        }
    }

    public static void drinkWater(PlayerEntity player) {
        if (!HCConfigCommon.isThirstEnabled.get().booleanValue()) return;

        World world = player.getCommandSenderWorld();
        player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> {
            data.addThirstLevel(player, 1);
            world.playSound(player, player.blockPosition(), SoundEvents.GENERIC_DRINK, SoundCategory.PLAYERS, 0.4F, 1.0F);

            if (!world.isClientSide()) {
                Random rand = new Random();
                double poisonProbability = rand.nextDouble();
                double thirstProbability = rand.nextDouble();

                if (poisonProbability <= HCConfigCommon.poisonDebuffProbability.get()) player.addEffect(new EffectInstance(Effects.POISON, 200, 0));
                if (thirstProbability <= HCConfigCommon.thirstDebuffProbability.get()) player.addEffect(new EffectInstance(HCEffects.THIRST.get(), 200, 0));
            }
        });
    }

    //from Item.class
    private static BlockRayTraceResult getPlayerPOVHitResult(World p_219968_0_, PlayerEntity p_219968_1_, RayTraceContext.FluidMode p_219968_2_) {
        float f = p_219968_1_.xRot;
        float f1 = p_219968_1_.yRot;
        Vector3d vector3d = p_219968_1_.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = p_219968_1_.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();;
        Vector3d vector3d1 = vector3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return p_219968_0_.clip(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, p_219968_2_, p_219968_1_));
    }
}