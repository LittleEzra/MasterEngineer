package com.littleezra.masterengineer.blocks;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.blocks.custom.*;
import com.littleezra.masterengineer.items.ModItems;
import com.littleezra.masterengineer.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MasterEngineer.MOD_ID);

    public static final RegistryObject<Block> WOODEN_FRAME = registerBlock("wooden_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.of(ModMaterials.FRAME).noOcclusion().instabreak().sound(SoundType.WOOD)
                    .isValidSpawn(ModBlocks::never).isRedstoneConductor(ModBlocks::never).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Block> WHITE_FRAME = registerBlock("white_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.WOOL)));
    public static final RegistryObject<Block> LIGHT_GRAY_FRAME = registerBlock("light_gray_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_LIGHT_GRAY)));
    public static final RegistryObject<Block> GRAY_FRAME = registerBlock("gray_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryObject<Block> BLACK_FRAME = registerBlock("black_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_BLACK)));
    public static final RegistryObject<Block> ORANGE_FRAME = registerBlock("orange_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> MAGENTA_FRAME = registerBlock("magenta_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> LIGHT_BLUE_FRAME = registerBlock("light_blue_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryObject<Block> YELLOW_FRAME = registerBlock("yellow_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> LIME_FRAME = registerBlock("lime_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_LIGHT_GREEN)));
    public static final RegistryObject<Block> PINK_FRAME = registerBlock("pink_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_PINK)));
    public static final RegistryObject<Block> CYAN_FRAME = registerBlock("cyan_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_CYAN)));
    public static final RegistryObject<Block> PURPLE_FRAME = registerBlock("purple_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> BLUE_FRAME = registerBlock("blue_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_BLUE)));
    public static final RegistryObject<Block> BROWN_FRAME = registerBlock("brown_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_BROWN)));
    public static final RegistryObject<Block> GREEN_FRAME = registerBlock("green_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_GREEN)));
    public static final RegistryObject<Block> RED_FRAME = registerBlock("red_frame",
            () -> new FrameBlock(BlockBehaviour.Properties.copy(ModBlocks.WOODEN_FRAME.get()).color(MaterialColor.COLOR_RED)));

    public static final RegistryObject<Block> DUST_BRICKS = registerBlock("dust_bricks",
            () -> new DustBrickBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.2F, 5.0F)));
    public static final RegistryObject<Block> DUST_BRICK_STAIRS = registerBlock("dust_brick_stairs",
            () -> new StairBlock(() -> DUST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DUST_BRICKS.get())));
    public static final RegistryObject<Block> DUST_BRICK_SLAB = registerBlock("dust_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 5.0F)));
    public static final RegistryObject<Block> DUST_BRICK_WALL = registerBlock("dust_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(DUST_BRICKS.get())));

    public static final RegistryObject<Block> SMALL_DUST_BRICKS = registerBlock("small_dust_bricks",
            () -> new DustBrickBlock(BlockBehaviour.Properties.copy(DUST_BRICKS.get())));
    public static final RegistryObject<Block> SMALL_DUST_BRICK_STAIRS = registerBlock("small_dust_brick_stairs",
            () -> new StairBlock(() -> SMALL_DUST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(SMALL_DUST_BRICKS.get())));
    public static final RegistryObject<Block> SMALL_DUST_BRICK_SLAB = registerBlock("small_dust_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 5.0F)));
    public static final RegistryObject<Block> SMALL_DUST_BRICK_WALL = registerBlock("small_dust_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SMALL_DUST_BRICKS.get())));

    public static final RegistryObject<Block> CHISELED_DUST_BRICKS = registerBlock("chiseled_dust_bricks",
            () -> new DustBrickBlock(BlockBehaviour.Properties.copy(DUST_BRICKS.get())));
    public static final RegistryObject<Block> DUST = registerBlock("dust",
            () -> new DustLayerBlock(BlockBehaviour.Properties.of(Material.TOP_SNOW, MaterialColor.STONE).sound(SoundType.WOOL).instabreak().noCollission()));
    public static final RegistryObject<Block> DUST_BLOCK = registerBlock("dust_block",
            () -> new DustBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.WOOL).strength(0.4F, 0F).speedFactor(0.4F)
                    .isValidSpawn(ModBlocks::always).isRedstoneConductor(ModBlocks::always).isViewBlocking(ModBlocks::always).isSuffocating(ModBlocks::always)));

    public static final RegistryObject<Block> ALLOCITE_BLOCK = registerBlock("allocite_block",
            () -> new AllociteBlock(BlockBehaviour.Properties.of(Material.AMETHYST).sound(ModSounds.ALLOCITE).noOcclusion().lightLevel(state -> 5).strength(1.5F, 400F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALLOCITE_PLATING = registerBlock("allocite_plating",
            () -> new AllociteBlock(BlockBehaviour.Properties.of(Material.AMETHYST).sound(SoundType.GLASS).noOcclusion().strength(1.7F, 400F).requiresCorrectToolForDrops()));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static LeavesBlock leaves(SoundType p_152615_) {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(p_152615_).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }
    private static Boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static Boolean always(BlockState p_50810_, BlockGetter p_50811_, BlockPos p_50812_, EntityType<?> p_50813_) {
        return (boolean)true;
    }
    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return (boolean)(p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT);
    }

    private static Block simpleFlammableBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new Block(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static SlabBlock flammableSlabBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new SlabBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static StairBlock flammableStairBlock(Block base, BlockBehaviour.Properties properties, int flammability, int firespread){
        return new StairBlock(base::defaultBlockState, properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceBlock flammableFenceBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceGateBlock(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
