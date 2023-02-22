package com.littleezra.masterengineer.entity.custom;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.capabilities.MarkHandlerProvider;
import com.littleezra.masterengineer.entity.ModEntityTypes;
import com.littleezra.masterengineer.items.ModItems;
import com.littleezra.masterengineer.items.custom.MarkArrowItem;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
        if (this.color == null) this.color = new Vector3f(1f, 1f, 1f);
    }

    public MarkArrow(Level pLevel, LivingEntity pShooter, Vector3f color) {
        super(ModEntityTypes.MARK_ARROW.get(), pShooter, pLevel);
        this.setColor(color);
    }

    private Vector3f color;

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("red", this.getColor().x);
        pCompound.putFloat("green", this.getColor().y);
        pCompound.putFloat("blue", this.getColor().z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setColor(new Vector3f(
                pCompound.getFloat("red"),
                pCompound.getFloat("green"),
                pCompound.getFloat("blue")));
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide && !this.inGround) {
            ((ServerLevel)this.level).sendParticles(new DustParticleOptions(this.getColor(), 1), this.getX(), this.getY(), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (pResult.getEntity() instanceof LivingEntity){
            pResult.getEntity().getCapability(MarkHandlerProvider.MARK_HANDLER).ifPresent(markHandler -> {
                markHandler.setColor(this.getColor());
                markHandler.setTickDuration(200);
            });
        }
    }

    protected ItemStack getPickupItem() {
        ItemStack itemStack = new ItemStack(ModItems.MARK_ARROW.get());
        ((MarkArrowItem)ModItems.MARK_ARROW.get()).setColor(itemStack, MasterEngineer.getIntColor(this.getColor()));

        return new ItemStack(ModItems.MARK_ARROW.get());
    }

    public Vector3f getColor() {
        return this.color;
    }
    public void setColor(Vector3f color) {
        this.color = color;
    }
}
