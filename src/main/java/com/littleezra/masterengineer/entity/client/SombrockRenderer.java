package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.entity.custom.Sombrock;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SombrockRenderer extends GeoEntityRenderer<Sombrock> {
    public SombrockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SombrockModel());
    }
}
