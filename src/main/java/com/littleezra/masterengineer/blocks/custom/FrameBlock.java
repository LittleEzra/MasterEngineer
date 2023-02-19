package com.littleezra.masterengineer.blocks.custom;

import com.littleezra.masterengineer.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.phys.BlockHitResult;

public class FrameBlock extends Block {
    public FrameBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND){
            if (pPlayer.getItemInHand(pHand).isEmpty()){
                pLevel.destroyBlock(pPos, true);
                return InteractionResult.SUCCESS;
            }
            if (pPlayer.getItemInHand(pHand).getItem() instanceof BlockItem blockItem && this.blockCanReplace(blockItem.getBlock().defaultBlockState())){
                this.dropResources(pState, pLevel, pPos, pHit.getDirection());
            }
        }
        return InteractionResult.PASS;
    }

    public void dropResources(BlockState pState, Level pLevel, BlockPos pPos, Direction pDirection){
        getDrops(pState, (ServerLevel)pLevel, pPos, null).forEach((itemStack) -> {
            popResourceFromFace(pLevel, pPos, pDirection, itemStack);
        });
        pState.spawnAfterBreak((ServerLevel)pLevel, pPos, ItemStack.EMPTY, true);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        boolean flag = false;
        if (pUseContext.getItemInHand().getItem() instanceof BlockItem blockItem){
            flag = blockCanReplace(blockItem.getBlock().defaultBlockState());
        }
        return flag && (pUseContext.getItemInHand().isEmpty() || pUseContext.getItemInHand().getItem() != this.asItem());
    }

    public boolean blockCanReplace(BlockState pState){
        return !pState.is(ModTags.Blocks.BUILDING_FRAMES);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
