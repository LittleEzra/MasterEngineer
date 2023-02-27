package com.littleezra.masterengineer;

import com.littleezra.masterengineer.advancement.criteria.NearEntityTrigger;
import com.littleezra.masterengineer.blocks.ModBlocks;
import com.littleezra.masterengineer.effect.ModMobEffects;
import com.littleezra.masterengineer.entity.ModEntityTypes;
import com.littleezra.masterengineer.entity.client.MarkArrowRenderer;
import com.littleezra.masterengineer.entity.client.SombrockRenderer;
import com.littleezra.masterengineer.items.ModItems;
import com.littleezra.masterengineer.particle.ModParticles;
import com.littleezra.masterengineer.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Vector;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MasterEngineer.MOD_ID)
public class MasterEngineer
{
    public static final String MOD_ID = "masterengineer";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static NearEntityTrigger NEAR_ENTITY_TRIGGER = null;

    public MasterEngineer()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerItemColors);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            NEAR_ENTITY_TRIGGER = CriteriaTriggers.register(new NearEntityTrigger());
        });
    }

    public static void printDebug(String line){
        LOGGER.info(line);
    }
    public static void printDebug(boolean value){
        LOGGER.info(Boolean.toString(value));
    }
    public static void printDebug(int value){
        LOGGER.info(Integer.toString(value));
    }
    public static void printDebug(float value){
        LOGGER.info(Float.toString(value));
    }
    public static void printDebug(double value){
        LOGGER.info(Double.toString(value));
    }
    public static void printDebug(Vector3f vector3f){
        LOGGER.info("(" + vector3f.x + ", " + vector3f.y + ", " + vector3f.z + ")");
    }

    public static void printServer(String line, @Nullable MinecraftServer server){
        if (server != null) {
            server.sendSystemMessage(Component.literal("[MasterEngineer Mod] " + line));
        }
    }

    public static int getIntColor(String hex){
        Color col = Color.decode(hex);
        return getIntFromRGB(col.getRed(), col.getGreen(), col.getBlue());
    }

    public static int getIntColor(Vector3f vector3f){
        Color col = new Color(vector3f.x, vector3f.y, vector3f.z);
        return getIntFromRGB(col.getRed(), col.getGreen(), col.getBlue());
    }

    public static Vector3f getVector3Color(String hex, float divisor){
        Color col = Color.decode(hex);
        return new Vector3f(col.getRed() / divisor, col.getGreen() / divisor, col.getBlue() / divisor);
    }
    public static Vector3f getVector3Color(String hex){
        Color col = Color.decode(hex);
        return new Vector3f(col.getRed() / 255F, col.getGreen() / 255F, col.getBlue() / 255F);
    }

    public static int getIntFromRGB(int Red, int Green, int Blue){

        int R = (Red << 16)  & 0x00FF0000;
        int G = (Green << 8) & 0x0000FF00;
        int B = Blue & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }

    public void addCreative(CreativeModeTabEvent.BuildContents event){
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS){{
            event.accept(ModItems.SOMBROCK_SPAWN_EGG);
        }}
        if (event.getTab() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModBlocks.DUST);
            event.accept(ModItems.ALLOCITE_CRYSTAL);
        }
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS){

        }
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.RESONANCE_FORK);
            event.accept(ModItems.FEATHER_DUSTER);
        }
        if (event.getTab() == CreativeModeTabs.OP_BLOCKS){

        }
        if (event.getTab() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.MARK_ARROW);
        }

// Blocks!
        if (event.getTab() == CreativeModeTabs.COLORED_BLOCKS){
            event.accept(ModBlocks.WOODEN_FRAME);
            event.accept(ModBlocks.WHITE_FRAME);
            event.accept(ModBlocks.LIGHT_GRAY_FRAME);
            event.accept(ModBlocks.GRAY_FRAME);
            event.accept(ModBlocks.BLACK_FRAME);
            event.accept(ModBlocks.BROWN_FRAME);
            event.accept(ModBlocks.RED_FRAME);
            event.accept(ModBlocks.ORANGE_FRAME);
            event.accept(ModBlocks.YELLOW_FRAME);
            event.accept(ModBlocks.LIME_FRAME);
            event.accept(ModBlocks.GREEN_FRAME);
            event.accept(ModBlocks.CYAN_FRAME);
            event.accept(ModBlocks.LIGHT_BLUE_FRAME);
            event.accept(ModBlocks.BLUE_FRAME);
            event.accept(ModBlocks.PURPLE_FRAME);
            event.accept(ModBlocks.MAGENTA_FRAME);
            event.accept(ModBlocks.PINK_FRAME);
        }
        if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(ModBlocks.ALLOCITE_BLOCK);
        }
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.WOODEN_FRAME);

            event.accept(ModBlocks.DUST_BRICKS);
            event.accept(ModBlocks.DUST_BRICK_STAIRS);
            event.accept(ModBlocks.DUST_BRICK_SLAB);
            event.accept(ModBlocks.DUST_BRICK_WALL);
            event.accept(ModBlocks.CHISELED_DUST_BRICKS);
            event.accept(ModBlocks.SMALL_DUST_BRICKS);
            event.accept(ModBlocks.SMALL_DUST_BRICK_STAIRS);
            event.accept(ModBlocks.SMALL_DUST_BRICK_SLAB);
            event.accept(ModBlocks.SMALL_DUST_BRICK_WALL);
            event.accept(ModBlocks.DUST_BLOCK);
            event.accept(ModBlocks.ALLOCITE_PLATING);
            event.accept(ModBlocks.GHOST_BLOCK);
        }
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS){

        }
        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(ModBlocks.GHOST_BLOCK);
        }
    }
    public void registerItemColors(RegisterColorHandlersEvent.Item event){
        event.register((itemStack, i) -> i > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack), ModItems.MARK_ARROW.get());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.MARK_ARROW.get(), MarkArrowRenderer::new);
            EntityRenderers.register(ModEntityTypes.SOMBROCK.get(), SombrockRenderer::new);
        }
    }
}
