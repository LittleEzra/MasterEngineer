package com.littleezra.masterengineer.networking.packets;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.client.ClientPassageData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UsePassageC2SPacket {
    final BlockPos pos;
    public UsePassageC2SPacket(BlockPos pos){
        this.pos = pos;
    }
    public UsePassageC2SPacket(FriendlyByteBuf buf){
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBlockPos(this.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server Side
            context.getSender().sendSystemMessage(Component.literal("Teleporting to " + MasterEngineer.getBlockPosString(pos)));
        });
        return true;
    }
}
