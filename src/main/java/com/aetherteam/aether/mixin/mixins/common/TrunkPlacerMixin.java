package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.block.natural.AetherGrassBlock;
import com.aetherteam.aether.block.natural.EnchantedAetherGrassBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public abstract class TrunkPlacerMixin {
    @WrapOperation(method = "setDirtAt", at = @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"))
    private static <T, U> void aetherFabric$adjustSetCall(BiConsumer<T, U> instance, T t, U u, Operation<Void> original, @Local(argsOnly = true) LevelSimulatedReader level, @Local(argsOnly = true) BiConsumer<BlockPos, BlockState> blockSetter, @Local(argsOnly = true) RandomSource random, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) TreeConfiguration config) {
        var state = ((LevelReader) level).getBlockState(pos);
        if (state.getBlock() instanceof EnchantedAetherGrassBlock grassBlock && grassBlock.onTreeGrow(state, ((LevelReader) level), blockSetter, random, pos, config)) {
            return;
        }

        if (state.getBlock() instanceof AetherGrassBlock grassBlock && grassBlock.onTreeGrow(state, ((LevelReader) level), blockSetter, random, pos, config)) {
            return;
        }

        original.call(instance, t, u);
    }
}
