package com.aetherteam.aether.client.renderer.block;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.minecraft.client.resources.model.BakedModel;

public class FastModel extends ForwardingBakedModel {
    public FastModel(BakedModel originalModel) {
        this.wrapped = originalModel;
    }

//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
//        return super.getQuads(state, side, rand, extraData, null);
//    }
//
//    @Override
//    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
//        return ChunkRenderTypeSet.of(Minecraft.useFancyGraphics() ? RenderType.cutoutMipped() : RenderType.solid());
//    }
}
