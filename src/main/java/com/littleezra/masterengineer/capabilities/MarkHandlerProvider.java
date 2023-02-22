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

public class MarkHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<MarkHandler> MARK_HANDLER =
            CapabilityManager.get(new CapabilityToken<MarkHandler>() {});

    private MarkHandler markHandler = null;
    private final LazyOptional<MarkHandler> optional = LazyOptional.of(this::createMarkHandler);

    private @NotNull MarkHandler createMarkHandler() {
        if (this.markHandler == null){
            this.markHandler = new MarkHandler();
        }

        return this.markHandler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MARK_HANDLER){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMarkHandler().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMarkHandler().loadNBTData(nbt);
    }
}
