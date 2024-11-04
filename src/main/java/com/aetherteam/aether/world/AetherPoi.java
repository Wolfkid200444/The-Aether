package com.aetherteam.aether.world;

import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class AetherPoi {
    public static final PoiType AETHER_PORTAL = PointOfInterestHelper.register(ResourceLocation.fromNamespaceAndPath("aether","aether_portal"), 0, 1, getBlockStates(AetherBlocks.AETHER_PORTAL.get()));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

    public static void init(){}
}
