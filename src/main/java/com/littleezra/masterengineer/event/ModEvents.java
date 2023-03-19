package com.littleezra.masterengineer.event;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.capabilities.MarkHandlerProvider;
import com.littleezra.masterengineer.entity.ModEntityTypes;
import com.littleezra.masterengineer.entity.custom.AngryFervour;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import com.littleezra.masterengineer.entity.custom.Sombrock;
import com.littleezra.masterengineer.particle.ModParticles;
import com.littleezra.masterengineer.particle.custom.ColorParticleOptions;
import com.littleezra.masterengineer.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
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
               if (!entity.level.isClientSide && markHandler.getTickDuration() > 0){
                   ((ServerLevel)entity.level).sendParticles(new ColorParticleOptions(ModParticles.MARK.get(), markHandler.getColor()),
                           entity.position().x, entity.position().y, entity.position().z, 1,
                           0d, 0d, 0d, 0d);
                   markHandler.tick();
               }
            });
            if (entity instanceof ServerPlayer serverPlayer){
                serverPlayer.level.getEntities(serverPlayer, serverPlayer.getBoundingBox().inflate(16)).forEach(nearby -> {
                    MasterEngineer.NEAR_ENTITY_TRIGGER.trigger(serverPlayer, nearby);
                });
            }
        }

        public static void onEntityTeleport(EntityTeleportEvent.EnderEntity event){
            LivingEntity entity = event.getEntityLiving();
            BlockPos pos = new BlockPos(
                    Math.round(event.getTargetX()),
                    Math.round(event.getTargetY()),
                    Math.round(event.getTargetZ()));
            if (entity.level.getBlockState(pos.below()).is(ModTags.Blocks.NO_TELEPORT) || entity.level.getBlockState(pos).is(ModTags.Blocks.NO_TELEPORT)){
                event.setCanceled(true);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MasterEngineer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents
    {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event)
        {
            event.put(ModEntityTypes.SOMBROCK.get(), Sombrock.createAttributes());
            event.put(ModEntityTypes.HAPPY_FERVOUR.get(), HappyFervour.createAttributes());
            event.put(ModEntityTypes.ANGRY_FERVOUR.get(), AngryFervour.createAttributes());
        }
    }
}
