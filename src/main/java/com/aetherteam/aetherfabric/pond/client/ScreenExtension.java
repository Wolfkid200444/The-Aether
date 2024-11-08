package com.aetherteam.aetherfabric.pond.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public interface ScreenExtension {

    default Minecraft aetherFabric$getMinecraft() {
        return throwUnimplementedException();
    }

    @Nullable
    default Slot aetherFabric$getSlotUnderMouse() {
        return null;
    }

    default int aetherFabric$getGuiLeft() {
        return 0;
    }

    default int aetherFabric$getGuiTop() {
        return 0;
    }

    default int aetherFabric$getXSize() {
        return 0;
    }

    default int aetherFabric$getYSize() {
        return 0;
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }

    default AbstractWidget onScreensWidgetAdd(AbstractWidget abstractWidget) {
        return abstractWidget;
    }
}
