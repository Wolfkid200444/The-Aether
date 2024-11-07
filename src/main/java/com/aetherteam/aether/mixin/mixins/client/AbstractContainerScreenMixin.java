package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aetherfabric.pond.client.ScreenExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements ScreenExtension {

    @Shadow
    protected int leftPos;

    @Shadow
    protected int topPos;

    @Shadow
    public int imageWidth;

    @Shadow
    public int imageHeight;

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Override
    public int getGuiLeft() {
        return this.leftPos;
    }

    @Override
    public int getGuiTop() {
        return this.topPos;
    }

    @Override
    public int getXSize() {
        return this.imageWidth;
    }

    @Override
    public int getYSize() {
        return this.imageHeight;
    }

    @Override
    @Nullable
    public Slot getSlotUnderMouse() {
        return this.hoveredSlot;
    }
}
