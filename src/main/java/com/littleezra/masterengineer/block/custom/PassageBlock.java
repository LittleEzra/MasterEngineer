package com.littleezra.masterengineer.block.custom;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.block.entity.PassageBlockEntity;
import com.littleezra.masterengineer.capabilities.PassageDataProvider;
import com.littleezra.masterengineer.networking.ModMessages;
import com.littleezra.masterengineer.networking.packets.SyncPassageDataS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class PassageBlock extends BaseEntityBlock {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public PassageBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, Boolean.valueOf(false)));
    }

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 10, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ACTIVATED);
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PassageBlockEntity){
                ((PassageBlockEntity) blockEntity).drops();
            }
            if (!pLevel.isClientSide() && pState.getValue(ACTIVATED)){
                pLevel.getCapability(PassageDataProvider.PASSAGE_DATA).ifPresent(passageData -> {
                    passageData.removePassage(pPos);
                    ModMessages.sendToAllClients(new SyncPassageDataS2CPacket(passageData.getPassages()));
                });
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()){
            if (pState.getValue(ACTIVATED)){
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof PassageBlockEntity){
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (PassageBlockEntity)blockEntity, pPos);
                } else{
                    throw new IllegalStateException("Container Provider is missing");
                }
            }
            if (pHand == InteractionHand.MAIN_HAND && pPlayer.getItemInHand(pHand).is(Items.ENDER_PEARL)){
                pPlayer.getItemInHand(pHand).shrink(1);

                BlockState state = pState.setValue(ACTIVATED, Boolean.valueOf(true));
                pLevel.setBlock(pPos, state, 2);

                pLevel.getCapability(PassageDataProvider.PASSAGE_DATA).ifPresent(passageData -> {
                    passageData.addPassage(pPos);
                    ModMessages.sendToAllClients(new SyncPassageDataS2CPacket(passageData.getPassages()));
                });
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PassageBlockEntity(pPos, pState);
    }
}
