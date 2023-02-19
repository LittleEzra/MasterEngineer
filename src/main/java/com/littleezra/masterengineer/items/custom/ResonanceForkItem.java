package com.littleezra.masterengineer.items.custom;

import com.littleezra.masterengineer.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ResonanceForkItem extends Item {
    public ResonanceForkItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pAttacker.level.playSound(null,
                pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(),
                ModSounds.RESONANCE_FORK_USE.get(), SoundSource.NEUTRAL, 1.5f, 0.4F / (pAttacker.getRandom().nextFloat() * 0.4F + 0.8F));
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        pLevel.playSound(null,
                pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                ModSounds.RESONANCE_FORK_USE.get(), SoundSource.NEUTRAL, 1.5f, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        pPlayer.getCooldowns().addCooldown(this, 120);

        List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, pPlayer.getBoundingBox().inflate(6));
        if (entities.size() > 0){
            for (LivingEntity entity : entities){
                if (entity != pPlayer || !entity.getType().getCategory().isFriendly()){
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                }
            }
        }
        if (itemstack.isDamageableItem() && !pPlayer.getAbilities().instabuild){
            itemstack.hurtAndBreak(1, pPlayer, (p_150686_) -> {
                p_150686_.broadcastBreakEvent(pUsedHand);
            });
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide);
    }
}
