package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.event.listeners.GuiListener;
import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("TAIL"))
    private void aetherFabric$onPostKeyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer == this.minecraft.getWindow().getWindow()) {
            AetherPlayerClientListener.onPress(key);
        }
    }

    @Definition(id = "bls", local = @Local(argsOnly = true, type = boolean[].class))
    @Definition(id = "screen", local = @Local(argsOnly = true, type = Screen.class))
    @Definition(id = "keyPressed", method = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z")
    @Expression("bls[0] = screen.keyPressed(?, ?, ?)")
    @Inject(method = "method_1454", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private static void aetherFabric$afterKeyPressedEvent(int code, Screen screen, boolean[] resultHack, int key, int scancode, int modifiers, CallbackInfo ci) {
        if(!resultHack[0]) GuiListener.onKeyPress(key, InputConstants.PRESS);
    }
}
