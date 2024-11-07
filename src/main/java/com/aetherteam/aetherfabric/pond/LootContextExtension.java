package com.aetherteam.aetherfabric.pond;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface LootContextExtension {

    @Nullable
    ResourceLocation getTableId();

    void setTableId(ResourceLocation tableId);
}
