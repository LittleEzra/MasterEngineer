package com.littleezra.masterengineer.blocks.custom;

import com.littleezra.masterengineer.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DustBrickBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public DustBrickBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return pState.is(ModTags.Blocks.DUST_COLLECTING_BRICKS) && pState.getValue(AGE) < 7;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.is(ModTags.Blocks.DUST_COLLECTING_BRICKS)) return;

        if (!DustLayerBlock.canBeDust(pLevel.getBlockState(pPos.above()), pPos.above(), pLevel)){
            return;
        }
        int age = pState.getValue(AGE);
        if (age < 7){
            pLevel.setBlock(pPos, this.defaultBlockState().setValue(AGE, Integer.valueOf(age + 1)), 2);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
