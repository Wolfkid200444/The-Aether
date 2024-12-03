package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockColors.class)
public interface BlockColorsAccessor {
//    @Accessor("blockColors")
//    Map<Block, BlockColor> aether$getBlockColors();
}
