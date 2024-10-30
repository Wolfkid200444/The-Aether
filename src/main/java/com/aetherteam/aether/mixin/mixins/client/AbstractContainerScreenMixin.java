package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.ScreenExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements ScreenExtension {

    @Shadow
    protected int leftPos;

    @Shadow
    protected int topPos;

    @Override
    public int getGuiLeft() {
        return this.leftPos;
    }

    @Override
    public int getGuiTop() {
        return this.topPos;
    }
}
