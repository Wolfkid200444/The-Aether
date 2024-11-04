package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.event.listeners.AudioListener;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow
    public abstract Connection getConnection();

    @Shadow
    public abstract RegistryAccess.Frozen registryAccess();

    @WrapOperation(method = "method_38542", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;getTag()Lnet/minecraft/nbt/CompoundTag;"))
    private CompoundTag aetherFabric$adjustLoadCall(ClientboundBlockEntityDataPacket instance, Operation<CompoundTag> original, @Local(argsOnly = true) BlockEntity blockEntity) {
        return !blockEntity.onDataPacket(this.getConnection(), instance, this.registryAccess()) ? original.call(instance) : new CompoundTag();
    }

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addEntity(Lnet/minecraft/world/entity/Entity;)V"))
    private void aetherFabric$onPlayerClone(ClientboundRespawnPacket packet, CallbackInfo ci) {
        AudioListener.onPlayerRespawn();
    }
}
