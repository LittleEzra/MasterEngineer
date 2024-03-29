package com.littleezra.masterengineer.item;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.ModEntityTypes;
import com.littleezra.masterengineer.item.custom.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MasterEngineer.MOD_ID);

    public static final RegistryObject<Item> SOMBROCK_SPAWN_EGG = ITEMS.register("sombrock_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.SOMBROCK, MasterEngineer.getIntColor("#5d5d52"), MasterEngineer.getIntColor("#a0a297"),
                    new Item.Properties()));

    public static final RegistryObject<Item> RESONANCE_FORK = ITEMS.register("resonance_fork",
            () -> new ResonanceForkItem(new Item.Properties().durability(10)));
    public static final RegistryObject<Item> MARK_ARROW = ITEMS.register("mark_arrow",
            () -> new MarkArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> FEATHER_DUSTER = ITEMS.register("feather_duster",
            () -> new FeatherDusterItem(new Item.Properties()));

    public static final RegistryObject<Item> ALLOCITE_CRYSTAL = ITEMS.register("allocite_crystal",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
