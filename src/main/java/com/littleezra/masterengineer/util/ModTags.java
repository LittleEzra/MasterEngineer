package com.littleezra.masterengineer.util;

import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> BUILDING_FRAMES = tag("building_frames");
        public static final TagKey<Block> DUST_COLLECTING_BRICKS = tag("dust_collecting_bricks");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MasterEngineer.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}
