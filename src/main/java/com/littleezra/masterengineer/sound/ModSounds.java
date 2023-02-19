package com.littleezra.masterengineer.sound;

import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MasterEngineer.MOD_ID);

    public static final RegistryObject<SoundEvent> RESONANCE_FORK_USE = registerSoundEvent("item.resonance_fork.use");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MasterEngineer.MOD_ID, name)));
    }
    private static RegistryObject<SoundEvent> registerSoundEvent(String name, float range)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MasterEngineer.MOD_ID, name), range));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
