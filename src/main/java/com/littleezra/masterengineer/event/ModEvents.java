package com.littleezra.masterengineer.event;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.capabilities.MarkHandlerProvider;
import com.littleezra.masterengineer.particle.ModParticles;
import com.littleezra.masterengineer.particle.custom.ColorParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MasterEngineer.MOD_ID)
    public static class ForgeEvents{

        @SubscribeEvent
        public static void onAttachCapabilitiesLiving(AttachCapabilitiesEvent<Entity> event){
            if (event.getObject() instanceof LivingEntity){
                if (!event.getObject().getCapability(MarkHandlerProvider.MARK_HANDLER).isPresent()){
                    event.addCapability(new ResourceLocation(MasterEngineer.MOD_ID, "properties"), new MarkHandlerProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event){
            if (event.isWasDeath()){
                event.getOriginal().getCapability(MarkHandlerProvider.MARK_HANDLER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(MarkHandlerProvider.MARK_HANDLER).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
            event.register(MarkHandlerProvider.class);
        }

        @SubscribeEvent
        public static void onLivingTick(LivingEvent.LivingTickEvent event){
            LivingEntity entity = event.getEntity();
            entity.getCapability(MarkHandlerProvider.MARK_HANDLER).ifPresent(markHandler -> {
               if (markHandler.getTickDuration() > 0){
                   MasterEngineer.printDebug(markHandler.getColor());
                   entity.level.addParticle(new ColorParticleOptions(ModParticles.MARK.get(), markHandler.getColor()),
                           entity.position().x, entity.position().y, entity.position().z,
                           0d, 0d, 0d);
                   markHandler.tick();
               }
            });
        }
    }

    @Mod.EventBusSubscriber(modid = MasterEngineer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents
    {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event)
        {

        }
    }
}
