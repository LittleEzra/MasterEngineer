package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.entity.custom.HappyFervour;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HappyFervourRenderer extends GeoEntityRenderer<HappyFervour> {
    public HappyFervourRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HappyFervourModel());
    }
}
