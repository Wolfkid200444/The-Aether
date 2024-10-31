package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.pond.BlockExtension;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockExtension {

    @Override
    public boolean supportsExternalFaceHiding(BlockState state) {
        return ItemBlockRenderTypes.getRenderType(state, false).equals(RenderType.solid());
    }
}
