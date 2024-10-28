package com.aetherteam.aether.event.listeners.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherTimeAttachment;
import com.aetherteam.aether.event.hooks.CapabilityHooks;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.entity.player.Player;

/**
 * Listener for Forge events to handle syncing the data for {@link AetherTimeAttachment}.
 */
public class AetherTimeListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> onLogin(handler.getPlayer()));
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> onChangeDimension(player));
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> onPlayerRespawn(newPlayer));
    }

    public static void onLogin(Player player) {
        CapabilityHooks.AetherTimeHooks.login(player);
    }

    public static void onChangeDimension(Player player) {
        CapabilityHooks.AetherTimeHooks.changeDimension(player);
    }

    public static void onPlayerRespawn(Player player) {
        CapabilityHooks.AetherTimeHooks.respawn(player);
    }
}
