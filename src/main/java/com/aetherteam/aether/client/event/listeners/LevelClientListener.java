package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;

public class LevelClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        bus.addListener(LevelClientListener::onRenderLevelLast);
    }

    /**
     * @see LevelClientHooks#renderDungeonBlockOverlays(RenderLevelStageEvent.Stage, PoseStack, Camera, Frustum, Minecraft)
     */
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Frustum frustum = event.getFrustum();
        Minecraft minecraft = Minecraft.getInstance();
        LevelClientHooks.renderDungeonBlockOverlays(stage, poseStack, camera, frustum, minecraft);
    }
}
