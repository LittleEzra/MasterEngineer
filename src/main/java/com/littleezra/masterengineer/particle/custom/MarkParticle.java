package com.littleezra.masterengineer.particle.custom;

import com.littleezra.masterengineer.particle.client.ModParticleRenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MarkParticle extends TextureSheetParticle {
    static final RandomSource RANDOM = RandomSource.create();
    private final SpriteSet sprites;

    protected MarkParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet sprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.sprites = sprites;
        this.quadSize *= 3;
    }

    public ParticleRenderType getRenderType() {
        return ModParticleRenderTypes.PARTICLE_SHEET_ALWAYS_VISIBLE;
    }

    public int getLightColor(float pPartialTick) {
        return 240;
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<ColorParticleOptions> {
        private final double SPEED_FACTOR = 0.25D;
        private final SpriteSet sprite;

        // Ill probably have to make a custom data thingy, like BlockOptions, but it takes in a RuneItem as input.

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(ColorParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            MarkParticle MarkParticle = new MarkParticle(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D, this.sprite);
            MarkParticle.setParticleSpeed(pXSpeed * 0.25D, pYSpeed * 0.25D, pZSpeed * 0.25D);
            MarkParticle.setLifetime(pLevel.random.nextInt(2) + 2);
            MarkParticle.pickSprite(this.sprite);

            MarkParticle.setColor(
                    pType.getColor().x,
                    pType.getColor().y,
                    pType.getColor().z);

            return MarkParticle;
        }
    }
}
