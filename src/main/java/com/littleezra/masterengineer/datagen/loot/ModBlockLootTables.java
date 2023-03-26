package com.littleezra.masterengineer.datagen.loot;

import com.littleezra.masterengineer.block.ModBlocks;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.WOODEN_FRAME.get());
        this.dropSelf(ModBlocks.WHITE_FRAME.get());
        this.dropSelf(ModBlocks.ORANGE_FRAME.get());
        this.dropSelf(ModBlocks.MAGENTA_FRAME.get());
        this.dropSelf(ModBlocks.LIGHT_BLUE_FRAME.get());
        this.dropSelf(ModBlocks.YELLOW_FRAME.get());
        this.dropSelf(ModBlocks.LIME_FRAME.get());
        this.dropSelf(ModBlocks.PINK_FRAME.get());
        this.dropSelf(ModBlocks.GRAY_FRAME.get());
        this.dropSelf(ModBlocks.LIGHT_GRAY_FRAME.get());
        this.dropSelf(ModBlocks.CYAN_FRAME.get());
        this.dropSelf(ModBlocks.PURPLE_FRAME.get());
        this.dropSelf(ModBlocks.BLUE_FRAME.get());
        this.dropSelf(ModBlocks.BROWN_FRAME.get());
        this.dropSelf(ModBlocks.GREEN_FRAME.get());
        this.dropSelf(ModBlocks.RED_FRAME.get());
        this.dropSelf(ModBlocks.BLACK_FRAME.get());

        this.dropSelf(ModBlocks.CHISELED_DUST_BRICKS.get());
        this.dropSelf(ModBlocks.DUST_BRICKS.get());
        this.dropSelf(ModBlocks.DUST_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.DUST_BRICK_SLAB.get());
        this.dropSelf(ModBlocks.DUST_BRICK_WALL.get());
        this.dropSelf(ModBlocks.SMALL_DUST_BRICKS.get());
        this.dropSelf(ModBlocks.SMALL_DUST_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.SMALL_DUST_BRICK_SLAB.get());
        this.dropSelf(ModBlocks.SMALL_DUST_BRICK_WALL.get());

        this.dropSelf(ModBlocks.DUST.get());
        this.dropSelf(ModBlocks.DUST_BLOCK.get());

        this.dropSelf(ModBlocks.ALLOCITE_BLOCK.get());
        this.dropSelf(ModBlocks.ALLOCITE_PLATING.get());
        this.dropSelf(ModBlocks.GHOST_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public LootTable.Builder createSeedlessBushDrops(Block block, ItemLike item) {
        return createShearsDispatchTable(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))));
    }
}