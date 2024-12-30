package com.aetherteam.aetherfabric.pond;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface EntityExtension {

    default boolean aetherFabric$shouldRiderSit() {
        return true;
    }

    default boolean aetherFabric$canRiderInteract() {
        return false;
    }

    default boolean aetherFabric$isInFluidType() {
        return throwUnimplementedException();
    }

    default void aetherFabric$sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
        throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}