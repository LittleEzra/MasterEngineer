package com.littleezra.masterengineer.capabilities;

import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3f;

public class MarkHandler {
    private Vector3f color = new Vector3f(0f, 0f, 0f);
    private int tickDuration;

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public int getTickDuration() {
        return tickDuration;
    }

    public void setTickDuration(int tickDuration) {
        this.tickDuration = tickDuration;
    }

    public void copyFrom(MarkHandler source){
        this.color = source.color;
        this.tickDuration = source.tickDuration;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("duration", this.tickDuration);

        nbt.putFloat("markRed", this.color.x);
        nbt.putFloat("markGreen", this.color.y);
        nbt.putFloat("markBlue", this.color.z);
    }

    public void loadNBTData(CompoundTag nbt){
        this.setTickDuration(nbt.getInt("duration"));

        this.setColor(new Vector3f(
                nbt.getFloat("markRed"),
                nbt.getFloat("markGreen"),
                nbt.getFloat("markBlue")));
    }

    public void tick() {
        tickDuration--;
    }
}
