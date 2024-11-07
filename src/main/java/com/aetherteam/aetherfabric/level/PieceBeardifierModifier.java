package com.aetherteam.aetherfabric.level;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

public interface PieceBeardifierModifier {
    BoundingBox getBeardifierBox();

    TerrainAdjustment getTerrainAdjustment();

    int getGroundLevelDelta();
}
