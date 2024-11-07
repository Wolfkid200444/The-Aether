package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aetherfabric.pond.BlockStateExtension;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends BlockBehaviour.BlockStateBase implements BlockStateExtension {

    protected BlockStateMixin(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState neighborState, Direction dir) {
        return this.getBlock().hidesNeighborFace(level, pos, ((BlockState)(Object)this), neighborState, dir);
    }

    @Override
    public boolean supportsExternalFaceHiding() {
        return this.getBlock().supportsExternalFaceHiding((BlockState)(Object)this);
    }

    @Override
    public Float getExplosionResistance(BlockGetter level, BlockPos pos, Explosion explosion) {
        return this.getBlock().getExplosionResistance(((BlockState)(Object)this), level, pos, explosion);
    }

    @Override
    public Float getFriction(LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return this.getBlock().getFriction(((BlockState)(Object)this), level, pos, entity);
    }
}
