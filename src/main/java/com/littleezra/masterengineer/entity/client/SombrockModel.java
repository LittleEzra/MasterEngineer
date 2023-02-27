package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.Sombrock;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SombrockModel extends GeoModel<Sombrock> {
    @Override
    public ResourceLocation getModelResource(Sombrock animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "geo/sombrock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Sombrock animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "textures/entity/sombrock.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Sombrock animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "animations/sombrock.animation.json");
    }
}
