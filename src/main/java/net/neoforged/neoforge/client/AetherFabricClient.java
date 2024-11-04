package net.neoforged.neoforge.client;

import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.network.payload.AdvancedAddEntityPayload;
import net.neoforged.neoforge.network.payload.KnownRegistryDataMapsPayload;
import net.neoforged.neoforge.network.payload.RegistryDataMapSyncPayload;
import net.neoforged.neoforge.registries.ClientRegistryManager;
import org.slf4j.Logger;

public class AetherFabricClient {

    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        // Data Map
        ClientPlayNetworking.registerGlobalReceiver(RegistryDataMapSyncPayload.TYPE, ClientRegistryManager::handleDataMapSync);
        ClientConfigurationNetworking.registerGlobalReceiver(KnownRegistryDataMapsPayload.TYPE, ClientRegistryManager::handleKnownDataMaps);
        //--
        ClientPlayNetworking.registerGlobalReceiver(AdvancedAddEntityPayload.TYPE, AetherFabricClient::handle);
    }

    public static void handle(AdvancedAddEntityPayload advancedAddEntityPayload, ClientPlayNetworking.Context context) {
        try {
            Entity entity = context.player().level().getEntity(advancedAddEntityPayload.entityId());
            if (entity instanceof IEntityWithComplexSpawn entityAdditionalSpawnData) {
                final RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(advancedAddEntityPayload.customPayload()), entity.registryAccess());
                try {
                    entityAdditionalSpawnData.readSpawnData(buf);
                } finally {
                    buf.release();
                }
            }
        } catch (Throwable t) {
            LOGGER.error("Failed to handle advanced add entity from server.", t);
            context.responseSender().disconnect(Component.translatable("aether_fabric.network.advanced_add_entity.failed", t.toString()));
        }
    }
}
