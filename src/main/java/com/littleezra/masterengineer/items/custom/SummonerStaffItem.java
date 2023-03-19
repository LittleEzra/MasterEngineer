package com.littleezra.masterengineer.items.custom;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.Summon;
import com.littleezra.masterengineer.sound.ModSounds;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class SummonerStaffItem extends Item {
    public final int SUMMON_COST;

    private final Function<Player, Summon> summonSupplier;

    public SummonerStaffItem(Properties pProperties, Function<Player, Summon> summonSupplier, int summonCost) {
        super(pProperties);
        this.summonSupplier = summonSupplier;
        this.SUMMON_COST = summonCost;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide || pContext.getHand() != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        Player player = pContext.getPlayer();
        InteractionHand hand = pContext.getHand();
        ItemStack itemStack = player.getItemInHand(hand);
        BlockPos pos = pContext.getClickedPos();
        BlockState above = player.getLevel().getBlockState(pos.above());
        if ((above.isAir() || !above.getMaterial().isSolid()) && itemStack.getItem() instanceof SummonerStaffItem){
            if (player.experienceLevel >= SUMMON_COST){
                player.giveExperienceLevels(-SUMMON_COST);

                Summon entity = this.summonSupplier.apply(player);
                if (entity != null){
                    spawnSummon(entity, player, pos);
                }

                player.getLevel().playSound(null,
                        player.getX(), player.getY(), player.getZ(),
                        ModSounds.SUMMON_STAFF_USE.get(), SoundSource.NEUTRAL, 1.5f,
                        0.4F / (player.getLevel().getRandom().nextFloat() * 0.4F + 0.8F));

                return InteractionResult.SUCCESS;
            } else{
                player.getLevel().playSound(null,
                        player.getX(), player.getY(), player.getZ(),
                        ModSounds.SUMMON_STAFF_FAIL.get(), SoundSource.NEUTRAL, 1.5f,
                        0.4F / (player.getLevel().getRandom().nextFloat() * 0.4F + 0.8F));
            }

        }
        return InteractionResult.PASS;
    }

    private double getCircleOffset(RandomSource random, double range){
        double x = (random.nextDouble() - 0.5D) * range;
        return (x < 0 ? -1 : 1) * x * x;
    }
    private double getOffset(RandomSource random, double range){
        return (random.nextDouble() - 0.5D) * range;
    }

    private final int PARTICLE_COUNT = 6;

    private void spawnSummon(Summon summon, Player player, BlockPos pPos){
        Vec3 pos = new Vec3(pPos.getX() + 0.5D + getCircleOffset(player.getRandom(), 1D),
                pPos.getY() + 1D,
                pPos.getZ() + 0.5D + getCircleOffset(player.getRandom(), 1D));

        summon.moveTo(pos);
        summon.setOwnerUUID(player.getUUID());

        player.getLevel().addFreshEntity(summon);
        for (int i = 0; i < PARTICLE_COUNT; i++){
            ((ServerLevel) player.getLevel()).sendParticles(new DustParticleOptions(summon.getSpawnColor(), 1.5F),
                    pos.x + getOffset(player.getRandom(), 0.4D),
                    pos.y + getOffset(player.getRandom(), 0.4D),
                    pos.z + getOffset(player.getRandom(), 0.4D),
                    1, 0D, 0d, 0d, 0d);
        }


        for(ServerPlayer serverplayer : player.getLevel().getEntitiesOfClass(ServerPlayer.class, summon.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, summon);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(SUMMON_COST == 1 ?
                        "tooltip.masterengineer.summon_cost.singular" : "tooltip.masterengineer.summon_cost.plural", SUMMON_COST)
                .withStyle(Style.EMPTY.withColor(MasterEngineer.getIntColor("#70b1e7"))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
