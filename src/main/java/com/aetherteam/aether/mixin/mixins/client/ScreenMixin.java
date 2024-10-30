package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.ScreenExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class ScreenMixin implements ScreenExtension {

    @Shadow
    @Nullable
    protected Minecraft minecraft;

    @Override
    public Minecraft getMinecraft() {
        return this.minecraft;
    }
}
