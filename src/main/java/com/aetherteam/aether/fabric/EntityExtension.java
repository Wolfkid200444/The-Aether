package com.aetherteam.aether.fabric;

public interface EntityExtension {
    default boolean isInFluidType() {
        throw new IllegalStateException("INJECTED INTERFACE METHOD NOT IMPLEMENTED!");
    }
}
