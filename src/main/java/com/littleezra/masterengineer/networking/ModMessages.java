package com.littleezra.masterengineer.networking;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.networking.packets.SyncPassageDataS2CPacket;
import com.littleezra.masterengineer.networking.packets.UsePassageC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MasterEngineer.MOD_ID))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SyncPassageDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncPassageDataS2CPacket::new)
                .encoder(SyncPassageDataS2CPacket::toBytes)
                .consumerMainThread(SyncPassageDataS2CPacket::handle)
                .add();

        net.messageBuilder(UsePassageC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UsePassageC2SPacket::new)
                .encoder(UsePassageC2SPacket::toBytes)
                .consumerMainThread(UsePassageC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToAllClients(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
