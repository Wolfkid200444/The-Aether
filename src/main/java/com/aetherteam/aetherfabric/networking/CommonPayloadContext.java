package com.aetherteam.aetherfabric.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherfabric.network.handling.IPayloadContext;

public record CommonPayloadContext(Player player, PacketSender responseSender) implements IPayloadContext {
}