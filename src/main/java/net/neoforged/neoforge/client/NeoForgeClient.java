package net.neoforged.neoforge.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.payload.RegistryDataMapSyncPayload;
import net.neoforged.neoforge.registries.ClientRegistryManager;
import net.neoforged.neoforge.registries.RegistryManager;

import java.util.Collections;

public class NeoForgeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(RegistryDataMapSyncPayload.TYPE, ClientRegistryManager::handleDataMapSync);
    }
}
