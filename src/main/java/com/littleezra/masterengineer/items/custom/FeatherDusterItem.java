package com.littleezra.masterengineer.items.custom;

import com.littleezra.masterengineer.blocks.ModBlocks;
import com.littleezra.masterengineer.blocks.custom.DustBrickBlock;
import com.littleezra.masterengineer.blocks.custom.DustLayerBlock;
import com.littleezra.masterengineer.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public class FeatherDusterItem extends Item {
    public FeatherDusterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();

        if (!level.isClientSide && pContext.getClickedFace() == Direction.UP
                && level.getBlockState(pos).getBlock() instanceof DustBrickBlock){
            if (level.getBlockState(pos).getValue(DustBrickBlock.AGE) < 7){
                return InteractionResult.PASS;
            }
            if (DustLayerBlock.canBeDust(level.getBlockState(pos.above()), pos.above(), level)){
                level.setBlock(pos, level.getBlockState(pos).setValue(DustBrickBlock.AGE, Integer.valueOf(0)), 11);
                level.setBlock(pos.above(), ModBlocks.DUST.get().defaultBlockState(), 11);
                for (int i = 0; i < 10; i++){
                    ((ServerLevel) level).sendParticles(new DustParticleOptions(new Vector3f(0.35F, 0.34F, 0.39F), 1F),
                            pos.above().getX() + 0.5D + getRandomOffset(0.5D, level.getRandom()),
                            pos.above().getY() + 0.2D,
                            pos.above().getZ() + 0.5D + getRandomOffset(0.5D, level.getRandom()), 1, 0d, 0d, 0d, 0d);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private double getRandomOffset(double offset, RandomSource randomSource){
        return (randomSource.nextDouble() - 0.5D) * offset;
    }
}
