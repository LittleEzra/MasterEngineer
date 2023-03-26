package com.littleezra.masterengineer.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PassageDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PassageData> PASSAGE_DATA =
            CapabilityManager.get(new CapabilityToken<PassageData>() {});

    private PassageData passageData = null;
    private final LazyOptional<PassageData> optional = LazyOptional.of(this::createPassageData);

    private @NotNull PassageData createPassageData() {
        if (this.passageData == null){
            this.passageData = new PassageData();
        }

        return this.passageData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PASSAGE_DATA){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPassageData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPassageData().loadNBTData(nbt);
    }
}
