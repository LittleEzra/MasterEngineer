package com.littleezra.masterengineer.items.custom;

import com.littleezra.masterengineer.MasterEngineer;
import com.littleezra.masterengineer.entity.custom.MarkArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.awt.*;

public class MarkArrowItem extends ArrowItem implements DyeableLeatherItem {
    public MarkArrowItem(Properties pProperties) {
        super(pProperties);
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        Vector3f color = new Vector3f(1, 1, 1);
        Color decoded = new Color(getColor(pStack));

        color.x = decoded.getRed() / 255f;
        color.y = decoded.getGreen() / 255f;
        color.z = decoded.getBlue() / 255f;
        return new MarkArrow(pLevel, pShooter, color);
    }
}
