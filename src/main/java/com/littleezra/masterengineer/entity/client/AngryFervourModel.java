package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.AngryFervour;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AngryFervourModel extends GeoModel<AngryFervour> {
    @Override
    public ResourceLocation getModelResource(AngryFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "geo/fervour_sprite.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AngryFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "textures/entity/fervour_sprite_angry.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AngryFervour animatable) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "animations/fervour_sprite.animation.json");
    }
}
