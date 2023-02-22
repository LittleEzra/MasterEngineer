package com.littleezra.masterengineer.capabilities;

import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3f;

public class MarkHandler {
    private Vector3f color = new Vector3f(0f, 0f, 0f);
    private int tickDuration;

    public Vector3f getColor() {
        return this.color;
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
        this.setColor(source.getColor());
        this.setTickDuration(source.getTickDuration());
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("duration", this.getTickDuration());

        nbt.putFloat("markRed", this.getColor().x);
        nbt.putFloat("markGreen", this.getColor().y);
        nbt.putFloat("markBlue", this.getColor().z);
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
