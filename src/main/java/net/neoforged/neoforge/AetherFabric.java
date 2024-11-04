package net.neoforged.neoforge;

import com.aetherteam.aether.mixin.mixins.common.accessor.ConnectionAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerCommonPacketListenerImplAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.network.payload.AdvancedAddEntityPayload;
import net.neoforged.neoforge.network.payload.KnownRegistryDataMapsPayload;
import net.neoforged.neoforge.network.payload.KnownRegistryDataMapsReplyPayload;
import net.neoforged.neoforge.network.payload.RegistryDataMapSyncPayload;
import net.neoforged.neoforge.network.tasks.RegistryDataMapNegotiation;
import net.neoforged.neoforge.registries.DataMapLoader;
import net.neoforged.neoforge.registries.RegistryManager;

import java.util.List;

public class AetherFabric {

    public static void init() {
        // Data Map
        PayloadTypeRegistry.configurationS2C().register(KnownRegistryDataMapsPayload.TYPE, KnownRegistryDataMapsPayload.STREAM_CODEC);
        PayloadTypeRegistry.configurationC2S().register(KnownRegistryDataMapsReplyPayload.TYPE, KnownRegistryDataMapsReplyPayload.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(RegistryDataMapSyncPayload.TYPE, RegistryDataMapSyncPayload.STREAM_CODEC);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> handler.addTask(new RegistryDataMapNegotiation(handler)));

        ServerConfigurationNetworking.registerGlobalReceiver(KnownRegistryDataMapsReplyPayload.TYPE, RegistryManager::handleKnownDataMapsReply);

        RegistryManager.initDataMaps();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(DataMapLoader.ID, DataMapLoader::new);

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            RegistryManager.getDataMaps().forEach((registry, values) -> {
                final var regOpt = player.getServer().overworld().registryAccess().registry(registry);
                if (regOpt.isEmpty()) return;
                if (!ServerPlayNetworking.canSend(player, RegistryDataMapSyncPayload.TYPE)) return;
                var connection = ((ServerCommonPacketListenerImplAccessor) player.connection).aetherFabric$connection();
                // Note: don't send data maps over in-memory connections, else the client-side handling will wipe non-synced data maps.
                if (connection.isMemoryConnection()) return;
                final var playerMaps = ((ConnectionAccessor) connection).aetherFabric$getChannel().attr(RegistryManager.ATTRIBUTE_KNOWN_DATA_MAPS).get();
                if (playerMaps == null) return; // Skip gametest players for instance
                RegistryManager.handleSync(player, regOpt.get(), playerMaps.getOrDefault(registry, List.of()));
            });
        });

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (client) return;

            DataMapLoader.apply(registries);
        });

        //--

        PayloadTypeRegistry.playS2C().register(AdvancedAddEntityPayload.TYPE, AdvancedAddEntityPayload.STREAM_CODEC);
    }

}
