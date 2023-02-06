package jp.nemi.hardcore.network;

import jp.nemi.hardcore.capability.ThirstLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerThirstLevelPacket implements INormalMessage {
    int thirstLevel;
    int thirstSaturationLevel;
    float thirstExhaustionLevel;

    public PlayerThirstLevelPacket(int thirstLevel, int thirstSaturationLevel, float thirstExhaustionLevel) {
        this.thirstLevel = thirstLevel;
        this.thirstSaturationLevel = thirstSaturationLevel;
        this.thirstExhaustionLevel = thirstExhaustionLevel;
    }

    public PlayerThirstLevelPacket(PacketBuffer buf) {
        this.thirstLevel = buf.readInt();
        this.thirstSaturationLevel = buf.readInt();
        this.thirstExhaustionLevel = buf.readFloat();
    }

    @Override
    public void toBytes(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(this.thirstLevel);
        packetBuffer.writeInt(this.thirstSaturationLevel);
        packetBuffer.writeFloat(this.thirstExhaustionLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            context.get().enqueueWork(() -> (Minecraft.getInstance()).player.getCapability(ThirstLevelCapability.PLAYER_THIRST_LEVEL).ifPresent(date -> {
                date.setThirstLevel(this.thirstLevel);
                date.setThirstSaturationLevel(this.thirstSaturationLevel);
                date.setThirstExhaustionLevel(this.thirstExhaustionLevel);
            }));
    }
}
