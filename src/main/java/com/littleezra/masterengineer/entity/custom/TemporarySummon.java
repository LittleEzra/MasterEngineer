package com.littleezra.masterengineer.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.level.Level;

public abstract class TemporarySummon extends Summon{
    private float lifetime;

    protected TemporarySummon(EntityType<? extends TemporarySummon> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
