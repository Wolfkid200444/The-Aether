package com.aetherteam.aether.fabric;

import net.minecraft.client.Minecraft;

public interface ScreenExtension {

    default Minecraft getMinecraft() {
        return throwUnimplementedException();
    }

    default int getGuiLeft() {
        return 0;
    }

    default int getGuiTop() {
        return 0;
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
