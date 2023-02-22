package com.littleezra.masterengineer.sound;

import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MasterEngineer.MOD_ID);

    public static final RegistryObject<SoundEvent> RESONANCE_FORK_USE = registerSoundEvent("item.resonance_fork.use");

    public static final RegistryObject<SoundEvent> ALLOCITE_BREAK = registerSoundEvent("block.allocite.break");
    public static final RegistryObject<SoundEvent> ALLOCITE_PLACE = registerSoundEvent("block.allocite.place");
    public static final RegistryObject<SoundEvent> ALLOCITE_STEP = registerSoundEvent("block.allocite.step");
    public static final RegistryObject<SoundEvent> ALLOCITE_FALL = registerSoundEvent("block.allocite.fall");
    public static final RegistryObject<SoundEvent> ALLOCITE_HIT = registerSoundEvent("block.allocite.hit");

    public static final ForgeSoundType ALLOCITE = new ForgeSoundType(1f, 1f,
            ModSounds.ALLOCITE_BREAK,
            ModSounds.ALLOCITE_STEP,
            ModSounds.ALLOCITE_PLACE,
            ModSounds.ALLOCITE_HIT,
            ModSounds.ALLOCITE_FALL
    );

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
