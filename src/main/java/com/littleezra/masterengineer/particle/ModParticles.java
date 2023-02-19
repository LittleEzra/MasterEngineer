package com.littleezra.masterengineer.particle;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.particle.custom.ColorParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MasterEngineer.MOD_ID);

    public static final RegistryObject<ParticleType<ColorParticleOptions>> MARK = PARTICLE_TYPES.register("mark",
            () -> new ParticleType<ColorParticleOptions>(true, ColorParticleOptions.DESERIALIZER) {
                @Override
                public Codec<ColorParticleOptions> codec() {
                    return ColorParticleOptions.codec(MARK.get());
                }
            });

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
