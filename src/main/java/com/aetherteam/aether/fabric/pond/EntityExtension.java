package com.aetherteam.aether.fabric.pond;

import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface EntityExtension {

    default boolean shouldRiderSit() {
        return true;
    }

    default boolean canRiderInteract() {
        return false;
    }

    default boolean isInFluidType() {
        return throwUnimplementedException();
    }

    @Nullable
    default Collection<ItemEntity> getCapturedDrops() {
        return throwUnimplementedException();
    }

    default void addCapturedDrops(ItemEntity... captureDrops) {
        addCapturedDrops(List.of(captureDrops));
    }

    default boolean addCapturedDrops(Collection<ItemEntity> captureDrops) {
        return throwUnimplementedException();
    }

    default void capturingDrops(boolean value) {
        throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
