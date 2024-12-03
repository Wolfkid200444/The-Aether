package com.aetherteam.aetherfabric.networking;

import com.aetherteam.aetherfabric.network.handling.IPayloadContext;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.world.entity.player.Player;

public record CommonPayloadContext(Player player, PacketSender responseSender) implements IPayloadContext {
}
