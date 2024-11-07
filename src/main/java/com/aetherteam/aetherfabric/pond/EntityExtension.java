package com.aetherteam.aetherfabric.pond;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

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

    default void sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
        throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
