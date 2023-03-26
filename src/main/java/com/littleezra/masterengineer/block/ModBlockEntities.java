package com.littleezra.masterengineer.block;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.block.entity.PassageBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MasterEngineer.MOD_ID);

    public static final RegistryObject<BlockEntityType<PassageBlockEntity>> PASSAGE =
            BLOCK_ENTITIES.register("gem_infusing_station", () ->
                    BlockEntityType.Builder.of(PassageBlockEntity::new,
                            ModBlocks.PASSAGE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
