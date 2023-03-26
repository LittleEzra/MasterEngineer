package com.littleezra.masterengineer.block;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ModMaterials {
    public static final Material FRAME = (new Material.Builder(MaterialColor.WOOD).replaceable().build());
    public static final Material ALLOCITE = (new Material.Builder(MaterialColor.COLOR_CYAN).build());
    public static final Material GHOST = (new Material.Builder(MaterialColor.COLOR_LIGHT_BLUE).nonSolid().build());
}
