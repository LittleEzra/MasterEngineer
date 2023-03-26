package com.littleezra.masterengineer.networking.packets;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.client.ClientPassageData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SyncPassageDataS2CPacket {
    private final List<BlockPos> passages;

    public SyncPassageDataS2CPacket(List<BlockPos> passages){
        this.passages = passages;
    }
    public SyncPassageDataS2CPacket(FriendlyByteBuf buf){
        this.passages = new ArrayList<>();
        int count = buf.readInt();
        for (int i = 0; i < count; i++){
            this.passages.add(buf.readBlockPos());
        }
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(this.passages.size());
        for (BlockPos passage : passages) {
            buf.writeBlockPos(passage);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MasterEngineer.printDebug("Sync Packet Sent!");
            // Client Side
            ClientPassageData.set(this.passages);
        });
        return true;
    }
}
