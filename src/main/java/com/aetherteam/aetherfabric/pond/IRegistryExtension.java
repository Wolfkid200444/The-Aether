package com.aetherteam.aetherfabric.pond;

import net.minecraft.resources.ResourceKey;
import com.aetherteam.aetherfabric.pond.FullDataMapAccess;
import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IRegistryExtension<T> {

    @Nullable
    default <A> A getData(DataMapType<T, A> type, ResourceKey<T> key) {
        final var innerMap = ((FullDataMapAccess<T>) (this)).getDataMaps().get(type);
        return innerMap == null ? null : (A) innerMap.get(key);
    }

    default <A> Map<ResourceKey<T>, A> getDataMap(DataMapType<T, A> type) {
        return (Map<ResourceKey<T>, A>) ((FullDataMapAccess<T>) (this)).getDataMaps().getOrDefault(type, Map.of());
    }
}