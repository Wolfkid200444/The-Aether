package net.neoforged.neoforge.mixin;

import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.extensions.IMappedRegistryExtension;
import net.neoforged.neoforge.pond.FullDataMapAccess;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> implements FullDataMapAccess<T>, IMappedRegistryExtension<T> {

    @Unique
    private final Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps = new IdentityHashMap<>();

    @Override
    public void setDataMaps(Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps) {
        this.dataMaps.clear();
        this.dataMaps.putAll(dataMaps);
    }

    @Override
    public Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> getDataMaps() {
        return Collections.unmodifiableMap(this.dataMaps);
    }
}
