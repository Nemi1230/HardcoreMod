package jp.nemi.hardcore.network;

import jp.nemi.hardcore.HCCore;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Function;

public class SimpleNetworkHandler {
    public static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(HCCore.MOD_ID, "main"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void init() {
        int id = 0;
        registerMessage(id++, PlayerThirstLevelPacket.class, PlayerThirstLevelPacket::new);
        registerMessage(id++, DrinkWaterPacket.class, DrinkWaterPacket::new);
    }

    private static <T extends INormalMessage> void registerMessage(int index, Class<T> messageType, Function<PacketBuffer, T> decoder) {
        CHANNEL.registerMessage(index, messageType, INormalMessage::toBytes, decoder, (message, context) -> {
            message.process(context);
            context.get().setPacketHandled(true);
        });
    }
}
