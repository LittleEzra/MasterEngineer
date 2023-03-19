package com.littleezra.masterengineer.items.custom;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import com.littleezra.masterengineer.entity.custom.Summon;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Function;

public class HappyFervourSummonerStaffItem extends SummonerStaffItem {
    public HappyFervourSummonerStaffItem(Item.Properties pProperties, Function<Player, Summon> summonSupplier, int summonCost) {
        super(pProperties, summonSupplier, summonCost);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (player != null && !level.isClientSide && player.isShiftKeyDown()){
            BlockState state = level.getBlockState(pContext.getClickedPos());
            float destroySpeed = state.getDestroySpeed(level, pContext.getClickedPos());
            if (destroySpeed >= 0f && destroySpeed <= 5.0F){

                List<HappyFervour> happyFervours = level.getEntitiesOfClass(HappyFervour.class, player.getBoundingBox().inflate(16f),
                        happyFervour -> happyFervour.getOwner() != null && happyFervour.getOwner() == player);
                happyFervours.forEach(happyFervour -> {
                    Goal goal = new HappyFervour.DigGoal(happyFervour, state.getBlock());
                    happyFervour.setDig(goal);
                });

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        } else{
            return super.useOn(pContext);
        }
    }
}
