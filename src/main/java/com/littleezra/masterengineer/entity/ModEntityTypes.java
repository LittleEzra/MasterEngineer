package com.littleezra.masterengineer.entity;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.MarkArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MasterEngineer.MOD_ID);

    public static final RegistryObject<EntityType<MarkArrow>> MARK_ARROW = ENTITY_TYPES.register("mark_arrow",
            () -> EntityType.Builder.<MarkArrow>of(MarkArrow::new, MobCategory.MISC).sized(1f, 0.625f)
                    .build(new ResourceLocation(MasterEngineer.MOD_ID, "mark_arrow").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
