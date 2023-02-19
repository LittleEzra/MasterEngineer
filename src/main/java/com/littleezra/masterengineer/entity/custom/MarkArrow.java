package com.littleezra.masterengineer.entity.custom;

import com.littleezra.masterengineer.capabilities.MarkHandlerProvider;
import com.littleezra.masterengineer.entity.ModEntityTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.joml.Vector3f;

public class MarkArrow extends AbstractArrow {
    public MarkArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MarkArrow(Level pLevel, LivingEntity pShooter, Vector3f color) {
        super(ModEntityTypes.MARK_ARROW.get(), pShooter, pLevel);
        this.color = color;
    }

    private Vector3f color = new Vector3f(1, 1, 1);

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("red", color.x);
        pCompound.putFloat("green", color.y);
        pCompound.putFloat("blue", color.z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        Vector3f vector3f = new Vector3f(
                pCompound.getFloat("red"),
                pCompound.getFloat("green"),
                pCompound.getFloat("blue"));
        this.color = vector3f;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide && !this.inGround) {
            this.level.addParticle(new DustParticleOptions(color, 1), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (pResult.getEntity() instanceof LivingEntity){
            pResult.getEntity().getCapability(MarkHandlerProvider.MARK_HANDLER).ifPresent(markHandler -> {
                markHandler.setColor(this.color);
                markHandler.setTickDuration(200);
            });
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    public Vector3f getColor() {
        return this.color;
    }
}
