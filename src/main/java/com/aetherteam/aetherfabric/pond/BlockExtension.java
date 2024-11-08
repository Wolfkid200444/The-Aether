package com.aetherteam.aetherfabric.pond;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface BlockExtension {

    default boolean aetherFabric$supportsExternalFaceHiding(BlockState state) {
        return true;
    }

    default boolean aetherFabric$hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return false;
    }

    @Nullable
    default Float aetherFabric$getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return null;
    }

    @Nullable
    default Float aetherFabric$getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return null;
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
