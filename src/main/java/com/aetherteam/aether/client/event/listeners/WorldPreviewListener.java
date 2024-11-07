package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.WorldPreviewHooks;
import com.aetherteam.aetherfabric.events.CancellableCallback;
import com.aetherteam.aetherfabric.client.events.LivingEntityRenderEvents;
import com.aetherteam.aetherfabric.client.events.PlayerRenderEvents;
import com.aetherteam.cumulus.fabric.OpeningScreenEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;

public class WorldPreviewListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        OpeningScreenEvents.POST.register((oldScreen, newScreen) -> {
            onGuiOpenLowest(newScreen);
            return null;
        });
        // GameRendererMixin.aetherFabric$wrapScreenRenderer -> WorldPreviewListener.onScreenRender
        WorldRenderEvents.LAST.register(context -> onRenderLevelLast());
        ClientTickEvents.END_CLIENT_TICK.register(client -> WorldPreviewListener.onClientTick());
        // CameraMixin.aetherFabric$beforeCameraSetup -> WorldPreviewListener.onCameraView
        //bus.addListener(WorldPreviewListener::onRenderOverlay);
        PlayerRenderEvents.BEFORE_RENDER.register((player, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> WorldPreviewListener.onRenderPlayer(renderer, callback));
        LivingEntityRenderEvents.BEFORE_RENDER.register((livingEntity, renderer, partialTick, poseStack, multiBufferSource, packedLight, callback) -> onRenderEntity(livingEntity, renderer, callback));
    }

    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    public static void onGuiOpenLowest(Screen newScreen) {
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    public static void onScreenRender(Screen screen, CancellableCallback callback) {
        if (WorldPreviewHooks.hideScreen(screen)) {
            callback.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld(RenderLevelStageEvent.Stage)
     */
    public static void onRenderLevelLast() {
        WorldPreviewHooks.renderMenuWithWorld();
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    public static void onClientTick() {
        WorldPreviewHooks.tickMenuWhenPaused();
    }

    /**
     * @see WorldPreviewHooks#angleCamera(double)
     */
    public static void onCameraView(double partialTick) {
        WorldPreviewHooks.angleCamera(partialTick);
    }
//
//    /**
//     * @see WorldPreviewHooks#hideOverlays()
//     */
//    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
//        if (WorldPreviewHooks.hideOverlays()) {
//            event.setCanceled(true);
//        }
//    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderPlayer(PlayerRenderer renderer, CancellableCallback callback) {
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        if (hide) {
            callback.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

    /**
     * @see WorldPreviewHooks#shouldHideEntity(Entity)
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderEntity(Entity entity, EntityRenderer<?> renderer, CancellableCallback callback) {
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        if (hide) {
            callback.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }
}
