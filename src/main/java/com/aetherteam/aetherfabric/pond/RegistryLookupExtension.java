package com.aetherteam.aetherfabric.pond;

import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

public interface RegistryLookupExtension<R> {

    @Nullable
    default <T> T aetherFabric$getData(DataMapType<R, T> type, ResourceKey<R> key) {
        return null;
    }
}
