package net.neoforged.neoforge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.network.payload.RegistryDataMapSyncPayload;
import net.neoforged.neoforge.registries.DataMapLoader;

public class NeoForge implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
            .registerReloadListener(
                DataMapLoader.ID,
                provider -> new DataMapLoader((RegistryAccess) provider)
            );

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            throw new IllegalStateException("ADD SYNC PACKET FOR DATAMAPS");
        });

        PayloadTypeRegistry.playS2C().register(RegistryDataMapSyncPayload.TYPE, RegistryDataMapSyncPayload.STREAM_CODEC);
    }
}
