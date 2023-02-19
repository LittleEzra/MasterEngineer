package com.littleezra.masterengineer.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

public class DustBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public DustBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.isClientSide){
            level.addParticle(new DustParticleOptions(new Vector3f(0.35F, 0.34F, 0.39F), 1F),
                    entity.position().x + getCircleOffset(level.getRandom(), 1D),
                    entity.position().y + 0.15D,
                    entity.position().z + getCircleOffset(level.getRandom(), 1D),
                    0D, 0D, 0D);
        }
        return true;
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        for (int i = 0; i < numberOfParticles; i++){
            level.sendParticles(new DustParticleOptions(new Vector3f(0.35F, 0.34F, 0.39F), 1F),
                    entity.position().x + getCircleOffset(level.getRandom(), 1.5D),
                    entity.position().y + 0.15D,
                    entity.position().z + getCircleOffset(level.getRandom(), 1.5D), 1, 0D, 0D, 0D, 2D);
        }
        return true;
    }

    private double getCircleOffset(RandomSource random, double range){
        double x = (random.nextDouble() - 0.5D) * range;
        return (x < 0 ? -1 : 1) * x * x;
    }
    private double getOffset(RandomSource random, double range){
        return (random.nextDouble() - 0.5D) * range;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.block();
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }

    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0.2F;
    }
}
