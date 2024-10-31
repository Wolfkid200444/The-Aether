package net.neoforged.neoforge.registries;

import com.aetherteam.aether.mixin.mixins.common.accessor.ConnectionAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerCommonPacketListenerImplAccessor;
import io.netty.util.AttributeKey;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.payload.KnownRegistryDataMapsPayload;
import net.neoforged.neoforge.network.payload.KnownRegistryDataMapsReplyPayload;
import net.neoforged.neoforge.network.payload.RegistryDataMapSyncPayload;
import net.neoforged.neoforge.network.tasks.RegistryDataMapNegotiation;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@ApiStatus.Internal
public class RegistryManager {
    private static Map<ResourceKey<Registry<?>>, Map<ResourceLocation, DataMapType<?, ?>>> dataMaps = Map.of();

    @Nullable
    public static <R> DataMapType<R, ?> getDataMap(ResourceKey<? extends Registry<R>> registry, ResourceLocation key) {
        final var map = dataMaps.get(registry);
        return map == null ? null : (DataMapType<R, ?>) map.get(key);
    }

    /**
     * {@return a view of all registered data maps}
     */
    public static Map<ResourceKey<Registry<?>>, Map<ResourceLocation, DataMapType<?, ?>>> getDataMaps() {
        return dataMaps;
    }

    public static void initDataMaps() {
        final Map<ResourceKey<Registry<?>>, Map<ResourceLocation, DataMapType<?, ?>>> dataMapTypes = new HashMap<>();
        RegisterDataMapTypesEvent.EVENT.invoker().registerMaps(new RegisterDataMapTypesEvent(dataMapTypes));
        dataMaps = new IdentityHashMap<>();
        dataMapTypes.forEach((key, values) -> dataMaps.put(key, Collections.unmodifiableMap(values)));
        dataMaps = Collections.unmodifiableMap(dataMapTypes);
    }

    public static final AttributeKey<Map<ResourceKey<? extends Registry<?>>, Collection<ResourceLocation>>> ATTRIBUTE_KNOWN_DATA_MAPS = AttributeKey.valueOf("neoforge:known_data_maps");

    @ApiStatus.Internal
    public static void handleKnownDataMapsReply(final KnownRegistryDataMapsReplyPayload payload, ServerConfigurationNetworking.Context context) {
        var channel = ((ConnectionAccessor) ((ServerCommonPacketListenerImplAccessor) context.networkHandler()).aetherFabric$connection()).aetherFabric$getChannel();
        channel.attr(RegistryManager.ATTRIBUTE_KNOWN_DATA_MAPS).set(payload.dataMaps());
        context.networkHandler().completeTask(RegistryDataMapNegotiation.TYPE);
    }

    public static void init() {
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
                handleSync(player, regOpt.get(), playerMaps.getOrDefault(registry, List.of()));
            });
        });

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (client) return;

            DataMapLoader.apply(registries);
        });
    }

    public static <T> void handleSync(ServerPlayer player, Registry<T> registry, Collection<ResourceLocation> attachments) {
        if (attachments.isEmpty()) return;
        final Map<ResourceLocation, Map<ResourceKey<T>, ?>> att = new HashMap<>();
        attachments.forEach(key -> {
            final var attach = RegistryManager.getDataMap(registry.key(), key);
            if (attach == null || attach.networkCodec() == null) return;
            att.put(key, registry.getDataMap(attach));
        });
        if (!att.isEmpty()) {
            PacketDistributor.sendToPlayer(player, new RegistryDataMapSyncPayload<>(registry.key(), att));
        }
    }
}
