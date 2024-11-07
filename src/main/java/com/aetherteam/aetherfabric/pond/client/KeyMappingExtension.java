package com.aetherteam.aetherfabric.pond.client;

import com.mojang.blaze3d.platform.InputConstants;

public interface KeyMappingExtension {

    default InputConstants.Key getKey() {
        return throwUnimplementedException();
    }

    /**
     * {@return true if the key conflict context and modifier are active and the keyCode matches this binding, false otherwise}
     */
    default boolean isActiveAndMatches(InputConstants.Key keyCode) {
        return keyCode != InputConstants.UNKNOWN && keyCode.equals(getKey());
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
