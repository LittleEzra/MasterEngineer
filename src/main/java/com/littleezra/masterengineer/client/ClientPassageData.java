package com.littleezra.masterengineer.client;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ClientPassageData {
    private static List<BlockPos> passages = new ArrayList<>();

    public static void set(List<BlockPos> passages){
        ClientPassageData.passages.clear();
        ClientPassageData.passages.addAll(passages);
    }

    public static List<BlockPos> getPassages(){
        return passages;
    }
}
