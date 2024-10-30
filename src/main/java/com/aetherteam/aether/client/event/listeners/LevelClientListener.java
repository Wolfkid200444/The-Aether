package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;

public class LevelClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(LevelClientListener::onRenderLevelLast);
    }

    /**
     * @see LevelClientHooks#renderDungeonBlockOverlays(PoseStack, Camera, Frustum, Minecraft)
     */
    public static void onRenderLevelLast(WorldRenderContext context) {
        PoseStack poseStack = context.matrixStack();
        Camera camera = context.camera();
        Frustum frustum = context.frustum();
        Minecraft minecraft = Minecraft.getInstance();
        LevelClientHooks.renderDungeonBlockOverlays(poseStack, camera, frustum, minecraft);
    }
}
