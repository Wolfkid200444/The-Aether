package net.neoforged.neoforge.pond;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.ApiStatus;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;

@ApiStatus.Internal
public interface FullDataMapAccess<T> {

    default void setDataMaps(Consumer<Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>>> builder) {
        Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps = new IdentityHashMap<>();

        builder.accept(dataMaps);

        this.setDataMaps(dataMaps);
    }

    void setDataMaps(Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps);

    Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> getDataMaps();
}
