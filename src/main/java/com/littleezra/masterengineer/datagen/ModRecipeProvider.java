package com.littleezra.masterengineer.datagen;

import com.littleezra.masterengineer.blocks.ModBlocks;
import com.littleezra.masterengineer.items.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOODEN_FRAME.get(), 16)
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .pattern("ISI")
                .pattern("S S")
                .pattern("ISI")
                .group("building_frame").unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(pWriter);

        coloredFrameRecipe(pWriter, ModBlocks.WHITE_FRAME.get(), Items.WHITE_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.ORANGE_FRAME.get(), Items.ORANGE_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.MAGENTA_FRAME.get(), Items.MAGENTA_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.LIGHT_BLUE_FRAME.get(), Items.LIGHT_BLUE_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.YELLOW_FRAME.get(), Items.YELLOW_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.LIME_FRAME.get(), Items.LIME_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.PINK_FRAME.get(), Items.PINK_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.GRAY_FRAME.get(), Items.GRAY_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.LIGHT_GRAY_FRAME.get(), Items.LIGHT_GRAY_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.CYAN_FRAME.get(), Items.CYAN_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.PURPLE_FRAME.get(), Items.PURPLE_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.BLUE_FRAME.get(), Items.BLUE_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.BROWN_FRAME.get(), Items.BROWN_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.GREEN_FRAME.get(), Items.GREEN_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.RED_FRAME.get(), Items.RED_DYE);
        coloredFrameRecipe(pWriter, ModBlocks.BLACK_FRAME.get(), Items.BLACK_DYE);

        twoByTwoPacker(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUST_BLOCK.get(), ModBlocks.DUST.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.DUST.get(), 4).requires(ModBlocks.DUST_BLOCK.get())
                        .unlockedBy(getHasName(ModBlocks.DUST_BLOCK.get()), has(ModBlocks.DUST_BLOCK.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUST_BRICKS.get(), 4)
                .define('#', ModBlocks.DUST_BLOCK.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.DUST_BRICKS.get()), has(ModBlocks.DUST_BRICKS.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMALL_DUST_BRICKS.get(), 4)
                .define('#', ModBlocks.DUST_BRICKS.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.DUST_BRICKS.get()), has(ModBlocks.DUST_BRICKS.get())).save(pWriter);

        stair(ModBlocks.DUST_BRICK_STAIRS.get(), ModBlocks.DUST_BRICKS.get(), pWriter);
        slab(ModBlocks.DUST_BRICK_SLAB.get(), ModBlocks.DUST_BRICKS.get(), pWriter);
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUST_BRICK_WALL.get(), ModBlocks.DUST_BRICKS.get());

        stair(ModBlocks.SMALL_DUST_BRICK_STAIRS.get(), ModBlocks.SMALL_DUST_BRICKS.get(), pWriter);
        slab(ModBlocks.SMALL_DUST_BRICK_SLAB.get(), ModBlocks.SMALL_DUST_BRICKS.get(), pWriter);
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMALL_DUST_BRICK_WALL.get(), ModBlocks.SMALL_DUST_BRICKS.get());

        chiseled(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DUST_BRICKS.get(), ModBlocks.DUST_BRICK_SLAB.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DUST_BRICKS.get())
                .define('#', ModBlocks.SMALL_DUST_BRICK_SLAB.get()).pattern("#").pattern("#")
                .unlockedBy(getHasName(ModBlocks.DUST_BRICK_SLAB.get()), has(ModBlocks.DUST_BRICK_SLAB.get()))
                .save(pWriter, getConversionRecipeName(ModBlocks.CHISELED_DUST_BRICKS.get(), ModBlocks.SMALL_DUST_BRICK_SLAB.get()));

        stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DUST_BRICKS.get(), ModBlocks.DUST_BRICKS.get());
        stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DUST_BRICKS.get(), ModBlocks.SMALL_DUST_BRICKS.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RESONANCE_FORK.get())
                .define('/', Items.STICK)
                .define('C', Items.COPPER_INGOT)
                .define('A', Blocks.AMETHYST_BLOCK)
                .pattern("A A")
                .pattern("ACA")
                .pattern(" / ")
                .unlockedBy("has_amethyst_shard", inventoryTrigger(
                        ItemPredicate.Builder.item().of(Items.AMETHYST_SHARD).build()))
                .save(pWriter);
    }

    protected static void coloredFrameRecipe(Consumer<FinishedRecipe> pWriter, ItemLike pColoredFrame, ItemLike pDye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pColoredFrame, 8)
                .define('F', ModBlocks.WOODEN_FRAME.get())
                .define('D', pDye)
                .pattern("FFF")
                .pattern("FDF")
                .pattern("FFF")
                .group("building_frame").unlockedBy("has_wooden_frame", has(ModBlocks.WOODEN_FRAME.get()))
                .save(pWriter);
    }

    public static void stair(ItemLike pStair, ItemLike pMaterial, Consumer<FinishedRecipe> pWriter){
        stairBuilder(pStair, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pWriter);
        stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, pStair, pMaterial);
    }

    public static void slab(ItemLike pSlab, ItemLike pMaterial, Consumer<FinishedRecipe> pWriter){
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, pSlab, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pWriter);
        stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, pSlab, pMaterial, 2);
    }
}
