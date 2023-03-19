package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HappyFervourModel extends GeoModel<HappyFervour> {
    @Override
    public ResourceLocation getModelResource(HappyFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "geo/fervour_sprite.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HappyFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "textures/entity/fervour_sprite_happy.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HappyFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "animations/fervour_sprite.animation.json");
    }
}
