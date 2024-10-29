package com.aetherteam.aether.mixin.mixins.client.fabric;

import com.aetherteam.aether.fabric.NetworkRegisterHelper;
import com.aetherteam.aether.fabric.networking.CommonPayloadContext;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(value = NetworkRegisterHelper.class, remap = false)
public interface NetworkRegisterHelperMixin {

    @Inject(method = "playToClient", at = @At("TAIL"), remap = false)
    private <T extends CustomPacketPayload> void aether$registerClientPacketHandlers(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec, BiConsumer<T, CommonPayloadContext> consumer, CallbackInfo ci) {
        ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
            consumer.accept(payload, new CommonPayloadContext(context.player(), context.responseSender()));
        });
    }
}
