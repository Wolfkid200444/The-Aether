package com.aetherteam.aether.fabric.pond;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;

public interface RegistryLookupExtension<R> {

    @Nullable
    default <T> T getData(DataMapType<R, T> type, ResourceKey<R> key) {
        return null;
    }
}
