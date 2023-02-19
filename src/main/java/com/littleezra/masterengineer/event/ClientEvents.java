package com.littleezra.masterengineer.event;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.particle.ModParticles;
import com.littleezra.masterengineer.particle.custom.MarkParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MasterEngineer.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

    }

    @Mod.EventBusSubscriber(modid = MasterEngineer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            event.register(ModParticles.MARK.get(), MarkParticle.Provider::new);
        }
    }
}
