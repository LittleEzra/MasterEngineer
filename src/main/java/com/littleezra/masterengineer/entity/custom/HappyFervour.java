package com.littleezra.masterengineer.entity.custom;

import com.littleezra.masterengineer.MasterEngineer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;

public class HappyFervour extends Summon implements GeoAnimatable {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public HappyFervour(EntityType<? extends Summon> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier createAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 10D)
                .build();
    }

    public Goal digGoal;
    public int digTimer = 0;

    public void setDig(Goal digGoal){
        this.removeCurrentDigGoal();
        this.digGoal = digGoal;
        this.digTimer = 300;
        this.goalSelector.addGoal(4, digGoal);

        this.goalSelector.getRunningGoals().forEach(g -> {
            MasterEngineer.printDebug(g.getGoal().getClass().getName());

        });
    }
    public void removeCurrentDigGoal(){
        this.goalSelector.removeGoal(digGoal);
        this.digGoal = null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.digTimer <= 0 && this.digGoal != null) {
            this.removeCurrentDigGoal();
        }

        if (this.digGoal != null && this.digTimer > 0) {
            this.digTimer--;
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(6, new Summon.FollowOwnerGoal(this, 1D, 8.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    private final RawAnimation IDLE_ANIMATION = RawAnimation.begin().then("fervour_sprite.animation.idle", Animation.LoopType.LOOP);
    private final RawAnimation WALK_ANIMATION = RawAnimation.begin().then("fervour_sprite.animation.move", Animation.LoopType.LOOP);

    private PlayState predicate(AnimationState animationState){
        if (animationState.isMoving()){
            animationState.getController().setAnimation(WALK_ANIMATION);
        } else{
            animationState.getController().setAnimation(IDLE_ANIMATION);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object o) {
        return this.tickCount;
    }

    @Override
    public Vector3f getSpawnColor() {
        // Sorrow Color: #70b1e7
        return MasterEngineer.getVector3Color("#3eed8c");
    }

    public static class DigGoal extends Goal {
        protected final HappyFervour happyFervour;
        protected BlockPos targetBlockPos;
        protected int breakProgress;
        protected int lastBreakProgress;
        protected Block targetBlock;

        public DigGoal(HappyFervour happyFervour, Block block) {
            this.happyFervour = happyFervour;
            this.targetBlock = block;
        }

        @Override
        public boolean canUse() {
            return canStart();
        }

        public boolean canStart() {
            targetBlockPos = null;
            breakProgress = 0;

            if (this.happyFervour.getOwner() != null) {
                for (BlockPos blockPos : BlockPos.spiralAround(this.happyFervour.blockPosition(), 8, Direction.EAST, Direction.SOUTH)) {
                    BlockState blockState = this.happyFervour.level.getBlockState(blockPos);
                    if (blockState.getBlock() == targetBlock) {
                        if (this.happyFervour.getNavigation().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1f)) {
                            targetBlockPos = blockPos;
                            return true;
                        }
                    }
                }

            }
            return false;
        }

        public void tick() {
            if (targetBlockPos == null) return;

            BlockState blockState = this.happyFervour.level.getBlockState(targetBlockPos);
            if (blockState.getBlock() != targetBlock) {
                this.canStart();
                return;
            }

            if (this.happyFervour.getRandom().nextInt(20) == 0) {
                if (!this.happyFervour.swinging) {
                    this.happyFervour.swing(this.happyFervour.getUsedItemHand());
                }
            }

            ++this.breakProgress;
            int i = (int)((float)this.breakProgress / 2400f);
            if (i != this.lastBreakProgress) {
                this.happyFervour.level.destroyBlockProgress(this.happyFervour.getId(), this.targetBlockPos, i);
                this.lastBreakProgress = i;
            }

            if (this.breakProgress == 240) {
                this.happyFervour.level.removeBlock(this.targetBlockPos, false);
                this.happyFervour.level.levelEvent(2001, this.targetBlockPos, Block.getId(this.happyFervour.level.getBlockState(this.targetBlockPos)));
            }

        }

        @Override
        public boolean canContinueToUse() {
            return targetBlockPos != null && this.happyFervour.getOwner() != null;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public Block getBlock(){
            return targetBlock;
        }

        @Override
        public void start() {
            this.breakProgress = 0;
        }
    }
}
