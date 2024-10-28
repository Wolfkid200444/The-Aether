package com.aetherteam.aether.fabric.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CommonPayloadContext(Player player, PacketSender responseSender) implements IPayloadContext {
}
