package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.fabric.pond.BlockExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockExtension {
    @WrapOperation(method = "shouldRenderFace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;skipRendering(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z"))
    private static boolean aetherFabric$supportFaceHiding(BlockState instance, BlockState state, Direction direction, Operation<Boolean> original, @Local(argsOnly = true) BlockGetter level, @Local(argsOnly = true) Direction face, @Local(argsOnly = true, ordinal = 1) BlockPos pos) {
        return original.call(instance, state, direction) || (state.hidesNeighborFace(level, pos, instance, face) && instance.supportsExternalFaceHiding());
    }
}
