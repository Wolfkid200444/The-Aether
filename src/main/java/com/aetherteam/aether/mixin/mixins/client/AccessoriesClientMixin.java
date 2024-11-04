package com.aetherteam.aether.mixin.mixins.client;

import io.wispforest.accessories.client.AccessoriesClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AccessoriesClient.class)
public class AccessoriesClientMixin {
    @Inject(method = "lambda$init$13", at = @At("HEAD"), cancellable = true)
    private static void aetherFabric$fixSomethingPossibly(Minecraft client, boolean success, CallbackInfo ci) {
        if (!success) ci.cancel();
    }
}
