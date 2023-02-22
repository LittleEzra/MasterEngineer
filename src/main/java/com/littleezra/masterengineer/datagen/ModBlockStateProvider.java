package com.littleezra.masterengineer.datagen;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput packOutput, ExistingFileHelper exFileHelper) {
        super(packOutput, MasterEngineer.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithRenderType(ModBlocks.WOODEN_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.WHITE_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.ORANGE_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.MAGENTA_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.LIGHT_BLUE_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.YELLOW_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.LIME_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.PINK_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.GRAY_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.LIGHT_GRAY_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.CYAN_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.PURPLE_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.BLUE_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.BROWN_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.GREEN_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.RED_FRAME.get(), "cutout");
        simpleBlockWithRenderType(ModBlocks.BLACK_FRAME.get(), "cutout");

        simpleBlock(ModBlocks.DUST_BLOCK.get());
        stairsBlock((StairBlock) ModBlocks.DUST_BRICK_STAIRS.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/dust_bricks"));
        slabBlock((SlabBlock) ModBlocks.DUST_BRICK_SLAB.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/dust_bricks"),
                new ResourceLocation(MasterEngineer.MOD_ID, "block/dust_bricks"));
        wallBlock((WallBlock) ModBlocks.DUST_BRICK_WALL.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/dust_bricks"));
        wallBlockInventory((WallBlock) ModBlocks.DUST_BRICK_WALL.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/dust_bricks"));

        stairsBlock((StairBlock) ModBlocks.SMALL_DUST_BRICK_STAIRS.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/small_dust_bricks"));
        slabBlock((SlabBlock) ModBlocks.SMALL_DUST_BRICK_SLAB.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/small_dust_bricks"),
                new ResourceLocation(MasterEngineer.MOD_ID, "block/small_dust_bricks"));
        wallBlock((WallBlock) ModBlocks.SMALL_DUST_BRICK_WALL.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/small_dust_bricks"));
        wallBlockInventory((WallBlock) ModBlocks.SMALL_DUST_BRICK_WALL.get(), new ResourceLocation(MasterEngineer.MOD_ID, "block/small_dust_bricks"));

        simpleBlock(ModBlocks.ALLOCITE_BLOCK.get());
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation blockTexture(Block block, String append){
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + append);
    }

    public void simpleBlockWithRenderType(Block block, String renderType){
        simpleBlock(block, models().cubeAll(name(block), blockTexture(block)).renderType(renderType));
    }
    public void wallBlockInventory(WallBlock block, ResourceLocation texture){
        models().wallInventory(name(block) + "_inventory", texture);
    }
    public void nonRotatedPillarBlock(Block block){
        simpleBlock(block, models().cubeColumn(name(block), blockTexture(block), blockTexture(block, "_top")));
    }
}
