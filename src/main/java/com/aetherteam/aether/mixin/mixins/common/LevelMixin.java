package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aetherfabric.events.BlockEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Inject(method = "updateNeighborsAt", at = @At("HEAD"))
    private void aetherFabric$runUpdateEvent(BlockPos pos, Block block, CallbackInfo ci){
        var level = (Level) (Object) this;

        var isCancelled = new MutableBoolean(false);

        BlockEvents.NEIGHBOR_UPDATE.invoker().onNeighborUpdate(level, pos, level.getBlockState(pos), EnumSet.allOf(Direction.class), false, isCancelled);
    }
}
