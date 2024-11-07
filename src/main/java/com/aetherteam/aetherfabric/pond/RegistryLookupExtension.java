package com.aetherteam.aetherfabric.pond;

import net.minecraft.resources.ResourceKey;
import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;

public interface RegistryLookupExtension<R> {

    @Nullable
    default <T> T getData(DataMapType<R, T> type, ResourceKey<R> key) {
        return null;
    }
}