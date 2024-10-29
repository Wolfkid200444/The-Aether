package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.events.LevelEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void aetherFabric$levelTickEvents(ClientLevel instance, BooleanSupplier hasTimeLeft, Operation<Void> original) {
        LevelEvents.BEFORE.invoker().beforeTick(instance);

        original.call(instance, hasTimeLeft);

        LevelEvents.AFTER.invoker().afterTick(instance);
    }
}
