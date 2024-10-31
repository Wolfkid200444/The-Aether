package com.aetherteam.aether.fabric;

import java.util.Map;

public interface LanguageExtension {

    default void setLanguageData(Map<String, String> data) {
        throwUnimplementedException();
    }

    default Map<String, String> getLanguageData() {
        return throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
