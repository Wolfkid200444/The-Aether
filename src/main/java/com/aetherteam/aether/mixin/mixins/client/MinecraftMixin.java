package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.events.AddPackFindersEvent;
import com.aetherteam.aether.fabric.events.LevelEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    @Final
    private PackRepository resourcePackRepository;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void aetherFabric$levelTickEvents(ClientLevel instance, BooleanSupplier hasTimeLeft, Operation<Void> original) {
        LevelEvents.BEFORE.invoker().beforeTick(instance);

        original.call(instance, hasTimeLeft);

        LevelEvents.AFTER.invoker().afterTick(instance);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;reload()V"))
    private void aetherFabric$addClientPackResources(GameConfig gameConfig, CallbackInfo ci) {
        AddPackFindersEvent.invokeEvent(PackType.CLIENT_RESOURCES, this.resourcePackRepository);
    }
}
