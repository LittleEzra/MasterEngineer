package com.littleezra.masterengineer.entity.custom;

import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

public class Sombrock extends PathfinderMob implements GeoAnimatable {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean sighing;

    public boolean isSighing() {
        return sighing;
    }

    public void setSighing(boolean sighing) {
        this.sighing = sighing;
    }

    public Sombrock(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new Sombrock.SombrockMoveControl(this);
    }

    private final RawAnimation IDLE_ANIMATION = RawAnimation.begin().then("sombrock.animation.idle", Animation.LoopType.LOOP);
    private final RawAnimation SIGH_ANIMATION = RawAnimation.begin().then("sombrock.animation.sigh", Animation.LoopType.PLAY_ONCE);

    private PlayState predicate(AnimationState animationState){
        animationState.getController().setAnimation(IDLE_ANIMATION);
        return PlayState.CONTINUE;
    }

    private PlayState sighPredicate(AnimationState animationState){
        if (this.sighing && animationState.getController().getAnimationState() == AnimationController.State.STOPPED){
            animationState.getController().forceAnimationReset();
            animationState.getController().setAnimation(SIGH_ANIMATION);
        }
        if (!animationState.isCurrentAnimation(SIGH_ANIMATION)){
            this.sighing = false;
        }
        return PlayState.CONTINUE;
    }

    public static AttributeSupplier createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1D)
                .build();
    }

    public boolean onClimbable() {
        return false;
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
                float f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
            }
        }

        this.calculateEntityAnimation(this, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new Sombrock.SombrockRandomStrollGoal(this));
        this.goalSelector.addGoal(7, new Sombrock.SombrockSighGoal(this, 0.1F));
        this.goalSelector.addGoal(7, new Sombrock.SombrockLookGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller",
                0, this::predicate));
        controllerRegistrar.add(new AnimationController(this, "sighController",
                0, this::sighPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return this.tickCount;
    }

    static class SombrockMoveControl extends MoveControl {
        private final Sombrock sombrock;
        private int floatDuration;

        public SombrockMoveControl(Sombrock pSombrock) {
            super(pSombrock);
            this.sombrock = pSombrock;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.sombrock.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.sombrock.getX(), this.wantedY - this.sombrock.getY(), this.wantedZ - this.sombrock.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.sombrock.setDeltaMovement(this.sombrock.getDeltaMovement().add(vec3.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 pPos, int pLength) {
            AABB aabb = this.sombrock.getBoundingBox();

            for(int i = 1; i < pLength; ++i) {
                aabb = aabb.move(pPos);
                if (!this.sombrock.level.noCollision(this.sombrock, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class SombrockSighGoal extends Goal{

        private final Sombrock sombrock;
        private final float probability;

        public SombrockSighGoal(Sombrock sombrock, float probability){
            this.sombrock = sombrock;
            this.probability = probability;
        }

        @Override
        public boolean canUse() {
            if (sombrock.getRandom().nextFloat() >= 0.2F){
                return false;
            }
            return sombrock.getDeltaMovement().lengthSqr() == 0D;
        }

        @Override
        public void tick() {
            sombrock.setSighing(true);
        }
    }

    static class SombrockRandomStrollGoal extends Goal {
        private final Sombrock sombrock;

        private int moveDelay;

        public SombrockRandomStrollGoal(Sombrock pSombrock) {
            this.sombrock = pSombrock;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            MoveControl movecontrol = this.sombrock.getMoveControl();
            if (sombrock.isSighing() ||  moveDelay > 0 || sombrock.getRandom().nextFloat() >= 0.5F){
                return false;
            }
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.sombrock.getX();
                double d1 = movecontrol.getWantedY() - this.sombrock.getY();
                double d2 = movecontrol.getWantedZ() - this.sombrock.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void tick() {
            if (moveDelay > 0) --moveDelay;
        }

        public void start() {
            Vec3 pos = DefaultRandomPos.getPos(sombrock, 10, 7);
            if (pos != null){
                this.sombrock.getMoveControl().setWantedPosition(pos.x, pos.y + 0.5D, pos.z, 1.0D);
                moveDelay = 200;
            }
        }
    }

    static class SombrockLookGoal extends Goal {
        private final Sombrock sombrock;

        public SombrockLookGoal(Sombrock pSombrock) {
            this.sombrock = pSombrock;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            Vec3 deltaMovement = this.sombrock.getDeltaMovement();
            if (deltaMovement.lengthSqr() > 0) {
                this.sombrock.setYRot(-((float)Mth.atan2(deltaMovement.x, deltaMovement.z)) * (180F / (float)Math.PI));
                this.sombrock.yBodyRot = this.sombrock.getYRot();
            }
        }
    }
}
