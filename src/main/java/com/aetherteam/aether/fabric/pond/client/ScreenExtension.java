package com.aetherteam.aether.fabric.pond.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public interface ScreenExtension {

    default Minecraft getMinecraft() {
        return throwUnimplementedException();
    }

    @Nullable
    default Slot getSlotUnderMouse() {
        return null;
    }

    default int getGuiLeft() {
        return 0;
    }

    default int getGuiTop() {
        return 0;
    }

    default int getXSize() {
        return 0;
    }

    default int getYSize() {
        return 0;
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
