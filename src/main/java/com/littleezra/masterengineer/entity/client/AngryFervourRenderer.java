package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.entity.custom.AngryFervour;
import com.littleezra.masterengineer.entity.custom.HappyFervour;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AngryFervourRenderer extends GeoEntityRenderer<AngryFervour> {
    public AngryFervourRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AngryFervourModel());
    }
}
