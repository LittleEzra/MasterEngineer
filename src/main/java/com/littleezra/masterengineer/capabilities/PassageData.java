package com.littleezra.masterengineer.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PassageData {
    private final List<BlockPos> passages = new ArrayList<>();
    public List<BlockPos> getPassages(){
        return this.passages;
    }
    public void addPassage(BlockPos pos){
        passages.add(pos);
    }
    public void removePassage(BlockPos pos){
        passages.remove(pos);
    }
    public void clear(){
        this.passages.clear();
    }

    public void copyFrom(PassageData source){
        this.clear();
        this.passages.addAll(source.getPassages());
    }

    public void saveNBTData(CompoundTag nbt){
        ListTag list = new ListTag();
        this.passages.forEach(pos -> {
            list.add(NbtUtils.writeBlockPos(pos));
        });
        nbt.put("passages", list);
    }

    public void loadNBTData(CompoundTag nbt){
        ListTag list  = nbt.getList("passages", Tag.TAG_COMPOUND);
        for (Tag t : list){
            CompoundTag posTag = (CompoundTag) t;
            BlockPos pos = NbtUtils.readBlockPos(posTag);
            this.passages.add(pos);
        }
    }
}
