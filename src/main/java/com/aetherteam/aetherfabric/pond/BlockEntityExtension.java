package com.aetherteam.aetherfabric.pond;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;

public interface BlockEntityExtension {
    default boolean aetherFabric$handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        return false;
    }

    default boolean aetherFabric$onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider lookupProvider) {
        return false;
    }
}
