package com.aetherteam.aetherfabric.pond;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public interface IHolderLookupProviderExtension {
    private HolderLookup.Provider self() {
        return (HolderLookup.Provider) this;
    }

    /**
     * Shortcut method to get a holder from a ResourceKey.
     *
     * @throws IllegalStateException if the registry or key is not found.
     */
    default <T> Holder<T> aetherFabric$holderOrThrow(ResourceKey<T> key) {
        return this.self().lookupOrThrow(key.registryKey()).getOrThrow(key);
    }

    /**
     * Shortcut method to get an optional holder from a ResourceKey.
     */
    default <T> Optional<Holder.Reference<T>> aetherFabric$holder(ResourceKey<T> key) {
        Optional<? extends HolderLookup.RegistryLookup<T>> registry = this.self().lookup(key.registryKey());
        return registry.flatMap(tRegistryLookup -> tRegistryLookup.get(key));
    }
}