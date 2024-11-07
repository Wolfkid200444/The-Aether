package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.listeners.WorldPreviewListener;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    protected abstract void loadEffect(ResourceLocation resourceLocation);

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void aetherFabric$wrapScreenRenderer(Screen instance, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        WorldPreviewListener.onScreenRender(instance, callback);

        if(!callback.isCanceled()) original.call(instance, guiGraphics, mouseX, mouseY, partialTick);
    }

    @Inject(method = "checkEntityPostEffect", at = @At("TAIL"))
    private void aetherFabric$checkForEffect(Entity entity, CallbackInfo ci) {
        if (entity != null && entity.getType().equals(AetherEntityTypes.SUN_SPIRIT.get())) {
            this.loadEffect(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shaders/post/sun_spirit.json"));
        }
    }
}
