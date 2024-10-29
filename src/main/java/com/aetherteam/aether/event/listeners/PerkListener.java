package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.PerkHooks;
import com.aetherteam.aether.perk.types.MoaSkins;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.entity.player.Player;

public class PerkListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> playerLoggedIn(handler.getPlayer()));
    }

    /**
     * @see PerkHooks#refreshPerks(Player)
     */
    public static void playerLoggedIn(Player player) {
        PerkHooks.refreshPerks(player);
        MoaSkins.registerMoaSkins(player.level());
    }
}
