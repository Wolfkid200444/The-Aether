package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aetherfabric.pond.BlockEntityExtension;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements BlockEntityExtension {
}
