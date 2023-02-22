package com.littleezra.masterengineer.particle.custom;

import com.littleezra.masterengineer.particle.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class ColorParticleOptions implements ParticleOptions{
    public static final ParticleOptions.Deserializer<ColorParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<ColorParticleOptions>() {
        public ColorParticleOptions fromCommand(ParticleType<ColorParticleOptions> type, StringReader pReader) throws CommandSyntaxException {
            Vector3f vector3f = ColorParticleOptions.readVector3f(pReader);
            return new ColorParticleOptions(type, vector3f);
        }

        public ColorParticleOptions fromNetwork(ParticleType<ColorParticleOptions> type, FriendlyByteBuf pBuffer) {
            Vector3f vector3f = ColorParticleOptions.readVector3f(pBuffer);
            return new ColorParticleOptions(type, vector3f);
        }
    };

    private final Vector3f color;
    private final ParticleType<ColorParticleOptions> type;

    public ColorParticleOptions(ParticleType<ColorParticleOptions> type, Vector3f color) {
        this.type = type;
        this.color = color;
    }

    public static Codec<ColorParticleOptions> codec(ParticleType<ColorParticleOptions> pType) {
        return ExtraCodecs.VECTOR3F.xmap((vector3f) -> {
            return new ColorParticleOptions(pType, vector3f);
        }, ColorParticleOptions::getColor);
    }

    @Override
    public ParticleType<?> getType() {
        return this.type;
    }

    public static Vector3f readVector3f(StringReader pStringInput) throws CommandSyntaxException {
        pStringInput.expect(' ');
        float f = pStringInput.readFloat();
        pStringInput.expect(' ');
        float f1 = pStringInput.readFloat();
        pStringInput.expect(' ');
        float f2 = pStringInput.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf pBuffer) {
        return new Vector3f(pBuffer.readFloat(), pBuffer.readFloat(), pBuffer.readFloat());
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color.x());
        pBuffer.writeFloat(this.color.y());
        pBuffer.writeFloat(this.color.z());
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.getColor().x, this.getColor().y, this.getColor().z);
    }

    public Vector3f getColor() {
        return this.color;
    }
}
