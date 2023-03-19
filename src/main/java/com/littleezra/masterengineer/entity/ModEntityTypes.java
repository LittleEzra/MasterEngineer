package com.littleezra.masterengineer.entity;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.AngryFervour;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import com.littleezra.masterengineer.entity.custom.MarkArrow;
import com.littleezra.masterengineer.entity.custom.Sombrock;
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
    public static final RegistryObject<EntityType<Sombrock>> SOMBROCK = ENTITY_TYPES.register("sombrock",
            () -> EntityType.Builder.of(Sombrock::new, MobCategory.CREATURE).sized(1f, 2.25f)
                    .build(new ResourceLocation(MasterEngineer.MOD_ID, "sombrock").toString()));

    public static final RegistryObject<EntityType<HappyFervour>> HAPPY_FERVOUR = ENTITY_TYPES.register("happy_fervour",
            () -> EntityType.Builder.of(HappyFervour::new, MobCategory.CREATURE).sized(0.375F, 0.5F)
                    .build(new ResourceLocation(MasterEngineer.MOD_ID, "happy_fervour").toString()));
    public static final RegistryObject<EntityType<AngryFervour>> ANGRY_FERVOUR = ENTITY_TYPES.register("angry_fervour",
            () -> EntityType.Builder.of(AngryFervour::new, MobCategory.CREATURE).sized(0.375F, 0.5F)
                    .build(new ResourceLocation(MasterEngineer.MOD_ID, "angry_fervour").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
