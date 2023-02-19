package com.littleezra.masterengineer.datagen;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.blocks.ModBlocks;
import com.littleezra.masterengineer.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, MasterEngineer.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(ModBlocks.WOODEN_FRAME.get());
        blockItem(ModBlocks.WHITE_FRAME.get());
        blockItem(ModBlocks.ORANGE_FRAME.get());
        blockItem(ModBlocks.MAGENTA_FRAME.get());
        blockItem(ModBlocks.LIGHT_BLUE_FRAME.get());
        blockItem(ModBlocks.YELLOW_FRAME.get());
        blockItem(ModBlocks.LIME_FRAME.get());
        blockItem(ModBlocks.PINK_FRAME.get());
        blockItem(ModBlocks.GRAY_FRAME.get());
        blockItem(ModBlocks.LIGHT_GRAY_FRAME.get());
        blockItem(ModBlocks.CYAN_FRAME.get());
        blockItem(ModBlocks.PURPLE_FRAME.get());
        blockItem(ModBlocks.BLUE_FRAME.get());
        blockItem(ModBlocks.BROWN_FRAME.get());
        blockItem(ModBlocks.GREEN_FRAME.get());
        blockItem(ModBlocks.RED_FRAME.get());
        blockItem(ModBlocks.BLACK_FRAME.get());

        blockItem(ModBlocks.CHISELED_DUST_BRICKS.get());
        blockItem(ModBlocks.DUST_BRICKS.get());
        blockItem(ModBlocks.DUST_BRICK_STAIRS.get());
        blockItem(ModBlocks.DUST_BRICK_SLAB.get());
        wallItem(ModBlocks.DUST_BRICK_WALL.get());
        blockItem(ModBlocks.SMALL_DUST_BRICKS.get());
        blockItem(ModBlocks.SMALL_DUST_BRICK_STAIRS.get());
        blockItem(ModBlocks.SMALL_DUST_BRICK_SLAB.get());
        wallItem(ModBlocks.SMALL_DUST_BRICK_WALL.get());

        basicItem(ModBlocks.DUST.get().asItem());
        blockItem(ModBlocks.DUST_BLOCK.get());

        handheldItem(ModItems.RESONANCE_FORK.get());
        handheldItem(ModItems.FEATHER_DUSTER.get());
    }

    public String blockName(Block block){
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private ItemModelBuilder blockItem(Block block) {
        return withExistingParent(blockName(block),
                new ResourceLocation(MasterEngineer.MOD_ID, "block/" + blockName(block)));
    }
    private ItemModelBuilder spawnEggItem(Item item) {
        return withExistingParent(item.toString(),
                new ResourceLocation("item/template_spawn_egg"));
    }
    private ItemModelBuilder flatBlockItem(Block block) {
        return withExistingParent(blockName(block),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MasterEngineer.MOD_ID,"block/" + blockName(block)));
    }
    private ItemModelBuilder simpleBlockItem(Block block) {
        return withExistingParent(blockName(block),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MasterEngineer.MOD_ID,"item/" + blockName(block)));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.toString(), new ResourceLocation("item/handheld"))
                .texture("layer0", new ResourceLocation(MasterEngineer.MOD_ID,"item/" + item));
    }

    private ItemModelBuilder wallItem(Block block){
        return withExistingParent(blockName(block),
                new ResourceLocation(MasterEngineer.MOD_ID, "block/" + blockName(block) + "_inventory"));
    }
}
