package com.littleezra.masterengineer.entity.client;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.MarkArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MarkArrowRenderer extends ArrowRenderer<MarkArrow> {
    public MarkArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(MarkArrow pEntity) {
        return new ResourceLocation(MasterEngineer.MOD_ID, "textures/entity/projectiles/mark_arrow.png");
    }


}
