package jp.nemi.hardcore.network;

import jp.nemi.hardcore.capability.ThirstLevelCapability;
import jp.nemi.hardcore.event.ThirstEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class DrinkWaterPacket implements INormalMessage {
    public DrinkWaterPacket() {}

    public DrinkWaterPacket(PacketBuffer buffer) {}

    @Override
    public void toBytes(PacketBuffer packetBuffer) {}

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        ThirstEvent.drinkWater((PlayerEntity) player);
        player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(data -> SimpleNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PlayerThirstLevelPacket(data.getThirstLevel(), data.getThirstSaturationLevel(), data.getThirstExhaustionLevel())));
    }
}
